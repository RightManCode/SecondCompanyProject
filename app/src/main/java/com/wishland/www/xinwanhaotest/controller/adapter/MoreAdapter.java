package com.wishland.www.xinwanhaotest.controller.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wishland.www.xinwanhaotest.R;

import com.wishland.www.xinwanhaotest.model.bean.ABean;
import com.wishland.www.xinwanhaotest.utils.FrescoUtil;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/5/28.
 */

public class MoreAdapter extends BastListAdapter {

    private final List<ABean.DataBean> list;
    private Context context;

    public MoreAdapter(List<ABean.DataBean> activity, Context baseContext) {
        super(activity);
        this.list = activity;
        this.context = baseContext;
    }


    @Override
    public View setView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewholder;
            if (convertView == null) {
                convertView = View.inflate(context, R.layout.home_item, null);
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
