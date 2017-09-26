package com.wishland.www.xinwanhaotest.controller.base;

import android.content.Context;
import android.view.View;

import com.wishland.www.xinwanhaotest.view.customgridview.CustomViewPager;


/**
 * Created by Administrator on 2017/4/24.
 */

public abstract class BaseView {
    public Context bastcontext;
    public View rootView;
    public CustomViewPager bastViewpager;

    public BaseView(Context context, CustomViewPager fundsViewpager) {
        this.bastcontext = context;
        this.bastViewpager = fundsViewpager;
        rootView =  setView();
        initData();
    }

    //强制子类实现
    public abstract View setView();

    /**
     * 子类挑选实现
     */
    public void initData(){
    }
}
