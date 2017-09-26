package com.wishland.www.xinwanhaotest.controller.fragment.fundsmanagement.pagerview;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.wishland.www.xinwanhaotest.R;
import com.wishland.www.xinwanhaotest.controller.base.BaseView;
import com.wishland.www.xinwanhaotest.controller.fragment.fundsmanagement.FundsManagementPage;
import com.wishland.www.xinwanhaotest.model.Model;
import com.wishland.www.xinwanhaotest.model.bean.GlobalBean;
import com.wishland.www.xinwanhaotest.model.bean.MessageType;
import com.wishland.www.xinwanhaotest.model.sp.AccountDataSP;
import com.wishland.www.xinwanhaotest.utils.AppUtils;
import com.wishland.www.xinwanhaotest.utils.LogUtil;
import com.wishland.www.xinwanhaotest.utils.ToastUtil;
import com.wishland.www.xinwanhaotest.utils.Utils_time;
import com.wishland.www.xinwanhaotest.view.activity.DetailQuestDealActivity;
import com.wishland.www.xinwanhaotest.view.customgridview.CustomViewPager;


import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 交易记录
 */

public class FundsDealMoney extends BaseView {

    @BindView(R.id.funds_deal_radiobutton_add)
    RadioButton fundsDealRadiobuttonAdd;
    @BindView(R.id.funds_deal_radiobutton_get)
    RadioButton fundsDealRadiobuttonGet;
    @BindView(R.id.funds_deal_radiobutton_atm)
    RadioButton fundsDealRadiobuttonAtm;
    @BindView(R.id.funds_deal_radiobutton_other)
    RadioButton fundsDealRadiobuttonOther;
    @BindView(R.id.funds_deal_radiogroup)
    RadioGroup fundsDealRadiogroup;
    @BindView(R.id.funds_deal_start_yearmd)
    Button fundsDealStartYearmd;
    @BindView(R.id.funds_deal_start_hour)
    Button fundsDealStartHour;
    @BindView(R.id.funds_deal_start_minute)
    Button fundsDealStartMinute;
    @BindView(R.id.funds_deal_end_yearmd)
    Button fundsDealEndYearmd;
    @BindView(R.id.funds_deal_end_hour)
    Button fundsDealEndHour;
    @BindView(R.id.funds_deal_end_minute)
    Button fundsDealEndMinute;
    @BindView(R.id.funds_deal_content)
    Button fundsDealContent;
    @BindView(R.id.funds_deal_content_linear)
    LinearLayout fundsDealContentLinear;
    @BindView(R.id.funds_deal_button)
    Button fundsDealButton;
    private int maketype;
    private static final int ADDMONEY = 0;
    private static final int REMITMONEY = 1;
    private static final int GETMONEY = 2;
    private static final int OTHER = 3;
    private FundsManagementPage fundsmanagement;
    private String ListHeadType = "存款记录";
    private GlobalBean globalBean;
    private List<GlobalBean.DataBean.TradeSubTypeBean> tradeSubType;
    private Model instance;
    private AccountDataSP accountDataSP;
    private String year;


    public FundsDealMoney(Context context, CustomViewPager fundsViewpager, FundsManagementPage fundsManagementPage) {
        super(context, fundsViewpager);
        this.fundsmanagement = fundsManagementPage;
    }

    @Override
    public View setView() {
        View funds = View.inflate(bastcontext, R.layout.fundsdealmoneylayout, null);
        ButterKnife.bind(this, funds);
        bastViewpager.setObjectForPosition(funds, 2);
        return funds;
    }

    @Override
    public void initData() {
        super.initData();
        LogUtil.e("交易加载数据");
        init();
        setGroupListener();
    }

    private void init() {
        year = Utils_time.getyear() + "";
        fundsDealStartYearmd.setHint(year);
        fundsDealEndYearmd.setHint(year);
        fundsDealRadiobuttonAdd.setText(R.string.addRMB);
        fundsDealRadiobuttonGet.setText(R.string.makeRMB);
        fundsDealRadiobuttonAtm.setText(R.string.getRMB);
        fundsDealRadiobuttonOther.setText(R.string.otherRMB);
        fundsDealRadiogroup.check(R.id.funds_deal_radiobutton_add);
        maketype = ADDMONEY;
        instance = Model.getInstance();
        accountDataSP = instance.getAccountDataSP();

        String accountData = accountDataSP.getAccountData(AccountDataSP.ACCOUNT_GLOBAL);
        globalBean = instance.getGson().fromJson(accountData, GlobalBean.class);


    }

