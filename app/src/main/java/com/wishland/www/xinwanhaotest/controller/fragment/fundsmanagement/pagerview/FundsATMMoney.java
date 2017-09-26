package com.wishland.www.xinwanhaotest.controller.fragment.fundsmanagement.pagerview;


import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wishland.www.xinwanhaotest.R;
import com.wishland.www.xinwanhaotest.controller.base.BaseView;
import com.wishland.www.xinwanhaotest.controller.fragment.fundsmanagement.FundsManagementPage;
import com.wishland.www.xinwanhaotest.model.Model;
import com.wishland.www.xinwanhaotest.model.sp.AccountDataSP;
import com.wishland.www.xinwanhaotest.model.sp.UserSP;
import com.wishland.www.xinwanhaotest.utils.AppUtils;
import com.wishland.www.xinwanhaotest.utils.LogUtil;
import com.wishland.www.xinwanhaotest.utils.NetUtils;
import com.wishland.www.xinwanhaotest.utils.ToastUtil;
import com.wishland.www.xinwanhaotest.utils.Utils_time;
import com.wishland.www.xinwanhaotest.view.activity.MainActivity;
import com.wishland.www.xinwanhaotest.view.customgridview.CustomViewPager;
import com.wishland.www.xinwanhaotest.view.customgridview.EmptyLayout;


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
 * 取款
 */

public class FundsATMMoney extends BaseView {
    @BindView(R.id.hands_user_SR)
    TextView handsUserSR;
    @BindView(R.id.hands_user_mame_SR)
    TextView handsUserMameSR;
    @BindView(R.id.hands_account_SR)
    TextView handsAccountSR;
    @BindView(R.id.hands_ATM_account_SR)
    TextView handsATMAccountSR;
    @BindView(R.id.hands_ATM_site_SR)
    TextView handsATMSiteSR;
    @BindView(R.id.hands_ATM_money_SR_edit)
    EditText handsATMMoneySREdit;
    @BindView(R.id.hands_ATM_password_SR_edit)
    EditText handsATMPasswordSREdit;
    @BindView(R.id.hands_ATM_button)
    Button handsATMButton;
    @BindView(R.id.funds_atm_text1)
    TextView fundsAtmText1;
    @BindView(R.id.funds_atm_text2)
    TextView fundsAtmText2;
    @BindView(R.id.funds_atm_text3)
    TextView fundsAtmText3;
    @BindView(R.id.empty_layout)
    EmptyLayout emptyLayout;

    private Model instance;
    private String encryption;
    private String account_bank_name;
    private UserSP userSP;
    private AccountDataSP accountDataSP;
    private FundsManagementPage fmgp;
    private Map<String, String> map;


    public View getAtmview() {
        return atmview;
    }

    private View atmview;

    public FundsATMMoney(Context context, CustomViewPager fundsViewpager, FundsManagementPage fundsManagementPage) {
        super(context, fundsViewpager);
        this.fmgp = fundsManagementPage;

    }

    @Override
    public View setView() {
        atmview = View.inflate(bastcontext, R.layout.fundsatmmoney, null);
        ButterKnife.bind(this, atmview);
        bastViewpager.setObjectForPosition(atmview, 1);
        return atmview;
    }

    @Override
    public void initData() {
        super.initData();
        LogUtil.e("取款加载数据");
        instance = Model.getInstance();
        userSP = instance.getUserSP();
        map = new HashMap<>();
        accountDataSP = instance.getAccountDataSP();
        fundsAtmText1.setText(R.string.ATM_prompt_1);
        fundsAtmText2.setText(R.string.ATM_prompt_2);
        fundsAtmText3.setText(R.string.ATM_prompt_3);

        String Account_User_Name = accountDataSP.getAccountData(AccountDataSP.ACCOUNT_USER_NAME);
        account_bank_name = accountDataSP.getAccountData(AccountDataSP.ACCOUNT_BANK_NAME);
        String Account_Bank_Number = accountDataSP.getAccountData(AccountDataSP.ACCOUNT_BANK_NUMBER);
        String Account_Bank_Address = accountDataSP.getAccountData(AccountDataSP.ACCOUNT_BANK_ADDRESS);

        encryption = Utils_time.encryption(Account_Bank_Number);
        handsUserSR.setText(userSP.getString(UserSP.LOGIN_USERNAME));

        handsUserMameSR.setText(Account_User_Name);
        handsAccountSR.setText(account_bank_name);
        handsATMAccountSR.setText(encryption);
        handsATMSiteSR.setText(Account_Bank_Address);

    }


