package com.wishland.www.xinwanhaotest.controller.fragment.line;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.wishland.www.xinwanhaotest.R;
import com.wishland.www.xinwanhaotest.controller.adapter.LineMeWalletAdapter;
import com.wishland.www.xinwanhaotest.controller.adapter.LineToWalletAdapter;
import com.wishland.www.xinwanhaotest.controller.base.BaseFragment;
import com.wishland.www.xinwanhaotest.model.Model;
import com.wishland.www.xinwanhaotest.model.bean.LineMoneyBean;
import com.wishland.www.xinwanhaotest.model.sp.UserSP;
import com.wishland.www.xinwanhaotest.utils.AppUtils;
import com.wishland.www.xinwanhaotest.utils.LogUtil;
import com.wishland.www.xinwanhaotest.utils.NetUtils;
import com.wishland.www.xinwanhaotest.utils.ToastUtil;
import com.wishland.www.xinwanhaotest.utils.Utils_time;
import com.wishland.www.xinwanhaotest.view.activity.LoginInActivity;
import com.wishland.www.xinwanhaotest.view.customgridview.CustomGridView;
import com.wishland.www.xinwanhaotest.view.customgridview.EmptyLayout;
import com.wishland.www.xinwanhaotest.view.refresh.MaterialRefreshLayout;
import com.wishland.www.xinwanhaotest.view.refresh.MaterialRefreshListener;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.ResponseBody;
import rx.Subscriber;

/**
 * Created by Administrator on 2017/4/15.
 * 额度转换
 */

public class LineConversionPage extends BaseFragment {
    Unbinder unbinder;
    @BindView(R.id.lt_from)
    TextView ltfrom;
    @BindView(R.id.lt_to)
    TextView ltto;
    @BindView(R.id.line_set_money_edittext)
    EditText lineSetMoneyEdittext;
    @BindView(R.id.line_all_button)
    Button lineAllButton;
    @BindView(R.id.line_100_button)
    Button line100Button;
    @BindView(R.id.line_500_button)
    Button line500Button;
    @BindView(R.id.line_1000_button)
    Button line1000Button;
    @BindView(R.id.line_5000_button)
    Button line5000Button;
    @BindView(R.id.line_10000_button)
    Button line10000Button;
    @BindView(R.id.line_putin_button)
    Button linePutinButton;
    @BindView(R.id.line_to_wallet_gridview)
    CustomGridView lineToWalletGridview;
    @BindView(R.id.line_me_wallet_gridview)
    CustomGridView lineMeWalletGridview;
    @BindView(R.id.quest_refresh)
    public MaterialRefreshLayout questrefresh;
    @BindView(R.id.empty_layout)
    EmptyLayout emptyLayout;
    @BindView(R.id.line_srcollview)
    ScrollView lineSrcollview;
    private List<LineMoneyBean.DataBean.WalletBean> titlename;
    private int fromconunt = -1;
    private int toconunt = -1;
    private LineMeWalletAdapter lineMeWalletAdapter;
    private LineToWalletAdapter lineToWalletAdapter;
    private Model instance;
    private Drawable down;
    private Drawable up;
    private Drawable usericon;
    private Drawable passicon;
    private UserSP userSP;
    private InputMethodManager imm;
    private View line;
    private Map<String, String> map;

