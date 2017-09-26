package com.wishland.www.xinwanhaotest.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.wishland.www.xinwanhaotest.R;

import com.wishland.www.xinwanhaotest.api.BaseApi;
import com.wishland.www.xinwanhaotest.controller.adapter.HomeMoreAdapter;
import com.wishland.www.xinwanhaotest.model.Model;
import com.wishland.www.xinwanhaotest.model.bean.ABean;
import com.wishland.www.xinwanhaotest.model.bean.LISuccess;
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

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import rx.Subscriber;

/**
 * Created by Administrator on 2017/5/26.
 */

public class HomeMoreActivity extends AutoLayoutActivity {

    @BindView(R.id.more_listview)
    ListView homelistview;
    @BindView(R.id.top_welcome)
    TextView topWelcome;
    @BindView(R.id.quest_refresh)
    MaterialRefreshLayout questrefresh;
    @BindView(R.id.empty_layout)
    EmptyLayout emptyLayout;

    private Model instance;
    private HomeMoreAdapter homeMoreAdapter;
    private UserSP userSP;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homemorelist);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        ActivityManager.getActivityManager().addActivity(this);
        init();
    }

    private void init() {
        instance = Model.getInstance();
        userSP = instance.getUserSP();
        homeMoreAdapter = new HomeMoreAdapter(this);

        topWelcome.setText("更多优惠");
        questrefresh.autoRefresh();
        questrefresh.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(final MaterialRefreshLayout materialRefreshLayout) {
                requestMore();
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginEvent(LISuccess lis) {
        if (lis.getStatus()) {
            questrefresh.autoRefresh();
        }
    }

    /**
     * 请求更多
     */
    private void requestMore() {
        String token = userSP.getToken(UserSP.LOGIN_TOKEN);
        instance.apiMore(token, NetUtils.getParamsPro().get("signature"), new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {
                AppUtils.getInstance().onRespons("home-更多数据请求完成");
                questrefresh.finishRefresh();
                LogUtil.e("home-更多数据请求完成");
            }

            @Override
            public void onError(Throwable e) {
                AppUtils.getInstance().onRespons("home-更多数据请求失败：" + e.getMessage());
                questrefresh.finishRefresh();
                emptyLayout.showEmpty();
                LogUtil.e("home-更多数据请求失败：" + e.getMessage());
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                questrefresh.finishRefresh();
                emptyLayout.hide();
                try {
                    String string = responseBody.string();
                    JSONObject jsonObject = instance.getJsonObject(string);
                    int status = jsonObject.optInt("status");
                    instance.setToken_SP(jsonObject.optString("token"));
                    LogUtil.e("home-更多数据请求失败：" + status);
                    if (status == 200) {
                        ABean aBean = instance.getGson().fromJson(string, ABean.class);
                        List<ABean.DataBean> data = aBean.getData();
                        setListView(data);

                    } else {
                        String errorMsg = jsonObject.optString("errorMsg");
                        if (errorMsg.equals("用户未登录")) {
                            instance.skipLoginActivity(HomeMoreActivity.this, LoginInActivity.class);
                        } else {
                            ToastUtil.showShort(HomeMoreActivity.this, errorMsg);
                        }
                    }

                } catch (IOException e) {
                    AppUtils.getInstance().onRespons("home-更多数据请求失败：" + e.getMessage());
                    e.printStackTrace();
                } catch (JSONException e) {
                    AppUtils.getInstance().onRespons("home-更多数据请求失败：" + e.getMessage());
                    e.printStackTrace();
                }
            }
        });
    }


    private void setListView(final List<ABean.DataBean> data) {
        homeMoreAdapter.setHomeMoredata(data);
        homelistview.setAdapter(homeMoreAdapter);
        homelistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(HomeMoreActivity.this, DetailsHtmlPageActivity.class);
                intent.putExtra(BaseApi.HTML5DATA, data.get(i).getUrl());
                HomeMoreActivity.this.startActivity(intent);
            }
        });
        homeMoreAdapter.notifyDataSetChanged();
    }

    @OnClick({R.id.top_fanhui})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.top_fanhui:
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        ActivityManager.getActivityManager().deleteActivity(this);
    }
}
