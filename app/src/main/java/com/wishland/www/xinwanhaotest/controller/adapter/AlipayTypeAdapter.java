package com.wishland.www.xinwanhaotest.controller.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.wishland.www.xinwanhaotest.R;
import com.wishland.www.xinwanhaotest.model.bean.AlipayBean;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/25.
 */

public class AlipayTypeAdapter extends BastListAdapter {

    private  Context context;
    private List<AlipayBean.DataBean.AccountBean> account;

    public AlipayTypeAdapter(Context context, List<AlipayBean.DataBean.AccountBean> account) {
        super(account);
        this.context = context;
        this.account = account;
    }

    @Override
    public View setView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.alipaytype, parent,false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
            AutoUtils.autoSize(convertView);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        String type = account.get(position).getType();

       switch (type){
           case "picture":
               viewHolder.alipayFrameImage.setVisibility(View.VISIBLE);
               viewHolder.alipayFrameText.setVisibility(View.GONE);

               Glide.with(context).load(account.get(position).getPicName()).into(viewHolder.alipayFrameImage);
               break;
           case "comment":
               viewHolder.alipayFrameImage.setVisibility(View.GONE);
               viewHolder.alipayFrameText.setVisibility(View.VISIBLE);

               viewHolder.atUsername.setText(account.get(position).getPicName());
               break;
       }

        viewHolder.atName.setText(account.get(position).getAlipayName());
        viewHolder.atUser.setText(account.get(position).getAlipayID());
        viewHolder.tiState.setText(account.get(position).getMemo());

        return convertView;
    }

    class ViewHolder {
        @BindView(R.id.at_username)
        TextView atUsername;
        @BindView(R.id.alipay_frame_text)
        LinearLayout alipayFrameText;
        @BindView(R.id.alipay_frame_image)
        ImageView alipayFrameImage;
        @BindView(R.id.at_name)
        TextView atName;
        @BindView(R.id.at_user)
        TextView atUser;
        @BindView(R.id.ti_state)
        TextView tiState;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
