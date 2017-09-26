package com.wishland.www.xinwanhaotest.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.wishland.www.xinwanhaotest.R;

import com.wishland.www.xinwanhaotest.controller.adapter.FundsCathecticDealAdapter;
import com.wishland.www.xinwanhaotest.model.Model;
import com.wishland.www.xinwanhaotest.model.bean.LISuccess;
import com.wishland.www.xinwanhaotest.model.bean.MessageType;
import com.wishland.www.xinwanhaotest.model.bean.QueryMessageListBean;
import com.wishland.www.xinwanhaotest.model.sp.UserSP;
import com.wishland.www.xinwanhaotest.utils.ActivityManager;
import com.wishland.www.xinwanhaotest.utils.AppUtils;
import com.wishland.www.xinwanhaotest.utils.LogUtil;
import com.wishland.www.xinwanhaotest.utils.NetUtils;
import com.wishland.www.xinwanhaotest.utils.ToastUtil;
import com.wishland.www.xinwanhaotest.view.refresh.MaterialRefreshLayout;
import com.wishland.www.xinwanhaotest.view.refresh.MaterialRefreshListener;
import com.zhy.autolayout.AutoLayoutActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import rx.Subscriber;

/**
 * Created by Administrator on 2017/5/21.
 */

public class DetailQuestDealActivity extends AutoLayoutActivity {


    @BindView(R.id.top_welcome)
    TextView topWelcome;
    @BindView(R.id.detail_quest_listview)
    ListView fundslistview;
    @BindView(R.id.quest_refresh)
    MaterialRefreshLayout questrefresh;

    private String starttime;
    private String endtime;
    private int type;
    private List<QueryMessageListBean> list;
    private Model instance;
    private String name;
    private String Subtype;
    private FundsCathecticDealAdapter fundsCathecticDealAdapter;
    private UserSP userSP;
    private Map<String, String> map;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailquestdata);
        ActivityManager.getActivityManager().addActivity(this);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        init();

    }

    private void init() {
        list = new ArrayList<>();
        map = new HashMap<>();
        instance = Model.getInstance();
        userSP = instance.getUserSP();
        MessageType message = (MessageType) getIntent().getSerializableExtra("MESSAGE");
        starttime = message.getStarttime();
        endtime = message.getEndtime();
        type = message.getType();
        name = message.getName();
        topWelcome.setText(name);
        Subtype = message.getContent();

        fundsCathecticDealAdapter = new FundsCathecticDealAdapter(DetailQuestDealActivity.this, type);

        questrefresh.autoRefresh();
        questrefresh.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(final MaterialRefreshLayout materialRefreshLayout) {
                if (list.size() != 0) {
                    list.clear();
                }
                requestMessage(type);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginEvent(LISuccess lis) {
        if (lis.getStatus()) {
            questrefresh.autoRefresh();
        }
    }


    private void requestMessage(final int type) {
        String token = userSP.getToken(UserSP.LOGIN_TOKEN);
        int queryCnt = 500;
        int queryId = 0;
        if (map.size() != 0) {
            map.clear();
        }
        map.put("start", starttime);
        map.put("queryCnt", queryCnt + "");
        map.put("queryId", queryId + "");
        map.put("end", endtime);
        map.put("subtype", Subtype);
        map.put("type", type + "");

        instance.apiMessage(starttime, queryCnt, queryId + "",
                endtime, Subtype, type, token, NetUtils.getParamsPro(map).get("signature")
                , new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                        AppUtils.getInstance().onRespons("交易记录查询完成");
                        questrefresh.finishRefresh();
                        LogUtil.e("交易記錄查詢完成");
                    }

                    @Override
                    public void onError(Throwable e) {
                        AppUtils.getInstance().onRespons("交易记录查询失败：" + e.getMessage());
                        questrefresh.finishRefresh();
                        ToastUtil.showShort(DetailQuestDealActivity.this, "网络异常");
                        LogUtil.e("交易記錄查詢異常：" + e.getMessage());
                    }

                    @Override
                    public void onNext(ResponseBody json) {
                        questrefresh.finishRefresh();
                        try {
                            final String string = json.string();
                            final JSONObject jsonObject = instance.getJsonObject(string);
                            int status = jsonObject.optInt("status");
                            instance.setToken_SP(jsonObject.optString("token"));
                            AppUtils.getInstance().onRespons("交易记录查询成功");
                            LogUtil.e("交易記錄查詢成功");
                            if (status == 200) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        setDataItem(jsonObject, type);
                                        topWelcome.setText(name + "  ( " + list.size() + "条记录 )");
                                        fundsCathecticDealAdapter.setData(list);
                                        fundslistview.setAdapter(fundsCathecticDealAdapter);
                                    }
                                });
                            } else {
                                String errorMsg = jsonObject.optString("errorMsg");
                                if (errorMsg.equals("用户未登录")) {
                                    instance.skipLoginActivity(DetailQuestDealActivity.this, LoginInActivity.class);
                                } else {
                                    ToastUtil.showShort(DetailQuestDealActivity.this, errorMsg);
                                }
                            }

                        } catch (IOException e) {
                            AppUtils.getInstance().onRespons("交易记录查询失败：" + e.getMessage());
                            e.printStackTrace();
                        } catch (JSONException e) {
                            AppUtils.getInstance().onRespons("交易记录查询失败：" + e.getMessage());
                            e.printStackTrace();
                        }

                    }
                });
    }

    private void setDataItem(final JSONObject json, final int type) {

        JSONObject data = json.optJSONObject("data");
        switch (type) {
            case 0:
            case 1:
            case 2:
                JSONArray getcommonList = data.optJSONArray("commonList");
                if (getcommonList == null) {
                    ToastUtil.showShort(DetailQuestDealActivity.this, name + "无记录");
                } else {
                    list = instance.getGson().fromJson(getcommonList.toString(), new TypeToken<List<QueryMessageListBean>>() {
                    }.getType());
                }
                break;
            case 3:
                JSONArray otherList = data.optJSONArray("otherList");
                if (otherList == null) {
                    ToastUtil.showShort(DetailQuestDealActivity.this, name + "无记录");
                } else {
                    list = instance.getGson().fromJson(otherList.toString(), new TypeToken<List<QueryMessageListBean>>() {
                    }.getType());
                }
                break;
        }
    }

    @OnClick(R.id.top_fanhui)
    public void onViewClicked() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        ActivityManager.getActivityManager().deleteActivity(this);
    }
}
