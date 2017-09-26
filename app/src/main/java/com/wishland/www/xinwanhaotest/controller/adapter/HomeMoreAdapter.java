package com.wishland.www.xinwanhaotest.controller.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wishland.www.xinwanhaotest.R;

import com.wishland.www.xinwanhaotest.model.bean.ABean;
import com.wishland.www.xinwanhaotest.utils.FrescoUtil;
import com.wishland.www.xinwanhaotest.view.activity.HomeMoreActivity;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/5/26.
 */

public class HomeMoreAdapter extends BaseAdapter {
    private Context context;
    private List<ABean.DataBean> list;

    public HomeMoreAdapter(HomeMoreActivity homeMoreActivity) {
        this.context = homeMoreActivity;
    }


    public void setHomeMoredata(List<ABean.DataBean> homeMoredata) {
        this.list = homeMoredata;
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
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewholder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.more_item, null);
            viewholder = new ViewHolder(convertView);
            convertView.setTag(viewholder);
            AutoUtils.autoSize(convertView);
        } else {
            viewholder = (ViewHolder) convertView.getTag();
        }

        FrescoUtil.loadGifPicOnNet(viewholder.moreIm, list.get(position).getImgUrl());

        viewholder.moreTvLeft.setText(list.get(position).getStart());
        viewholder.moreTvRight.setText(list.get(position).getEnd());

        return convertView;
    }


    class ViewHolder {
        @BindView(R.id.more_im)
        SimpleDraweeView moreIm;
        @BindView(R.id.more_tv_left)
        TextView moreTvLeft;
        @BindView(R.id.more_tv_right)
        TextView moreTvRight;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
