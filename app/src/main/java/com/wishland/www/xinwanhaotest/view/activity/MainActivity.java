package com.wishland.www.xinwanhaotest.view.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.AbsCallback;
import com.umeng.analytics.MobclickAgent;
import com.wishland.www.xinwanhaotest.R;

import com.wishland.www.xinwanhaotest.api.BaseApi;
import com.wishland.www.xinwanhaotest.controller.adapter.LeftPopupWindows;
import com.wishland.www.xinwanhaotest.controller.fragment.fundsmanagement.FundsManagementPage;
import com.wishland.www.xinwanhaotest.controller.fragment.homepage.HomePage;
import com.wishland.www.xinwanhaotest.controller.fragment.line.LineConversionPage;
import com.wishland.www.xinwanhaotest.controller.fragment.message.MessagePage;
import com.wishland.www.xinwanhaotest.model.Model;
import com.wishland.www.xinwanhaotest.model.bean.AppUpdate;
import com.wishland.www.xinwanhaotest.model.bean.CustomerService;
import com.wishland.www.xinwanhaotest.model.bean.VersionHostBean;
import com.wishland.www.xinwanhaotest.model.sp.AccountDataSP;
import com.wishland.www.xinwanhaotest.model.sp.UserSP;
import com.wishland.www.xinwanhaotest.utils.ActivityManager;
import com.wishland.www.xinwanhaotest.utils.AppUtils;
import com.wishland.www.xinwanhaotest.utils.FrescoUtil;
import com.wishland.www.xinwanhaotest.utils.LogUtil;
import com.wishland.www.xinwanhaotest.utils.MD5Utils;
import com.wishland.www.xinwanhaotest.utils.NetUtils;
import com.wishland.www.xinwanhaotest.utils.PhoneInfoUtils;
import com.wishland.www.xinwanhaotest.utils.ToastUtil;
import com.wishland.www.xinwanhaotest.utils.UtilTools;
import com.zhy.autolayout.AutoLayoutActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import rx.Subscriber;

public class MainActivity extends AutoLayoutActivity {

