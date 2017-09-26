package com.wishland.www.xinwanhaotest.view.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


import com.wishland.www.xinwanhaotest.R;
import com.wishland.www.xinwanhaotest.api.BaseApi;
import com.wishland.www.xinwanhaotest.controller.base.BaseRetrofit;
import com.wishland.www.xinwanhaotest.model.Model;
import com.wishland.www.xinwanhaotest.model.bean.LISuccess;
import com.wishland.www.xinwanhaotest.model.bean.UserSaveMoney;
import com.wishland.www.xinwanhaotest.model.sp.UserSP;
import com.wishland.www.xinwanhaotest.utils.ActivityManager;
import com.wishland.www.xinwanhaotest.utils.DensityUtil;
import com.wishland.www.xinwanhaotest.utils.LogUtil;
import com.wishland.www.xinwanhaotest.utils.NetUtils;
import com.wishland.www.xinwanhaotest.utils.ToastUtil;
import com.wishland.www.xinwanhaotest.utils.Utils_time;
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
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;

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
 * 存款-充值页面
 */

public class OnlinePaymentActivity extends AutoLayoutActivity {

    @BindView(R.id.top_welcome)
    TextView topWelcome;
    @BindView(R.id.ot_minmoney)
    TextView ot_minmoney;
    @BindView(R.id.ot_username)
    TextView otUsername;
    @BindView(R.id.ot_current)
    TextView otCurrent;
    @BindView(R.id.ot_money)
    EditText otMoney;
    @BindView(R.id.quest_refresh)
    MaterialRefreshLayout questRefresh;
    @BindView(R.id.empty_layout)
    EmptyLayout emptyLayout;
    @BindView(R.id.top_fanhui)
    ImageView topFanhui;
    @BindView(R.id.ot_button)
    Button otButton;
    @BindView(R.id.ot_radiogroup)
    RadioGroup otradiogroup;
    private Model instance;
    private String i;
    private String title;
    private UserSaveMoney userSaveMoney;
    private UserSP userSP;
    private String code = null;
    public final static String PAY = "pay";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.onlinepayment);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        ActivityManager.getActivityManager().addActivity(this);
        Intent intent = getIntent();
        i = intent.getStringExtra("URI");
        title = intent.getStringExtra("SAVE_TITLE");
        init();
        listener();
    }

    private void listener() {
        emptyLayout.setEmptyButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emptyLayout.showLoading();
                questRefresh.autoRefresh();
            }
        });

        otradiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int i = group.indexOfChild(group.findViewById(checkedId));//得到指定孩子的下标
                code = userSaveMoney.getData().getSelect().get(i).getCode();
            }
        });

        questRefresh.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                requestData(i);
            }
        });

    }

    private void init() {
        topWelcome.setText(title);
        instance = Model.getInstance();
        userSP = instance.getUserSP();

        questRefresh.autoRefresh();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginEvent(LISuccess lis) {
        if (lis.getStatus()) {
            questRefresh.autoRefresh();
        }
    }


    private void requestData(String ei) {
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
                .url(ei)
                .post(build)
                .build();
        okhttp.newCall(requestPost).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                LogUtil.e("存款二级页面请求失败：" + e.getMessage());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        questRefresh.finishRefresh();
                        emptyLayout.showEmpty();
                    }
                });
                otButton.setClickable(false);
                ToastUtil.showUI(OnlinePaymentActivity.this, "网络异常....");
            }

            @Override
            public void onResponse(Call call, Response response) {
                LogUtil.e("存款二级页面请求成功");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        questRefresh.finishRefresh();
                        emptyLayout.hide();
                    }
                });
                try {
                    String string = response.body().string();
                    LogUtil.e("存款二级页面请求成功" + "123456798");
                    JSONObject jsonObject = instance.getJsonObject(string);
                    int status = jsonObject.optInt("status");
                    instance.setToken_SP(jsonObject.optString("token"));
                    if (status == 200) {
                        otButton.setClickable(true);
                        userSaveMoney = instance.getGson().fromJson(string, UserSaveMoney.class);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                otUsername.setText(userSaveMoney.getData().getUser().getUsername());
                                otCurrent.setText(userSaveMoney.getData().getUser().getMoney());
                                ot_minmoney.setText(userSaveMoney.getData().getLimit() + "元");
                                List<UserSaveMoney.DataBean.SelectBean> select = userSaveMoney.getData().getSelect();
                                if (select.size() != 0) {
                                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                    params.setMargins(20, 10, 20, 10);
                                    otradiogroup.removeAllViews();
                                    code = null;
                                    for (int x = 0; x < select.size(); x++) {

                                        String img = select.get(x).getImg();
                                        RadioButton rb = new RadioButton(OnlinePaymentActivity.this);
                                        rb.setButtonDrawable(R.drawable.radiobutton_selector);
                                        rb.setTag(img);
                                        rb.setPadding(10, 0, 10, 0);
                                        rb.setLayoutParams(params);
                                        getImageFromNet(img, rb);
                                        otradiogroup.setPadding(0, DensityUtil.dp2px(OnlinePaymentActivity.this, 15), 0, DensityUtil.dp2px(OnlinePaymentActivity.this, 15));
                                        otradiogroup.addView(rb);
                                    }
                                }
                            }
                        });
                    } else {
                        otButton.setClickable(false);
                        String errorMsg = jsonObject.optString("errorMsg");
                        if (errorMsg.equals("用户未登录")) {
                            instance.skipLoginActivity(OnlinePaymentActivity.this, LoginInActivity.class);
                        } else {
                            ToastUtil.showUI(OnlinePaymentActivity.this, errorMsg);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });


    }

    @OnClick({R.id.top_fanhui, R.id.button_pc, R.id.ot_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.top_fanhui:
                finish();
                break;
            case R.id.button_pc:
                instance.toBrowser(this);
                break;
            case R.id.ot_button:
                String newurl = null;
                String money = otMoney.getText().toString().trim();
                if (money.isEmpty()) {
                    ToastUtil.showShort(this, "请输入存款金额...");
                } else {
                    double v = Double.parseDouble(money);
                    if (v < 10) {
                        ToastUtil.showShort(this, "存款金额最少不低于10元");
                    } else {
                        String url = userSaveMoney.getData().getUrl();
                        if (url.isEmpty()) {
                            ToastUtil.showShort(this, "网络异常,请下拉刷新...");
                        } else {
                            List<UserSaveMoney.DataBean.SelectBean> select = userSaveMoney.getData().getSelect();
                            LogUtil.e("单选数组：" + select.size());
                            if (select.size() == 0) {
                                newurl = url.replace("[token]", userSP.getToken(UserSP.LOGIN_TOKEN)).replace("[money]", money).replace("&code=[code]", "");

                                Intent intent = new Intent(this, DetailsHtmlPageActivity.class);
                                intent.putExtra(BaseApi.HTML5DATA, newurl);
                                LogUtil.e("跳转接口：" + newurl);
                                startActivity(intent);
                                otMoney.setText(""); //清除金额
                            } else {
                                if (code != null) {
                                    newurl = url.replace("[token]", userSP.getToken(UserSP.LOGIN_TOKEN)).replace("[money]", money).replace("[code]", code); //单选

                                    Intent intent = new Intent(this, DetailsHtmlPageActivity.class);
                                    intent.putExtra(BaseApi.HTML5DATA, newurl);
                                    LogUtil.e("跳转接口：" + newurl);
                                    startActivity(intent);
                                    otMoney.setText(""); //清除金额
                                } else {
                                    ToastUtil.showShort(this, "请选择支付方式");
                                }

                            }


                        }
                    }

                }
                break;
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        questRefresh.autoRefresh();
    }

    @Nullable
    private void getImageFromNet(final String l, final RadioButton rb) {
        instance.getExecutorService().execute(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection conn = null;
                String tag = (String) rb.getTag();
                if (tag.equals(l)) {
                    try {
                        URL url = new URL(l);
                        conn = (HttpURLConnection) url.openConnection();
                        conn.setRequestMethod("GET");//链接方式
                        conn.setConnectTimeout(3000);//链接超时等待时间
                        conn.setReadTimeout(3000);   //读取超时等待时间
                        conn.connect();              //开启链接

                        int responseCode = conn.getResponseCode();
                        if (responseCode == 200) {
                            //访问成功
                            InputStream is = conn.getInputStream(); //获得服务器返回的流数据
                            final Bitmap bitmap = BitmapFactory.decodeStream(is); //根据流数据 创建一个bitmap对象

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Drawable drawable = new BitmapDrawable(bitmap);
                                    drawable.setBounds(0, 0, DensityUtil.dp2px(OnlinePaymentActivity.this, 90), DensityUtil.dp2px(OnlinePaymentActivity.this, 30));
                                    rb.setCompoundDrawables(null, null, drawable, null);
                                }
                            });

                        } else {
                            //访问失败
                            LogUtil.e("访问失败===responseCode：" + responseCode);
                        }

                    } catch (ProtocolException e) {
                        e.printStackTrace();
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if (conn != null) {
                            conn.disconnect(); //断开连接
                        }
                    }
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        ActivityManager.getActivityManager().deleteActivity(this);
    }
}
