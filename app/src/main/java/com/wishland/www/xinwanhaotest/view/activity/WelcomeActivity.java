package com.wishland.www.xinwanhaotest.view.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;


import com.wishland.www.xinwanhaotest.R;
import com.wishland.www.xinwanhaotest.api.ApiAddress;
import com.wishland.www.xinwanhaotest.api.HttpApiInstance;
import com.wishland.www.xinwanhaotest.controller.base.BaseRetrofit;
import com.wishland.www.xinwanhaotest.model.Model;
import com.wishland.www.xinwanhaotest.utils.ActivityManager;
import com.wishland.www.xinwanhaotest.utils.AppUtils;
import com.wishland.www.xinwanhaotest.utils.LogUtil;
import com.wishland.www.xinwanhaotest.utils.MD5Utils;
import com.wishland.www.xinwanhaotest.utils.NetUtils;
import com.wishland.www.xinwanhaotest.utils.RequestDomainUtil;
import com.wishland.www.xinwanhaotest.utils.ToastUtil;
import com.wishland.www.xinwanhaotest.utils.UtilTools;
import com.zhy.autolayout.AutoLayoutActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;


/**
 * Created by Administrator on 2017/7/30.
 */

@RuntimePermissions
public class WelcomeActivity extends AutoLayoutActivity {

    private List<String> list;
    private int count = 0;
    public static ApiAddress observable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //判断是否有网络
        if (!UtilTools.isNetworkAvalible(this)) {
            setContentView(R.layout.view_nonetwork);
            ToastUtil.showUI(this, "网络异常,请检查设置！");
            return;
        }
        setContentView(R.layout.welcome);
        ButterKnife.bind(this);
        WelcomeActivityPermissionsDispatcher.doCallWithCheck(this);
        ActivityManager.getActivityManager().addActivity(this);
    }

    private void init() {
        RequestDomainUtil.initDomain(this);
//        initurl();
//        requestUrl(count);
    }

    private void initurl() {
        list = new ArrayList();
        list.add("http://161.202.156.112/api/");
//        list.add("http://www.026961.com/");
//        list.add("http://www.026972.com/");
//        list.add("http://www.026963.com/");
//        list.add("http://www.026975.com/");
//        list.add("http://www.026957.com/");
    }

    private void requestUrl(final int ct) {
        if (count == list.size()) {
            ToastUtil.showUI(WelcomeActivity.this, "初始化应用失败,请关闭重新尝试...");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.exit(0);

        } else {
            verifyHost(list.get(ct));
//            LogUtil.e("请求：" + count);
//            OkHttpClient okhttp = BaseRetrofit.getOkhttp();
//
//            FormBody.Builder builder = new FormBody.Builder();
//
//            FormBody build = builder
//                    .add("app_id", "1126939")
//                    .build();
//            final String s = list.get(ct);
//            LogUtil.e("请求url：" + s);
//            Request requestPost = new Request.Builder()
//                    .url(s)
//                    .post(build)
//                    .build();
//            okhttp.newCall(requestPost).enqueue(new Callback() {
//
//                @Override
//                public void onFailure(Call call, IOException e) {
//                    LogUtil.e("域名请求失败：" + e.getMessage());
//                    requestUrl(++count);
//                }
//
//                @Override
//                public void onResponse(Call call, final Response response) {
//                    try {
//                        String string = response.body().string();
//                        JSONObject jsonObject = Model.getInstance().getJsonObject(string);
//                        int status = jsonObject.optInt("status");
//                        if (status == 200) {
//                            String s1 = jsonObject.optJSONObject("data").optString("url");
//                            verifyHost(s1);
//                        } else {
//                            requestUrl(++count);
//                        }
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        requestUrl(++count);
//                    }
//
//                }
//            });
        }
    }

    private void verifyHost(final String host) {
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
                requestUrl(++count);
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
                            observable = BaseRetrofit.getInstance().getObservable(new HttpApiInstance(host + "/"));
                            AppUtils.getInstance().setHost(host);
                            Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            requestUrl(++count);
                        }
                    } else {

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                    requestUrl(++count);
                }
            }
        });
    }

    @NeedsPermission({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void doCall() {
        init();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        WelcomeActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @OnShowRationale({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void showDialog(final PermissionRequest request) {
        request.proceed();
    }

    @OnPermissionDenied({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void refuse() {
        init();
    }

 /*   @OnNeverAskAgain({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})//被拒绝并且勾选了不再提醒
    void goSetting() {
        AskForPermission();
    }

    */

    /**
     * 被拒绝并且不再提醒,提示用户去设置界面重新打开权限
     *//*
    private void AskForPermission() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("当前应用缺少存储权限,请去设置界面打开\n打开之后按两次返回键可回到该应用哦");
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setPositiveButton("设置", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.setData(Uri.parse("package:" + getPackageName())); // 根据包名打开对应的设置界面
                startActivity(intent);
            }
        });
        builder.create().show();
    }*/
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.getActivityManager().deleteActivity(this);
    }

}
