package com.wishland.www.xinwanhaotest.controller.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wishland.www.xinwanhaotest.R;
import com.wishland.www.xinwanhaotest.model.bean.HKBean;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/25.
 */

public class PayTypeAdapter extends BastListAdapter {

    private  List<HKBean.DataBean.BankBean> bank;

    public PayTypeAdapter(List<HKBean.DataBean.BankBean> bank) {
        super(bank);
        this.bank = bank;
    }

    @Override
    public View setView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.paytype, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
            AutoUtils.autoSize(convertView);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tiName.setText(bank.get(position).getName());
        viewHolder.tiBank.setText(bank.get(position).getBank());
        viewHolder.tiNumber.setText(bank.get(position).getCardid());
        viewHolder.tiAddress.setText(bank.get(position).getAddress());

        return convertView;
    }

    class ViewHolder {
        @BindView(R.id.ti_name)
        TextView tiName;
        @BindView(R.id.ti_bank)
        TextView tiBank;
        @BindView(R.id.ti_number)
        TextView tiNumber;
        @BindView(R.id.ti_address)
        TextView tiAddress;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
