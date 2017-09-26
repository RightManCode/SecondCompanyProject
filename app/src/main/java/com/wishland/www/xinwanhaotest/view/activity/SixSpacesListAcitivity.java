package com.wishland.www.xinwanhaotest.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.wishland.www.xinwanhaotest.R;

import com.wishland.www.xinwanhaotest.api.BaseApi;
import com.wishland.www.xinwanhaotest.controller.adapter.SixSpacesAdapter;
import com.wishland.www.xinwanhaotest.controller.base.BaseRetrofit;
import com.wishland.www.xinwanhaotest.model.Model;
import com.wishland.www.xinwanhaotest.model.bean.LISuccess;
import com.wishland.www.xinwanhaotest.model.bean.SixSpacesBean;
import com.wishland.www.xinwanhaotest.model.sp.UserSP;
import com.wishland.www.xinwanhaotest.utils.ActivityManager;
import com.wishland.www.xinwanhaotest.utils.LogUtil;
import com.wishland.www.xinwanhaotest.utils.NetUtils;
import com.wishland.www.xinwanhaotest.utils.ToastUtil;
import com.wishland.www.xinwanhaotest.utils.Utils_time;
import com.wishland.www.xinwanhaotest.view.MyApplication;
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
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by Administrator on 2017/5/15.
 */

public class SixSpacesListAcitivity extends AutoLayoutActivity {

    @BindView(R.id.top_welcome)
    TextView topWelcome;
    @BindView(R.id.sixspaces_listview)
    ListView sixspacesListview;
    @BindView(R.id.sixspaces_refresh)
    MaterialRefreshLayout sixspacesRefresh;
    @BindView(R.id.empty_layout)
    EmptyLayout emptyLayout;
    private String uri;
    private String title;
    private Model instance;
    private SixSpacesBean sixSpacesBean;
    private SixSpacesAdapter six;
    private UserSP userSP;
    private Map<String, String> UMHotmap = new HashMap();

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sixspaceslistacitivity);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        ActivityManager.getActivityManager().addActivity(this);
        init();
    }

    private void init() {
        uri = getIntent().getStringExtra(BaseApi.SIXURI);
        title = getIntent().getStringExtra(BaseApi.SIXTEXT);
        topWelcome.setText(title);
        instance = Model.getInstance();
        userSP = instance.getUserSP();

        six = new SixSpacesAdapter(SixSpacesListAcitivity.this);

        sixspacesRefresh.autoRefresh();
        sixspacesRefresh.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(final MaterialRefreshLayout materialRefreshLayout) {
                //下拉刷新...
                requestData(uri);

            }
        });

        emptyLayout.setEmptyButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emptyLayout.showLoading();
                sixspacesRefresh.autoRefresh();

            }
        });

        six.setOnItemOnClickListener(new SixSpacesAdapter.OnItemOnClickListener() {
            @Override
            public void setItemCount(int positon) {
                if (UMHotmap.size() != 0) {
                    UMHotmap.clear();
                }
                String name = sixSpacesBean.getData().getGame().get(positon).getName();
                String playPath = sixSpacesBean.getData().getPlayPath();
                String para = sixSpacesBean.getData().getGame().get(positon).getPara();
                String url = playPath + para;
                String replace = url.replace("[token]", userSP.getToken(UserSP.LOGIN_TOKEN));

                UMHotmap.put("游戏", name);
                MyApplication.onUMEvent(SixSpacesListAcitivity.this, "0002", (HashMap<String, String>) UMHotmap, 0);

                toNewActivity(replace);
            }
        });

    }

    @OnClick({R.id.top_fanhui})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.top_fanhui:
                finish();
                break;
        }
    }

    private void requestData(String url) {
        OkHttpClient okhttp = BaseRetrofit.getInstance().client;
        String token = userSP.getToken(UserSP.LOGIN_TOKEN);
        String seconds = Utils_time.getSeconds();
        FormBody.Builder builder = new FormBody.Builder();

        FormBody build = builder
                .add("token", token)
                .add("time", seconds)
                .add("signature", NetUtils.getParamsPro().get("signature"))
                .add("version", BaseApi.VERSION)
                .build();
        Request requestPost = new Request.Builder()
                .url(url)
                .post(build)
                .build();
        okhttp.newCall(requestPost).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        sixspacesRefresh.finishRefresh();
                        emptyLayout.showEmpty();
                    }
                });
                ToastUtil.showUI(SixSpacesListAcitivity.this, "网络异常....");
            }

            @Override
            public void onResponse(Call call, final Response response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        sixspacesRefresh.finishRefresh();
                        emptyLayout.hide();
                    }
                });
                try {
                    String string = response.body().string();
                    JSONObject jsonObject = instance.getJsonObject(string);
                    int status = jsonObject.optInt("status");
                    instance.setToken_SP(jsonObject.optString("token"));
                    LogUtil.e("二级页面请求");
                    if (status == 200) {
                        sixSpacesBean = instance.getGson().fromJson(string, new SixSpacesBean().getClass());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                six.setListData(sixSpacesBean.getData().getGame(), sixSpacesBean.getData().getImgPath());
                                sixspacesListview.setAdapter(six);
                                six.notifyDataSetChanged();
                            }
                        });
                    } else {
                        String errorMsg = jsonObject.optString("errorMsg");
                        if (errorMsg.equals("用户未登录")) {
                            instance.skipLoginActivity(SixSpacesListAcitivity.this, LoginInActivity.class);
                        } else {
                            ToastUtil.showShort(SixSpacesListAcitivity.this, errorMsg);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });


    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginEvent(LISuccess lis) {
        if(lis.getStatus()) {
            sixspacesRefresh.autoRefresh();
        }
    }

    /**
     * @param url 跳转详情页
     */
    private void toNewActivity(String url) {
        Intent intent = new Intent(BaseApi.NEWHTML);
        intent.putExtra(BaseApi.HTML5DATA, url);
        startActivity(intent);
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        ActivityManager.getActivityManager().deleteActivity(this);
    }
}
