package com.wishland.www.xinwanhaotest.view.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.wishland.www.xinwanhaotest.R;

import com.wishland.www.xinwanhaotest.model.Model;
import com.wishland.www.xinwanhaotest.model.sp.AccountDataSP;
import com.wishland.www.xinwanhaotest.model.sp.UserSP;
import com.wishland.www.xinwanhaotest.utils.ActivityManager;
import com.wishland.www.xinwanhaotest.utils.AppUtils;
import com.wishland.www.xinwanhaotest.utils.CodeUtils;
import com.wishland.www.xinwanhaotest.utils.LogUtil;
import com.wishland.www.xinwanhaotest.utils.NetUtils;
import com.wishland.www.xinwanhaotest.utils.ToastUtil;
import com.wishland.www.xinwanhaotest.utils.ToolsMthode;
import com.wishland.www.xinwanhaotest.utils.Utils_time;
import com.wishland.www.xinwanhaotest.view.customgridview.EmptyLayout;
import com.zhy.autolayout.AutoLayoutActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import rx.Subscriber;

/**
 * Created by Administrator on 2017/4/19.
 * 注册页面
 */

public class SignInActivity extends AutoLayoutActivity {

    @BindView(R.id.signin_edit_username)
    EditText signinEditUsername;
    @BindView(R.id.sign_edit_passwork)
    EditText signEditPasswork;
    @BindView(R.id.sign_edit_twopasswork)
    EditText signEditTwopasswork;
    @BindView(R.id.sign_edit_person_name)
    EditText signEditPersonName;
    @BindView(R.id.sign_edit_code)
    EditText signEditCode;
    @BindView(R.id.signin_button)
    Button signinButton;
    @BindView(R.id.sign_button_havausername)
    Button signButtonHavausername;
    @BindView(R.id.signin_refresh_code_image_SR)
    ImageView signinRefreshCodeImageSR;
    @BindView(R.id.empty_layout)
    EmptyLayout emptyLayout;

