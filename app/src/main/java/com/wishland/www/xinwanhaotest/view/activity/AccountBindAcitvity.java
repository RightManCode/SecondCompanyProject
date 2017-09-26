package com.wishland.www.xinwanhaotest.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.wishland.www.xinwanhaotest.R;
import com.wishland.www.xinwanhaotest.model.Model;
import com.wishland.www.xinwanhaotest.model.bean.BindSuccess;
import com.wishland.www.xinwanhaotest.model.sp.AccountDataSP;
import com.wishland.www.xinwanhaotest.model.sp.UserSP;
import com.wishland.www.xinwanhaotest.utils.ActivityManager;
import com.wishland.www.xinwanhaotest.utils.AppUtils;
import com.wishland.www.xinwanhaotest.utils.CodeUtils;
import com.wishland.www.xinwanhaotest.utils.LogUtil;
import com.wishland.www.xinwanhaotest.utils.NetUtils;
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
import butterknife.Unbinder;
import okhttp3.ResponseBody;
import rx.Subscriber;

/**
 * Created by Administrator on 2017/5/19.
 */

public class AccountBindAcitvity extends AutoLayoutActivity {
    Unbinder unbinder;
    @BindView(R.id.account_user)
    TextView accountUser;
    @BindView(R.id.account_user_mame)
    TextView accountUserMame;
    @BindView(R.id.account_account_bank)
    TextView accountAccountBank;
    @BindView(R.id.account_account_number)
    EditText accountAccountNumber;
    @BindView(R.id.account_bank_address)
    EditText accountBankAddress;
    @BindView(R.id.account_button)
    Button accountButton;
    @BindView(R.id.funds_atm_text1)
    TextView fundsAtmText1;
    @BindView(R.id.funds_atm_text2)
    TextView fundsAtmText2;
    @BindView(R.id.funds_atm_text3)
    TextView fundsAtmText3;
    @BindView(R.id.top_welcome)
    TextView topWelcome;
    @BindView(R.id.account_bank_wpassword)
    EditText accountBankWpassword;
    @BindView(R.id.empty_layout)
    EmptyLayout emptyLayout;

    private CodeUtils codeutils;
    private UserSP userSP;
    private Model instance;
    private String bankCode;
    private String bank;
    private String accountData;
    private Map<String, String> map;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accountbing);
        ActivityManager.getActivityManager().addActivity(this);
        unbinder = ButterKnife.bind(this);
        ButterKnife.bind(this);
        init();


    }

    private void init() {
        instance = Model.getInstance();
        userSP = instance.getUserSP();
        map = new HashMap<>();
        topWelcome.setText(R.string.account_bind_toptext);
        codeutils = new CodeUtils();
        accountData = instance.getAccountDataSP().getAccountData(AccountDataSP.ACCOUNT_USER_NAME);
        accountUserMame.setText(accountData);

        settingData();
    }

    private void settingData() {
        accountUser.setText(userSP.getString(UserSP.LOGIN_USERNAME));
        fundsAtmText1.setText(R.string.ATM_prompt_1);
        fundsAtmText2.setText(R.string.ATM_prompt_2);
        fundsAtmText3.setText(R.string.ATM_prompt_3);
    }

    @OnClick({R.id.account_account_bank, R.id.top_fanhui, R.id.account_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.account_account_bank:
                Intent intent = new Intent(this, AccountRequestBankActivity.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.top_fanhui:
                setBackResult(false);
                break;
            case R.id.account_button:
                emptyLayout.showLoading();
                putInRequest();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            bank = data.getStringExtra("Bank");
            bankCode = data.getStringExtra("BankCode");
            accountAccountBank.setText(bank);
            accountAccountBank.setTextColor(getResources().getColor(R.color.write));
        }
    }

    private void putInRequest() {
        String getbank = accountAccountBank.getText().toString().trim();
        String banknum = accountAccountNumber.getText().toString().trim();
        String address = accountBankAddress.getText().toString().trim();

        String wpassword = accountBankWpassword.getText().toString().trim();

        String token = userSP.getToken(UserSP.LOGIN_TOKEN);
        if (map.size() != 0) {
            map.clear();
        }
        map.put("pay_name", accountData);
        map.put("pay_card", getbank);
        map.put("pay_num", banknum);
        map.put("pay_address", address);
        map.put("bankCode", bankCode);
        map.put("wpassword", wpassword);

        instance.apiAccount_Bind(accountData, getbank, banknum, address, bankCode, wpassword, token, NetUtils.getParamsPro(map).get("signature")
                , new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                        AppUtils.getInstance().onRespons("账户绑定请求完成");
                        LogUtil.e("账户绑定请求完成");
                        emptyLayout.hide();
                    }

                    @Override
                    public void onError(Throwable e) {
                        AppUtils.getInstance().onRespons("账户绑定请求异常：" + e.getMessage());
                        LogUtil.e("账户绑定请求异常：" + e.getMessage());
                        setSuccess("网络异常");
                        emptyLayout.hide();
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        emptyLayout.hide();
                        try {
                            AppUtils.getInstance().onRespons("账户绑定请求成功：" );
                            String string = responseBody.string();
                            LogUtil.e("账户绑定请求成功:" + string);
                            JSONObject jsonObject = instance.getJsonObject(string);
                            int status = jsonObject.optInt("status");
                            instance.setToken_SP(jsonObject.optString("token"));
                            if (status == 200) {
                                // setSuccess("账户绑定成功");
                                setBackResult(true);
                                EventBus.getDefault().post(new BindSuccess("ok"));
                            } else {
                                String errorMsg = jsonObject.optString("errorMsg");
                                if (errorMsg.equals("用户未登录")) {
                                    Model.getInstance().skipLoginActivity(AccountBindAcitvity.this, LoginInActivity.class);
                                } else {
                                    setSuccess(errorMsg);
                                }
                            }
                        } catch (IOException e) {
                            AppUtils.getInstance().onRespons("账户绑定请求失败：" + e.getMessage());
                            e.printStackTrace();
                        } catch (JSONException e) {
                            AppUtils.getInstance().onRespons("账户绑定请求失败：" + e.getMessage());
                            setSuccess("文本包含非法字符,请修改...");
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void setBackResult(boolean back) {
        Intent intent = new Intent();
        intent.putExtra("Bind_Updata", back);
        setResult(RESULT_OK, intent);
        finish();
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
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        ActivityManager.getActivityManager().deleteActivity(this);
    }
}
