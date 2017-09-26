package com.wishland.www.xinwanhaotest.controller.fragment.fundsmanagement;


import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.wishland.www.xinwanhaotest.R;
import com.wishland.www.xinwanhaotest.controller.adapter.FundsPupupListAdapter;
import com.wishland.www.xinwanhaotest.controller.adapter.FundsViewPagerAdapter;
import com.wishland.www.xinwanhaotest.controller.base.BaseFragment;
import com.wishland.www.xinwanhaotest.controller.base.BaseView;
import com.wishland.www.xinwanhaotest.controller.fragment.fundsmanagement.pagerview.FundsATMMoney;
import com.wishland.www.xinwanhaotest.controller.fragment.fundsmanagement.pagerview.FundsBankSavings;
import com.wishland.www.xinwanhaotest.controller.fragment.fundsmanagement.pagerview.FundsDealMoney;
import com.wishland.www.xinwanhaotest.controller.fragment.fundsmanagement.pagerview.FundsLineMessage;
import com.wishland.www.xinwanhaotest.model.Model;
import com.wishland.www.xinwanhaotest.model.bean.AccountBean;
import com.wishland.www.xinwanhaotest.model.bean.BindSuccess;
import com.wishland.www.xinwanhaotest.model.sp.AccountDataSP;
import com.wishland.www.xinwanhaotest.model.sp.UserSP;
import com.wishland.www.xinwanhaotest.utils.AppUtils;
import com.wishland.www.xinwanhaotest.utils.DensityUtil;
import com.wishland.www.xinwanhaotest.utils.LogUtil;
import com.wishland.www.xinwanhaotest.utils.NetUtils;
import com.wishland.www.xinwanhaotest.utils.ToastUtil;
import com.wishland.www.xinwanhaotest.utils.Utils_time;
import com.wishland.www.xinwanhaotest.view.activity.AccountBindAcitvity;
import com.wishland.www.xinwanhaotest.view.activity.LoginInActivity;
import com.wishland.www.xinwanhaotest.view.activity.MainActivity;
import com.wishland.www.xinwanhaotest.view.activity.PersonalAccountActivity;
import com.wishland.www.xinwanhaotest.view.customgridview.CustomViewPager;
import com.wishland.www.xinwanhaotest.view.customgridview.EmptyLayout;
import com.wishland.www.xinwanhaotest.view.refresh.MaterialRefreshLayout;
import com.wishland.www.xinwanhaotest.view.refresh.MaterialRefreshListener;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.ResponseBody;
import rx.Subscriber;

/**
 * 资金管理
 */

public class FundsManagementPage extends BaseFragment {
    Unbinder unbinder;
    @BindView(R.id.funds_setting_account)
    TextView fundsSettingAccount;
    @BindView(R.id.funds_setaccount_money)
    TextView fundsSetaccountMoney;
    @BindView(R.id.funds_tablayout)
    TabLayout fundsTablayout;
    @BindView(R.id.funds_viewpager)
    CustomViewPager fundsViewpager;
    @BindView(R.id.quest_refresh)
    public MaterialRefreshLayout questrefresh;
    @BindView(R.id.empty_layout)
    EmptyLayout emptyLayout;

    private List<BaseView> List_View;
    private List<String> list_title;
    private PopupWindow DataPupup;
    private View DataView;
    private ListView listview;
    private Intent intent;
    public List<String> listyear;
    public List<String> list;
    private Model instance;
    private int Tabposition = 0;
    public int DealContent;
    private FundsViewPagerAdapter fundsViewPagerAdapter;
    private FundsBankSavings fundsBankSavings;
    private FundsATMMoney fundsATMMoney;
    private FundsDealMoney fundsDealMoney;
    private FundsLineMessage fundsLineMessage;
    private UserSP userSP;
    private AccountDataSP accountDataSP;
    private View funds;