    @BindView(R.id.main_linearLayout)
    LinearLayout mainLinearLayout;
    @BindView(R.id.top_imageview)
    ImageView topImageview;
    //    @BindView(R.id.signout)
//    TextView signout;
//    @BindView(R.id.setting_button)
//    ImageView settingButton;
//    @BindView(R.id.login_username)
//    TextView loginUsername;
    @BindView(R.id.main_betting_button)
    Button mainBettingButton;
    @BindView(R.id.main_fund_button)
    Button mainFundButton;
    @BindView(R.id.main_line_button)
    Button mainLineButton;
    @BindView(R.id.main_message_count_text)
    TextView mainMessageCountText;
    @BindView(R.id.main_customer_button)
    Button mainCustomerButton;
    @BindView(R.id.top_all_relativelayout)
    RelativeLayout topAllRelativelayout;
    @BindView(R.id.mian_Group_button)
    LinearLayout mianGroupButton;
    @BindView(R.id.signin)
    TextView signin;
    @BindView(R.id.loginin)
    TextView loginin;
    @BindView(R.id.main_login_side_bar)
    Button mainLoginSideBar;
    @BindView(R.id.main_messagepage_button)
    Button mainMessagepageButton;
    private FragmentTransaction fragmentTransaction;
    private HomePage homepage;                   //主页
    private FundsManagementPage fundsmanagement; //资金管理
    private LineConversionPage lineconversion;   //额度转换
    private MessagePage messagepage;             //信息中心
    private static final int HOMEPAGE = 0;
    private static final int FUNDSMANAGEMENT = 1;
    private static final int LINECONVERSION = 2;
    private static final int MESSAGEPAGE = 3;
    private static final String SERVICE = "service";//线上客服
    // ba7f3e0c42377123a384779f0c64924f
    // 3da17c02d49a0b40186ffb09005cb8b2
    private static final String UPDATAAPKCODE = "ba7f3e0c42377123a384779f0c64924f";
    private int nowcurrent = HOMEPAGE;
    private Intent intent;
    private Model instance;
    private UserSP userSP;
    private File file;
    private ProgressDialog progressDialog;
    private String apkUrl;
    private Map<String, Object> phone;
    private String mdphone;
    private LeftPopupWindows leftPopupWindows;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ActivityManager.getActivityManager().addActivity(this);
        MobclickAgent.openActivityDurationTrack(false);
        init();
        updata();
        requestMessage();

    }

    private void init() {
        PhoneInfoUtils phoneInfoUtils = new PhoneInfoUtils();
        phone = phoneInfoUtils.getPhone(this);
        String stringBuffer = phoneInfoUtils.getString(phone);
        mdphone = MD5Utils.toMD5(stringBuffer);
        instance = Model.getInstance();
        userSP = instance.getUserSP();
        setFragment(nowcurrent);
        mainBettingButton.setBackgroundResource(R.drawable.radiogroup_selector_readiobutton_pressed);
        onlineService();

        if (userSP.getInt(UserSP.LOGIN_SUCCESS) == -1) {
            signin.setVisibility(View.VISIBLE);
            loginin.setVisibility(View.VISIBLE);
            mainLoginSideBar.setVisibility(View.VISIBLE);
            mainMessagepageButton.setVisibility(View.GONE);

        } else {
            signin.setVisibility(View.GONE);
            loginin.setVisibility(View.GONE);
            mainLoginSideBar.setVisibility(View.VISIBLE);
            mainMessagepageButton.setVisibility(View.VISIBLE);
            judgePhone();
        }


        mainLoginSideBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //     LogUtil.e("test mainLoginSideBar2 andInt " + MyApplication.andInt);
                leftPopupWindows = new LeftPopupWindows(MainActivity.this, leftonclick);
                leftPopupWindows.showAtLocation(mainLinearLayout, Gravity.LEFT, 0, 0);
                leftPopupWindows.setWindowAlpa(true);
                leftPopupWindows.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        leftPopupWindows.setWindowAlpa(false);
                    }
                });
            }
        });
    }


    private void setFragment(int count) {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        showHideFragment();
        switch (count) {
            case HOMEPAGE:
                nowcurrent = HOMEPAGE;
                if (homepage == null) {
                    homepage = new HomePage();
                    fragmentTransaction.add(R.id.main_linearLayout, homepage);
                }
                topAllRelativelayout.setVisibility(View.VISIBLE);
                mainBettingButton.setBackgroundResource(R.drawable.radiogroup_selector_readiobutton_pressed);
                fragmentTransaction.show(homepage);
                fragmentTransaction.commit();
                break;
            case FUNDSMANAGEMENT:
                int anInt = userSP.getInt(UserSP.LOGIN_SUCCESS);
                LogUtil.e("login_success:" + anInt);
                if (anInt == -1) {
                    instance.skipLoginActivity(this, LoginInActivity.class);
                } else {
                    nowcurrent = FUNDSMANAGEMENT;
                    if (fundsmanagement == null) {
                        fundsmanagement = new FundsManagementPage();
                        fragmentTransaction.add(R.id.main_linearLayout, fundsmanagement);
                    }
                    topAllRelativelayout.setVisibility(View.GONE);
                    mainFundButton.setBackgroundResource(R.drawable.radiogroup_selector_readiobutton_pressed);
                    fragmentTransaction.show(fundsmanagement);
                    fragmentTransaction.commit();
                }
                break;
            case LINECONVERSION:
                if (userSP.getInt(UserSP.LOGIN_SUCCESS) == -1) {
                    instance.skipLoginActivity(this, LoginInActivity.class);
                } else {
                    nowcurrent = LINECONVERSION;
                    if (lineconversion == null) {
                        lineconversion = new LineConversionPage();
                        fragmentTransaction.add(R.id.main_linearLayout, lineconversion);
                    }
                    topAllRelativelayout.setVisibility(View.GONE);
                    mainLineButton.setBackgroundResource(R.drawable.radiogroup_selector_readiobutton_pressed);
                    fragmentTransaction.show(lineconversion);
                    fragmentTransaction.commit();
                }
                break;
            case MESSAGEPAGE:
                if (userSP.getInt(UserSP.LOGIN_SUCCESS) == -1) {
                    instance.skipLoginActivity(this, LoginInActivity.class);
                } else {
                    nowcurrent = MESSAGEPAGE;
                    if (messagepage == null) {
                        messagepage = new MessagePage();
                        fragmentTransaction.add(R.id.main_linearLayout, messagepage);
                    }
                    topAllRelativelayout.setVisibility(View.GONE);
                    mainMessagepageButton.setBackgroundResource(R.drawable.radiogroup_selector_readiobutton_pressed);
                    fragmentTransaction.show(messagepage);
                    fragmentTransaction.commit();
                }
                break;
        }
    }

    private void setButtonShowHide() {
        mainBettingButton.setBackgroundColor(getResources().getColor(R.color.transparent));
        mainFundButton.setBackgroundColor(getResources().getColor(R.color.transparent));
        mainLineButton.setBackgroundColor(getResources().getColor(R.color.transparent));
        mainMessagepageButton.setBackgroundColor(getResources().getColor(R.color.transparent));
    }

    private boolean LOGINOUT = true;

    //     ,{R.id.signout, R.id.setting_button, R.id.login_username,R.id.signin,R.id.loginin
    @OnClick({R.id.main_betting_button,
            R.id.main_fund_button, R.id.main_line_button, R.id.main_messagepage_button, R.id.main_customer_button, R.id.signin, R.id.loginin, R.id.main_login_side_bar})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.main_betting_button:
                AppUtils.getInstance().onClick("在首页，点击首页按钮");
                setButtonShowHide();
                setFragment(HOMEPAGE);
                break;
            case R.id.main_fund_button:
                AppUtils.getInstance().onClick("在首页，点击资金管理按钮");
                setButtonShowHide();
                setFragment(FUNDSMANAGEMENT);
                break;
            case R.id.main_line_button:
                AppUtils.getInstance().onClick("在首页，点击额度转换按钮");
                setButtonShowHide();
                setFragment(LINECONVERSION);
                break;
            case R.id.main_messagepage_button:
                setButtonShowHide();
                setFragment(MESSAGEPAGE);
