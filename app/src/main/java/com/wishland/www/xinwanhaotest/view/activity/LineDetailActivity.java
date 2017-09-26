package com.wishland.www.xinwanhaotest.view.activity;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.wishland.www.xinwanhaotest.R;
import com.wishland.www.xinwanhaotest.controller.adapter.FundsLinePupupListAdapter;
import com.wishland.www.xinwanhaotest.controller.adapter.LineDetailAdapter;
import com.wishland.www.xinwanhaotest.model.Model;
import com.wishland.www.xinwanhaotest.model.bean.LISuccess;
import com.wishland.www.xinwanhaotest.model.bean.LineDetailBean;
import com.wishland.www.xinwanhaotest.model.bean.LineMessage;
import com.wishland.www.xinwanhaotest.model.bean.LineMoneyBean;
import com.wishland.www.xinwanhaotest.model.sp.UserSP;
import com.wishland.www.xinwanhaotest.utils.ActivityManager;
import com.wishland.www.xinwanhaotest.utils.AppUtils;
import com.wishland.www.xinwanhaotest.utils.DensityUtil;
import com.wishland.www.xinwanhaotest.utils.LogUtil;
import com.wishland.www.xinwanhaotest.utils.NetUtils;
import com.wishland.www.xinwanhaotest.utils.ToastUtil;
import com.wishland.www.xinwanhaotest.utils.Utils_time;
import com.wishland.www.xinwanhaotest.view.customgridview.EmptyLayout;
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
import okhttp3.ResponseBody;
import rx.Subscriber;

public class LineDetailActivity extends AutoLayoutActivity {
    @BindView(R.id.top_welcome)
    TextView topWelcome;
    @BindView(R.id.ed_s_year)
    Button edSYear;
    @BindView(R.id.ed_s_hour)
    Button edSHour;
    @BindView(R.id.ed_s_minute)
    Button edSMinute;
    @BindView(R.id.ed_e_year)
    Button edEYear;
    @BindView(R.id.ed_e_hour)
    Button edEHour;
    @BindView(R.id.ed_e_minute)
    Button edEMinute;
    @BindView(R.id.ed_from)
    Button edFrom;
    @BindView(R.id.ed_to)
    Button edTo;
    @BindView(R.id.ed_listview)
    ListView edListview;
    @BindView(R.id.empty_layout)
    EmptyLayout emptyLayout;


