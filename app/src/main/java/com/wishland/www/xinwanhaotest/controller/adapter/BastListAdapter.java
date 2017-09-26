package com.wishland.www.xinwanhaotest.controller.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/4/24.
 */

public abstract class BastListAdapter extends BaseAdapter {
    private  List list;
    public BastListAdapter(List listviewimage) {
        this.list = listviewimage;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return setView(position,convertView,parent);
    }

    public abstract View setView(int position, View convertView, ViewGroup parent);

}