    @Override
    public View setView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        funds = inflater.inflate(R.layout.fundsmanagement, container, false);
        unbinder = ButterKnife.bind(this, funds);
        EventBus.getDefault().register(this);
        return funds;
    }

    @Override
    public void setData() {
        super.setData();
        init();
        questrefresh.autoRefresh();
        questrefresh.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                ((MainActivity) baseContext).requestGlobal();
                persionRequest();
            }
        });

        emptyLayout.setEmptyButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emptyLayout.showLoading();
                ((MainActivity) baseContext).requestGlobal();
                persionRequest();
            }
        });
    }

    private void init() {
        listyear = Utils_time.getYearMonthDayHourMinuteSecond();
        list = new ArrayList<>();
        instance = Model.getInstance();
        userSP = instance.getUserSP();
        accountDataSP = instance.getAccountDataSP();
        fundsTablayout.setTabMode(TabLayout.MODE_FIXED);
        list_title = new ArrayList<>();

        list_title.add("存款");
        list_title.add("取款");
        list_title.add("交易记录");
        list_title.add("额度查询");
        // list_title.add("投注记录");

        List_View = new ArrayList<>();
        fundsViewPagerAdapter = new FundsViewPagerAdapter(getContext());
    }

    private void setViewpageChild() {
        if (fundsBankSavings == null) {
            fundsBankSavings = new FundsBankSavings(getContext(), fundsViewpager);
            List_View.add(fundsBankSavings);
        }/*else{
            //fundsBankSavings.request();
            fundsBankSavings.bankview.postInvalidate();
        }*/

        if (fundsATMMoney == null) {
            fundsATMMoney = new FundsATMMoney(getContext(), fundsViewpager, this);
            List_View.add(fundsATMMoney);
        }
        if (fundsDealMoney == null) {
            fundsDealMoney = new FundsDealMoney(getContext(), fundsViewpager, this);
            List_View.add(fundsDealMoney);
        }
      /*  if(fundsCathectic == null) {
            fundsCathectic = new FundsCathectic(baseContext, fundsViewpager, this);
             List_View.add(fundsCathectic);
        }*/
        if (fundsLineMessage == null) {
            fundsLineMessage = new FundsLineMessage(getContext(), fundsViewpager, this);
            List_View.add(fundsLineMessage);
        }

        fundsViewPagerAdapter.setList(List_View, list_title);
        fundsViewpager.setAdapter(fundsViewPagerAdapter);
      /*  if (fundsBankSavings != null) {

        }*/
        fundsViewPagerAdapter.notifyDataSetChanged();
        fundsViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                Tabposition = position;
                fundsViewpager.resetHeight(position);
                if (position == 1) {
                    String accountData = accountDataSP.getAccountData(AccountDataSP.ACCOUNT_BANK_NUMBER);
                    LogUtil.e("取款-是否绑定：" + accountData);
                    if (accountData.isEmpty() || "null".equals(accountData)) {
                        intent = new Intent(getContext(), AccountBindAcitvity.class);
                        getContext().startActivity(intent);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        fundsViewpager.resetHeight(0);
        fundsTablayout.setupWithViewPager(fundsViewpager);
        fundsTablayout.getTabAt(Tabposition).select();

    }


    @OnClick({R.id.funds_setting_account, R.id.funds_setting, R.id.button_pc})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button_pc:  //进入浏览器PC版
                instance.toBrowser(getContext());
                break;
            case R.id.funds_setting_account:
            case R.id.funds_setting:
                AppUtils.getInstance().onClick("在资金管理界面，点击设置按钮!");
                AppUtils.getInstance().onEvent("在资金管理界面，点击设置按钮", "进入我的账号界面");
                AppUtils.getInstance().onEnter(PersonalAccountActivity.class);
                intent = new Intent(getContext(), PersonalAccountActivity.class);
                startActivity(intent);
                break;
        }
    }

    /**
     * 个人信息请求
     */
    public void persionRequest() {
        String token = userSP.getToken(UserSP.LOGIN_TOKEN);
        instance.apiAccount(token, NetUtils.getParamsPro().get("signature"), new Subscriber<ResponseBody>() {

            @Override
            public void onCompleted() {
                AppUtils.getInstance().onRespons("Funds个人页面请求完成");
                questrefresh.finishRefresh();
                LogUtil.e("Funds个人页面请求完成");
            }

            @Override
            public void onError(Throwable e) {
                AppUtils.getInstance().onRespons("Funds个人页面请求失败：" + e.getMessage());
                questrefresh.finishRefresh();
                LogUtil.e("Funds个人页面请求失败" + e.getMessage());
                //emptyLayout.showEmpty();
            }

            @Override
            public void onNext(final ResponseBody account) {
                questrefresh.finishRefresh();
                emptyLayout.hide();
                try {
                    String string = account.string();
                    JSONObject jsonObject = instance.getJsonObject(string);
                    int status = jsonObject.optInt("status");
                    instance.setToken_SP(jsonObject.optString("token"));
                    AppUtils.getInstance().onRespons("Funds个人页面请求成功");
                    LogUtil.e("Funds个人页面请求成功:" + string);
                    if (status == 200) {
                        AccountBean accountBean = instance.getGson().fromJson(string, AccountBean.class);
                        setAccountDataSP(accountBean);
                        setViewpageChild();
                        fundsSetaccountMoney.setText(accountBean.getData().getBalanceInfo().getBalance() + "\n");
                        fundsSettingAccount.setText(userSP.getString(UserSP.LOGIN_USERNAME));
                    } else {
                        String errorMsg = jsonObject.optString("errorMsg");
                        if (errorMsg.equals("用户未登录")) {
                            instance.skipLoginActivity(getActivity(), LoginInActivity.class);
                        } else {
                            ToastUtil.showShort(getContext(), errorMsg);
                        }
                    }
                } catch (IOException e) {
                    AppUtils.getInstance().onRespons("Funds个人页面请求失败：" + e.getMessage());
                    e.printStackTrace();
                } catch (JSONException e) {
                    AppUtils.getInstance().onRespons("Funds个人页面请求失败：" + e.getMessage());
                    e.printStackTrace();
                }
            }
        });
    }

    private void setAccountDataSP(final AccountBean accountBean) {
        AccountBean.DataBean.FinanceInfoBean financeInfo = accountBean.getData().getFinanceInfo();

        accountDataSP.setA_User_Name(AccountDataSP.ACCOUNT_USER_NAME, financeInfo.getPayName());
        accountDataSP.setA_Bank_Name(AccountDataSP.ACCOUNT_BANK_NAME, financeInfo.getBank() + "");
        accountDataSP.setA_Bank_Number(AccountDataSP.ACCOUNT_BANK_NUMBER, financeInfo.getBankAccount() + "");
        accountDataSP.setA_Bank_Address(AccountDataSP.ACCOUNT_BANK_ADDRESS, financeInfo.getAccountAddress() + "");
        accountDataSP.setA_Bank_money(AccountDataSP.ACCOUNT_BANK_MONEY, accountBean.getData().getBalanceInfo().getBalance() + "");
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            persionRequest();
        }
    }

    /**
     * 设置popupwindow弹出框
     *
     * @param list
     * @param button
     */
    public void setDataPopup(final List<String> list, final Button button, final int type) {
        WindowManager manager = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        int height = display.getHeight() / 2;

        if (DataPupup == null) {
            DataView = View.inflate(getActivity(), R.layout.funds_popupwindow, null);
            DataPupup = new PopupWindow(DataView);
            DataView.invalidate();
            listview = (ListView) DataView.findViewById(R.id.funds_popupview);
        }


        DataPupup.setWidth(button.getWidth());
        if (type == 2) {
            DataPupup.setHeight(DensityUtil.dp2px(getContext(), 130));
        } else {
            DataPupup.setHeight(DensityUtil.dp2px(getContext(), 230));
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
                if (type == 2) {
                    DealContent = i;
                }

                button.setText(list.get(i));
                DataPupup.dismiss();

            }
        });

        ColorDrawable colorDrawable = new ColorDrawable(getActivity().getResources().getColor(R.color.home_popupgrid_item_bg));
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(BindSuccess bs) {
        if (fundsATMMoney != null && "ok".equals(bs.getStatus())) {
            persionRequest();
            fundsATMMoney.getAtmview().postInvalidate();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }
}
