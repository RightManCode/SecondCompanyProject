package com.wishland.www.xinwanhaotest.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.wishland.www.xinwanhaotest.controller.base.BaseRetrofit;
import com.wishland.www.xinwanhaotest.model.Model;
import com.wishland.www.xinwanhaotest.model.sp.UserSP;

import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by gerry on 2017/8/30.
 */

public class AppUtils {


    private static AppUtils instance;
    private boolean report;
    private List<UserEvent> info;
    private String host;
    private Context context;
    private String time;
    private String CRASH_SP = "CRASH_SP";
    private String CRASH = "CRASH";

    private AppUtils() {
    }

    public static AppUtils getInstance() {
        if (instance == null)
            instance = new AppUtils();
        return instance;
    }

    public void init(Context context) {
        this.context = context;
        info = new ArrayList<>();
        time = getTime();
        String string = context.getSharedPreferences(CRASH_SP, 0).getString(CRASH, "");
        if (!TextUtils.isEmpty(string)) {
            updateInfo(string);
        }
    }

    public void onCrash(Exception e) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(CRASH_SP, 0);
        info.add(new UserEvent(getTime(), "onCrash", e.getMessage()));
        sharedPreferences.edit().putString(CRASH, new Gson().toJson(info)).apply();
    }

    private String getTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        return formatter.format(curDate);
    }

    public void setHost(String host) {
        this.host = host;
        checkIsNeedUpdateDebugInfo(host);
    }

    public void onEnter(Class<?> cls) {
        info.add(new UserEvent(getTime(), "onEnterActivity", cls.getName()));
    }

    public void onQuit(Class<?> cls) {
        info.add(new UserEvent(getTime(), "onQuitActivity", cls.getName()));
    }

    public void onClick(String infos) {
        info.add(new UserEvent(getTime(), "onClick", infos));
        isNeedUpdata();
    }

    public void onClick(Map<String, Object> infos) {
        onClick(infos);
    }

    public void onEvent(String eventName, String infos) {
        info.add(new UserEvent(getTime(), eventName, infos));
        isNeedUpdata();
    }

    public void onEvent(String eventName, Map<String, Object> infos) {
        onEvent(eventName, infos);
    }

    public void onRequest(String url, Map<String, Object> params) {
        info.add(new UserEvent(getTime(), "onRequest", new AppRequest(url, params)));
        isNeedUpdata();
    }

    public void onRequest(String url, FormBody params) {
        HashMap<String, Object> mapParams = new HashMap<>();
        for (int i = 0; i < params.size(); i++) {
            mapParams.put(params.encodedName(i), params.value(i));
        }
        info.add(new UserEvent(getTime(), "onRequest", new AppRequest(url, mapParams)));
        isNeedUpdata();
    }

    public void onRespons(String s) {
        info.add(new UserEvent(getTime(), "onRespons", s));
        isNeedUpdata();
    }


    private void isNeedUpdata() {
        Log.e("AppUtils", "info.size():" + info.size());
        if (info.size() >= 10) {
            if (report && !TextUtils.isEmpty(host)) {
                updateInfo(new Gson().toJson(info));
            }
            info.clear();
        }
    }

    private Map<String, String> getDefaultInfo() {
        final Map<String, String> info = new HashMap<>();
        try {
            info.put("phone_model", Build.MODEL);//设备型号
            info.put("phone_brand", Build.BRAND);//设备厂商
            info.put("phone_system_version", Build.VERSION.RELEASE);//系统版本
            info.put("app_version", PhoneInfoUtils.getAPPVersion(context));//app版本
            info.put("phone_operator", PhoneInfoUtils.getOperatorType(context));//运营商
            info.put("net_type", PhoneInfoUtils.getNetType(context));//网络类型
            info.put("host", host);//网络类型
            info.put("open_app_time", time);//本次打开或使用APP的时间
        } catch (Exception e) {
            e.printStackTrace();
        }
        return info;
    }

    private void checkIsNeedUpdateDebugInfo(String host) {
        OkHttpClient okhttp = BaseRetrofit.getOkhttp();
        Map<String, String> paramsPro = NetUtils.getParamsPro();
        FormBody.Builder builder = new FormBody.Builder();
        for (String s :
                paramsPro.keySet()) {
            builder
                    .add(s, paramsPro.get(s));
        }
        FormBody build = builder.build();
        Request requestPost = new Request.Builder()
                .url(host + "/index.php?vcode/config")
                .post(build)
                .build();
        Log.e("AppUtils", build.toString());
        okhttp.newCall(requestPost).enqueue(new Callback() {


            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, final Response response) {
                try {
                    String string = response.body().string();
                    Log.e("AppUtils", "report_string:" + string);
                    JSONObject jsonObject = Model.getInstance().getJsonObject(string);
                    int status = jsonObject.optInt("status");
                    if (status == 200) {
                        report = jsonObject.optJSONObject("data").optBoolean("report");
                    } else {

                    }
                } catch (Exception e) {
                }

            }
        });
    }

    private void updateInfo(String s) {
        try {
            Map<String, String> defaultInfo = getDefaultInfo();
            defaultInfo.put("userEvent", s.substring(1, s.length() - 1).replaceAll("\\\\\"", "\""));
            try {
                UserSP userSP = Model.getInstance().getUserSP();
                if (userSP.getInt(UserSP.LOGIN_SUCCESS) != -1) {
                    defaultInfo.put("userId", userSP.getString(UserSP.LOGIN_USERNAME));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            OkHttpClient okhttp = BaseRetrofit.getOkhttp();
            Map<String, String> map = new HashMap<>();
            map.put("os", "Android");
            map.put("key", Build.MODEL);
            map.put("info", new Gson().toJson(defaultInfo));
            Map<String, String> paramsPro = NetUtils.getParamsPro(map);
            FormBody.Builder builder = new FormBody.Builder();
            for (String s1 :
                    paramsPro.keySet()) {
                builder
                        .add(s1, paramsPro.get(s1));
            }
            FormBody build = builder.build();
            Request requestPost = new Request.Builder()
                    .url(host + "/index.php?spy/report")
                    .post(build)
                    .build();
            okhttp.newCall(requestPost).enqueue(new Callback() {


                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, final Response response) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class UserEvent {
        private String time;
        private String eventName;
        private Object eventInfos;

        public UserEvent(String time, String eventName, Object eventInfos) {
            this.time = time;
            this.eventName = eventName;
            this.eventInfos = eventInfos;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getEventName() {
            return eventName;
        }

        public void setEventName(String eventName) {
            this.eventName = eventName;
        }

        public Object getEventInfos() {
            return eventInfos;
        }

        public void setEventInfos(Object eventInfos) {
            this.eventInfos = eventInfos;
        }
    }

    private class AppRequest {
        private String url;
        private Map<String, Object> params;

        public AppRequest(String url, Map<String, Object> params) {
            this.url = url;
            this.params = params;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public Map<String, Object> getParams() {
            return params;
        }

        public void setParams(Map<String, Object> params) {
            this.params = params;
        }
    }
}
