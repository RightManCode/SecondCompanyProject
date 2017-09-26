package com.wishland.www.xinwanhaotest.controller.fragment.fundsmanagement.pagerview;


import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.wishland.www.xinwanhaotest.R;
import com.wishland.www.xinwanhaotest.controller.base.BaseView;
import com.wishland.www.xinwanhaotest.controller.fragment.fundsmanagement.FundsManagementPage;
import com.wishland.www.xinwanhaotest.model.Model;
import com.wishland.www.xinwanhaotest.model.sp.UserSP;
import com.wishland.www.xinwanhaotest.utils.AppUtils;
import com.wishland.www.xinwanhaotest.utils.LogUtil;
import com.wishland.www.xinwanhaotest.utils.NetUtils;
import com.wishland.www.xinwanhaotest.utils.ToastUtil;
import com.wishland.www.xinwanhaotest.utils.Utils_time;
import com.wishland.www.xinwanhaotest.view.customgridview.CustomViewPager;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import rx.Subscriber;

/**
 * 投注记录
 */

public class FundsCathectic extends BaseView {

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
    @BindView(R.id.funds_deal_content_linear)
    LinearLayout fundsDealContentLinear;
    @BindView(R.id.funds_deal_button)
    Button fundsDealButton;
    @BindView(R.id.funds_deal_content)
    Button fundsDealContent;
    private int maketype;
    private static final int PLAYGAME = 0;
    private static final int TIMENOTE = 1;
    private static final int NOTEPAGE = 2;
    private static final int SEXNOTE = 3;
    private FundsManagementPage fundsmanagement;
    private Model instance;
    private UserSP userSP;
    private Map<String, String> map;


    public FundsCathectic(Context context, CustomViewPager fundsViewpager, FundsManagementPage fundsManagementPage) {
        super(context, fundsViewpager);
        this.fundsmanagement = fundsManagementPage;
    }

    @Override
    public View setView() {
        View view = View.inflate(bastcontext, R.layout.fundscathecticlayout, null);
        ButterKnife.bind(this, view);
        bastViewpager.setObjectForPosition(view, 4);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        init();
        setGroupListener();
    }

    private void init() {
        fundsDealContentLinear.setVisibility(View.VISIBLE);
        instance = Model.getInstance();
        userSP = instance.getUserSP();
        map = new HashMap<>();
        fundsDealRadiobuttonAdd.setText(R.string.playcolor);
        fundsDealRadiobuttonGet.setText(R.string.timecolor);
        fundsDealRadiobuttonAtm.setText(R.string.colornote);
        fundsDealRadiobuttonOther.setText(R.string.sexcolor);
        fundsDealRadiogroup.check(R.id.funds_deal_radiobutton_add);
        maketype = PLAYGAME;
    }

    private void setGroupListener() {
        fundsDealRadiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int id = fundsDealRadiogroup.indexOfChild(fundsDealRadiogroup.findViewById(i));
                switch (id) {
                    case PLAYGAME:
                        fundsDealContentLinear.setVisibility(View.VISIBLE);
                        maketype = PLAYGAME;
                        break;
                    case TIMENOTE:
                        fundsDealContentLinear.setVisibility(View.VISIBLE);
                        maketype = TIMENOTE;
                        break;
                    case NOTEPAGE:
                        fundsDealContentLinear.setVisibility(View.VISIBLE);
                        maketype = NOTEPAGE;
                        break;
                    case SEXNOTE:
                        fundsDealContentLinear.setVisibility(View.GONE);
                        maketype = SEXNOTE;
                        break;
                }
            }
        });
    }

    @OnClick({R.id.funds_deal_content, R.id.funds_deal_start_yearmd, R.id.funds_deal_start_hour, R.id.funds_deal_start_minute, R.id.funds_deal_end_yearmd, R.id.funds_deal_end_hour, R.id.funds_deal_end_minute, R.id.funds_deal_button})
    public void onViewClicked(View view) {
        String type = null;
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
                break;
            case R.id.funds_deal_button:
                switch (maketype) {
                    case PLAYGAME:
                        type = "ty";
                        ToastUtil.showShort(bastcontext, "体育信息查询");
                        requestMessage(type);
                        break;
                    case TIMENOTE:
                        type = "ss";
                        ToastUtil.showShort(bastcontext, "时时彩信息查询");
                        requestMessage(type);
                        break;
                    case NOTEPAGE:
                        type = "pt";
                        ToastUtil.showShort(bastcontext, "彩票信息查询");
                        requestMessage(type);
                        break;
                    case SEXNOTE:
                        type = "other";
                        ToastUtil.showShort(bastcontext, "六合彩信息查询");
                        requestMessage(type);
                        break;
                }
                break;
        }
    }


    private void requestMessage(final String type) {
        String starttime = fundsDealStartYearmd.getText().toString() + " " + fundsDealStartHour.getText().toString() + ":" + fundsDealStartMinute.getText().toString();
        String endtime = fundsDealEndYearmd.getText().toString() + " " + fundsDealEndHour.getText().toString() + ":" + fundsDealEndMinute.getText().toString();

        String token = userSP.getToken(UserSP.LOGIN_TOKEN);
        int queryCnt = 500;
        int queryId = 0;
        int Subtype = 0;
        if (map.size() != 0) {
            map.clear();
        }

        map.put("start", starttime);
        map.put("queryCnt", queryCnt + "");
        map.put("queryId", queryId + "");
        map.put("end", endtime);
        map.put("Subtype", Subtype + "");
        map.put("type", type);

        instance.apiBet(starttime, queryCnt, queryId + "", endtime, Subtype, type, token, NetUtils.getParamsPro(map).get("signature"), new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {
                AppUtils.getInstance().onRespons("投注记录查询完成");
                LogUtil.e("投注記錄查詢完成");
            }

            @Override
            public void onError(Throwable e) {
                AppUtils.getInstance().onRespons("投注记录查询异常：" + e.getMessage());
                LogUtil.e("投注記錄查詢異常：" + e.getMessage());
            }

            @Override
            public void onNext(ResponseBody queryBetBean) {
                try {
                    String string = queryBetBean.string();
                    JSONObject jsonObject = instance.getJsonObject(string);
                    instance.setToken_SP(jsonObject.optString("token"));
                    int status = jsonObject.optInt("status");
                    if (status == 200) {
                        LogUtil.e("投注記錄查詢成功");
                    } else {
                        ToastUtil.showShort(bastcontext, "投注記錄查詢成功");
                    }

                } catch (IOException e) {
                    AppUtils.getInstance().onRespons("投注记录查询异常：" + e.getMessage());
                    e.printStackTrace();
                } catch (JSONException e) {
                    AppUtils.getInstance().onRespons("投注记录查询异常：" + e.getMessage());
                    e.printStackTrace();
                }
            }
        });
    }
}
