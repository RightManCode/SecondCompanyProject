package com.wishland.www.xinwanhaotest.view.activity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.wishland.www.xinwanhaotest.R;

import com.wishland.www.xinwanhaotest.api.BaseApi;
import com.wishland.www.xinwanhaotest.controller.adapter.AlipayTypeAdapter;
import com.wishland.www.xinwanhaotest.controller.adapter.FundsPupupListAdapter;
import com.wishland.www.xinwanhaotest.controller.adapter.PayTextAdapter;
import com.wishland.www.xinwanhaotest.controller.base.BaseRetrofit;
import com.wishland.www.xinwanhaotest.model.Model;
import com.wishland.www.xinwanhaotest.model.bean.AlipayBean;
import com.wishland.www.xinwanhaotest.model.bean.LISuccess;
import com.wishland.www.xinwanhaotest.model.sp.UserSP;
import com.wishland.www.xinwanhaotest.utils.ActivityManager;
import com.wishland.www.xinwanhaotest.utils.AppUtils;
import com.wishland.www.xinwanhaotest.utils.DensityUtil;
import com.wishland.www.xinwanhaotest.utils.LogUtil;
import com.wishland.www.xinwanhaotest.utils.NetUtils;
import com.wishland.www.xinwanhaotest.utils.ToastUtil;
import com.wishland.www.xinwanhaotest.utils.Utils_time;
import com.wishland.www.xinwanhaotest.view.customgridview.CustomListView;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import okhttp3.ResponseBody;
import rx.Subscriber;

/**
 * Created by Administrator on 2017/7/25.
 */

public class AlipayActivity extends AutoLayoutActivity {

    @BindView(R.id.top_welcome)
    TextView topWelcome;
    @BindView(R.id.alipay_type_name)
    TextView alipaytypename;
    @BindView(R.id.alipay_setid)
    TextView alipaysetid;
    @BindView(R.id.alipay_setid_name)
    TextView alipaysetidname;
    @BindView(R.id.scrollView)
    NestedScrollView scrollView;
    @BindView(R.id.alipay_text01)
    TextView alipayText01;
    @BindView(R.id.alipay_tl)
    CustomListView alipayTl;
    @BindView(R.id.alipay_lt)
    CustomListView alipayLt;
    @BindView(R.id.alipay_text02)
    TextView alipayText02;
    @BindView(R.id.alipay_username)
    TextView alipayUsername;
    @BindView(R.id.alipay_EM)
    EditText alipayEM;
    @BindView(R.id.alipay_id)
    Button alipayId;
    @BindView(R.id.alipay_idname)
    EditText alipayIdname;
    @BindView(R.id.alipay_type)
    EditText alipayType;
    @BindView(R.id.alipay_data)
    Button alipayData;
    @BindView(R.id.alipay_button)
    Button alipayButton;
    @BindView(R.id.quest_refresh)
    MaterialRefreshLayout questRefresh;
    @BindView(R.id.empty_layout)
    EmptyLayout emptyLayout;

