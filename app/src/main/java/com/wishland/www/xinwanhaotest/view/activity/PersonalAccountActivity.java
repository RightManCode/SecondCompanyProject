package com.wishland.www.xinwanhaotest.view.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wishland.www.xinwanhaotest.R;

import com.wishland.www.xinwanhaotest.model.Model;
import com.wishland.www.xinwanhaotest.model.bean.AccountBean;
import com.wishland.www.xinwanhaotest.model.bean.LISuccess;
import com.wishland.www.xinwanhaotest.model.sp.AccountDataSP;
import com.wishland.www.xinwanhaotest.model.sp.UserSP;
import com.wishland.www.xinwanhaotest.utils.ActivityManager;
import com.wishland.www.xinwanhaotest.utils.AppUtils;
import com.wishland.www.xinwanhaotest.utils.LogUtil;
import com.wishland.www.xinwanhaotest.utils.NetUtils;
import com.wishland.www.xinwanhaotest.utils.ToastUtil;
import com.wishland.www.xinwanhaotest.utils.ToolsMthode;
import com.wishland.www.xinwanhaotest.utils.Utils_time;
import com.wishland.www.xinwanhaotest.view.customgridview.EmptyLayout;
import com.wishland.www.xinwanhaotest.view.refresh.MaterialRefreshLayout;
import com.wishland.www.xinwanhaotest.view.refresh.MaterialRefreshListener;
import com.zhy.autolayout.AutoLayoutActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
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
 * Created by Administrator on 2017/4/28.
 * 我的账户
 */

public class PersonalAccountActivity extends AutoLayoutActivity {

    @BindView(R.id.personalaccount_user_show_hide)
    TextView personalaccountUserShowHide;
    @BindView(R.id.personalaccount_user_SR)
    TextView personalaccountUserSR;                 //账户
    @BindView(R.id.personalaccount_usertime_SR)
    TextView personalaccountUsertimeSR;             //注册时间
    @BindView(R.id.personalaccount_userloginintime_SR)
    TextView personalaccountUserloginintimeSR;      //最近登录时间
    @BindView(R.id.personalaccount_usermoney_SR)
    TextView personalaccountUsermoneySR;            //钱包余额
    @BindView(R.id.personalaccount_usermoneycount_SR)
    TextView personalaccountUsermoneycountSR;       //体现所需投注量
    @BindView(R.id.personalaccount_payeeusername_SR)
    TextView personalaccountPayeeusernameSR;        //收款人姓名
    @BindView(R.id.personalaccount_payeebank_SR)
    TextView personalaccountPayeebankSR;            //收款银行
    @BindView(R.id.personalaccount_bankaccount_SR)
    TextView personalaccountBankaccountSR;          //银行账号
    @BindView(R.id.personalaccount_bankaddress_SR)
    TextView personalaccountBankaddressSR;          //开户地址
    @BindView(R.id.personalaccount_userdata_linearlayout)
    LinearLayout personalaccountUserdataLinearlayout;


