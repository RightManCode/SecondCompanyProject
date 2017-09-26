package com.wishland.www.xinwanhaotest.controller.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.wishland.www.xinwanhaotest.controller.base.BaseView;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/4/24.
 */

public class FundsViewPagerAdapter extends PagerAdapter {

    private List<BaseView> list;
    private final Context pageContext;
    private List<String> title;

    public FundsViewPagerAdapter(Context baseContext) {
        this.pageContext = baseContext;
    }

    public void setList(List<BaseView> list_view, List<String> list_title) {
        this.title = list_title;
        this.list = list_view;
    }

    @Override
    public int getCount() {
        return title.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        BaseView baseView = list.get(position);
        baseView.initData();
        View rootView = baseView.rootView;
        container.addView(rootView);
        AutoUtils.autoSize(rootView);
        return rootView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title.get(position);
    }
}
