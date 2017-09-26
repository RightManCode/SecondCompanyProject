package com.wishland.www.xinwanhaotest.controller.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2017/4/15.
 * Fragment 抽象基类
 */

public abstract class BaseFragment extends Fragment{
    public  Context baseContext;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseContext =  getActivity();
        initVariable();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = setView(inflater, container, savedInstanceState);
        setData();
        return view;
    }

    protected  void initVariable(){};

    //抽象方法，强制子类实现
    public abstract View setView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

    /**
     * 子类挑选实现（加载数据）
     */
    public void setData(){

    }
}
