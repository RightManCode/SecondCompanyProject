package com.wishland.www.xinwanhaotest.controller.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wishland.www.xinwanhaotest.R;

import com.wishland.www.xinwanhaotest.model.bean.LineDetailBean;
import com.wishland.www.xinwanhaotest.view.activity.LineDetailActivity;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/12.
 */

public class LineDetailAdapter extends BaseAdapter {
    private Context mcontext;
    private List<LineDetailBean.DataBean.ListBean> list;

    public LineDetailAdapter(LineDetailActivity lineDetailActivity) {
        this.mcontext = lineDetailActivity;

    }


     public void setData(List<LineDetailBean.DataBean.ListBean> list) {
          this.list = list;
      }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.line_detail_item, null, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
            AutoUtils.autoSize(convertView);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvNumber.setText(list.get(i).getSerialNum());
        viewHolder.tvMdtime.setText(list.get(i).getUsaTime());
        viewHolder.tvBjtime.setText(list.get(i).getChinaTime());
        viewHolder.tvContent.setText(list.get(i).getFrom() + " - " + list.get(i).getTo());
        viewHolder.tvMoney.setText(list.get(i).getSum()+"");
        viewHolder.tvState.setText(list.get(i).getDesc());
        viewHolder.tvover.setText(list.get(i).getResult());
        return convertView;
    }


    class ViewHolder {
        @BindView(R.id.tv_number)
        TextView tvNumber;
        @BindView(R.id.tv_mdtime)
        TextView tvMdtime;
        @BindView(R.id.tv_bjtime)
        TextView tvBjtime;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.tv_money)
        TextView tvMoney;
        @BindView(R.id.tv_state)
        TextView tvState;
        @BindView(R.id.tv_over)
        TextView tvover;
        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