    @Override
    public View setView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        line = inflater.inflate(R.layout.lineconversionpage, container, false);
        unbinder = ButterKnife.bind(this, line);
        return line;
    }


    @Override
    public void setData() {
        super.setData();
        questrefresh.autoRefresh();
        questrefresh.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(final MaterialRefreshLayout materialRefreshLayout) {
                fromconunt = toconunt = -1;
                lineRequestGridData();
            }
        });

        emptyLayout.setEmptyButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emptyLayout.showLoading();
                questrefresh.autoRefresh();

            }
        });

        lineSrcollview.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.requestFocusFromTouch();
                return false;
            }
        });

        init();
    }


    private void init() {
        imm = (InputMethodManager) baseContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        instance = Model.getInstance();
        userSP = instance.getUserSP();
        map = new HashMap<>();
        lineSrcollview.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
        lineSrcollview.setFocusable(true);
        lineSrcollview.setFocusableInTouchMode(true);

        setDrwable();


    }

    private void setGridView() {
        //我的账户
        lineMeWalletAdapter = new LineMeWalletAdapter(getContext(), titlename);
        lineMeWalletGridview.setAdapter(lineMeWalletAdapter);
        lineMeWalletGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                lineMeWalletAdapter.setSelection(i);
                updateText(i, 1, titlename);
                fromconunt = i;
                lineMeWalletAdapter.notifyDataSetChanged();
            }
        });
        //转入
        lineToWalletAdapter = new LineToWalletAdapter(titlename);
        lineToWalletGridview.setAdapter(lineToWalletAdapter);
        lineToWalletGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                lineToWalletAdapter.setSelection(i);
                updateText(i, 2, titlename);
                toconunt = i;
                lineToWalletAdapter.notifyDataSetChanged();
            }
        });
    }

    public void updateText(int postion, int type, List<LineMoneyBean.DataBean.WalletBean> name) {
        switch (type) {
            case 1:
                AppUtils.getInstance().onClick("在额度转换界面，点击" + name.get(postion).getName() + "按钮，转出");
                ltfrom.setText(name.get(postion).getName() + " -> " + name.get(postion).getAmout() + " ( 转出 )");
                FromShow = false;
                ltfrom.setCompoundDrawables(usericon, null, down, null);
                lineMeWalletGridview.setVisibility(View.GONE);

                ToShow = true;
                ltto.setCompoundDrawables(passicon, null, up, null);
                lineToWalletGridview.setVisibility(View.VISIBLE);
                break;
            case 2:
                AppUtils.getInstance().onClick("在额度转换界面，点击" + name.get(postion).getName() + "按钮，转入");
                ltto.setText(name.get(postion).getName() + " -> " + name.get(postion).getAmout() + " ( 转入 )");
                ToShow = false;
                ltto.setCompoundDrawables(passicon, null, down, null);
                lineToWalletGridview.setVisibility(View.GONE);
                break;
        }

    }

    private boolean FromShow = true;
    private boolean ToShow = true;

    private void setDrwable() {
        down = getResources().getDrawable(R.drawable.icon_down);
        down.setBounds(0, 0, down.getMinimumWidth(), down.getMinimumHeight());

        up = getResources().getDrawable(R.drawable.icon_up);
        up.setBounds(0, 0, up.getMinimumWidth(), up.getMinimumHeight());

        usericon = getResources().getDrawable(R.drawable.walleticon);
        usericon.setBounds(0, 0, usericon.getMinimumWidth(), usericon.getMinimumHeight());

        passicon = getResources().getDrawable(R.drawable.moneyicon);
        passicon.setBounds(0, 0, passicon.getMinimumWidth(), passicon.getMinimumHeight());
    }

    @OnClick({R.id.button_pc, R.id.lt_from, R.id.lt_to, R.id.line_200_button, R.id.line_all_button, R.id.line_100_button, R.id.line_500_button, R.id.line_1000_button, R.id.line_5000_button, R.id.line_10000_button, R.id.line_putin_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lt_from:
                FromShow = !FromShow;
                if (FromShow) {
                    ltfrom.setCompoundDrawables(usericon, null, up, null);
                    lineMeWalletGridview.setVisibility(View.VISIBLE);

                } else {
                    ltfrom.setCompoundDrawables(usericon, null, down, null);
                    lineMeWalletGridview.setVisibility(View.GONE);
                }
                break;
            case R.id.lt_to:
                ToShow = !ToShow;
                if (ToShow) {
                    ltto.setCompoundDrawables(passicon, null, up, null);
                    lineToWalletGridview.setVisibility(View.VISIBLE);

                } else {
                    ltto.setCompoundDrawables(passicon, null, down, null);
                    lineToWalletGridview.setVisibility(View.GONE);
                }
                break;
            case R.id.line_all_button:
                if (fromconunt == -1) {
                    ToastUtil.showShort(getContext(), "您还没有选择我的账号");
                } else {
                    String amout = titlename.get(fromconunt).getAmout();
                    String s = Utils_time.judgeString(amout);
                    lineSetMoneyEdittext.setText(s);
                }
                break;
            case R.id.line_100_button:
                lineSetMoneyEdittext.setText(R.string.one_hundred);
                break;
            case R.id.line_200_button:
                lineSetMoneyEdittext.setText(R.string.two_hundred);
                break;
            case R.id.line_500_button:
                lineSetMoneyEdittext.setText(R.string.five_hundred);
                break;
            case R.id.line_1000_button:
                lineSetMoneyEdittext.setText(R.string.ten_hundred);
                break;
            case R.id.line_5000_button:
                lineSetMoneyEdittext.setText(R.string.fifty_hundred);
                break;
            case R.id.line_10000_button:
                lineSetMoneyEdittext.setText(R.string.one_hundred_hundred);
                break;
            case R.id.line_putin_button:
                AppUtils.getInstance().onClick("在额度转换界面，点击提交按钮！");
                imm.hideSoftInputFromWindow(line.getWindowToken(), 0);
                PutInMoney();
                break;
            case R.id.button_pc:  //进入浏览器PC版
                instance.toBrowser(getContext());
                break;
        }
    }

    /**
     * 金額轉換提交
     */
    private void PutInMoney() {
        final String amount = lineSetMoneyEdittext.getText().toString() + "";
        final String token = userSP.getToken(UserSP.LOGIN_TOKEN);
        if (fromconunt == -1 || toconunt == -1) {

            if (fromconunt == -1) {
                FromShow = true;
                ltfrom.setCompoundDrawables(usericon, null, up, null);
                lineMeWalletGridview.setVisibility(View.VISIBLE);
            } else {
                ToShow = true;
                ltto.setCompoundDrawables(passicon, null, up, null);
                lineToWalletGridview.setVisibility(View.VISIBLE);
            }
            ToastUtil.showShort(getActivity(), "请选择账号");

        } else if (amount.isEmpty()) {
            ToastUtil.showShort(getActivity(), "请输入金额...");
        } else {
            emptyLayout.showLoading();
            final String fromwallettype = titlename.get(fromconunt).getWallettype();
            String towallettype = titlename.get(toconunt).getWallettype();
            if (map.size() != 0) {
                map.clear();
            }
            map.put("fromWalletType", fromwallettype);
            map.put("toWalletType", towallettype);
            map.put("amount", amount);

            instance.apiLinePutIn(fromwallettype, towallettype, amount, token, NetUtils.getParamsPro(map).get("signature"), new Subscriber<ResponseBody>() {


                @Override
                public void onCompleted() {
                    AppUtils.getInstance().onRespons("额度转换提交完成");
                    AppUtils.getInstance().onEvent("在额度转换界面，点击提交按钮", "额度转换提交完成！");
                    LogUtil.e("额度转换提交完成");
                }

                @Override
                public void onError(Throwable e) {
                    AppUtils.getInstance().onEvent("在额度转换界面，点击提交按钮", "额度转换提交失败！");
                    AppUtils.getInstance().onRespons("额度转换提交失败：" + e.getMessage());
                    ltfrom.setText(R.string.judge_money);
                    ltto.setText(R.string.account_in);
                    lineSetMoneyEdittext.setText("");
                    lineRequestGridData();
                    lineMeWalletAdapter.notifyDataSetChanged();
                    lineToWalletAdapter.notifyDataSetChanged();
                    emptyLayout.hide();
                    LogUtil.e("额度转换提交失败---" + e.getMessage());
                    setSuccess("网络异常：", titlename.get(fromconunt).getName() + "  -->  " + titlename.get(toconunt).getName(), "转账金额：" + amount);
                }

                @Override
                public void onNext(ResponseBody json) {
                    AppUtils.getInstance().onEvent("在额度转换界面，点击提交按钮", "额度转换提交成功！");
                    ltfrom.setText(R.string.judge_money);
                    ltto.setText(R.string.account_in);
                    lineSetMoneyEdittext.setText("");
                    emptyLayout.hide();
                    lineRequestGridData();
                    try {
                        String string = json.string();
                        JSONObject jsonObject = instance.getJsonObject(string);
                        instance.setToken_SP(jsonObject.optString("token"));
                        int status = jsonObject.getInt("status");
                        if (status == 200) {
                            GVshowhide();

                            setSuccess("转换成功:", titlename.get(fromconunt).getName() + "  -->  " + titlename.get(toconunt).getName(), "转账金额：" + amount);
                        } else {
                            String errorMsg = jsonObject.optString("errorMsg");
                            setSuccess(errorMsg, titlename.get(fromconunt).getName() + "  -->  " + titlename.get(toconunt).getName(), "转账金额：" + amount);
                        }
                        fromconunt = toconunt = -1;
                        lineMeWalletAdapter.notifyDataSetChanged();
                        lineToWalletAdapter.notifyDataSetChanged();
                        AppUtils.getInstance().onRespons("额度转换提交成功：");
                        LogUtil.e("额度转换提交成功---:" + json.string());
                    } catch (IOException e) {
                        AppUtils.getInstance().onEvent("在额度转换界面，点击提交按钮", e.getMessage());
                        AppUtils.getInstance().onRespons("额度转换提交失败：" + e.getMessage());
                        e.printStackTrace();
                    } catch (JSONException e) {
                        AppUtils.getInstance().onEvent("在额度转换界面，点击提交按钮", e.getMessage());
                        AppUtils.getInstance().onRespons("额度转换提交失败：" + e.getMessage());
                        e.printStackTrace();
                    }
                }

            });
        }

    }

    public void GVshowhide() {
        FromShow = true;
        ltfrom.setCompoundDrawables(usericon, null, up, null);
        lineMeWalletGridview.setVisibility(View.VISIBLE);

        ToShow = false;
        ltto.setCompoundDrawables(passicon, null, down, null);
        lineToWalletGridview.setVisibility(View.GONE);
    }

    /**
     * 亲求转换数据
     */
    private void lineRequestGridData() {
        String token = userSP.getToken(UserSP.LOGIN_TOKEN);
        instance.apiLineMoneyData(token, NetUtils.getParamsPro().get("signature"), new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {
                AppUtils.getInstance().onRespons("额度转换数据请求完成");
                questrefresh.finishRefresh();
                LogUtil.e("额度转换数据请求完成");
            }

            @Override
            public void onError(Throwable e) {
                ltfrom.setText(R.string.judge_money);
                ltto.setText(R.string.account_in);
                lineSetMoneyEdittext.setText("");
                questrefresh.finishRefresh();
                emptyLayout.showEmpty();
                AppUtils.getInstance().onRespons("额度转换数据请求失败：" + e.getMessage());
                LogUtil.e("额度转换数据请求失败" + e.getMessage());
            }

            @Override
            public void onNext(ResponseBody json) {
                ltfrom.setText(R.string.judge_money);
                ltto.setText(R.string.account_in);
                lineSetMoneyEdittext.setText("");
                questrefresh.finishRefresh();
                emptyLayout.hide();
                try {
                    String string = json.string();
                    JSONObject jsonObject = instance.getJsonObject(string);
                    instance.setToken_SP(jsonObject.optString("token"));
                    int status = jsonObject.optInt("status");
                    LogUtil.e("额度转换数据请求成功");
                    if (status == 200) {
                        LineMoneyBean lineMoneyBean = instance.getGson().fromJson(string, LineMoneyBean.class);
                        titlename = lineMoneyBean.getData().getWallet();
                        setGridView();
                        lineMeWalletAdapter.notifyDataSetChanged();
                        lineToWalletAdapter.notifyDataSetChanged();
                        GVshowhide();
                    } else {
                        String errorMsg = jsonObject.optString("errorMsg");
                        if (errorMsg.equals("用户未登录")) {
                            instance.skipLoginActivity(getActivity(), LoginInActivity.class);
                        } else {
                            ToastUtil.showShort(getContext(), errorMsg);
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

    private void setSuccess(String type, String fromto, String money) {
        View view = View.inflate(baseContext, R.layout.line_success, null);
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

        final AlertDialog.Builder alertDialog = instance.getAlertDialog(getContext());
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