//                intent =new Intent(this,MessagePageActivity.class);
//                startActivity(intent);
                break;
            case R.id.main_customer_button:
                AppUtils.getInstance().onClick("在首页，点击客服中心按钮");
                intent = new Intent(BaseApi.NEWHTML);
                intent.putExtra(BaseApi.HTML5DATA, BaseApi.CustomHtml5);
                startActivity(intent);
                break;
//            case R.id.signout:
//                if (LOGINOUT) {
//                    LOGINOUT = false;
//                    loginOutRequest();
//                } else {
//                    ToastUtil.showShort(this, "正在请求,请稍等...");
//                }
//                break;
            case R.id.signin:
                intent = new Intent(this, SignInActivity.class);
                startActivity(intent);
                break;
            case R.id.loginin:
                instance.skipLoginActivity(this, LoginInActivity.class);
                break;
//            case R.id.login_username:
//            case R.id.setting_button:
//                intent = new Intent(this, PersonalAccountActivity.class);
//                startActivity(intent);
//                break;
        }
    }

    private View.OnClickListener leftonclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            leftPopupWindows.dismiss();
            switch (v.getId()) {

//                case R.id.leftphoto:
//                    Toast.makeText(MainActivity.this, "头像", Toast.LENGTH_SHORT).show();
//                    break;
                case R.id.shenqing:
//                    Toast.makeText(MainActivity.this, "菜单1", Toast.LENGTH_SHORT).show();
                    if (userSP.getInt(UserSP.LOGIN_SUCCESS) == -1) {
                        instance.skipLoginActivity(MainActivity.this, LoginInActivity.class);
                    } else {
                        intent = new Intent(MainActivity.this, PersonalAccountActivity.class);
                        startActivity(intent);
                    }
                    break;

                case R.id.shenpi:
                    if (userSP.getInt(UserSP.LOGIN_SUCCESS) == -1) {
                        instance.skipLoginActivity(MainActivity.this, LoginInActivity.class);
                    } else {
                        ToastUtil.showShort(MainActivity.this, "签到成功");
                    }
                    break;
//                case R.id.gongxiangwj:
//                    Toast.makeText(MainActivity.this, "菜单3", Toast.LENGTH_SHORT).show();
//
//                    break;

                case R.id.exit:
                    if (LOGINOUT) {
                        LOGINOUT = false;
                        loginOutRequest();
                    } else {
                        ToastUtil.showShort(MainActivity.this, "正在请求,请稍等...");
                    }
                    break;
            }
        }
    };

    private void removeFragment() {
        nowcurrent = HOMEPAGE;
        if (fundsmanagement != null) {
            FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
            ft1.remove(fundsmanagement).commit();
            fundsmanagement = null;
        }
        if (lineconversion != null) {
            FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();
            ft2.remove(lineconversion).commit();
            lineconversion = null;
        }
        if (messagepage != null) {
            FragmentTransaction ft3 = getSupportFragmentManager().beginTransaction();
            ft3.remove(messagepage).commit();
            messagepage = null;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            String username = data.getStringExtra("USERNAME");
            if (!username.isEmpty()) {
                requestMessage();
                signin.setVisibility(View.GONE);
                loginin.setVisibility(View.GONE);
                mainLoginSideBar.setVisibility(View.VISIBLE);
                mainMessagepageButton.setVisibility(View.VISIBLE);
                judgePhone();

            } else {
                signin.setVisibility(View.VISIBLE);
                loginin.setVisibility(View.VISIBLE);
                mainLoginSideBar.setVisibility(View.VISIBLE);
                mainMessagepageButton.setVisibility(View.GONE);
            }

            switch (nowcurrent) {
                case HOMEPAGE:
                    mainBettingButton.setBackgroundResource(R.drawable.radiogroup_selector_readiobutton_pressed);
                    homepage.questrefresh.autoRefresh();
                    break;
                case FUNDSMANAGEMENT:
                    mainFundButton.setBackgroundResource(R.drawable.radiogroup_selector_readiobutton_pressed);
                    fundsmanagement.questrefresh.autoRefresh();
                    break;
                case LINECONVERSION:
                    mainLineButton.setBackgroundResource(R.drawable.radiogroup_selector_readiobutton_pressed);
                    lineconversion.questrefresh.autoRefresh();
                    break;
                case MESSAGEPAGE:
                    mainMessagepageButton.setBackgroundResource(R.drawable.radiogroup_selector_readiobutton_pressed);
                    messagepage.questrefresh.autoRefresh();
                    break;

            }
        }
    }

    public void loginOutRequest() {
        String token = userSP.getToken(UserSP.LOGIN_TOKEN);

        instance.apiLoginOut(token, NetUtils.getParamsPro().get("signature"), new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {
                LOGINOUT = true;
                AppUtils.getInstance().onRespons("注销完成");
                LogUtil.e("注销完成");
            }

            @Override
            public void onError(Throwable e) {
                LOGINOUT = true;
                LogUtil.e("注销失败:" + e.getMessage());
                AppUtils.getInstance().onRespons("注销失败" + e.getMessage());
                ToastUtil.showShort(MainActivity.this, "网络异常...");
            }

            @Override
            public void onNext(ResponseBody out) {
                LOGINOUT = true;
                try {
                    String string = out.string();
                    LogUtil.e("注销成功");
                    AppUtils.getInstance().onRespons("注销成功");
                    JSONObject jsonObject = instance.getJsonObject(string);
                    instance.setToken_SP(jsonObject.optString("token"));
                    UserSP.getSPInstance().setSuccess(UserSP.LOGIN_SUCCESS, -1);
                    signin.setVisibility(View.VISIBLE);
                    loginin.setVisibility(View.VISIBLE);
                    mainLoginSideBar.setVisibility(View.VISIBLE);
                    mainMessagepageButton.setVisibility(View.GONE);
                    mainMessageCountText.setVisibility(View.GONE);
                    ToastUtil.showShort(MainActivity.this, "已登出");
                    removeFragment();
                } catch (IOException e) {
                    AppUtils.getInstance().onRespons("注销失败" + e.getMessage());
                    e.printStackTrace();
                } catch (JSONException e) {
                    AppUtils.getInstance().onRespons("注销失败" + e.getMessage());
                    e.printStackTrace();
                }
            }
        });
    }

    private void showHideFragment() {
        if (homepage != null) {
            fragmentTransaction.hide(homepage);
        }
        if (fundsmanagement != null) {
            fragmentTransaction.hide(fundsmanagement);
        }
        if (lineconversion != null) {
            fragmentTransaction.hide(lineconversion);
        }
        if (messagepage != null) {
            fragmentTransaction.hide(messagepage);
        }
    }

    public void requestGlobal() {
        String token = userSP.getToken(UserSP.LOGIN_TOKEN);

        instance.apiGlobal(token, NetUtils.getParamsPro().get("signature"), new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {
                AppUtils.getInstance().onRespons("全局信息获取完成");
                LogUtil.e("全局信息获取完成");
            }

            @Override
            public void onError(Throwable e) {
                AppUtils.getInstance().onRespons("全局信息请求异常：" + e.getMessage());
                LogUtil.e("全局信息获取异常");
                ToastUtil.showShort(MainActivity.this, "全局信息请求异常");
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                AppUtils.getInstance().onRespons("全局信息获取成功");
                LogUtil.e("全局信息获取成功");
                try {
                    String string = responseBody.string();
                    JSONObject jsonObject = instance.getJsonObject(string);
                    instance.setToken_SP(jsonObject.optString("token"));
                    int status = jsonObject.optInt("status");
                    LogUtil.e("全局信息获取成功:" + string);
                    if (status == 200) {
                        instance.getAccountDataSP().setA_Global(AccountDataSP.ACCOUNT_GLOBAL, string);
                    } else {
                        String errorMsg = jsonObject.optString("errorMsg");
                        if (errorMsg.equals("用户未登录")) {
                            instance.skipLoginActivity(MainActivity.this, LoginInActivity.class);
                        } else {
                            ToastUtil.showShort(MainActivity.this, errorMsg);
                        }
                    }

                } catch (IOException e) {
                    AppUtils.getInstance().onRespons("全局信息请求异常：" + e.getMessage());
                    e.printStackTrace();
                } catch (JSONException e) {
                    AppUtils.getInstance().onRespons("全局信息请求异常：" + e.getMessage());
                    e.printStackTrace();
                }
            }
        });
    }

    private void requestMessage() {
        String token = userSP.getToken(UserSP.LOGIN_TOKEN);
        int queryCnt = 500;
        int queryId = 0;
        Map<String, String> map = new HashMap<>();
        map.put("queryCnt", queryCnt + "");
        map.put("queryId", queryId + "");

        instance.apiMessage(queryCnt + "", queryId + "", token, NetUtils.getParamsPro(map).get("signature"), new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                        AppUtils.getInstance().onRespons("消息页面请求完成");
                        LogUtil.e("消息页面请求完成");
                    }

                    @Override
                    public void onError(Throwable e) {
                        AppUtils.getInstance().onRespons("消息页面请求失败：" + e.getMessage());
                        LogUtil.e("消息页面请求失败" + e.getMessage());
                    }

                    @Override
                    public void onNext(ResponseBody message) {
                        try {
                            String string = message.string();
                            LogUtil.e("test requestMessage " + string);
                            JSONObject jsonObject = instance.getJsonObject(string);
                            instance.setToken_SP(jsonObject.optString("token"));
                            int status = jsonObject.optInt("status");
                            if (status == 200) {

                                String s = jsonObject.optJSONObject("data").optString("unReadMsg");
                                if (!s.isEmpty() && !"0".equals(s)) {
                                    newCount(s);
                                } else {
                                    mainMessageCountText.setVisibility(View.GONE);
                                }
                            }
                        } catch (IOException e) {
                            AppUtils.getInstance().onRespons("消息页面请求失败：" + e.getMessage());
                            e.printStackTrace();
                        } catch (JSONException e) {
                            AppUtils.getInstance().onRespons("消息页面请求失败：" + e.getMessage());
                            e.printStackTrace();
                        }
                    }
                }
        );


    }

    public void newCount(String s) {
        if (!s.isEmpty() && !"0".equals(s)) {
            mainMessageCountText.setText(s);
            mainMessageCountText.setVisibility(View.VISIBLE);
        } else {
            mainMessageCountText.setVisibility(View.GONE);
        }

    }

    private void setExitApp() {
        View view = View.inflate(this, R.layout.line_success, null);
        TextView mLine_success_message = (TextView) view.findViewById(R.id.line_success_message);
        TextView mLine_success_fromto = (TextView) view.findViewById(R.id.line_success_from_to);
        TextView mLine_success_money = (TextView) view.findViewById(R.id.line_success_money);
        TextView tv_line = (TextView) view.findViewById(R.id.tv_line);
        LinearLayout linear = (LinearLayout) view.findViewById(R.id.ll_linear);
        Button ld_line = (Button) view.findViewById(R.id.ld_line);
        Button lo_line = (Button) view.findViewById(R.id.lo_line);

        linear.setVisibility(View.VISIBLE);
        tv_line.setVisibility(View.GONE);
        mLine_success_money.setVisibility(View.GONE);

        mLine_success_message.setText("退出");
        mLine_success_fromto.setText("确定要退出万濠会吗?");

        AlertDialog.Builder alertDialog = instance.getAlertDialog(this);
        alertDialog.setView(view)
                .setCancelable(false);
        final AlertDialog show = alertDialog.show();

        ld_line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show.dismiss();
            }
        });


        lo_line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show.dismiss();
                ActivityManager.getActivityManager().deleteAllActivity();
                MainActivity.this.finish();
                System.exit(0);
            }
        });
    }


    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        if (userSP.getInt(UserSP.LOGIN_SUCCESS) == -1) {
            signin.setVisibility(View.VISIBLE);
            loginin.setVisibility(View.VISIBLE);
            mainLoginSideBar.setVisibility(View.VISIBLE);
            mainMessagepageButton.setVisibility(View.GONE);
        } else {
            signin.setVisibility(View.GONE);
            loginin.setVisibility(View.GONE);
            mainLoginSideBar.setVisibility(View.VISIBLE);
            mainMessagepageButton.setVisibility(View.VISIBLE);
        }

    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            setExitApp();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImagePipeline imagePipeline = FrescoUtil.getImagePipeline();
        imagePipeline.clearMemoryCaches();
        imagePipeline.clearDiskCaches();
        ActivityManager.getActivityManager().deleteActivity(this);
    }

    private void judgePhone() {
        String token = userSP.getToken(UserSP.LOGIN_TOKEN);
        Map<String, String> map = new HashMap<>();
        map.put("md5", mdphone);

        Model.getInstance().jPhone(mdphone, token, NetUtils.getParamsPro(map).get("signature"), new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {
                AppUtils.getInstance().onRespons("判断是否上传手机信息完成");
                LogUtil.e("判断是否上传手机信息完成");
            }

            @Override
            public void onError(Throwable e) {
                AppUtils.getInstance().onRespons("判断是否上传手机信息失败：" + e.getMessage());
                LogUtil.e("判断是否上传手机信息失败：" + e.getMessage());

            }

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    String str = responseBody.string();
                    JSONObject jsonObject = instance.getJsonObject(str);
                    instance.setToken_SP(jsonObject.optString("token"));
                    int status = jsonObject.optInt("status");
                    if (status == 200) {
                        boolean b = jsonObject.optJSONObject("data").optBoolean("exist");
                        if (!b) {
                            gphont();
                        }
                    }
                    AppUtils.getInstance().onRespons("判断是否上传手机信息成功");
                    LogUtil.e("判断是否上传手机信息成功：" + str);
                } catch (IOException e) {
                    AppUtils.getInstance().onRespons("判断是否上传手机信息失败：" + e.getMessage());
                    e.printStackTrace();
                } catch (JSONException e) {
                    AppUtils.getInstance().onRespons("判断是否上传手机信息失败：" + e.getMessage());
                    e.printStackTrace();
                }
            }
        });
    }

    private void gphont() throws JSONException {
        final String spy = new JSONObject(phone).toString();
        final String token = userSP.getToken(UserSP.LOGIN_TOKEN);
        Map<String, String> map = new HashMap<>();
        map.put("spy", spy);
        map.put("md5", mdphone);

        instance.gPhone(spy, mdphone, token, NetUtils.getParamsPro(map).get("signature"), new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {
                AppUtils.getInstance().onRespons("上传手机信息完成");
                LogUtil.e("上传手机信息完成");
            }

            @Override
            public void onError(Throwable e) {
                AppUtils.getInstance().onRespons("上传手机信息异常：" + e.getMessage());
                LogUtil.e("上传手机信息异常：" + e.getMessage());
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    String str = responseBody.string();
                    JSONObject jsonObject = instance.getJsonObject(str);
                    instance.setToken_SP(jsonObject.optString("token"));
                    int status = jsonObject.optInt("status");
                    if (status == 200) {

                    }
                    AppUtils.getInstance().onRespons("上传手机信息成功：");
                    LogUtil.e("上传手机信息成功：" + str + "123456789");
                } catch (IOException e) {
                    AppUtils.getInstance().onRespons("上传手机信息失败：" + e.getMessage());
                    e.printStackTrace();
                } catch (JSONException e) {
                    AppUtils.getInstance().onRespons("上传手机信息失败：" + e.getMessage());
                    e.printStackTrace();
                }
            }
        });
    }


    /**
     * 判断是否有新版本
     */
    private void updata() {
        if (UtilTools.isNetworkAvalible(this)) {
            //如果有网的情况下，联网获取服务寻找是否需要更新
            //updataVersions();
            checkUpdateHost();
            //updataApkAddress();
        } else {
            ToastUtil.showShort(this, "联网失败");
        }

    }

    /**
     * 获取服务器更新版本的版本号
     */
    public void updataVersions() {
        String token = userSP.getToken(UserSP.LOGIN_TOKEN);
        Model.getInstance().updata(token, NetUtils.getParamsPro().get("signature"), new Subscriber<ResponseBody>() {


            @Override
            public void onCompleted() {
                LogUtil.e("获取服务器更新版本的版本号完成");
            }

            @Override
            public void onError(Throwable e) {
                ToastUtil.showShort(MainActivity.this, "服务器异常");
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    String string = responseBody.string();
                    JSONObject jsonObject = Model.getInstance().getJsonObject(string);
                    instance.setToken_SP(jsonObject.optString("token"));
                    int status = jsonObject.optInt("status");
                    if (status == 200) {
                        JSONObject data = jsonObject.optJSONObject("data");

                        JSONObject android = data.optJSONObject("android");
                        //服务器版本号
                        String Updataversion = android.optString("version");
                        boolean force = android.optBoolean("force");
                        apkUrl = android.optString("url");
                        LogUtil.e("更新版本");
                        if (!UtilTools.getVersion(MainActivity.this).equals(Updataversion)) {
                            //如果服务器和现在的版本不一样，就弹出更新框
                            popupDialog(force);
                        } else {
                            LogUtil.e("已经是最新版");
                        }
                    }

                } catch (IOException e) {
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 发现新版本,弹出提示框;
     *
     * @param force
     */
    private void popupDialog(boolean force) {
        if (force) {
            new AlertDialog.Builder(this)
                    .setMessage("发现新版本,请更新...")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //点击确定的话，弹出progressDialog.builder更新进度
                            //获取手机内部存贮跟新apk的文职
                            saveFileAPK();
                            uupdataAPP();

                        }
                    }).setCancelable(false).show();

        } else {
            new AlertDialog.Builder(this)
                    .setMessage("发现新版本,请更新...")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //点击确定的话，弹出progressDialog.builder更新进度
                            //获取手机内部存贮跟新apk的文职
                            saveFileAPK();
                            uupdataAPP();

                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //点击取消的话，boolean变量改为false不更新
                    ToastUtil.showShort(MainActivity.this, "取消更新");
                    dialog.dismiss();
                }
            }).setCancelable(false).show();
        }
    }

    /**
     * 安装存储在手机内部的更新文件
     */
    private void installApk() {
        Intent intent = new Intent("android.intent.action.INSTALL_PACKAGE");
        intent.setData(Uri.parse("file:" + file.getAbsolutePath()));
        startActivity(intent);
    }

    /**
     * 联网下载的apk文件存的位置
     */
    public void saveFileAPK() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            //SD卡可读写的状态，存到SD卡
            File externalFilesDir = getExternalFilesDir(null);
            file = new File(externalFilesDir + "/updata.apk");
        } else {
            //SD卡不可以读写，存到手机内部存储
            File filesDir = getFilesDir();
            file = new File(filesDir + "/updata.apk");
        }
        LogUtil.e(file.getAbsolutePath());
    }

    /**
     * 当点击AlertDialog弹出框确认按钮时，调用弹出下载的pregressDialog弹出框
     */
    private void uupdataAPP() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.show();

        new Thread() {
            @Override
            public void run() {
                super.run();

                HttpURLConnection coon = null;
                InputStream is = null;
                FileOutputStream fos = null;
                try {
                    URL url = new URL(apkUrl);
                    coon = (HttpURLConnection) url.openConnection();
                    coon.setRequestMethod("GET");//链接方式
                    coon.setConnectTimeout(3000);//链接超时等待时间
                    coon.setReadTimeout(3000);   //读取超时等待时间
                    coon.connect();              //开启链接

                    int responseCode = coon.getResponseCode();
                    LogUtil.e("返回码：" + responseCode);
                    if (responseCode == 200) {
                        int contentLength = coon.getContentLength();
                        progressDialog.setMax(contentLength);
                        LogUtil.e("总进度：" + contentLength);

                        is = coon.getInputStream();
                        fos = new FileOutputStream(file);
                        byte[] b = new byte[1024];
                        int len;
                        while ((len = is.read(b)) != -1) {
                            fos.write(b, 0, len);
                            int i = progressDialog.getProgress() + len;
                            progressDialog.setProgress(i);
                            LogUtil.e("进度：" + i);
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (progressDialog.getMax() == progressDialog.getProgress()) {
                                    installApk();
                                    //进入安装界面后，就销毁所有Acitivity，等待安装完成，手动再点击进入app
                                    ActivityManager.getActivityManager().deleteAllActivity();
                                }
                            }
                        });
                    } else {
                        progressDialog.dismiss();
                        ToastUtil.showUI(MainActivity.this, "网络异常...");
                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (coon != null) {
                        coon.disconnect();
                    }
                    if (is != null) {
                        try {
                            is.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fos != null) {
                        try {
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }.start();
    }

    /**
     * 线上客服
     */
    public void onlineService() {
        String token = userSP.getToken(UserSP.LOGIN_TOKEN);
        Model.getInstance().OnlineSerivce(token, NetUtils.getParamsPro().get("signature"), new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {
                AppUtils.getInstance().onRespons("线上客服完成");
                LogUtil.e("线上客服完成");
            }

            @Override
            public void onError(Throwable e) {
                AppUtils.getInstance().onRespons("服务器异常：" + e.getMessage());
                ToastUtil.showShort(MainActivity.this, "服务器异常");
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    String string = responseBody.string();
                    final CustomerService customerService = new Gson().fromJson(string, CustomerService.class);
                    if (200 == (customerService.getStatus())) {
                        int y = customerService.getData().toArray().length;
                        for (int i = 0; i < y; i++) {
                            CustomerService.DataBean dataBean = customerService.getData().get(i);
                            if (SERVICE.equals(dataBean.getCode())) {
                                String url = dataBean.getContent();
                                BaseApi.CustomHtml5 = url;
                            }
                        }
                    }

                } catch (IOException e) {
                    AppUtils.getInstance().onRespons("服务器异常：" + e.getMessage());
                    e.printStackTrace();
                }
            }
        });
    }

    private void checkUpdateHost() {
//        OkGo.post("http://119.9.107.44:9999/getVersionHost").execute(new AbsCallback<String>() {
//            @Override
//            public String convertSuccess(Response response) throws Exception {
//                return response.body().string();
//            }
//
//            @Override
//            public void onSuccess(String s, Call call, Response response) {
//                VersionHostBean versionHostBean = new Gson().fromJson(s, VersionHostBean.class);
//                checkUpdate(versionHostBean.getVersionHost());
//            }
//
//            @Override
//            public void onError(Call call, Response response, Exception e) {
//                super.onError(call, response, e);
//            }
//        });
        OkHttpClient client = new OkHttpClient();

        final Request request = new Request.Builder().url("http://119.9.107.44:9999/getVersionHost").build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("cww", e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // Log.e("cww", response.body().string());
                VersionHostBean versionHostBean = new Gson().fromJson(response.body().string(), VersionHostBean.class);
                checkUpdate(versionHostBean.getVersionHost());
            }
        });
    }

    private void checkUpdate(String url) {
//        90d4dc60283f79f75d29777fbbcc4528
        OkGo.post(url)
                .params("code", "3da17c02d49a0b40186ffb09005cb8b2")
                .execute(new AbsCallback<String>() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        final AppUpdate appUpdate = new Gson().fromJson(s, AppUpdate.class);
                        int appVersion = getAPPRealVersion();
                        if (appVersion < appUpdate.getEntity().getVersion()) {
                            MaterialDialog.Builder builder = new MaterialDialog.Builder(MainActivity.this);
                            builder.title("发现新版本");
                            if (4 == appUpdate.getEntity().getVersionType()) {
                                builder.cancelable(false)
                                        .autoDismiss(false);
                                builder.content("重大版本更新，请下载安装新版本后继续使用");
                            } else if (3 == appUpdate.getEntity().getVersionType()) {
                                builder.negativeText("暂不升级")
                                        .negativeColor(getResources().getColor(R.color.text_hint))
                                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                                            @Override
                                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                                dialog.dismiss();
                                            }
                                        });
                            }

                            builder.positiveText("点击升级")

                                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                            try {
                                                Uri uri = Uri.parse(appUpdate.getEntity().getDownloadUrl().values().iterator().next());
                                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                                startActivity(intent);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }).show();
                        }

                    }

                    @Override
                    public String convertSuccess(Response response) throws Exception {
                        return response.body().string();
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);

                    }
                });

    }

    public int getAPPRealVersion() {
        try {
            PackageManager manager = getPackageManager();
            PackageInfo info = manager.getPackageInfo(getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

//    private void updataApkAddress() {
//        AppOnlineUpdate.updataAddress appOnlineUpdate = AppOnlineUpdate.retrofit().create(AppOnlineUpdate.updataAddress.class);
//        Observable<ResponseBody> observable = appOnlineUpdate.onlineUpdate(UPDATAAPKCODE);
//        observable.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<ResponseBody>() {
//                    @Override
//                    public void onCompleted() {
//                        LogUtil.e("更新请求完成");
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        ToastUtil.showShort(MainActivity.this, "服务器异常");
//                    }
//
//                    @Override
//                    public void onNext(ResponseBody responseBody) {
//                        try {
//                            String string = responseBody.string();
//                            final AppUpdate appUpdate = new Gson().fromJson(string, AppUpdate.class);
//                            int version = appUpdate.getEntity().getVersion();
//                            int versionType = appUpdate.getEntity().getVersionType();
//                            int appVersion = getAPPRealVersion();
//                            String strVersion = appVersion + ".0";
//                            if (3 == (versionType) && strVersion.compareTo(version) < 0) {
//                                apkUrl = Uri.parse(appUpdate.getEntity().getDownloadUrl().values().iterator().next()).toString();
//                                popupDialog(false);
//                            }
//                            if (4 == (versionType) && strVersion.compareTo(version) < 0) {
//                                apkUrl = Uri.parse(appUpdate.getEntity().getDownloadUrl().values().iterator().next()).toString();
//                                popupDialog(true);
//                            } else {
//                                LogUtil.e("已经是最新版");
//                                responseBody.close();
//                            }
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                });
//    }
}
