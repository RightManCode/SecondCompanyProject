package com.wishland.www.xinwanhaotest.view.activity;

import android.content.Context;
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
import com.wishland.www.xinwanhaotest.controller.adapter.FundsPupupListAdapter;
import com.wishland.www.xinwanhaotest.controller.adapter.PayTextAdapter;
import com.wishland.www.xinwanhaotest.controller.adapter.PayTypeAdapter;
import com.wishland.www.xinwanhaotest.controller.base.BaseRetrofit;
import com.wishland.www.xinwanhaotest.model.Model;
import com.wishland.www.xinwanhaotest.model.bean.HKBean;
import com.wishland.www.xinwanhaotest.model.bean.LISuccess;
import com.wishland.www.xinwanhaotest.model.sp.UserSP;
import com.wishland.www.xinwanhaotest.utils.ActivityManager;
import com.wishland.www.xinwanhaotest.utils.AppUtils;
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
import java.util.ArrayList;
import java.util.Collections;
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
 * Created by Administrator on 2017/7/24.
 */

public class HKActivity extends AutoLayoutActivity {

    @BindView(R.id.top_welcome)
    TextView topWelcome;
    @BindView(R.id.scrollView)
    NestedScrollView scrollView;
    @BindView(R.id.pay_text01)
    TextView payText01;
    @BindView(R.id.pay_tl)
    ListView paytl;
    @BindView(R.id.pay_lt)
    ListView paylt;
    @BindView(R.id.pay_text02)
    TextView payText02;
    @BindView(R.id.pay_userid)
    TextView payUserid;
    @BindView(R.id.pay_EM)
    EditText payEM;
    @BindView(R.id.pay_bank)
    Button payBank;
    @BindView(R.id.pay_data)
    Button payData;
    @BindView(R.id.pay_shi)
    Button payShi;
    @BindView(R.id.pay_fen)
    Button payFen;
    @BindView(R.id.pay_miao)
    Button payMiao;
    @BindView(R.id.pay_type)
    Button payType;
    @BindView(R.id.pay_other_type)
    EditText payothertype;
    @BindView(R.id.pay_address)
    EditText payAddress;
    @BindView(R.id.pay_button)
    Button payButton;
    @BindView(R.id.quest_refresh)
    MaterialRefreshLayout questRefresh;
    @BindView(R.id.empty_layout)
    EmptyLayout emptyLayout;