    private CodeUtils codeutils;
    private Map<String, String> map;
    private int five = 5;
    private Model instance;
    private String username;
    private String password;
    private String personname;
    private UserSP userSP;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signinlayout);
        ButterKnife.bind(this);
        ActivityManager.getActivityManager().addActivity(this);
        init();
    }

    private void init() {
        codeutils = new CodeUtils();
        map = new HashMap();
        instance = Model.getInstance();
        userSP = instance.getUserSP();
        requestCode();
    }

    @OnClick({R.id.top_fanhui, R.id.signin_button, R.id.sign_button_havausername, R.id.signin_refresh_code_image_SR})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.top_fanhui:
                finish();
                break;
            case R.id.signin_button:
                emptyLayout.showLoading();
                putInSignIn();
                break;
            case R.id.sign_button_havausername:
                finish();
                break;
            case R.id.signin_refresh_code_image_SR:
                requestCode();
                break;
        }
    }

    private void requestCode() {
        String token = userSP.getToken(UserSP.LOGIN_TOKEN);
        instance.apiCode(token, NetUtils.getParamsPro().get("signature"), new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {
                AppUtils.getInstance().onRespons("验证码请求完成");
                LogUtil.e("验证码请求完成");
            }

            @Override
            public void onError(Throwable e) {
                AppUtils.getInstance().onRespons("验证码请求异常");
                LogUtil.e("验证码请求异常：" + e.getMessage());
            }

            @Override
            public void onNext(ResponseBody code) {
                try {
                    String string = code.string();

                    JSONObject json = instance.getJsonObject(string);
                    int status = json.optInt("status");
                    instance.setToken_SP(json.optString("token"));
                    if (status == 200) {
                        AppUtils.getInstance().onRespons("验证码请求成功");
                        JSONObject jsonObject = json.optJSONObject("data");
                        final String code1 = jsonObject.optInt("code") + "";
                        Bitmap bitmap = codeutils.createBitmap(code1);
                        signinRefreshCodeImageSR.setImageBitmap(bitmap);
                    } else {
                        AppUtils.getInstance().onRespons("验证码请求失败--->网络异常");
                        ToastUtil.showShort(SignInActivity.this, "网络异常");
                    }

                    LogUtil.e("验证码请求成功");

                } catch (IOException e) {
                    AppUtils.getInstance().onRespons("验证码请求失败：" + e.getMessage());
                    e.printStackTrace();
                } catch (JSONException e) {
                    AppUtils.getInstance().onRespons("验证码请求失败：" + e.getMessage());
                    e.printStackTrace();
                }
            }
        });
    }

    private void putInSignIn() {
        username = signinEditUsername.getText().toString().trim();
        password = signEditPasswork.getText().toString().trim();
        String twpassword = signEditTwopasswork.getText().toString().trim();
        personname = signEditPersonName.getText().toString().trim();
        String code = signEditCode.getText().toString().trim();
        String seconds = Utils_time.getSeconds();
        String token = userSP.getToken(UserSP.LOGIN_TOKEN);

        if (ToolsMthode.judgeUsername(username)) {
            if (ToolsMthode.judgePasswork(password, five) && ToolsMthode.judgePasswork(twpassword, five)) {
                if (password.equals(twpassword)) {

                    if (map.size() != 0) {
                        map.clear();
                    }

                    map.put("username", username);
                    map.put("rpassword", password);
                    map.put("realName", personname);
                    map.put("code", code);
                    map.put("time", seconds);

                    instance.apiSignIn(username, password, personname, code, token, NetUtils.getParamsPro(map).get("signature"), new Subscriber<ResponseBody>() {
                        @Override
                        public void onCompleted() {
                            AppUtils.getInstance().onRespons("注册完成");
                            LogUtil.e("注册完成");
                            emptyLayout.hide();
                        }

                        @Override
                        public void onError(Throwable e) {
                            AppUtils.getInstance().onRespons("注册异常：" + e.getMessage());
                            emptyLayout.hide();
                            LogUtil.e("注册异常：" + e.getMessage());
                        }

                        @Override
                        public void onNext(ResponseBody json) {
                            emptyLayout.hide();

                            try {
                                String string = json.string();
                                JSONObject jsonObject = instance.getJsonObject(string);
                                instance.setToken_SP(jsonObject.optString("token"));
                                int status = jsonObject.optInt("status");
                                if (status == 200) {
                                    AppUtils.getInstance().onRespons("注册成功");
                                    ToastUtil.showShort(SignInActivity.this, "注册成功");
                                    deleteUserSP();
                                } else {
                                    String error = jsonObject.optString("errorMsg");
                                    AppUtils.getInstance().onRespons("注册异常：");
                                    ToastUtil.showShort(SignInActivity.this, error);
                                }
                                LogUtil.e("注册成功");
                            } catch (IOException e) {
                                AppUtils.getInstance().onRespons("注册异常：" + e.getMessage());
                                e.printStackTrace();
                            } catch (JSONException e) {
                                AppUtils.getInstance().onRespons("注册异常：" + e.getMessage());
                                e.printStackTrace();
                            }
                        }
                    });
                } else {
                    emptyLayout.hide();
                    ToastUtil.showShort(SignInActivity.this, "两次密码不一致，请重新输入.");
                }

            } else {
                emptyLayout.hide();
                ToastUtil.showShort(SignInActivity.this, "密码格式错误,请检查密码.");

            }
        } else {
            emptyLayout.hide();
            ToastUtil.showShort(SignInActivity.this, "用户名格式错误,用户名错误.");
        }

    }

    public void deleteUserSP() {
        ActivityManager.getActivityManager().deleteAllActivity();
        LogUtil.e("栈的大小：" + ActivityManager.getActivityManager().getStackCount());
        userSP.setSuccess(UserSP.LOGIN_SUCCESS, 1);
        userSP.setUserName(UserSP.LOGIN_USERNAME, username);
        userSP.setCheckedSP(UserSP.LOGIN_CHECKED, false);
        instance.getAccountDataSP().setA_User_Name(AccountDataSP.ACCOUNT_USER_NAME, personname);

        Intent intent = new Intent(SignInActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.getActivityManager().deleteActivity(this);
    }


}