    private String uri;
    private String title;
    private Model instance;
    private UserSP userSP;
    private List<String> ymd;
    private PopupWindow DataPupup;
    private View DataView;
    private ListView listview;
    public List<String> listbank;
    private AlipayBean alipayBean;
    public final static String ALIPAY = "alipay";
    public final static String WEIXIN = "weixin";
    public final static String CAIFUTONG = "caifutong";
    private String type_id;
    private String text_title;
    private Map<String, String> map;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alipayactivity);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        ActivityManager.getActivityManager().addActivity(this);
        Intent intent = getIntent();
        type_id = intent.getStringExtra("TYPE");
        text_title = intent.getStringExtra("TITLE");
        uri = intent.getStringExtra("URI");
        title = intent.getStringExtra("SAVE_TITLE");

        inIt();
        listener();

    }

    private void inIt() {
        scrollView.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
        scrollView.setFocusable(true);
        scrollView.setFocusableInTouchMode(true);
        map = new HashMap<>();
        ymd = Utils_time.getYearMonthDayHourMinuteSecond();
        topWelcome.setText(title);
        alipaytypename.setText(text_title + "转账");
        alipaysetid.setText(text_title + "账户");
        alipaysetidname.setText(text_title + "姓名");
        alipayData.setText(ymd.get(0).toString());

        listbank = new ArrayList<>();
        instance = Model.getInstance();
        userSP = instance.getUserSP();
        alipayUsername.setText(userSP.getString(UserSP.LOGIN_USERNAME));
        questRefresh.autoRefresh();

    }

    private void listener() {
        emptyLayout.setEmptyButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emptyLayout.showLoading();
                questRefresh.autoRefresh();
            }
        });

        questRefresh.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                requestData(uri);
            }
        });


        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.requestFocusFromTouch();
                return false;
            }
        });
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
                LogUtil.e("支付宝二级页面请求失败：" + e.getMessage());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        questRefresh.finishRefresh();
                        emptyLayout.showEmpty();
                    }
                });
                ToastUtil.showUI(AlipayActivity.this, "网络异常....");
            }

            @Override
            public void onResponse(Call call, Response response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        questRefresh.finishRefresh();
                        emptyLayout.hide();
                    }
                });
                try {
                    final String string = response.body().string();
                    LogUtil.e("支付宝二级页面请求成功");
                    JSONObject jsonObject = instance.getJsonObject(string);
                    int status = jsonObject.optInt("status");
                    instance.setToken_SP(jsonObject.optString("token"));
                    if (status == 200) {
                        alipayBean = instance.getGson().fromJson(string, AlipayBean.class);

                        if (listbank.size() != 0) {
                            listbank.clear();
                        }
                        List<AlipayBean.DataBean.AccountBean> account = alipayBean.getData().getAccount();
                        if (account != null) {
                            for (int x = 0; x < account.size(); x++) {
                                listbank.add(alipayBean.getData().getAccount().get(x).getAlipayName());
                            }
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                alipayText01.setText(alipayBean.getData().getTip1());
                                alipayText02.setText(alipayBean.getData().getTip2());
                                List<AlipayBean.DataBean.AccountBean> account = alipayBean.getData().getAccount();
                                List<String> tip = alipayBean.getData().getTip();
                                if (account != null && account.size() != 0) {
                                    alipayTl.setAdapter(new AlipayTypeAdapter(AlipayActivity.this, account));
                                    alipayId.setText(alipayBean.getData().getAccount().get(0).getAlipayName());
                                }

                                if (tip != null && tip.size() != 0) {
                                    alipayLt.setAdapter(new PayTextAdapter(tip));
                                }
                            }
                        });
                    } else {
                        String errorMsg = jsonObject.optString("errorMsg");
                        if (errorMsg.equals("用户未登录")) {
                            instance.skipLoginActivity(AlipayActivity.this, LoginInActivity.class);
                        } else {
                            ToastUtil.showUI(AlipayActivity.this, errorMsg);
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

    @OnClick({R.id.top_fanhui, R.id.alipay_id, R.id.alipay_data, R.id.button_pc, R.id.alipay_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.top_fanhui:
                finish();
                break;
            case R.id.alipay_id:
                if (alipayBean != null) {
                    setDataPopup(listbank, alipayId, 3);
                }
                break;
            case R.id.alipay_data:
                setDataPopup(ymd, alipayData, 1);
                break;
            case R.id.button_pc:
                instance.toBrowser(this);
                break;
            case R.id.alipay_button:
                String money = alipayEM.getText().toString().trim();
                String id = alipayId.getText().toString().trim();
                String idname = alipayIdname.getText().toString().trim();
                String type = alipayType.getText().toString().trim();
                String date = alipayData.getText().toString();

                if (!money.isEmpty()) {
                    if (!id.isEmpty()) {
                        if (!idname.isEmpty()) {
                            if (!type.isEmpty()) {
                                if (!date.isEmpty()) {
                                    putIn(type_id, money, date, type, id);
                                } else {
                                    ToastUtil.showShort(this, "请选择转账日期...");
                                }
                            } else {
                                ToastUtil.showShort(this, "请输入订单后四位...");
                            }
                        } else {
                            ToastUtil.showShort(this, "请输入转账姓名...");
                        }
                    } else {
                        ToastUtil.showShort(this, "请选择转账账号...");
                    }
                } else {
                    ToastUtil.showShort(this, "转账金额不能为空...");
                }
                break;
        }
    }

    private void putIn(String pay_type, final String money, String date, String type, final String name) {
        String token = userSP.getToken(UserSP.LOGIN_TOKEN);
        if (map.size() != 0) {
            map.clear();
        }
        map.put("type", pay_type);
        map.put("v_amount", money);
        map.put("IntoBank", name);
        map.put("cn_date", date);
        map.put("IntoType", type);

        instance.paypal(pay_type, money, name, date, "", "", "", "", type, "", "", token, NetUtils.getParamsPro(map).get("signature")
                , new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                        AppUtils.getInstance().onRespons("支付宝提交请求完成");
                        LogUtil.e("支付宝提交请求完成");
                    }

                    @Override
                    public void onError(Throwable e) {
                        AppUtils.getInstance().onRespons("支付宝请求提交异常：" + e.getMessage());
                        setSuccess("网络异常：", "账号：" + name, "汇款金额：" + money);
                        LogUtil.e("支付宝提交请求异常：" + e.getMessage());
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            String string = responseBody.string();
                            JSONObject jsonObject = instance.getJsonObject(string);
                            int status = jsonObject.optInt("status");
                            instance.setToken_SP(jsonObject.optString("token"));
                            if (status == 200) {
                                String errorMsg = jsonObject.optString("errorMsg");
                                alipayEM.setText("");
                                alipayIdname.setText("");
                                alipayType.setText("");
                                setSuccess(errorMsg, "账号：" + name, "汇款金额：" + money);
                            } else {
                                String errorMsg = jsonObject.optString("errorMsg");
                                if (errorMsg.equals("用户未登录")) {
                                    instance.skipLoginActivity(AlipayActivity.this, LoginInActivity.class);
                                } else {
                                    setSuccess(errorMsg, "账号：" + name, "汇款金额：" + money);
                                }
                            }
                        } catch (IOException e) {
                            AppUtils.getInstance().onRespons("支付宝请求提交异常：" + e.getMessage());
                            e.printStackTrace();
                        } catch (JSONException e) {
                            AppUtils.getInstance().onRespons("支付宝请求提交异常：" + e.getMessage());
                            e.printStackTrace();
                        }
                    }
                });

    }

    private void setSuccess(String type, String fromto, String money) {
        View view = View.inflate(this, R.layout.line_success, null);
        TextView mLine_success_message = (TextView) view.findViewById(R.id.line_success_message);
        TextView mLine_success_fromto = (TextView) view.findViewById(R.id.line_success_from_to);
        TextView mLine_success_money = (TextView) view.findViewById(R.id.line_success_money);
        TextView tv_line = (TextView) view.findViewById(R.id.tv_line);
        LinearLayout linear = (LinearLayout) view.findViewById(R.id.ll_linear);

        linear.setVisibility(View.GONE);
        tv_line.setVisibility(View.VISIBLE);

        mLine_success_message.setText(type);
        mLine_success_fromto.setText(fromto);
        mLine_success_money.setText(money);

        final AlertDialog.Builder alertDialog = instance.getAlertDialog(this);
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginEvent(LISuccess lis) {
        if (lis.getStatus()) {
            questRefresh.autoRefresh();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        ActivityManager.getActivityManager().deleteActivity(this);
    }

    /**
     * 设置popupwindow弹出框
     *
     * @param list
     */
    public void setDataPopup(final List<String> list, final Button button, int type) {
        WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        int height = display.getHeight() / 2;

        if (DataPupup == null) {
            DataView = View.inflate(this, R.layout.funds_popupwindow, null);
            DataPupup = new PopupWindow(DataView);
            DataView.invalidate();
            listview = (ListView) DataView.findViewById(R.id.funds_popupview);
        }


        DataPupup.setWidth(button.getWidth());
        switch (type) {
            case 1:
                DataPupup.setHeight(DensityUtil.dp2px(this, 230));
                break;
            case 2:
                DataPupup.setHeight(DensityUtil.dp2px(this, 130));
                break;
            case 3:
                DataPupup.setHeight(DensityUtil.dp2px(this, 100));
                break;
        }

        DataView.invalidate();
        if (DataPupup.isShowing()) {
            DataPupup.dismiss();
        }

        //设置适配器
        FundsPupupListAdapter fundsPupupListAdapter = new FundsPupupListAdapter(list);
        listview.setAdapter(fundsPupupListAdapter);
        //设置点击监听
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                button.setText(list.get(i));
                DataPupup.dismiss();

            }
        });

        ColorDrawable colorDrawable = new ColorDrawable(this.getResources().getColor(R.color.home_popupgrid_item_bg));
        DataPupup.setBackgroundDrawable(colorDrawable);
        DataPupup.setFocusable(true);
        int[] position = new int[2];
        button.getLocationOnScreen(position);
        if (position[1] > height) {
            DataPupup.showAsDropDown(button, 0, -DataPupup.getHeight() - button.getHeight());
        } else {
            DataPupup.showAsDropDown(button, 0, 0);
        }

        DataPupup.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                DataPupup.dismiss();
            }
        });
    }
}