    private void setSuccess(String type, String back, String money) {
        View view = View.inflate(bastcontext, R.layout.line_success, null);
        TextView mLine_success_message = (TextView) view.findViewById(R.id.line_success_message);
        TextView mLine_success_fromto = (TextView) view.findViewById(R.id.line_success_from_to);
        TextView mLine_success_money = (TextView) view.findViewById(R.id.line_success_money);
        TextView tv_line = (TextView) view.findViewById(R.id.tv_line);
        LinearLayout linear = (LinearLayout) view.findViewById(R.id.ll_linear);

        linear.setVisibility(View.GONE);
        tv_line.setVisibility(View.VISIBLE);

        mLine_success_message.setText(type);
        mLine_success_fromto.setText(back);
        mLine_success_money.setText("金额：" + money);

        AlertDialog.Builder alertDialog = instance.getAlertDialog(bastcontext);
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

    @OnClick({R.id.atm_all, R.id.hands_ATM_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.atm_all:
                handsATMMoneySREdit.setText(accountDataSP.getAccountData(AccountDataSP.ACCOUNT_BANK_MONEY));
                break;
            case R.id.hands_ATM_button:
                AppUtils.getInstance().onClick("在资金管理界面，取款时，点击提交按钮！");
                emptyLayout.showLoading();
                String token = userSP.getToken(UserSP.LOGIN_TOKEN);
                final String Amount = handsATMMoneySREdit.getText().toString().trim();
                String mpassword = handsATMPasswordSREdit.getText().toString().trim();


                if (!Amount.isEmpty() && !mpassword.isEmpty()) {

                    if (Double.valueOf(handsATMMoneySREdit.getText().toString()) > Double.valueOf(accountDataSP.getAccountData(AccountDataSP.ACCOUNT_BANK_MONEY))) {
                        ToastUtil.showUI((MainActivity) bastcontext, "钱包余额不足！");
                        emptyLayout.hide();
                        return;
                    }

                    if (Double.valueOf(handsATMMoneySREdit.getText().toString()) < 100) {
                        ToastUtil.showUI((MainActivity) bastcontext, "取款金额最低为100元！");
                        emptyLayout.hide();
                        return;
                    }

                    if (map.size() != 0) {
                        map.clear();
                    }

                    map.put("Amount", Amount);
                    map.put("passWord", mpassword);

                    instance.apiEssayMoney(mpassword, Amount, token, NetUtils.getParamsPro(map).get("signature"), new Subscriber<ResponseBody>() {
                        @Override
                        public void onCompleted() {
                            AppUtils.getInstance().onRespons("提现完成");
                            AppUtils.getInstance().onEvent("点击提交按钮", "提现完成！");
                            LogUtil.e("体现完成");
                            emptyLayout.hide();
                            handsATMMoneySREdit.setText("");
                            handsATMPasswordSREdit.setText("");
                        }

                        @Override
                        public void onError(Throwable e) {
                            emptyLayout.hide();
                            AppUtils.getInstance().onRespons("提现失败：" + e.getMessage());
                            AppUtils.getInstance().onEvent("点击提交按钮", "提现失败！");
                            LogUtil.e("体现失败" + e.getMessage());
                            setSuccess("网络异常", account_bank_name + ":" + encryption, Amount);
                        }

                        @Override
                        public void onNext(ResponseBody essayMoneyBean) {
                            emptyLayout.hide();
                            try {
                                String string = essayMoneyBean.string();
                                JSONObject jsonObject = instance.getJsonObject(string);
                                instance.setToken_SP(jsonObject.optString("token"));
                                int status = jsonObject.optInt("status");
                                if (status == 200) {
                                    AppUtils.getInstance().onEvent("点击提交按钮", "提现成功！");
                                    fmgp.persionRequest();
                                    setSuccess("提现成功", account_bank_name + ":" + encryption, Amount);
                                } else {
                                    String errorMsg = jsonObject.optString("errorMsg");
                                    setSuccess(errorMsg, account_bank_name + ":" + encryption, Amount);
                                }
                            } catch (IOException e) {
                                AppUtils.getInstance().onRespons("提现失败：" + e.getMessage());
                                AppUtils.getInstance().onEvent("点击提交按钮", e.getMessage());
                                e.printStackTrace();
                            } catch (JSONException e) {
                                AppUtils.getInstance().onRespons("提现失败：" + e.getMessage());
                                AppUtils.getInstance().onEvent("点击提交按钮", e.getMessage());
                                e.printStackTrace();
                            }
                        }
                    });
                } else {
                    emptyLayout.hide();
                    ToastUtil.showUI((MainActivity) bastcontext, "取款金额或取款密码不能为空");
                }

                break;
        }
    }
}