    private String uri;
    private String title;
    private Model instance;
    private UserSP userSP;
    private PopupWindow DataPupup;
    private View DataView;
    private ListView listview;
    private List<String> ymd;
    public List<String> list;
    public List<String> listbank;
    private HKBean hkBean;
    public final static String HK = "hk";
    private Map<String, String> map;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hkactivity);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        ActivityManager.getActivityManager().addActivity(this);
        Intent intent = getIntent();
        uri = intent.getStringExtra("URI");
        title = intent.getStringExtra("SAVE_TITLE");

        inIt();
        listener();

    }

    private void inIt() {
        map = new HashMap<>();
        scrollView.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
        scrollView.setFocusable(true);
        scrollView.setFocusableInTouchMode(true);
        ymd = Utils_time.getYearMonthDayHourMinuteSecond();
        topWelcome.setText(title);
        payData.setText(ymd.get(0));
        list = new ArrayList<>();
        listbank = new ArrayList<>();
        instance = Model.getInstance();
        userSP = instance.getUserSP();
        payUserid.setText(userSP.getString(UserSP.LOGIN_USERNAME));
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

    @OnClick({R.id.top_fanhui, R.id.pay_bank, R.id.pay_data, R.id.pay_shi, R.id.pay_fen, R.id.pay_miao, R.id.pay_type, R.id.pay_button, R.id.button_pc})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.top_fanhui:
                finish();
                break;
            case R.id.pay_bank:
                if (hkBean != null) {
                    setDataPopup(listbank, payBank, 3);
                }
                break;
            case R.id.pay_data:
                setDataPopup(ymd, payData, 1);
                break;
            case R.id.pay_shi:
                if (list.size() != 0) {
                    list.clear();
                }
                Collections.addAll(list, Utils_time.hourarray);
                setDataPopup(list, payShi, 1);
                break;
            case R.id.pay_fen:
                if (list.size() != 0) {
                    list.clear();
                }
                Collections.addAll(list, Utils_time.minutearray);
                setDataPopup(list, payFen, 1);
                break;
            case R.id.pay_miao:
                if (list.size() != 0) {
                    list.clear();
                }
                Collections.addAll(list, Utils_time.minutearray);
                setDataPopup(list, payMiao, 1);
                break;
            case R.id.pay_type:
                if (hkBean != null) {
                    setDataPopup(hkBean.getData().getType(), payType, 2);
                }
                break;
            case R.id.pay_button:
                String money = payEM.getText().toString().trim();
                String bank = payBank.getText().toString().trim();
                String date = payData.getText().toString();
                String shi = payShi.getText().toString().trim().isEmpty() ? "00" : payShi.getText().toString().trim();
                String fen = payFen.getText().toString().trim().isEmpty() ? "00" : payFen.getText().toString().trim();
                String miao = payMiao.getText().toString().trim().isEmpty() ? "00" : payMiao.getText().toString().trim();
                String type = payType.getText().toString().trim();
                String othertype = payothertype.getText().toString().trim();
                String address = payAddress.getText().toString().trim();

                if (!money.isEmpty()) {
                    if (!bank.isEmpty()) {
                        if (!date.isEmpty()) {
                            if (!type.isEmpty()) {
                                if (!address.isEmpty()) {
                                    putIn(HK, money, bank, date, shi, fen, miao, type, othertype, address);
                                } else {
                                    ToastUtil.showShort(this, "请选择汇款地点...");
                                }
                            } else {
                                ToastUtil.showShort(this, "请选择汇款方式...");
                            }
                        } else {
                            ToastUtil.showShort(this, "请选择汇款日期...");
                        }
                    } else {
                        ToastUtil.showShort(this, "请选择汇款银行...");
                    }
                } else {
                    ToastUtil.showShort(this, "汇款金额不能为空...");
                }
                break;
            case R.id.button_pc:
                instance.toBrowser(this);
                break;
        }
    }

    private void putIn(String pay_type, final String money, final String bank, String date, String shi, String fen,
                       String miao, String type, String othertype, String address) {
        String token = userSP.getToken(UserSP.LOGIN_TOKEN);
        if (map.size() != 0) {
            map.clear();
        }
        if (othertype.isEmpty()) {
            map.put("type", pay_type);
            map.put("v_amount", money);
            map.put("IntoBank", bank);
            map.put("cn_date", date);
            map.put("s_h", shi);
            map.put("s_i", fen);
            map.put("s_s", miao);
            map.put("InType", type);
            map.put("v_site", address);
        } else {
            map.put("type", pay_type);
            map.put("v_amount", money);
            map.put("IntoBank", bank);
            map.put("cn_date", date);
            map.put("s_h", shi);
            map.put("s_i", fen);
            map.put("s_s", miao);
            map.put("InType", type);
            map.put("IntoType", othertype);
            map.put("v_site", address);
        }

        instance.paypal(pay_type, money, bank, date, shi, fen, miao, type, othertype, "", address, token,
                NetUtils.getParamsPro(map).get("signature"), new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                        AppUtils.getInstance().onRespons("汇款提交请求完成");
                        LogUtil.e("汇款提交请求完成");
                    }

                    @Override
                    public void onError(Throwable e) {
                        AppUtils.getInstance().onRespons("汇款提交请求异常：" + e.getMessage());
                        setSuccess("网络异常：", "账号：" + bank, "汇款金额：" + money);
                        LogUtil.e("汇款提交请求异常：" + e.getMessage());
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            String string = responseBody.string();
                            JSONObject jsonObject = instance.getJsonObject(string);
                            int status = jsonObject.optInt("status");
                            instance.setToken_SP(jsonObject.optString("token"));
                            AppUtils.getInstance().onRespons("汇款提交请求成功");
                            LogUtil.e("汇款提交请求成功");
                            if (status == 200) {
                                String errorMsg = jsonObject.optString("errorMsg");
                                payEM.setText("");
                                payAddress.setText("");
                                setSuccess(errorMsg, "账号：" + bank, "汇款金额：" + money);
                            } else {
                                String errorMsg = jsonObject.optString("errorMsg");
                                if (errorMsg.equals("用户未登录")) {
                                    instance.skipLoginActivity(HKActivity.this, LoginInActivity.class);
                                } else {
                                    setSuccess(errorMsg, "账号：" + bank, "汇款金额：" + money);
                                }
                            }
                        } catch (IOException e) {
                            AppUtils.getInstance().onRespons("汇款提交请求异常：" + e.getMessage());
                            e.printStackTrace();
                        } catch (JSONException e) {
                            AppUtils.getInstance().onRespons("汇款提交请求异常：" + e.getMessage());
                            e.printStackTrace();
                        }
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
                LogUtil.e("公司汇款二级页面请求失败：" + e.getMessage());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        questRefresh.finishRefresh();
                        emptyLayout.showEmpty();
                    }
                });
                ToastUtil.showUI(HKActivity.this, "网络异常....");
            }

            @Override
            public void onResponse(Call call, Response response) {
                LogUtil.e("公司汇款二级页面请求成功");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        questRefresh.finishRefresh();
                        emptyLayout.hide();
                    }
                });
                try {
                    final String string = response.body().string();
                    LogUtil.e("公司汇款二级页面请求成功");
                    JSONObject jsonObject = instance.getJsonObject(string);
                    int status = jsonObject.optInt("status");
                    instance.setToken_SP(jsonObject.optString("token"));
                    if (status == 200) {
                        hkBean = instance.getGson().fromJson(string, HKBean.class);
                        if (listbank.size() != 0) {
                            listbank.clear();
                        }
                        List<HKBean.DataBean.BankBean> bank = hkBean.getData().getBank();
                        if (bank != null) {
                            for (int x = 0; x < bank.size(); x++) {
                                listbank.add(hkBean.getData().getBank().get(x).getBank());
                            }
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                payText01.setText(hkBean.getData().getTip1());
                                payText02.setText(hkBean.getData().getTip2());
                                List<HKBean.DataBean.BankBean> bankt = hkBean.getData().getBank();
                                if (bankt != null && bankt.size() != 0) {
                                    paytl.setAdapter(new PayTypeAdapter(bankt));
                                    payBank.setText(hkBean.getData().getBank().get(0).getBank());
                                }

                                List<String> tip = hkBean.getData().getTip();
                                if (tip != null && tip.size() != 0) {
                                    paylt.setAdapter(new PayTextAdapter(tip));
                                }
                            }
                        });
                    } else {
                        String errorMsg = jsonObject.optString("errorMsg");
                        if (errorMsg.equals("用户未登录")) {
                            instance.skipLoginActivity(HKActivity.this, LoginInActivity.class);
                        } else {
                            ToastUtil.showUI(HKActivity.this, errorMsg);
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginEvent(LISuccess lis) {
        if (lis.getStatus()) {
            questRefresh.autoRefresh();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (DataPupup != null) {
        }
        EventBus.getDefault().unregister(this);
        ActivityManager.getActivityManager().deleteActivity(this);
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

    /**
     * 设置popupwindow弹出框
     *
     * @param list
     */
    public void setDataPopup(final List<String> list, final Button button, final int type) {
        WindowManager manager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
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
                String s = list.get(i);
                button.setText(s);
                if (type == 2) {
                    if (s.equals("其它[手动输入]")) {
                        payothertype.setVisibility(View.VISIBLE);
                    } else {
                        payothertype.setVisibility(View.GONE);
                        payothertype.setText("");
                    }
                }


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
