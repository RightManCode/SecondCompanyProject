package com.wishland.www.xinwanhaotest.utils;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.AbsCallback;
import com.wishland.www.xinwanhaotest.api.HttpApiInstance;
import com.wishland.www.xinwanhaotest.controller.base.BaseRetrofit;
import com.wishland.www.xinwanhaotest.model.Model;
import com.wishland.www.xinwanhaotest.model.bean.DomainBean;
import com.wishland.www.xinwanhaotest.model.bean.DomainChangeBean;
import com.wishland.www.xinwanhaotest.view.MyApplication;
import com.wishland.www.xinwanhaotest.view.activity.MainActivity;
import com.wishland.www.xinwanhaotest.view.activity.WelcomeActivity;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
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
 * Created by admin on 2017/9/9.
 */

public class RequestDomainUtil {
    private final static String DOMAINURL = "http://119.9.107.44:9999/getDomainMapper";

    private final static String UPDATEDOMAINURL = "http://119.9.107.44:9999/updateDomainMapper";

    private static List<DomainBean.DataBean> domainList;

    private static DomainBean domainBean;

    private static int count = 0;

    private static List<DomainChangeBean> changes = new ArrayList<>();

    private static Activity context;

    public static void initDomain(Activity c) {
        context = c;
        getDomainList();
    }

    private static List<DomainBean.DataBean> getDomainList() {
        Map<String, Object> params = setParams();
        OkGo.post(DOMAINURL).upString(new Gson().toJson(params))
                .execute(new AbsCallback<String>() {
                    @Override
                    public String convertSuccess(Response response) throws Exception {
                        return response.body().string();
                    }

                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.e("cww", "请求域名列表成功！");
                        domainBean = new Gson().fromJson(s, DomainBean.class);
                        domainList = domainBean.getData();
                        requestHostByUrl(count);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        //报错处理
                        Log.e("cww", "请求域名列表异常！" + e.getMessage());
                        exitApp();
                    }
                });
        return domainList;
    }

    private static Map<String, Object> setParams() {
        Map<String, Object> params = new HashMap<>();
        Map<String, String> p = new HashMap<>();
        p.put("code", "whh");
        p.put("domainState", "0");
        params.put("uri", "/getDomainMapper");
        params.put("token", "");
        params.put("paramData", p);
        return params;
    }

    private static void setUpdateParams() {
        Map<String, Object> paramData = new HashMap<>();
        paramData.put("changes", changes);
        Map<String, Object> params = new HashMap<>();
        params.put("uri", "/updateDomainMapper");
        params.put("token", "");
        params.put("paramData", paramData);
        OkGo.post(UPDATEDOMAINURL).upString(new Gson().toJson(params)).execute(new AbsCallback<String>() {
            @Override
            public String convertSuccess(Response response) throws Exception {
                return response.body().string();
            }

            @Override
            public void onSuccess(String s, Call call, Response response) {
                Log.e("cww", "提交域名请求信息" + s);
            }
        });
    }

    private static void requestHostByUrl(final int index) {
        if (domainList == null || index == domainList.size()) {
            setUpdateParams();
            exitApp();
        } else {
            Log.e("cww", "请求" + index);
            verifyHost(domainList.get(index).getDomain() + "/api/", index);
//            OkGo.post(domainList.get(index).getDomain() + "/api/").params("app_id", "1513775768").execute(new AbsCallback<String>() {
//                @Override
//                public String convertSuccess(Response response) throws Exception {
//                    return response.body().string();
//                }
//
//                @Override
//                public void onSuccess(String s, Call call, Response response) {
//                    try {
//                        LogUtil.e("域名请求成功：" + s + "");
//                        JSONObject jsonObject = Model.getInstance().getJsonObject(s);
//                        int status = jsonObject.optInt("status");
//                        if (status == 200) {
//                            String s1 = jsonObject.optJSONObject("data").optString("url");
//                            verifyHost(s1);
//                        } else {
//                            requestHostByUrl(++count);
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                        requestHostByUrl(++count);
//                    }
//                }
//
//                @Override
//                public void onError(Call call, Response response, Exception e) {
//                    super.onError(call, response, e);
//                    Log.e("cww", "请求数据报错:" + e.getMessage());
//                    requestHostByUrl(++count);
//                }
//            });
        }
    }

    private static void exitApp() {
        ToastUtil.showUI(context, "初始化应用失败,请关闭重新尝试...");
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        //System.exit(0);
    }

    private static void verifyHost(final String host, final int index) {
        OkHttpClient okhttp = BaseRetrofit.getOkhttp();
        HashMap<String, String> 小杨SB = new HashMap();
        小杨SB.put("code", "666777");
        Map<String, String> paramsPro = NetUtils.getParamsPro(小杨SB);
        FormBody.Builder builder = new FormBody.Builder();
        for (String s :
                paramsPro.keySet()) {
            builder
                    .add(s, paramsPro.get(s));
        }
        FormBody build = builder.build();
        Request requestPost = new Request.Builder()
                .url(host + "/index.php?vcode/validate")
                .post(build)
                .build();
        okhttp.newCall(requestPost).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                LogUtil.e("域名请求失败：" + e.getMessage());
                DomainChangeBean changeBean = new DomainChangeBean();
                changeBean.setId(domainList.get(index).getId());
                changeBean.setDomainState("1");
                changeBean.setDomain(domainList.get(index).getDomain());
                changeBean.setSystemCode(domainList.get(index).getSystemCode());
                changes.add(changeBean);
                requestHostByUrl(++count);
            }

            @Override
            public void onResponse(Call call, final Response response) {
                try {
                    String string = response.body().string();
                    JSONObject jsonObject = Model.getInstance().getJsonObject(string);
                    int status = jsonObject.optInt("status");
                    if (status == 200) {
                        String s1 = jsonObject.optJSONObject("data").optString("validate");
                        StringBuffer real = new StringBuffer();
                        for (int i = 0; i < s1.length() / 2; i++) {
                            real.append(s1.charAt(i * 2));
                        }
                        String s2 = real.toString();
                        String s = MD5Utils.toMD5("666777");

                        if (TextUtils.equals(s, s2)) {
                            WelcomeActivity.observable = BaseRetrofit.getInstance().getObservable(new HttpApiInstance(host));
                            AppUtils.getInstance().setHost(host);
                            setUpdateParams();
                            Intent intent = new Intent(MyApplication.Mcontext, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            MyApplication.Mcontext.startActivity(intent);

                        } else {
                            DomainChangeBean changeBean = new DomainChangeBean();
                            changeBean.setId(domainList.get(index).getId());
                            changeBean.setDomainState("1");
                            changeBean.setDomain(domainList.get(index).getDomain());
                            changeBean.setSystemCode(domainList.get(index).getSystemCode());
                            changes.add(changeBean);
                            requestHostByUrl(++count);
                        }
                    } else {

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    DomainChangeBean changeBean = new DomainChangeBean();
                    changeBean.setId(domainList.get(index).getId());
                    changeBean.setDomainState("1");
                    changeBean.setDomain(domainList.get(index).getDomain());
                    changeBean.setSystemCode(domainList.get(index).getSystemCode());
                    changes.add(changeBean);
                    requestHostByUrl(++count);
                    e.printStackTrace();
                }
            }
        });
    }
}