    private Model instance;
    private LineDetailAdapter lineDetailAdapter;
    private UserSP userSP;
    private PopupWindow DataPupup;
    private View DataView;
    private ListView listview;
    private List<LineMoneyBean.DataBean.WalletBean> wallet;
    private List<String> wtype;
    private List<String> year;
    public List<String> list_hm;
    private int FROM = 0;
    private int TO = 0;
    private LineMessage message;
    private List<LineDetailBean.DataBean.ListBean> list;
    private String istime;
    private String ietime;
    private Map<String, String> map;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.line_detailquestdata);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        ActivityManager.getActivityManager().addActivity(this);
        init();
        fundsLineRequest();

    }

    private void init() {
        year = Utils_time.getYearMonthDayHourMinuteSecond();
        wtype = new ArrayList<>();
        map = new HashMap<>();
        list_hm = new ArrayList<>();
        instance = Model.getInstance();
        userSP = instance.getUserSP();
        lineDetailAdapter = new LineDetailAdapter(this);
        create();
    }

    private void create() {
        message = (LineMessage) getIntent().getSerializableExtra("LINE_MESSAGE");
        String startyear = message.getStartyear();
        String starthour = message.getStarthour();
        String startmin = message.getStartmin();
        String endyear = message.getEndyear();
        String endhour = message.getEndhour();
        String endmin = message.getEndmin();
        istime = startyear + " " + starthour + ":" + startmin;
        ietime = endyear + " " + endhour + ":" + endmin;
        requestMessage(istime, ietime, "", "", 1);
        edSYear.setHint(startyear);
        edSHour.setHint(starthour);
        edSMinute.setHint(startmin);
        edEYear.setHint(endyear);
        edEHour.setHint(endhour);
        edEMinute.setHint(endmin);
        topWelcome.setText("额度转换记录");
    }

    private void requestMessage(final String stime, final String etime, final String from, String to, final int type) {
        emptyLayout.showLoading();
        if (list != null) {
            if (list.size() != 0) {
                list.clear();
                lineDetailAdapter.notifyDataSetChanged();
            }
        }

        String token = userSP.getToken(UserSP.LOGIN_TOKEN);
        int queryCnt = 500;
        String queryId = "0";

        if (map.size() != 0) {
            map.clear();
        }

        if (type == 1) {
            map.put("queryid", queryId);
            map.put("queryCnt", queryCnt + "");
            map.put("start", stime);
            map.put("end", etime);
        } else {
            map.put("queryid", queryId);
            map.put("queryCnt", queryCnt + "");
            map.put("start", stime);
            map.put("end", etime);
            map.put("from", from);
            map.put("to", to);
        }

        instance.apiLine(queryId, queryCnt, stime, etime, from, to, token, NetUtils.getParamsPro(map).get("signature"), new Subscriber<ResponseBody>() {


            @Override
            public void onCompleted() {
                emptyLayout.hide();
                AppUtils.getInstance().onEvent("在额度转换记录界面，点击查询信息按钮", "转换信息查询完成！");
                AppUtils.getInstance().onRespons("转换信息查询完成");
                LogUtil.e("转换信息查询完成");
            }

            @Override
            public void onError(Throwable e) {
                AppUtils.getInstance().onEvent("在额度转换记录界面，点击查询信息按钮", "转换信息查询异常！");
                AppUtils.getInstance().onRespons("转换信息查询异常：" + e.getMessage());
                ToastUtil.showShort(LineDetailActivity.this, "网络异常");
                LogUtil.e("转换信息查询异常：" + e.getMessage());
            }

            @Override
            public void onNext(ResponseBody responseBody) {

                try {
                    String string = responseBody.string();
                    AppUtils.getInstance().onRespons("转换信息查询成功");
                    AppUtils.getInstance().onEvent("在额度转换记录界面，点击查询信息按钮", "转换信息查询成功！");
                    LogUtil.e("转换信息查询成功");
                    final JSONObject jsonObject = instance.getJsonObject(string);
                    instance.setToken_SP(jsonObject.optString("token"));
                    int status = jsonObject.optInt("status");
                    if (status == 200) {
                        if (jsonObject.optJSONObject("data").optJSONArray("list") == null) {

                            if (type == 2) {
                                ToastUtil.showShort(LineDetailActivity.this, wtype.get(FROM) + "->" + wtype.get(TO) + "无记录...");
                            } else {
                                ToastUtil.showShort(LineDetailActivity.this, "近期无转换记录...");
                            }

                        } else {
                            final LineDetailBean lineDetailBean = instance.getGson().fromJson(string, LineDetailBean.class);
                            list = lineDetailBean.getData().getList();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    lineDetailAdapter.setData(list);
                                    edListview.setAdapter(lineDetailAdapter);
                                }
                            });
                        }
                    } else {
                        String errorMsg = jsonObject.optString("errorMsg");
                        if (errorMsg.equals("用户未登录")) {
                            instance.skipLoginActivity(LineDetailActivity.this, LoginInActivity.class);
                        } else {
                            ToastUtil.showShort(LineDetailActivity.this, errorMsg);
                        }
                    }
                } catch (IOException e) {
                    AppUtils.getInstance().onRespons("额度转换信息查询失败：" + e.getMessage());
                    AppUtils.getInstance().onEvent("在额度转换记录界面，点击查询信息按钮", e.getMessage());
                    e.printStackTrace();
                } catch (JSONException e) {
                    AppUtils.getInstance().onRespons("额度转换信息查询失败：" + e.getMessage());
                    AppUtils.getInstance().onEvent("在额度转换记录界面，点击查询信息按钮", e.getMessage());
                    e.printStackTrace();
                }
            }
        });

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginEvent(LISuccess lis) {
        if (lis.getStatus()) {
            requestMessage(istime, ietime, "", "", 1);
        }
    }


    @OnClick({R.id.top_fanhui, R.id.ed_s_year, R.id.ed_s_hour, R.id.ed_s_minute, R.id.ed_e_year, R.id.ed_e_hour, R.id.ed_e_minute, R.id.ed_from, R.id.ed_to, R.id.ed_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.top_fanhui:
                finish();
                break;
            case R.id.ed_s_year:
                setDataPopup(year, edSYear, 2);
                break;
            case R.id.ed_e_year:
                setDataPopup(year, edEYear, 2);
                break;
            case R.id.ed_s_hour:
                if (list_hm.size() != 0) {
                    list_hm.clear();
                }
                Collections.addAll(list_hm, Utils_time.hourarray);
                setDataPopup(list_hm, edSHour, 2);
                break;
            case R.id.ed_e_hour:
                if (list_hm.size() != 0) {
                    list_hm.clear();
                }
                Collections.addAll(list_hm, Utils_time.hourarray);
                setDataPopup(list_hm, edEHour, 2);
                break;
            case R.id.ed_s_minute:
                if (list_hm.size() != 0) {
                    list_hm.clear();
                }
                Collections.addAll(list_hm, Utils_time.minutearray);
                setDataPopup(list_hm, edSMinute, 2);
                break;
            case R.id.ed_e_minute:
                if (list_hm.size() != 0) {
                    list_hm.clear();
                }
                Collections.addAll(list_hm, Utils_time.minutearray);
                setDataPopup(list_hm, edEMinute, 2);
                break;
            case R.id.ed_from:
                setDataPopup(wtype, edFrom, 0);
                break;
            case R.id.ed_to:
                setDataPopup(wtype, edTo, 1);
                break;
            case R.id.ed_button:
                Reappear();
                break;
        }
    }

    private void Reappear() {
        String start = edSYear.getText().toString().isEmpty() ? message.getStartyear() : edSYear.getText().toString();
        String end = edEYear.getText().toString().isEmpty() ? message.getEndyear() : edEYear.getText().toString();
        String starthour = edSHour.getText().toString().isEmpty() ? "00" : edSHour.getText().toString();
        String endhour = edEHour.getText().toString().isEmpty() ? "23" : edEHour.getText().toString();
        String startmin = edSMinute.getText().toString().isEmpty() ? "00" : edSMinute.getText().toString();
        String endmin = edEMinute.getText().toString().isEmpty() ? "59" : edEMinute.getText().toString();

        String starttime = start + " " + starthour + ":" + startmin;
        String endtime = end + " " + endhour + ":" + endmin;

        if (start.isEmpty()) {
            ToastUtil.showShort(this, "请选择开始年月日");
        } else {

            if (end.isEmpty()) {
                ToastUtil.showShort(this, "请选择结束年月日");
            } else {

                if (FROM == 0 || TO == 0) {
                    edFrom.setText("全部");
                    edTo.setText("全部");
                    FROM = TO = 0;
                    requestMessage(starttime, endtime, "", "", 1);
                } else {
                    if (FROM != TO) {
                        requestMessage(starttime, endtime, wallet.get(FROM - 1).getWallettype(), wallet.get(TO - 1).getWallettype(), 2);
                    } else {
                        ToastUtil.showShort(this, "转出和转入钱包不能相同");
                    }
                }
            }
        }
    }

    /**
     * 设置popupwindow弹出框
     *
     * @param button
     */
    private void setDataPopup(final List<String> year, final Button button, final int count) {
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
        switch (count) {
            case 0:
                DataPupup.setHeight(DensityUtil.dp2px(this, 200));
                break;
            case 1:
                DataPupup.setHeight(DensityUtil.dp2px(this, 200));
                break;
            case 2:
                DataPupup.setHeight(DensityUtil.dp2px(this, 230));
                break;
        }

        DataView.invalidate();

        if (DataPupup.isShowing()) {
            DataPupup.dismiss();
        }

        //设置适配器
        FundsLinePupupListAdapter fundsLinePupupListAdapter = new FundsLinePupupListAdapter(year);
        listview.setAdapter(fundsLinePupupListAdapter);
        //设置点击监听
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (count) {
                    case 0:
                        FROM = i;
                        break;
                    case 1:
                        TO = i;
                        break;
                }
                button.setText(year.get(i).toString());
                DataPupup.dismiss();

            }
        });

        ColorDrawable colorDrawable = new ColorDrawable(getResources().getColor(R.color.home_popupgrid_item_bg));
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


    /**
     * 亲求转换数据
     */
    private void fundsLineRequest() {
        String token = userSP.getToken(UserSP.LOGIN_TOKEN);
        instance.apiLineMoneyData(token, NetUtils.getParamsPro().get("signature"), new Subscriber<ResponseBody>() {


            @Override
            public void onCompleted() {
                AppUtils.getInstance().onRespons("额度转换数据请求完成");
                LogUtil.e("转换查询请求完成");
            }

            @Override
            public void onError(Throwable e) {
                AppUtils.getInstance().onRespons("额度转换数据请求失败：" + e.getMessage());
                LogUtil.e("转换查询请求失败：" + e.getMessage());
            }

            @Override
            public void onNext(ResponseBody json) {
                try {
                    String string = json.string();
                    JSONObject jsonObject = instance.getJsonObject(string);
                    instance.setToken_SP(jsonObject.optString("token"));
                    int status = jsonObject.optInt("status");
                    LogUtil.e("转换查询请求成功");
                    if (status == 200) {
                        LineMoneyBean lineMoneyBean = instance.getGson().fromJson(string, LineMoneyBean.class);
                        wallet = lineMoneyBean.getData().getWallet();

                        for (int x = 0; x < wallet.size(); x++) {
                            wtype.add(wallet.get(x).getName());
                        }
                        wtype.add(0, "全部");

                    } else {
                        String errorMsg = jsonObject.optString("errorMsg");
                        if (errorMsg.equals("用户未登录")) {
                            instance.skipLoginActivity(LineDetailActivity.this, LoginInActivity.class);
                        } else {
                            ToastUtil.showShort(LineDetailActivity.this, "请求异常...");
                        }
                    }
                } catch (IOException e) {
                    AppUtils.getInstance().onRespons("额度转换数据请求失败：" + e.getMessage());
                    e.printStackTrace();
                } catch (JSONException e) {
                    AppUtils.getInstance().onRespons("额度转换数据请求失败：" + e.getMessage());
                    e.printStackTrace();
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
