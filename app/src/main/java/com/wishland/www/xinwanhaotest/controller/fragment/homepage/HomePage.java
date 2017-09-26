package com.wishland.www.xinwanhaotest.controller.fragment.homepage;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.umeng.analytics.MobclickAgent;
import com.wishland.www.xinwanhaotest.R;


import com.wishland.www.xinwanhaotest.api.BaseApi;
import com.wishland.www.xinwanhaotest.controller.adapter.HomeMenuAdapter;
import com.wishland.www.xinwanhaotest.controller.adapter.MoreAdapter;
import com.wishland.www.xinwanhaotest.controller.adapter.Y_Divider;
import com.wishland.www.xinwanhaotest.controller.adapter.Y_DividerItemDecoration;
import com.wishland.www.xinwanhaotest.controller.base.BaseFragment;
import com.wishland.www.xinwanhaotest.model.Model;
import com.wishland.www.xinwanhaotest.model.bean.ABean;
import com.wishland.www.xinwanhaotest.model.bean.BBean;
import com.wishland.www.xinwanhaotest.model.bean.GBean;
import com.wishland.www.xinwanhaotest.model.sp.UserSP;
import com.wishland.www.xinwanhaotest.utils.AppUtils;
import com.wishland.www.xinwanhaotest.utils.FrescoUtil;
import com.wishland.www.xinwanhaotest.utils.LogUtil;
import com.wishland.www.xinwanhaotest.utils.NetUtils;
import com.wishland.www.xinwanhaotest.utils.ToastUtil;
import com.wishland.www.xinwanhaotest.view.MyApplication;
import com.wishland.www.xinwanhaotest.view.activity.HomeMoreActivity;
import com.wishland.www.xinwanhaotest.view.activity.LoginInActivity;
import com.wishland.www.xinwanhaotest.view.activity.MainActivity;
import com.wishland.www.xinwanhaotest.view.activity.SixSpacesListAcitivity;
import com.wishland.www.xinwanhaotest.view.customgridview.CustomListView;
import com.wishland.www.xinwanhaotest.view.customgridview.EmptyLayout;
import com.wishland.www.xinwanhaotest.view.customgridview.ExpandableLayout;
import com.wishland.www.xinwanhaotest.view.customgridview.Y_ItemEntityList;
import com.wishland.www.xinwanhaotest.view.customgridview.Y_OnBind;
import com.wishland.www.xinwanhaotest.view.refresh.MaterialRefreshLayout;
import com.wishland.www.xinwanhaotest.view.refresh.MaterialRefreshListener;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

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
import butterknife.Unbinder;
import okhttp3.ResponseBody;
import rx.Subscriber;

/**
 * Created by Administrator on 2017/4/15.
 * 主页
 */

public class HomePage extends BaseFragment {
    Unbinder unbinder;
    @BindView(R.id.home_banner)
    Banner homeBanner;
    @BindView(R.id.home_more_text)
    TextView homeMoreText;
    @BindView(R.id.home_textvhiew_pmd)
    TextView homeTextvhiewPmd;
    @BindView(R.id.ll_home)
    LinearLayout llHome;
    @BindView(R.id.quest_refresh)
    public MaterialRefreshLayout questrefresh;
    @BindView(R.id.home_more_listview)
    CustomListView morelistView;
    @BindView(R.id.empty_layout)
    EmptyLayout emptyLayout;