    private void setGroupListener() {
        fundsDealRadiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int id = fundsDealRadiogroup.indexOfChild(fundsDealRadiogroup.findViewById(i));
                switch (id) {
                    case ADDMONEY:
                        AppUtils.getInstance().onClick("在资金管理界面，交易记录，存款，点击查询信息按钮！");
                        ListHeadType = "存款记录";
                        fundsDealContentLinear.setVisibility(View.GONE);
                        maketype = ADDMONEY;
                        break;
                    case REMITMONEY:
                        AppUtils.getInstance().onClick("在资金管理界面，交易记录，汇款，点击查询信息按钮！");
                        ListHeadType = "汇款记录";
                        fundsDealContentLinear.setVisibility(View.GONE);
                        maketype = REMITMONEY;
                        break;
                    case GETMONEY:
                        AppUtils.getInstance().onClick("在资金管理界面，交易记录，提款，点击查询信息按钮！");
                        ListHeadType = "提款记录";
                        fundsDealContentLinear.setVisibility(View.GONE);
                        maketype = GETMONEY;
                        break;
                    case OTHER:
                        AppUtils.getInstance().onClick("在资金管理界面，交易记录，其他，点击查询信息按钮！");
                        ListHeadType = "其他记录";
                        fundsDealContentLinear.setVisibility(View.VISIBLE);
                        maketype = OTHER;
                        break;
                }
            }
        });
    }

    @OnClick({R.id.funds_deal_content, R.id.funds_deal_start_yearmd, R.id.funds_deal_start_hour, R.id.funds_deal_start_minute, R.id.funds_deal_end_yearmd, R.id.funds_deal_end_hour, R.id.funds_deal_end_minute, R.id.funds_deal_button})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.funds_deal_start_yearmd:
                fundsmanagement.setDataPopup(fundsmanagement.listyear, fundsDealStartYearmd, 1);
                break;
            case R.id.funds_deal_end_yearmd:
                fundsmanagement.setDataPopup(fundsmanagement.listyear, fundsDealEndYearmd, 1);
                break;
            case R.id.funds_deal_start_hour:
                if (fundsmanagement.list.size() != 0) {
                    fundsmanagement.list.clear();
                }
                Collections.addAll(fundsmanagement.list, Utils_time.hourarray);
                fundsmanagement.setDataPopup(fundsmanagement.list, fundsDealStartHour, 1);
                break;
            case R.id.funds_deal_end_hour:
                if (fundsmanagement.list.size() != 0) {
                    fundsmanagement.list.clear();
                }
                Collections.addAll(fundsmanagement.list, Utils_time.hourarray);
                fundsmanagement.setDataPopup(fundsmanagement.list, fundsDealEndHour, 1);
                break;
            case R.id.funds_deal_start_minute:
                if (fundsmanagement.list.size() != 0) {
                    fundsmanagement.list.clear();
                }
                Collections.addAll(fundsmanagement.list, Utils_time.minutearray);
                fundsmanagement.setDataPopup(fundsmanagement.list, fundsDealStartMinute, 1);
                break;
            case R.id.funds_deal_end_minute:
                if (fundsmanagement.list.size() != 0) {
                    fundsmanagement.list.clear();
                }
                Collections.addAll(fundsmanagement.list, Utils_time.minutearray);
                fundsmanagement.setDataPopup(fundsmanagement.list, fundsDealEndMinute, 1);
                break;
            case R.id.funds_deal_content:
                if (fundsmanagement.list.size() != 0) {
                    fundsmanagement.list.clear();
                }
                tradeSubType = globalBean.getData().getTradeSubType();
                for (int i = 0; i < tradeSubType.size(); i++) {
                    fundsmanagement.list.add(tradeSubType.get(i).getName());
                }
                fundsmanagement.setDataPopup(fundsmanagement.list, fundsDealContent, 2);
                break;
            case R.id.funds_deal_button:
                switch (maketype) {
                    case ADDMONEY:
                        questMessage(ADDMONEY, ListHeadType);
                        break;
                    case REMITMONEY:
                        questMessage(REMITMONEY, ListHeadType);
                        break;
                    case GETMONEY:
                        questMessage(GETMONEY, ListHeadType);
                        break;
                    case OTHER:
                        questMessage(OTHER, ListHeadType);
                        break;
                }

                break;
        }
    }

    private void questMessage(int type, String name) {
        String start = fundsDealStartYearmd.getText().toString().isEmpty() ? year : fundsDealStartYearmd.getText().toString();
        String end = fundsDealEndYearmd.getText().toString().isEmpty() ? year : fundsDealEndYearmd.getText().toString();
        String starthour = fundsDealStartHour.getText().toString().isEmpty() ? "00" : fundsDealStartHour.getText().toString();
        String endhour = fundsDealEndHour.getText().toString().isEmpty() ? "23" : fundsDealEndHour.getText().toString();
        String startmin = fundsDealStartMinute.getText().toString().isEmpty() ? "00" : fundsDealStartMinute.getText().toString();
        String endmin = fundsDealEndMinute.getText().toString().isEmpty() ? "59" : fundsDealEndMinute.getText().toString();

        String starttime = start + " " + starthour + ":" + startmin;
        String endtime = end + " " + endhour + ":" + endmin;
        String content = "3,4,5,6";
        if (tradeSubType != null) {
            content = tradeSubType.get(fundsmanagement.DealContent).getId();
        }

        if (start.isEmpty()) {
            ToastUtil.showShort(bastcontext, "请选择开始年月日");
        } else {
            if (end.isEmpty()) {
                ToastUtil.showShort(bastcontext, "请选择结束年月日");
            } else {
                MessageType messageType = new MessageType(starttime, endtime, content, type, name);
                Intent intent = new Intent(bastcontext, DetailQuestDealActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("MESSAGE", messageType);
                intent.putExtras(bundle);
                AppUtils.getInstance().onEvent("在资金管理界面，交易记录，点击查询信息按钮", "进入" + messageType.getName() + "进入界面（Activity）！");
                AppUtils.getInstance().onEnter(DetailQuestDealActivity.class);
                bastcontext.startActivity(intent);
            }


        }
    }
}

