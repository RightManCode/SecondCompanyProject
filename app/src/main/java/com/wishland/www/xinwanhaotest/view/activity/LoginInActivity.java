package com.wishland.www.xinwanhaotest.view.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.wishland.www.xinwanhaotest.R;


import com.wishland.www.xinwanhaotest.api.BaseApi;
import com.wishland.www.xinwanhaotest.model.Model;
import com.wishland.www.xinwanhaotest.model.bean.LISuccess;
import com.wishland.www.xinwanhaotest.model.sp.UserSP;
import com.wishland.www.xinwanhaotest.utils.ActivityManager;
import com.wishland.www.xinwanhaotest.utils.AppUtils;
import com.wishland.www.xinwanhaotest.utils.LogUtil;
import com.wishland.www.xinwanhaotest.utils.MD5Utils;
import com.wishland.www.xinwanhaotest.utils.NetUtils;
import com.wishland.www.xinwanhaotest.utils.ToastUtil;
import com.wishland.www.xinwanhaotest.utils.ToolsMthode;
import com.wishland.www.xinwanhaotest.view.customgridview.EmptyLayout;
import com.zhy.autolayout.AutoLayoutActivity;

import org.greenrobot.eventbus.EventBus;
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

import static com.wishland.www.xinwanhaotest.model.sp.UserSP.LOGIN_CHECKED;
import static com.wishland.www.xinwanhaotest.model.sp.UserSP.LOGIN_PASSWORD;
import static com.wishland.www.xinwanhaotest.model.sp.UserSP.LOGIN_USERNAME;

/**
 * Created by Administrator on 2017/4/18.
 * 登录页面
 */

public class LoginInActivity extends AutoLayoutActivity {
    @BindView(R.id.login_edit_username)
    EditText loginEditusername;
    @BindView(R.id.login_edit_passwork)
    EditText loginEditpasswork;
    @BindView(R.id.login_loginin_button)
    Button loginLogininButton;
    @BindView(R.id.login_no_signin)
    Button loginNoSignin;
    @BindView(R.id.login_no_passwork)
    Button loginNoPasswork;
    @BindView(R.id.login_checkbox_button)
    CheckBox login_checkbox_button;
    @BindView(R.id.empty_layout)
    EmptyLayout emptyLayout;
    @BindView(R.id.backbutton)
    Button backButton;

    private String username;
    private String password;
    private Intent intent;
    private Model instance;
    private UserSP userSP;
    private Map<String, String> map;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.longininlayout);
        ButterKnife.bind(this);
        ActivityManager.getActivityManager().addActivity(this);
        init();
    }

    private void init() {
        map = new HashMap<>();
        instance = Model.getInstance();
        userSP = instance.getUserSP();
        setSPUser();
    }

    private void setSPUser() {
        boolean setcheck = userSP.getBoolean(LOGIN_CHECKED);
        loginEditusername.setText(userSP.getString(LOGIN_USERNAME));
        if (setcheck) {
            login_checkbox_button.setChecked(setcheck);
            loginEditpasswork.setText(userSP.getString(LOGIN_PASSWORD));
        } else {
            deleteUserSP();
        }

    }

    public void getEditText() {
        username = loginEditusername.getText().toString().trim();
        password = loginEditpasswork.getText().toString().trim();
    }

    @OnClick({R.id.login_loginin_button, R.id.login_no_signin, R.id.login_no_passwork, R.id.backbutton})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_loginin_button:
                getEditText();
                if (ToolsMthode.judgeUsername(username)) {
                    if (ToolsMthode.judgePasswork(password, 5)) {
                        login();
                    } else {
                        ToastUtil.showShort(this, "密码格式错误,请重新输入.");
                    }
                } else {
                    ToastUtil.showShort(this, "用户名格式错误,请重新输入.");
                }

                break;
            case R.id.login_no_signin:
                intent = new Intent(LoginInActivity.this, SignInActivity.class);
                startActivity(intent);
                break;
            case R.id.login_no_passwork:
                intent = new Intent(BaseApi.NEWHTML);
                intent.putExtra(BaseApi.HTML5DATA, BaseApi.CustomHtml5);
                startActivity(intent);
                break;
            case R.id.backbutton:
                this.finish();
                break;
        }
    }


    /**
     * 联网登录
     */
    private void login() {
        //默认PopupWindow中的登录按钮隐藏
        emptyLayout.showLoading();
        String token = userSP.getToken(UserSP.LOGIN_TOKEN);
        final boolean checked = login_checkbox_button.isChecked();
        String sps = MD5Utils.toMD5(password);
        if (map.size() != 0) {
            map.clear();
        }
        map.put("username", username);
        map.put("password", sps);

        instance.api(username, sps, token, NetUtils.getParamsPro(map).get("signature"), new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {
                AppUtils.getInstance().onRespons("login请求完成");
                LogUtil.e("loginin请求完成");
                emptyLayout.hide();
            }

            @Override
            public void onError(Throwable e) {
                AppUtils.getInstance().onRespons("login请求失败");
                LogUtil.e("loginin请求失败" + e.toString());
                ToastUtil.showUI(LoginInActivity.this, "网络异常...");
                emptyLayout.hide();
                deleteUserSP();
            }

            @Override
            public void onNext(ResponseBody json) {
                try {
                    String string = json.string();
                    JSONObject jsonObject = instance.getJsonObject(string);
                    instance.setToken_SP(jsonObject.optString("token"));
                    int status = jsonObject.optInt("status");
                    AppUtils.getInstance().onRespons("login请求token");
                    LogUtil.e("loginin请求token:" + string);
                    if (status == 200) {
                        String stdy = jsonObject.optJSONObject("data").optString("status");
                        EventBus.getDefault().post(new LISuccess(stdy));
                        loginInSuccess(checked);
                    } else {
                        loginEditpasswork.setText("");
                        String errorMsg = jsonObject.optString("errorMsg");
                        ToastUtil.showUI(LoginInActivity.this, errorMsg);
                        deleteUserSP();
                    }
                } catch (IOException e) {
                    AppUtils.getInstance().onRespons("login" + e.getMessage());
                    e.printStackTrace();
                } catch (JSONException e) {
                    AppUtils.getInstance().onRespons("login" + e.getMessage());
                    e.printStackTrace();
                }
            }
        });
    }

    private void loginInSuccess(boolean checked) {
        if (checked) { //判断当前checked是否选中
            userSP.setCheckedSP(LOGIN_CHECKED, true);
            userSP.setPassWord(LOGIN_PASSWORD, password);
        } else {
            deleteUserSP();
        }
        userSP.setUserName(LOGIN_USERNAME, username);
        userSP.setSuccess(UserSP.LOGIN_SUCCESS, 1);
        Intent intent = new Intent();
        intent.putExtra("USERNAME", username);
        setResult(RESULT_OK, intent);
        finish();
    }


    public void deleteUserSP() {
        userSP.setCheckedSP(LOGIN_CHECKED, false);
        userSP.setPassWord(LOGIN_PASSWORD, "");

        loginEditpasswork.setText("");
        login_checkbox_button.setChecked(false);
    }

    /**
     * 退出删除当前activity
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.getActivityManager().deleteActivity(this);
    }
}