    @BindView(R.id.personalaccount_pass_showhide)
    TextView personalaccountPassShowhide;
    @BindView(R.id.personalaccount_oldpassword)
    EditText personalaccountOldpassword;            //原登录密码
    @BindView(R.id.personalaccount_newpassword)
    EditText personalaccountNewpassword;            //登录新密码
    @BindView(R.id.personalaccount_mnewpassword)
    EditText personalaccountMnewpassword;           //重复新密码
    @BindView(R.id.personalaccount_oknewpassword)
    Button personalaccountOknewpassword;            //提交
    @BindView(R.id.personalaccount_getmoneyoldpassword)
    EditText personalaccountGetmoneyoldpassword;    //原取款密码
    @BindView(R.id.personalaccount_getmoneynewpassword)
    EditText personalaccountGetmoneynewpassword;    //新取款密码
    @BindView(R.id.prepersonalaccount_getmoneynewpassword)
    EditText prepersonalaccountGetmoneynewpassword;//确认取款密码
    @BindView(R.id.personalaccount_okgetmoneynewpassword)
    Button personalaccountOkgetmoneynewpassword;    //提交
    @BindView(R.id.personalaccount_changepass_linear)
    LinearLayout personalaccountChangepassLinear;
    @BindView(R.id.top_fanhui)
    ImageView topFanhui;
    @BindView(R.id.top_welcome)
    TextView topWelcome;
    @BindView(R.id.quest_refresh)
    MaterialRefreshLayout questrefresh;
    @BindView(R.id.empty_layout)
    EmptyLayout emptyLayout;
    private Map<String, String> map;
    private Model instance;
    private AccountDataSP accountDataSP;
    private UserSP userSP;
    private Drawable usericon;
    private Drawable passicon;
    private Drawable up;
    private Drawable down;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personalaccountlayout);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        ActivityManager.getActivityManager().addActivity(this);
        init();
        initListener();


    }

    private boolean UserShow = false;
    private boolean PassShow = true;

    private void initListener() {
        questrefresh.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(final MaterialRefreshLayout materialRefreshLayout) {
                PersionRequest();
            }
        });

        emptyLayout.setEmptyButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emptyLayout.showLoading();
                questrefresh.autoRefresh();
            }
        });
    }

    private void init() {

        down = getResources().getDrawable(R.drawable.icon_down);
        down.setBounds(0, 0, down.getMinimumWidth(), down.getMinimumHeight());

        up = getResources().getDrawable(R.drawable.icon_up);
        up.setBounds(0, 0, up.getMinimumWidth(), up.getMinimumHeight());

        usericon = getResources().getDrawable(R.drawable.icon1);
        usericon.setBounds(0, 0, usericon.getMinimumWidth(), usericon.getMinimumHeight());

        passicon = getResources().getDrawable(R.drawable.icon2);
        passicon.setBounds(0, 0, passicon.getMinimumWidth(), passicon.getMinimumHeight());

        topWelcome.setText(R.string.main_top_myaccount);
        map = new HashMap();
        instance = Model.getInstance();
        accountDataSP = instance.getAccountDataSP();
        userSP = instance.getUserSP();
        questrefresh.autoRefresh();
    }


    @OnClick({R.id.personalaccount_pass_showhide, R.id.personalaccount_user_show_hide, R.id.persion_updata_bindbank, R.id.top_fanhui, R.id.personalaccount_oknewpassword, R.id.personalaccount_okgetmoneynewpassword})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.persion_updata_bindbank:
                Intent intent = new Intent(this, AccountBindAcitvity.class);
                startActivityForResult(intent, 3);
                break;
            case R.id.personalaccount_user_show_hide:
                UserShow = !UserShow;
                LogUtil.e("123456789");
                if (UserShow) {
                    personalaccountUserShowHide.setCompoundDrawables(usericon, null, down, null);
                    personalaccountUserdataLinearlayout.setVisibility(View.GONE);

                } else {
                    personalaccountUserShowHide.setCompoundDrawables(usericon, null, up, null);
                    personalaccountUserdataLinearlayout.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.personalaccount_pass_showhide:
                PassShow = !PassShow;
                if (PassShow) {
                    personalaccountChangepassLinear.setVisibility(View.GONE);
                    personalaccountPassShowhide.setCompoundDrawables(passicon, null, down, null);
                } else {
                    personalaccountChangepassLinear.setVisibility(View.VISIBLE);
                    personalaccountPassShowhide.setCompoundDrawables(passicon, null, up, null);
                }
                break;
            case R.id.personalaccount_oknewpassword:
                emptyLayout.showLoading();
                String oldpw = personalaccountOldpassword.getText().toString().trim();
                String loginpw = personalaccountNewpassword.getText().toString().trim();
                String loginrepw = personalaccountMnewpassword.getText().toString().trim();
                AmendLoginPassWord(oldpw, loginpw, loginrepw);
                break;
            case R.id.personalaccount_okgetmoneynewpassword:
                emptyLayout.showLoading();
                String oldpassword = personalaccountGetmoneyoldpassword.getText().toString().trim();
                String newpassword = personalaccountGetmoneynewpassword.getText().toString().trim();
                String surenewpassword = prepersonalaccountGetmoneynewpassword.getText().toString().trim();
                AmendPassWord(oldpassword, newpassword, surenewpassword);
                break;
            case R.id.top_fanhui:
                finish();
                break;
            /*case R.id.button_pc:  //进入浏览器PC版
                instance.toBrowser(this);
                break;*/
        }
    }

    private void AmendLoginPassWord(final String oldpw, final String loginpw, final String loginrepw) {
        ToolsMthode toolsMthode = new ToolsMthode();
        if (toolsMthode.judgePasswork(oldpw, 5)) {

            if (toolsMthode.judgePasswork(loginpw, 5)) {

                if (loginpw.equals(loginrepw)) {
                    String token = userSP.getToken(UserSP.LOGIN_TOKEN);
                    String seconds = Utils_time.getSeconds();
                    if (map.size() != 0) {
                        map.clear();
                    }

                    map.put("oriPW", oldpw);
                    map.put("newPW", loginpw);
                    map.put("token", token);
                    map.put("time", seconds);

                    instance.apiAmendLogin(oldpw, loginpw, token, NetUtils.getParamsPro(map).get("signature"), new Subscriber<ResponseBody>() {
                        @Override
                        public void onCompleted() {
                            AppUtils.getInstance().onRespons("修改登录密码完成");
                            LogUtil.e("修改登录密码完成");
                        }

                        @Override
                        public void onError(Throwable e) {
                            emptyLayout.hide();
                            AppUtils.getInstance().onRespons("修改登录密码失败：" + e.getMessage());
                            LogUtil.e("修改登录密码失败：" + e.getMessage());
                            setSuccess("非常抱歉! 网络异常");
                        }

                        @Override
                        public void onNext(ResponseBody json) {
                            emptyLayout.hide();
                            try {
                                String string = json.string();
                                JSONObject jsonObject = new JSONObject(string);
                                int status = jsonObject.optInt("status");
                                instance.setToken_SP(jsonObject.optString("token"));
                                if (status == 200) {
                                    Intent intent = new Intent(PersonalAccountActivity.this, LoginInActivity.class);
                                    PersonalAccountActivity.this.startActivity(intent);
                                    finish();
                                    userSP.setCheckedSP(UserSP.LOGIN_CHECKED, false);
                                    userSP.setSuccess(UserSP.LOGIN_SUCCESS, -1);
                                    instance.setToken_SP("");
                                    LogUtil.e("修改登录密码成功");
                                } else {
                                    String errorMsg = jsonObject.optString("errorMsg");
                                    if (errorMsg.equals("用户未登录")) {
                                        instance.skipLoginActivity(PersonalAccountActivity.this, LoginInActivity.class);
                                    } else {
                                        setSuccess(errorMsg);
                                    }

                                }
                                AppUtils.getInstance().onRespons("修改登录密码成功");
                                LogUtil.e("修改登录密码");
                            } catch (IOException e) {
                                AppUtils.getInstance().onRespons("修改登录密码失败:" + e.getMessage());
                                e.printStackTrace();
                            } catch (JSONException e) {
                                AppUtils.getInstance().onRespons("修改登录密码失败:" + e.getMessage());
                                e.printStackTrace();
                            }
                        }
                    });
                } else {
                    emptyLayout.hide();
                    ToastUtil.showUI(PersonalAccountActivity.this, "新密码格式错误,请重新输入.");

                }
            } else {
                emptyLayout.hide();
                ToastUtil.showUI(PersonalAccountActivity.this, "两次密码不一致，请重新输入.");
            }
        } else {
            emptyLayout.hide();
            ToastUtil.showUI(PersonalAccountActivity.this, "原密码格式错误,请重新输入.");
        }


    }

    private void AmendPassWord(final String oldpw, final String loginpw, String surenewpassword) {
        ToolsMthode toolsMthode = new ToolsMthode();
        if (toolsMthode.judgePasswork(oldpw, 5) && toolsMthode.judgePasswork(loginpw, 5) && toolsMthode.judgePasswork(surenewpassword, 5)) {
            if (!loginpw.equals(surenewpassword)) {
                emptyLayout.hide();
                ToastUtil.showUI(PersonalAccountActivity.this, "两次取款密码不一致,请重新输入.");
                return;
            }
            String token = userSP.getToken(UserSP.LOGIN_TOKEN);
            String seconds = Utils_time.getSeconds();
            if (map.size() != 0) {
                map.clear();
            }

            map.put("oriPW", oldpw);
            map.put("newPW", loginpw);
            map.put("token", token);
            map.put("time", seconds);

            instance.apiAmend(oldpw, loginpw, token, NetUtils.getParamsPro(map).get("signature"), new Subscriber<ResponseBody>() {
                @Override
                public void onCompleted() {
                    AppUtils.getInstance().onRespons("修改取款密码完成");
                    LogUtil.e("修改取款密码完成");
                    emptyLayout.hide();
                }

                @Override
                public void onError(Throwable e) {
                    AppUtils.getInstance().onRespons("修改取款密码失败：" + e.getMessage());
                    LogUtil.e("修改取款密码失败：" + e.getMessage());
                    emptyLayout.hide();
                    setSuccess("非常抱歉! 网络异常");
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
                            personalaccountGetmoneyoldpassword.setText("");
                            personalaccountGetmoneynewpassword.setText("");
                            prepersonalaccountGetmoneynewpassword.setText("");
                            setSuccess("取款密码修改成功");
                            AppUtils.getInstance().onRespons("修改取款密码成功");
                            LogUtil.e("修改取款密码成功");
                        } else {
                            String errorMsg = jsonObject.optString("errorMsg");
                            if (errorMsg.equals("用户未登录")) {
                                instance.skipLoginActivity(PersonalAccountActivity.this, LoginInActivity.class);
                            } else {
                                setSuccess(errorMsg);
                            }
                        }
                    } catch (IOException e) {
                        AppUtils.getInstance().onRespons("修改密码失败：" + e.getMessage());
                        e.printStackTrace();
                    } catch (JSONException e) {
                        AppUtils.getInstance().onRespons("修改密码失败：" + e.getMessage());
                        e.printStackTrace();
                    }
                }
            });
        } else {
            ToastUtil.showUI(PersonalAccountActivity.this, "取款密码格式错误,请重新输入.");
            emptyLayout.hide();
        }


    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginEvent(LISuccess lis) {
        if (lis.getStatus()) {
            questrefresh.autoRefresh();
        }
    }

    private void PersionRequest() {
        String token = userSP.getToken(UserSP.LOGIN_TOKEN);
        instance.apiAccount(token, NetUtils.getParamsPro().get("signature"), new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {
                AppUtils.getInstance().onRespons("个人页面请求完成");
                questrefresh.finishRefresh();
                LogUtil.e("个人页面请求完成");
            }

            @Override
            public void onError(Throwable e) {
                AppUtils.getInstance().onRespons("个人页面请求失败：" + e.getMessage());
                LogUtil.e("个人页面请求失败：" + e.getMessage());
                questrefresh.finishRefresh();
                emptyLayout.showEmpty();
            }

            @Override
            public void onNext(ResponseBody account) {
                questrefresh.finishRefresh();
                try {
                    String string = account.string();
                    JSONObject jsonObject = instance.getJsonObject(string);
                    instance.setToken_SP(jsonObject.optString("token"));
                    int status = jsonObject.optInt("status");
                    AppUtils.getInstance().onRespons("个人页面请求成功");
                    LogUtil.e("个人页面请求成功" + string);
                    if (status == 200) {
                        emptyLayout.hide();
                        AccountBean accountBean = instance.getGson().fromJson(string, AccountBean.class);
                        setAccount(accountBean);
                        setAccountDataSP(accountBean);
                    } else {
                        String errorMsg = jsonObject.optString("errorMsg");
                        if (errorMsg.equals("用户未登录")) {
                            instance.skipLoginActivity(PersonalAccountActivity.this, LoginInActivity.class);
                        } else {
                            ToastUtil.showShort(PersonalAccountActivity.this, errorMsg);
                        }
                    }
                } catch (IOException e) {
                    AppUtils.getInstance().onRespons("个人页面请求失败：" + e.getMessage());
                    e.printStackTrace();
                } catch (JSONException e) {
                    AppUtils.getInstance().onRespons("个人页面请求失败：" + e.getMessage());
                    e.printStackTrace();
                }
            }
        });
    }

    public void setAccount(AccountBean accountBean) {
        final AccountBean.DataBean.AccountInfoBean accountInfo = accountBean.getData().getAccountInfo();
        final AccountBean.DataBean.BalanceInfoBean balanceInfo = accountBean.getData().getBalanceInfo();
        final AccountBean.DataBean.FinanceInfoBean financeInfo = accountBean.getData().getFinanceInfo();

        accountDataSP.setA_User_Name(AccountDataSP.ACCOUNT_USER_NAME, financeInfo.getPayName());

        personalaccountUserSR.setText(userSP.getString(UserSP.LOGIN_USERNAME));
        personalaccountUsertimeSR.setText(accountInfo.getRegisterTime());
        personalaccountUserloginintimeSR.setText(accountInfo.getLastLoginTime());

        personalaccountUsermoneySR.setText(balanceInfo.getBalance());
        personalaccountUsermoneycountSR.setText(balanceInfo.getDrawingBet() + "");

        personalaccountPayeeusernameSR.setText(financeInfo.getPayName());
        personalaccountPayeebankSR.setText(financeInfo.getBank() + "");

        String encryption = Utils_time.encryption(financeInfo.getBankAccount() + "");
        personalaccountBankaccountSR.setText(encryption);
        personalaccountBankaddressSR.setText(financeInfo.getAccountAddress() + "");
    }

    private void setAccountDataSP(final AccountBean accountBean) {
        instance.getExecutorService().execute(new Runnable() {
            @Override
            public void run() {
                AccountBean.DataBean.FinanceInfoBean financeInfo = accountBean.getData().getFinanceInfo();
                accountDataSP.setA_User_Name(AccountDataSP.ACCOUNT_USER_NAME, financeInfo.getPayName());
                accountDataSP.setA_Bank_Name(AccountDataSP.ACCOUNT_BANK_NAME, financeInfo.getBank() + "");
                accountDataSP.setA_Bank_Number(AccountDataSP.ACCOUNT_BANK_NUMBER, financeInfo.getBankAccount() + "");
                accountDataSP.setA_Bank_Address(AccountDataSP.ACCOUNT_BANK_ADDRESS, financeInfo.getAccountAddress() + "");
                accountDataSP.setA_Bank_money(AccountDataSP.ACCOUNT_BANK_MONEY, accountBean.getData().getBalanceInfo().getBalance() + "");
            }
        });
    }

    private void setSuccess(String fromto) {
        View view = View.inflate(this, R.layout.line_success, null);
        TextView mLine_success_message = (TextView) view.findViewById(R.id.line_success_message);
        TextView mLine_success_fromto = (TextView) view.findViewById(R.id.line_success_from_to);
        TextView mLine_success_money = (TextView) view.findViewById(R.id.line_success_money);
        TextView tv_line = (TextView) view.findViewById(R.id.tv_line);
        LinearLayout linear = (LinearLayout) view.findViewById(R.id.ll_linear);

        linear.setVisibility(View.GONE);
        tv_line.setVisibility(View.VISIBLE);

        mLine_success_message.setText("通知");
        mLine_success_fromto.setText(fromto);
        mLine_success_money.setVisibility(View.GONE);

        AlertDialog.Builder alertDialog = instance.getAlertDialog(this);
        alertDialog.setView(view)
                .setCancelable(false);
        final AlertDialog show = alertDialog.show();


        tv_line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show.dismiss();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            boolean bind_updata = data.getBooleanExtra("Bind_Updata", false);
            if (bind_updata == true) {
                questrefresh.autoRefresh();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        ActivityManager.getActivityManager().deleteActivity(this);
    }
}