    private BBean bBean;
    private GBean gBean;
    private ArrayList<ExpandableLayout> expandableLayouts;
    private ExpandableLayout expandingLayout;
    private View.OnClickListener url;
    private View.OnClickListener l;
    private View clickedMEnu;
    private Y_ItemEntityList itemEntityList;
    private List list;
    private int BANNER = 1;
    private Intent intent;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == BANNER) {
                setBanner(list, bBean.getData());
                handler.removeMessages(BANNER);
            }
        }
    };
    private Model instance;
    private UserSP userSP;
    private List<ABean.DataBean> data;
    private Map<String, String> UMmap = new HashMap();
    private Map<String, String> UMGamemap = new HashMap();

    @Override
    public View setView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View home = inflater.inflate(R.layout.homepage, null);
        unbinder = ButterKnife.bind(this, home);
        return home;
    }

    @Override
    protected void initVariable() {
        url = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userSP.getInt(UserSP.LOGIN_SUCCESS) == 1) {
                    toNewActivity((String) v.getTag());
                } else {
                    instance.skipLoginActivity((MainActivity) baseContext, LoginInActivity.class);
                }
            }
        };
        l = new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (userSP.getInt(UserSP.LOGIN_SUCCESS) == 1) {
                    final int count = (int) v.getTag();
                    final String title = gBean.getData().getGame().get(count).getTitle();
                    final List<GBean.DataBean.GameBean.ItemsBean> items = gBean.getData().getGame().get(count).getItems();

                    if ("AG电子游艺".equals(gBean.getData().getGame().get(count).getTitle())) {
                        String weburl = gBean.getData().getGame().get(count).getItems().get(0).getHref();
                        String tit = gBean.getData().getGame().get(count).getItems().get(0).getName();
                        if (UMmap.size() != 0) {
                            UMmap.clear();
                        }

                        UMmap.put(title, tit);

                        MyApplication.onUMEvent(baseContext, "0001", (HashMap<String, String>) UMmap, 0);

                        //toNewActivity(url);
                        AppUtils.getInstance().onClick("在主页，点击AG电子按钮");
                        AppUtils.getInstance().onEvent("在主页，点击AG电子按钮", "进入AG电子游戏列表界面（Activity）");
                        AppUtils.getInstance().onEnter(SixSpacesListAcitivity.class);
                        toNewActivity(SixSpacesListAcitivity.class, weburl, tit);
                        if (expandingLayout != null) {
                            expandingLayout.fold();
                        }
                    } else {
                        if (v.equals(clickedMEnu)) {
                            clickedMEnu = null;
                            expandingLayout.fold();
                            expandingLayout = null;
                        } else {
                            clickedMEnu = v;
                            if (!expandableLayouts.get(count / 3).equals(expandingLayout)) {
                                if (expandingLayout != null)
                                    expandingLayout.fold();
                                expandingLayout = expandableLayouts.get(count / 3);
                                LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.item_content_titles, expandingLayout, false);
                                RecyclerView recyclerView = (RecyclerView) linearLayout.findViewById(R.id.item_content_recyclerview);
                                ImageView one = (ImageView) linearLayout.findViewById(R.id.item_content_arrows1);
                                ImageView two = (ImageView) linearLayout.findViewById(R.id.item_content_arrows2);
                                ImageView thr = (ImageView) linearLayout.findViewById(R.id.item_content_arrows3);
                                setArrowsShowHide(count, one, two, thr);
                                recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
                                recyclerView.setNestedScrollingEnabled(false);
                                itemEntityList.addItems(R.layout.item_home_game_menu, items)
                                        .addOnBind(R.layout.item_home_game_menu, new Y_OnBind() {
                                            @Override
                                            public void onBindChildViewData(HomeMenuAdapter.GeneralRecyclerViewHolder holder, Object itemData, int position) {
                                                holder.setText(R.id.tv_home_game_menu, (String) itemData);
                                            }
                                        });

                                HomeMenuAdapter adapter = new HomeMenuAdapter(baseContext, itemEntityList);
                                adapter.setOnItemClickListener(new HomeMenuAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, int position) {

                                        String tit = items.get(position).getName();
                                        if (UMmap.size() != 0) {
                                            UMmap.clear();
                                        }
                                        UMmap.put(title, tit);
                                        //友盟统计
                                        MyApplication.onUMEvent(baseContext, "0001", (HashMap<String, String>) UMmap, 0);
                                        try {
                                            String type = gBean.getData().getGame().get(count).getItems().get(position).getType();
                                            if (type.equals("url")) {
                                                if (UMGamemap.size() != 0) {
                                                    UMGamemap.clear();
                                                }
                                                UMGamemap.put("游戏", tit);
                                                MyApplication.onUMEvent(baseContext, "0002", (HashMap<String, String>) UMGamemap, 0);
                                                String weburl = gBean.getData().getGame().get(count).getItems().get(position).getHref();
                                                toNewActivity(weburl.replace("[token]", userSP.getToken(UserSP.LOGIN_TOKEN)));

                                            } else {
                                                GBean.DataBean.GameBean.ItemsBean itemsBean = gBean.getData().getGame().get(count).getItems().get(position);
                                                toNewActivity(SixSpacesListAcitivity.class, itemsBean.getHref(), itemsBean.getName());
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                            ToastUtil.showShort(baseContext, "服务器错误...");
                                        }
                                    }
                                });

                                recyclerView.setAdapter(adapter);
                                recyclerView.addItemDecoration(new DividerItemDecoration(baseContext, items));
                                expandingLayout.setContenView(linearLayout);
                                expandingLayout.unfold();
                            } else {
                                LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.item_content_titles, expandingLayout, false);
                                RecyclerView recyclerView = (RecyclerView) linearLayout.findViewById(R.id.item_content_recyclerview);
                                ImageView one = (ImageView) linearLayout.findViewById(R.id.item_content_arrows1);
                                ImageView two = (ImageView) linearLayout.findViewById(R.id.item_content_arrows2);
                                ImageView thr = (ImageView) linearLayout.findViewById(R.id.item_content_arrows3);
                                setArrowsShowHide(count, one, two, thr);
                                recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
                                recyclerView.setNestedScrollingEnabled(false);
                                itemEntityList.addItems(R.layout.item_home_game_menu, items)
                                        .addOnBind(R.layout.item_home_game_menu, new Y_OnBind() {
                                            @Override
                                            public void onBindChildViewData(HomeMenuAdapter.GeneralRecyclerViewHolder holder, Object itemData, final int position) {
                                                holder.setText(R.id.tv_home_game_menu, (String) itemData);
                                            }
                                        });

                                HomeMenuAdapter adapter = new HomeMenuAdapter(baseContext, itemEntityList);
                                adapter.setOnItemClickListener(new HomeMenuAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, int position) {
                                        String tit = items.get(position).getName();
                                        if (UMmap.size() != 0) {
                                            UMmap.clear();
                                        }
                                        UMmap.put(title, tit);
                                        //友盟统计
                                        MyApplication.onUMEvent(baseContext, "0001", (HashMap<String, String>) UMmap, 0);

                                        try {
                                            String type = gBean.getData().getGame().get(count).getItems().get(position).getType();
                                            if (type.equals("url")) {
                                                if (UMGamemap.size() != 0) {
                                                    UMGamemap.clear();
                                                }
                                                UMGamemap.put("游戏", tit);
                                                MyApplication.onUMEvent(baseContext, "0002", (HashMap<String, String>) UMGamemap, 0);
                                                String weburl = gBean.getData().getGame().get(count).getItems().get(position).getHref();
                                                toNewActivity(weburl.replace("[token]", userSP.getToken(UserSP.LOGIN_TOKEN)));

                                            } else {
                                                AppUtils.getInstance().onEnter(SixSpacesListAcitivity.class);
                                                GBean.DataBean.GameBean.ItemsBean itemsBean = gBean.getData().getGame().get(count).getItems().get(position);
                                                toNewActivity(SixSpacesListAcitivity.class, itemsBean.getHref(), itemsBean.getName());
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                            ToastUtil.showShort(baseContext, "服务器错误...");
                                        }


                                    }
                                });

                                recyclerView.setAdapter(adapter);
                                recyclerView.addItemDecoration(new DividerItemDecoration(baseContext, items));
                                expandingLayout.setContenView(linearLayout);
                            }
                        }
                    }

                } else {
                    instance.skipLoginActivity((MainActivity) baseContext, LoginInActivity.class);
                }

            }
        };

        expandableLayouts = new ArrayList<>();
    }

    private void setArrowsShowHide(int count, ImageView one, ImageView two, ImageView thr) {
        one.setVisibility(View.INVISIBLE);
        two.setVisibility(View.INVISIBLE);
        thr.setVisibility(View.INVISIBLE);
        int i = count % 3;
        switch (i) {
            case 0:
                one.setVisibility(View.VISIBLE);
                break;
            case 1:
                two.setVisibility(View.VISIBLE);
                break;
            case 2:
                thr.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void setData() {
        super.setData();
        init();

        emptyLayout.setEmptyButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emptyLayout.showLoading();
                questrefresh.autoRefresh();
            }
        });

        questrefresh.autoRefresh();

        questrefresh.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(final MaterialRefreshLayout materialRefreshLayout) {
                bannerR();
                AdsR();
                ActivityR();
                homeGame();
            }
        });

        morelistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int position = i + 1;
                AppUtils.getInstance().onClick("在主页，点击优惠活动列表中的第" + position + "条");
                AppUtils.getInstance().onEvent("在主页，点击优惠活动列表中的第" + position + "条", "打开优惠活动详情界面（url）！");
                toNewActivity(data.get(i).getUrl());
            }
        });

    }

    private void init() {
        list = new ArrayList();
        instance = Model.getInstance();
        userSP = instance.getUserSP();
        itemEntityList = new Y_ItemEntityList();

    }

    private void setBanner(final List list, final List<BBean.DataBean> listdata) {
        //设置banner样式
        homeBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        homeBanner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        homeBanner.setImages(list);
        //设置banner动画效果
        homeBanner.setBannerAnimation(Transformer.Default);
        //设置自动轮播，默认为true
        homeBanner.isAutoPlay(true);
        //设置轮播时间
        homeBanner.setDelayTime(4500);
        //设置指示器位置（当banner模式中有指示器时）
        homeBanner.setIndicatorGravity(BannerConfig.CENTER);

        homeBanner.start();

//        homeBanner.setOnBannerListener(new OnBannerListener() {
//            @Override
//            public void OnBannerClick(int position) {
//                AppUtils.getInstance().onClick("在主页，点击第" + position + "个轮播图片");
//                if (userSP.getInt(UserSP.LOGIN_SUCCESS) == 1) {
//                    String weburl = listdata.get(position).getUrl();
//                    if (!weburl.equals("#") && !weburl.isEmpty()) {
//                        AppUtils.getInstance().onEvent("在主页，点击第" + position + "个轮播图片", "打开新的界面（url）！");
//                        toNewActivity(weburl.replace("[token]", userSP.getToken(UserSP.LOGIN_TOKEN)));
//                    } else {
//                        AppUtils.getInstance().onEvent("在主页，点击第" + position + "个轮播图片", "地址无效：" + weburl);
//                        ToastUtil.showShort(baseContext, "地址无效：" + weburl);
//                    }
//                } else {
//                    instance.skipLoginActivity((MainActivity) baseContext, LoginInActivity.class);
//                }
//
//            }
//        });
    }

    private void setListView() {
        MoreAdapter moreAdapter = new MoreAdapter(data, baseContext);
        morelistView.setAdapter(moreAdapter);

    }

    /**
     * 联网请求数据
     */
    private void bannerR() {
        String token = userSP.getToken(UserSP.LOGIN_TOKEN);
        instance.apiBanner(token, NetUtils.getParamsPro().get("signature"), new Subscriber<ResponseBody>() {


            @Override
            public void onCompleted() {
                AppUtils.getInstance().onRespons("Home-banner请求完成");
                LogUtil.e("Home-banner请求完成");
            }

            @Override
            public void onError(Throwable e) {
                AppUtils.getInstance().onRespons("Home-banner请求异常：" + e.getMessage());
                LogUtil.e("Home-banner请求异常：" + e.getMessage());
                ToastUtil.showShort(baseContext, "轮播图请求异常...");
            }

            @Override
            public void onNext(ResponseBody responseBody) {

                try {
                    AppUtils.getInstance().onRespons("Home-banner请求成功");
                    String string = responseBody.string();
                    LogUtil.e("Home-banner请求成功:" + string);
                    JSONObject jsonObject = instance.getJsonObject(string);
                    int status = jsonObject.optInt("status");
                    instance.setToken_SP(jsonObject.optString("token"));
                    if (status == 200) {
                        bBean = instance.getGson().fromJson(string, BBean.class);
                        instance.getExecutorService().execute(new Runnable() {
                            @Override
                            public void run() {
                                if (list.size() != 0) {
                                    list.clear();
                                }
                                List<BBean.DataBean> data = bBean.getData();
                                for (int x = 0; x < data.size(); x++) {
                                    list.add(data.get(x).getImg());
                                }
                                handler.sendEmptyMessage(BANNER);
                            }
                        });
                    } else {
                        ToastUtil.showShort(baseContext, "轮播图请求异常...");
                    }

                } catch (IOException e) {
                    AppUtils.getInstance().onRespons("Home-banner请求异常：" + e.getMessage());
                    e.printStackTrace();
                } catch (JSONException e) {
                    AppUtils.getInstance().onRespons("Home-banner请求异常：" + e.getMessage());
                    e.printStackTrace();
                }


            }
        });
    }

    private void homeGame() {
        String token = userSP.getToken(UserSP.LOGIN_TOKEN);
        instance.apiGame(token,
                NetUtils.getParamsPro().get("signature"),
                new Subscriber<ResponseBody>() {

                    @Override
                    public void onCompleted() {
                        AppUtils.getInstance().onRespons("Home-Game页请求成功");
                        questrefresh.finishRefresh();
                        LogUtil.e("Home-Game页请求完成");
                    }

                    @Override
                    public void onError(Throwable e) {
                        AppUtils.getInstance().onRespons("Home-Game页请求失败:" + e.getMessage());
                        LogUtil.e("Home-Game页请求失败:" + e.getMessage());
                        ToastUtil.showShort(baseContext, "热门游戏请求异常");
                        questrefresh.finishRefresh();
                       // emptyLayout.showEmpty();

                    }

                    @Override
                    public void onNext(final ResponseBody strings) {
                        LogUtil.e("Home-Game页请求成功");
                        AppUtils.getInstance().onRespons("Home-Game页请求成功");
                        questrefresh.finishRefresh();
                        emptyLayout.hide();
                        try {
                            String string = strings.string();
                            LogUtil.e("Home-Game页请求成功:" + string);
                            JSONObject jsonObject = instance.getJsonObject(string);
                            int status = jsonObject.optInt("status");
                            instance.setToken_SP(jsonObject.optString("token"));

                            if (status == 200) {
                                gBean = instance.getGson().fromJson(string, GBean.class);
                                userSP.setSite(UserSP.LOGIN_SITE, gBean.getData().getSite());
                                expandableLayouts.clear();
                                llHome.removeAllViews();
                                ExpandableLayout inflate;
                                LinearLayout linearLayout = null;

                                List<GBean.DataBean.GameBean> game = gBean.getData().getGame();
//                                GBean.DataBean.GameBean gb = new GBean.DataBean.GameBean();
//                                gb.setTitle("AG电子");
//                                gb.setUrl(gBean.getData().getGame().get(1).getItems().get(0).getHref());
//                                gb.setImg(gBean.getData().getGame().get(1).getItems().get(0).getImg());
//                                game.add(1, gb);

                                if (game.size() % 3 == 0) {

                                    for (int i = 0; i < game.size(); i++) {
                                        if (i % 3 == 0) {
                                            inflate = (ExpandableLayout) LayoutInflater.from(getContext()).inflate(R.layout.item_home, llHome, false);
                                            expandableLayouts.add(inflate);
                                            FrameLayout headerLayout = inflate.getHeaderLayout();
                                            linearLayout = (LinearLayout) headerLayout.findViewById(R.id.ll_head);
                                            llHome.addView(inflate);
                                        }

                                        assert linearLayout != null;
                                        View inflate1 = LayoutInflater.from(getActivity()).inflate(R.layout.item_home_title, linearLayout, false);
                                        inflate1.setTag(i);
                                        inflate1.setOnClickListener(l);
                                        SimpleDraweeView ivHome = (SimpleDraweeView) inflate1.findViewById(R.id.iv_home);
                                        TextView tvHome = (TextView) inflate1.findViewById(R.id.tv_home);
                                        tvHome.setText(game.get(i).getTitle());
                                        FrescoUtil.loadGifPicOnNet(ivHome, game.get(i).getImg());
                                        linearLayout.addView(inflate1);
                                    }

                                } else {
                                    LogUtil.e("Home页请求成功2");
                                    int i1 = 3 - game.size() % 3;
                                    for (int i = 0; i < gBean.getData().getGame().size(); i++) {
                                        if (i % 3 == 0) {
                                            inflate = (ExpandableLayout) LayoutInflater.from(getContext()).inflate(R.layout.item_home, llHome, false);
                                            expandableLayouts.add(inflate);
                                            FrameLayout headerLayout = inflate.getHeaderLayout();
                                            linearLayout = (LinearLayout) headerLayout.findViewById(R.id.ll_head);
                                            llHome.addView(inflate);
                                        }

                                        assert linearLayout != null;
                                        View inflate1 = LayoutInflater.from(getActivity()).inflate(R.layout.item_home_title, linearLayout, false);
                                        inflate1.setTag(i);
                                        inflate1.setOnClickListener(l);
                                        SimpleDraweeView ivHome = (SimpleDraweeView) inflate1.findViewById(R.id.iv_home);
                                        TextView tvHome = (TextView) inflate1.findViewById(R.id.tv_home);
                                        tvHome.setText(game.get(i).getTitle());
                                        FrescoUtil.loadGifPicOnNet(ivHome, game.get(i).getImg());
                                        linearLayout.addView(inflate1);

                                    }

                                    for (int x = 1; x <= i1; x++) {
                                        View inflate2 = LayoutInflater.from(getActivity()).inflate(R.layout.item_home_title, linearLayout, false);
                                        inflate2.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                            }
                                        });
                                        SimpleDraweeView ivHome = (SimpleDraweeView) inflate2.findViewById(R.id.iv_home);
                                        TextView tvHome = (TextView) inflate2.findViewById(R.id.tv_home);
                                        ImageView iVview = (ImageView) inflate2.findViewById(R.id.iv_game_menu);
                                        ivHome.setVisibility(View.GONE);
                                        tvHome.setVisibility(View.GONE);
                                        iVview.setVisibility(View.GONE);
                                        linearLayout.addView(inflate2);
                                    }
                                }
                            } else {
                                ToastUtil.showShort(baseContext, "热门游戏请求异常");
                            }
                        } catch (IOException e) {
                            AppUtils.getInstance().onRespons("Home-Game页请求失败:" + e.getMessage());
                            e.printStackTrace();
                        } catch (JSONException e) {
                            AppUtils.getInstance().onRespons("Home-Game页请求失败:" + e.getMessage());
                            e.printStackTrace();
                        }

                    }

                });


    }

    private void AdsR() {
        String token = userSP.getToken(UserSP.LOGIN_TOKEN);
        instance.apiAds(token, NetUtils.getParamsPro().get("signature"), new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {
                AppUtils.getInstance().onRespons("Home-跑马灯请求完成");
                LogUtil.e("Home-跑马灯请求完成");
            }

            @Override
            public void onError(Throwable e) {
                AppUtils.getInstance().onRespons("Home-跑马灯请求异常：" + e.getMessage());
                LogUtil.e("Home-跑马灯请求异常：" + e.getMessage());
                ToastUtil.showShort(baseContext, "跑马灯请求异常...");
            }

            @Override
            public void onNext(ResponseBody responseBody) {

                try {
                    String string = responseBody.string();
                    JSONObject jsonObject = instance.getJsonObject(string);
                    int status = jsonObject.optInt("status");
                    instance.setToken_SP(jsonObject.optString("token"));
                    AppUtils.getInstance().onRespons("Home-跑马灯请求成功");
                    LogUtil.e("Home-跑马灯请求成功:" + string);
                    if (status == 200) {
                        String s = jsonObject.optJSONObject("data").optString("title");
                        homeTextvhiewPmd.setText(s);
                    } else {
                        ToastUtil.showShort(baseContext, "跑马灯请求异常...");
                    }

                } catch (IOException e) {
                    AppUtils.getInstance().onRespons("跑马灯请求异常：" + e.getMessage());
                    e.printStackTrace();
                } catch (JSONException e) {
                    AppUtils.getInstance().onRespons("跑马灯请求异常：" + e.getMessage());
                    e.printStackTrace();
                }
            }
        });

    }

    private void ActivityR() {
        String token = userSP.getToken(UserSP.LOGIN_TOKEN);
        instance.apiActivity(token, NetUtils.getParamsPro().get("signature"), new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {
                AppUtils.getInstance().onRespons("Home-优惠请求完成");
                LogUtil.e("Home-优惠请求完成");
            }

            @Override
            public void onError(Throwable e) {
                AppUtils.getInstance().onRespons("Home-优惠请求异常：" + e.getMessage());
                LogUtil.e("Home-优惠请求异常：" + e.getMessage());
                ToastUtil.showShort(baseContext, "优惠活动请求异常...");
            }

            @Override
            public void onNext(ResponseBody responseBody) {

                try {
                    String string = responseBody.string();
                    JSONObject jsonObject = instance.getJsonObject(string);
                    int status = jsonObject.optInt("status");
                    instance.setToken_SP(jsonObject.optString("token"));
                    AppUtils.getInstance().onRespons("Home-优惠请求成功");
                    LogUtil.e("Home-优惠请求成功:" + string);
                    if (status == 200) {
                        ABean aBean = instance.getGson().fromJson(string, ABean.class);
                        data = aBean.getData();
                        setListView();
                    } else {
                        ToastUtil.showShort(baseContext, "优惠活动请求异常...");
                    }

                } catch (IOException e) {
                    AppUtils.getInstance().onRespons("Home-优惠请求异常：" + e.getMessage());
                    e.printStackTrace();
                } catch (JSONException e) {
                    AppUtils.getInstance().onRespons("Home-优惠请求异常：" + e.getMessage());
                    e.printStackTrace();
                }
            }
        });
    }

    @OnClick({R.id.home_more_text, R.id.button_pc})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button_pc:  //进入浏览器PC版
                instance.toBrowser(baseContext);
                break;
            case R.id.home_more_text:
                intent = new Intent(baseContext, HomeMoreActivity.class);
                baseContext.startActivity(intent);
                break;
        }
    }

    /**
     * @param url 跳转二级页面详情页
     */
    private void toNewActivity(Class zlass, String url, String title) {
        intent = new Intent(getActivity(), zlass);
        intent.putExtra(BaseApi.SIXURI, url);
        intent.putExtra(BaseApi.SIXTEXT, title);
        getActivity().startActivity(intent);
    }

    /**
     * @param url 跳转详情页
     */
    private void toNewActivity(String url) {
        intent = new Intent(BaseApi.NEWHTML);
        intent.putExtra(BaseApi.HTML5DATA, url);
        getActivity().startActivity(intent);
    }

    @Override
    public void onStart() {
        super.onStart();
        homeBanner.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        homeBanner.stopAutoPlay();
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(getContext());
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(getContext());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    public class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            //用fresco加载图片简单用法，记得要写下面的createImageView方法
            SimpleDraweeView simpleDraweeView = (SimpleDraweeView) imageView;
            FrescoUtil.loadGifPicOnNet(simpleDraweeView, (String) path);
            //Glide 加载图片简单用法
            //Glide.with(context).load(path).into(imageView);
        }

        @Override
        public ImageView createImageView(Context context) {
            //使用fresco，需要创建它提供的ImageView，当然你也可以用自己自定义的具有图片加载功能的ImageView
            SimpleDraweeView simpleDraweeView = new SimpleDraweeView(context);
            return simpleDraweeView;
        }
    }

    private class DividerItemDecoration extends Y_DividerItemDecoration {

        private List<GBean.DataBean.GameBean.ItemsBean> subMenu;

        private DividerItemDecoration(Context context, List<GBean.DataBean.GameBean.ItemsBean> subMenu) {
            super(context);
            this.subMenu = subMenu;
        }

        @Override
        public Y_Divider getDivider(int itemPosition) {
            //顺序:left, top, right, bottom
            Y_Divider divider = new Y_Divider(false, false, false, false, 1, getResources().getColor(R.color.popup_linear));
            int size = subMenu.size();
            int i = size % 3;

            if (i == 0) {
                if (itemPosition < size - 3) {
                    switch (itemPosition % 3) {
                        case 0:
                        case 1:
                            //每一行前两个显示rignt和bottom
                            divider.setRight(true);
                            divider.setBottom(true);
                            break;
                        case 2:
                            //最后一个只显示bottom
                            divider.setBottom(true);
                            break;
                        default:
                            break;
                    }
                } else {
                    switch (itemPosition % 3) {
                        case 0:
                        case 1:
                            divider.setRight(true);
                            break;
                        case 2:
                            break;
                        default:
                            break;
                    }
                }
            } else {
                if (itemPosition < size - i) {
                    switch (itemPosition % 3) {
                        case 0:
                        case 1:
                            //每一行前两个显示rignt和bottom
                            divider.setRight(true);
                            divider.setBottom(true);
                            break;
                        case 2:
                            //最后一个只显示bottom
                            divider.setBottom(true);
                            break;
                        default:
                            break;
                    }
                } else {
                    switch (itemPosition % 3) {
                        case 0:
                        case 1:
                            divider.setRight(true);
                            break;
                        case 2:
                            break;
                        default:
                            break;
                    }
                }
            }
            return divider;
        }

    }
}