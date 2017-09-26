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
 * Created by Administrator on 2017/5/3.
 */

public class FundsPupupListAdapter extends BastListAdapter {

    private final List<String> list;

    public FundsPupupListAdapter(List<String> list) {
        super(list);
        this.list =list;
    }

    @Override
    public View setView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewholder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fundspopupitem, null);
            viewholder = new ViewHolder(convertView);
            convertView.setTag(viewholder);
            AutoUtils.autoSize(convertView);
        }else{
            viewholder = (ViewHolder) convertView.getTag();
        }

        viewholder.fundsPopupItemText.setText(list.get(position));
        return convertView;
    }

    class ViewHolder {
        @BindView(R.id.funds_popup_item_text)
        TextView fundsPopupItemText;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
