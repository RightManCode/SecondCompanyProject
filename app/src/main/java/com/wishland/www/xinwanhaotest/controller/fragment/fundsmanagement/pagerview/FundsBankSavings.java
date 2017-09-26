package com.wishland.www.xinwanhaotest.controller.fragment.fundsmanagement.pagerview;


import android.content.Context;
import android.view.View;
import android.widget.ExpandableListView;


import com.wishland.www.xinwanhaotest.R;
import com.wishland.www.xinwanhaotest.controller.adapter.FundsBankExpandAdapter;
import com.wishland.www.xinwanhaotest.controller.base.BaseView;
import com.wishland.www.xinwanhaotest.model.Model;
import com.wishland.www.xinwanhaotest.model.bean.FundsBankBank;
import com.wishland.www.xinwanhaotest.model.sp.UserSP;
import com.wishland.www.xinwanhaotest.utils.AppUtils;
import com.wishland.www.xinwanhaotest.utils.LogUtil;
import com.wishland.www.xinwanhaotest.utils.NetUtils;
import com.wishland.www.xinwanhaotest.utils.ToastUtil;
import com.wishland.www.xinwanhaotest.view.activity.LoginInActivity;
import com.wishland.www.xinwanhaotest.view.activity.MainActivity;
import com.wishland.www.xinwanhaotest.view.customgridview.CustomViewPager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import rx.Subscriber;

/**
 * 资金管理-存款
 */

public class FundsBankSavings extends BaseView {

    @BindView(R.id.fb_listview)
    ExpandableListView fbListview;
    private Model instance;
    public View bankview;
    private FundsBankExpandAdapter fundsBankExpandAdapter;
    public FundsBankBank fundsBankBank;
    private UserSP userSP;


    public FundsBankSavings(Context context, CustomViewPager fundsViewpager) {
        super(context, fundsViewpager);
    }

    @Override
    public View setView() {
        bankview = View.inflate(bastcontext, R.layout.fundsbacksave, null);
        ButterKnife.bind(this, bankview);
        bastViewpager.setObjectForPosition(bankview, 0);
        return bankview;
    }

    @Override
    public void initData() {
        super.initData();
        instance = Model.getInstance();
        userSP = instance.getUserSP();
        fbListview.setFocusable(false);

        //设置Group不可以点击
        fbListview.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;//返回true,表示不可点击
            }
        });

        //设置child点击事件
        fbListview.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                return false;
            }
        });

        fundsBankExpandAdapter = new FundsBankExpandAdapter(bastcontext);

        request();
    }

    private void setExpandlistview(FundsBankBank fundsBankBank) {
        fundsBankExpandAdapter.setExpandableData(fundsBankBank);
        fbListview.setAdapter(fundsBankExpandAdapter);
        //展开所有分组
        int groupCount = fbListview.getCount();
        for (int i = 0; i < groupCount; i++) {
            fbListview.expandGroup(i);
        }
        fbListview.setGroupIndicator(null);//将控件默认的左边箭头去掉

        fundsBankExpandAdapter.notifyDataSetChanged();
    }

    public void request() {
        LogUtil.e("存款请求");
        String token = userSP.getToken(UserSP.LOGIN_TOKEN);
        instance.apiRecharge(token, NetUtils.getParamsPro().get("signature"), new Subscriber<ResponseBody>() {

            @Override
            public void onCompleted() {
                AppUtils.getInstance().onRespons("支付请求完成");
                LogUtil.e("支付请求完成");
            }

            @Override
            public void onError(Throwable e) {
                AppUtils.getInstance().onRespons("支付请求异常：" + e.getMessage());
                LogUtil.e("支付请求异常：" + e.getMessage());
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    String string = responseBody.string();
                    AppUtils.getInstance().onRespons("支付请求成功");
                    LogUtil.e("支付请求成功");
                    JSONObject jsonObject = instance.getJsonObject(string);
                    instance.setToken_SP(jsonObject.optString("token"));
                    int status = jsonObject.optInt("status");
                    if (status == 200) {
                        fundsBankBank = instance.getGson().fromJson(string, FundsBankBank.class);
                        setExpandlistview(fundsBankBank);
                    } else {
                        String errorMsg = jsonObject.optString("errorMsg");
                        if (errorMsg.equals("用户未登录")) {
                            instance.skipLoginActivity((MainActivity) bastcontext, LoginInActivity.class);
                        } else {
                            ToastUtil.showShort(bastcontext, errorMsg);
                        }
                    }
                } catch (IOException e) {
                    AppUtils.getInstance().onRespons("支付请求异常：" + e.getMessage());
                    e.printStackTrace();
                } catch (JSONException e) {
                    AppUtils.getInstance().onRespons("支付请求异常：" + e.getMessage());
                    e.printStackTrace();
                }
            }
        });
    }
}
