package com.wishland.www.xinwanhaotest.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;


import com.wishland.www.xinwanhaotest.R;
import com.wishland.www.xinwanhaotest.controller.adapter.AccountBindPupupListAdapter;
import com.wishland.www.xinwanhaotest.model.Model;
import com.wishland.www.xinwanhaotest.model.bean.AccountBankBean;
import com.wishland.www.xinwanhaotest.model.sp.UserSP;
import com.wishland.www.xinwanhaotest.utils.ActivityManager;
import com.wishland.www.xinwanhaotest.utils.AppUtils;
import com.wishland.www.xinwanhaotest.utils.LogUtil;
import com.wishland.www.xinwanhaotest.utils.NetUtils;
import com.wishland.www.xinwanhaotest.utils.ToastUtil;
import com.wishland.www.xinwanhaotest.view.customgridview.EmptyLayout;
import com.wishland.www.xinwanhaotest.view.refresh.MaterialRefreshLayout;
import com.wishland.www.xinwanhaotest.view.refresh.MaterialRefreshListener;
import com.zhy.autolayout.AutoLayoutActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import rx.Subscriber;

/**
 * Created by Administrator on 2017/5/25.
 */

public class AccountRequestBankActivity extends AutoLayoutActivity {

    @BindView(R.id.account_banklist)
    ListView accountbanklist;
    @BindView(R.id.top_welcome)
    TextView topWelcome;
    @BindView(R.id.quest_refresh)
    MaterialRefreshLayout questrefresh;
    @BindView(R.id.empty_layout)
    EmptyLayout emptyLayout;
    private Model instance;
    private AccountBindPupupListAdapter fundsPupupListAdapter;
    private List<AccountBankBean.DataBean> data;
    private UserSP userSP;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accountbind_bank);
        ButterKnife.bind(this);
        ActivityManager.getActivityManager().addActivity(this);
        init();
    }

    private void init() {
        topWelcome.setText(R.string.select_bank);
        instance = Model.getInstance();
        userSP = instance.getUserSP();
        data = new ArrayList<>();
        questrefresh.autoRefresh();
        questrefresh.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(final MaterialRefreshLayout materialRefreshLayout) {
                requestBank();
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


    private void requestBank() {
        String token = userSP.getToken(UserSP.LOGIN_TOKEN);
        instance.apiBank(token, NetUtils.getParamsPro().get("signature"), new Subscriber<ResponseBody>() {


            @Override
            public void onCompleted() {
                questrefresh.finishRefresh();
                AppUtils.getInstance().onRespons("银行信息请求完成");
                LogUtil.e("银行信息请求完成");
            }

            @Override
            public void onError(Throwable e) {
                questrefresh.finishRefresh();
                emptyLayout.showEmpty();
                if (fundsPupupListAdapter != null) {
                    data.clear();
                    setListView(data);
                    fundsPupupListAdapter.notifyDataSetChanged();
                }
                AppUtils.getInstance().onRespons("银行信息请求异常：" + e.getMessage());
                LogUtil.e("银行信息请求异常");
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                questrefresh.finishRefresh();
                emptyLayout.hide();
                try {
                    String string = responseBody.string();
                    JSONObject jsonObject = instance.getJsonObject(string);
                    instance.setToken_SP(jsonObject.optString("token"));
                    int status = jsonObject.optInt("status");
                    if (status == 200) {
                        AccountBankBean accountBankBean = instance.getGson().fromJson(string, AccountBankBean.class);
                        data = accountBankBean.getData();
                        setListView(data);
                    } else {
                        String errorMsg = jsonObject.optString("errorMsg");
                        if (errorMsg.equals("用户未登录")) {
                            instance.skipLoginActivity(AccountRequestBankActivity.this, LoginInActivity.class);
                        } else {
                            ToastUtil.showShort(AccountRequestBankActivity.this, errorMsg);
                        }
                    }
                    AppUtils.getInstance().onRespons("银行信息请求成功：" );
                    LogUtil.e("银行信息请求成功");
                } catch (IOException e) {
                    AppUtils.getInstance().onRespons("银行信息请求失败：" + e.getMessage());
                    e.printStackTrace();
                } catch (JSONException e) {
                    AppUtils.getInstance().onRespons("银行信息请求失败：" + e.getMessage());
                    e.printStackTrace();
                }
            }
        });
    }


    private void setListView(final List<AccountBankBean.DataBean> listView) {
        fundsPupupListAdapter = new AccountBindPupupListAdapter(listView, AccountRequestBankActivity.this);
        accountbanklist.setAdapter(fundsPupupListAdapter);
        fundsPupupListAdapter.setOnItemOnClickListener(new AccountBindPupupListAdapter.OnItemOnClickListener() {
            @Override
            public void setItemCount(int positon) {
                Intent intent = new Intent();
                intent.putExtra("Bank", listView.get(positon).getBankname());
                intent.putExtra("BankCode", listView.get(positon).getBankno());
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.getActivityManager().deleteActivity(this);
    }

    @OnClick(R.id.top_fanhui)
    public void onViewClicked() {
        finish();
    }
}
