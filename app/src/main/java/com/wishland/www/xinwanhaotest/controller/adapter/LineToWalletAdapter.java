package com.wishland.www.xinwanhaotest.controller.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wishland.www.xinwanhaotest.R;
import com.wishland.www.xinwanhaotest.model.bean.LineMoneyBean;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/4/25.
 */

public class LineToWalletAdapter extends BaseAdapter {
    private final List<LineMoneyBean.DataBean.WalletBean> titlename;

    public LineToWalletAdapter(List<LineMoneyBean.DataBean.WalletBean> titlename) {
        this.titlename =titlename;
    }

    @Override
    public int getCount() {
        return titlename.size();
    }

    @Override
    public Object getItem(int i) {
        return titlename.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder viewholder;
        if (convertView == null) {
            convertView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.linegridviewitem_to, null);
            viewholder = new ViewHolder(convertView);
            convertView.setTag(viewholder);
            AutoUtils.autoSize(convertView);
        } else {
            viewholder = (ViewHolder) convertView.getTag();
        }

        viewholder.lineGridviewItemTextname.setText(titlename.get(i).getName());
        viewholder.lineGridviewItemTextmoney.setText(titlename.get(i).getAmout());

        if (clickTemp == i) {
            viewholder.lineGridviewItemClickBg.setBackgroundResource(R.color.line_gridview_item_pressed);
            viewholder.lineGridviewItemOk.setVisibility(View.VISIBLE);
        } else {
            viewholder.lineGridviewItemClickBg.setBackgroundResource(R.color.line_gridview_item_normal);
            viewholder.lineGridviewItemOk.setVisibility(View.GONE);
        }

        return convertView;

    }

    private int clickTemp = -1;

    public void setSelection(int position) {
        clickTemp = position;
    }

    class ViewHolder {
        @BindView(R.id.line_gridview_item_textname)
        TextView lineGridviewItemTextname;
        @BindView(R.id.line_gridview_item_textmoney)
        TextView lineGridviewItemTextmoney;
        @BindView(R.id.line_gridview_item_ok)
        ImageView lineGridviewItemOk;
        @BindView(R.id.line_gridview_item_click_bg)
        RelativeLayout lineGridviewItemClickBg;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
