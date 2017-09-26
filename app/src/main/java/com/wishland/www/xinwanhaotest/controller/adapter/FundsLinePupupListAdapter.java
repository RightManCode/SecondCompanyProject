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
 * Created by Administrator on 2017/5/23.
 */

public class FundsLinePupupListAdapter extends BastListAdapter {

    private final List<String> list_string;


    public FundsLinePupupListAdapter(List<String> year) {
        super(year);
        this.list_string = year;
    }

    @Override
    public View setView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewholder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fundspopupitem, null);
            viewholder = new ViewHolder(convertView);
            convertView.setTag(viewholder);
            AutoUtils.autoSize(convertView);
        } else {
            viewholder = (ViewHolder) convertView.getTag();
        }


        viewholder.fundsPopupItemText.setText(list_string.get(position).toString());


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
