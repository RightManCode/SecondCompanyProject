package com.wishland.www.xinwanhaotest.controller.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wishland.www.xinwanhaotest.R;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/25.
 */

public class PayTextAdapter extends BastListAdapter {

    private final List<String> tip;

    public PayTextAdapter(List<String> tip) {
        super(tip);
        this.tip = tip;
    }

    @Override
    public View setView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.paytext, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
            AutoUtils.autoSize(convertView);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.TText.setText(tip.get(position).toString());

        return convertView;
    }

     class ViewHolder {
        @BindView(R.id.T_text)
        TextView TText;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
