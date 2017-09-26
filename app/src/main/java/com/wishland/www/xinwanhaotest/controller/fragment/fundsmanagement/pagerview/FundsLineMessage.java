package com.wishland.www.xinwanhaotest.controller.fragment.fundsmanagement.pagerview;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.wishland.www.xinwanhaotest.R;
import com.wishland.www.xinwanhaotest.controller.base.BaseView;
import com.wishland.www.xinwanhaotest.controller.fragment.fundsmanagement.FundsManagementPage;
import com.wishland.www.xinwanhaotest.model.Model;
import com.wishland.www.xinwanhaotest.model.bean.LineMessage;
import com.wishland.www.xinwanhaotest.utils.AppUtils;
import com.wishland.www.xinwanhaotest.utils.LogUtil;
import com.wishland.www.xinwanhaotest.utils.ToastUtil;
import com.wishland.www.xinwanhaotest.utils.Utils_time;
import com.wishland.www.xinwanhaotest.view.activity.LineDetailActivity;
import com.wishland.www.xinwanhaotest.view.customgridview.CustomViewPager;

import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 转换信息查询
 */

public class FundsLineMessage extends BaseView {

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
    @BindView(R.id.funds_deal_button)
    Button fundsDealButton;
    private FundsManagementPage fundsManagementPage;
    private Model instance;
    private String year;

    public FundsLineMessage(Context context, CustomViewPager fundsViewpager, FundsManagementPage fundsManagementPage) {
        super(context, fundsViewpager);
        this.fundsManagementPage = fundsManagementPage;
    }

    @Override
    public View setView() {
        View view = View.inflate(bastcontext, R.layout.fundslinemessage, null);
        ButterKnife.bind(this, view);
        bastViewpager.setObjectForPosition(view, 3);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        LogUtil.e("额度加载数据");
        year = Utils_time.getyear() + "";
        fundsDealStartYearmd.setHint(year);
        fundsDealEndYearmd.setHint(year);
        instance = Model.getInstance();
    }

    @OnClick({R.id.funds_deal_start_yearmd, R.id.funds_deal_start_hour, R.id.funds_deal_start_minute, R.id.funds_deal_end_yearmd, R.id.funds_deal_end_hour, R.id.funds_deal_end_minute, R.id.funds_deal_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.funds_deal_start_yearmd:
                fundsManagementPage.setDataPopup(fundsManagementPage.listyear, fundsDealStartYearmd, 1);
                break;
            case R.id.funds_deal_end_yearmd:
                fundsManagementPage.setDataPopup(fundsManagementPage.listyear, fundsDealEndYearmd, 1);
                break;
            case R.id.funds_deal_start_hour:
                if (fundsManagementPage.list.size() != 0) {
                    fundsManagementPage.list.clear();
                }
                Collections.addAll(fundsManagementPage.list, Utils_time.hourarray);
                fundsManagementPage.setDataPopup(fundsManagementPage.list, fundsDealStartHour, 1);
                break;
            case R.id.funds_deal_end_hour:
                if (fundsManagementPage.list.size() != 0) {
                    fundsManagementPage.list.clear();
                }
                Collections.addAll(fundsManagementPage.list, Utils_time.hourarray);
                fundsManagementPage.setDataPopup(fundsManagementPage.list, fundsDealEndHour, 1);
                break;
            case R.id.funds_deal_start_minute:
                if (fundsManagementPage.list.size() != 0) {
                    fundsManagementPage.list.clear();
                }
                Collections.addAll(fundsManagementPage.list, Utils_time.minutearray);
                fundsManagementPage.setDataPopup(fundsManagementPage.list, fundsDealStartMinute, 1);
                break;
            case R.id.funds_deal_end_minute:
                if (fundsManagementPage.list.size() != 0) {
                    fundsManagementPage.list.clear();
                }
                Collections.addAll(fundsManagementPage.list, Utils_time.minutearray);
                fundsManagementPage.setDataPopup(fundsManagementPage.list, fundsDealEndMinute, 1);
                break;
            case R.id.funds_deal_button:
                AppUtils.getInstance().onClick("在资金管理界面，额度查询，点击查询信息按钮！");
                questLineMessage();
                break;
        }
    }

    private void questLineMessage() {

        String start = fundsDealStartYearmd.getText().toString().isEmpty() ? year : fundsDealStartYearmd.getText().toString();
        String end = fundsDealEndYearmd.getText().toString().isEmpty() ? year : fundsDealEndYearmd.getText().toString();
        String starthour = fundsDealStartHour.getText().toString().isEmpty() ? "00" : fundsDealStartHour.getText().toString();
        String endhour = fundsDealEndHour.getText().toString().isEmpty() ? "23" : fundsDealEndHour.getText().toString();
        String startmin = fundsDealStartMinute.getText().toString().isEmpty() ? "00" : fundsDealStartMinute.getText().toString();
        String endmin = fundsDealEndMinute.getText().toString().isEmpty() ? "59" : fundsDealEndMinute.getText().toString();

        if (start.isEmpty()) {
            ToastUtil.showShort(bastcontext, "请选择开始年月日");
        } else {

            if (end.isEmpty()) {
                ToastUtil.showShort(bastcontext, "请选择结束年月日");
            } else {
                LineMessage line = new LineMessage(start, end, starthour, endhour, startmin, endmin);
                Intent intent = new Intent(bastcontext, LineDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("LINE_MESSAGE", line);
                intent.putExtras(bundle);
                AppUtils.getInstance().onEvent("在额度查询界面，点击额度信息按钮", "进入额度转化记录界面（Activity）！");
                AppUtils.getInstance().onEnter(LineDetailActivity.class);
                bastcontext.startActivity(intent);
            }
        }
    }
}
