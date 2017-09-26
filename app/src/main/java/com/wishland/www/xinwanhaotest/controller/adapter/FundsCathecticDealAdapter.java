package com.wishland.www.xinwanhaotest.controller.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.wishland.www.xinwanhaotest.R;
import com.wishland.www.xinwanhaotest.model.bean.QueryMessageListBean;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/5/5.
 */

public class FundsCathecticDealAdapter extends BaseAdapter {
    /**
     * 存款
     */
    private static final int TYPE_ADDMONEY = 1;

    /**
     * 汇款
     */
    private static final int TYPE_REMIT = 2;

    /**
     * 提款
     */
    private static final int TYPE_PUTMONEY = 3;

    /**
     * 其他
     */
    private static final int TYPE_OTHER = 4;

    private List<QueryMessageListBean> listData;
    private Context context;
    private int type;

    public FundsCathecticDealAdapter(Context bastcontext, int type) {
        this.type = type;
        this.context = bastcontext;
    }

    public void setData(List<QueryMessageListBean> commonList){
        this.listData = commonList;
    }
    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public int getViewTypeCount() {
        int itemViewType = -1;
        if (0 == type) {
            itemViewType = TYPE_ADDMONEY;
        } else if (1 == type) {
            itemViewType = TYPE_REMIT;
        } else if (2 == type) {
            itemViewType = TYPE_PUTMONEY;
        } else if (3 == type) {
            itemViewType = TYPE_OTHER;
        }
        return itemViewType;
    }

    @Override
    public Object getItem(int i) {
        return listData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder viewholder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.fundscathdeal_item, null, false);
            viewholder = new ViewHolder(convertView);
            convertView.setTag(viewholder);
            AutoUtils.autoSize(convertView);
        } else {
            viewholder = (ViewHolder) convertView.getTag();
        }
        setData(getViewTypeCount(), viewholder, listData, position);
        return convertView;
    }


    private void setData(int viewTypeCount, ViewHolder viewholder, List<QueryMessageListBean> listData, int i) {
        switch (viewTypeCount) {
            case TYPE_ADDMONEY:
                viewholder.fundsDealItem1T.setText("存款流水号：");
                viewholder.fundsDealItem1SR.setText(listData.get(i).getSerialNum());
                viewholder.fundsDealItem2T.setText("美东时间：");
                viewholder.fundsDealItem2SR.setText(listData.get(i).getUsaTime());
                viewholder.fundsDealItem3T.setText("北京时间：");
                viewholder.fundsDealItem3SR.setText(listData.get(i).getChinaTime());
                viewholder.fundsDealItem4T.setText("存款金额：");
                viewholder.fundsDealItem4SR.setText(listData.get(i).getSum());
                viewholder.fundsDealItem5T.setText("手续费：");
                viewholder.fundsDealItem5SR.setText(listData.get(i).getPoints());
                viewholder.fundsDealItem6T.setText("存款状态：");
                viewholder.fundsDealItem6SR.setTextColor(context.getResources().getColor(R.color.red));
                viewholder.fundsDealItem6SR.setText(listData.get(i).getState());
                viewholder.fundsDealItem7Linear.setVisibility(View.GONE);
                break;
            case TYPE_REMIT:
                viewholder.fundsDealItem1T.setText("汇款流水号:");
                viewholder.fundsDealItem1SR.setText(listData.get(i).getSerialNum());
                viewholder.fundsDealItem2T.setText("提交时间：");
                viewholder.fundsDealItem2SR.setText(listData.get(i).getUsaTime());
                viewholder.fundsDealItem3T.setText("实际汇款时间：");
                viewholder.fundsDealItem3SR.setText(listData.get(i).getChinaTime());
                viewholder.fundsDealItem4T.setText("汇款金额：");
                viewholder.fundsDealItem4SR.setText(listData.get(i).getSum());
                viewholder.fundsDealItem5T.setText("汇款赠送：");
                viewholder.fundsDealItem5SR.setText(listData.get(i).getPoints());
                viewholder.fundsDealItem6T.setText("汇款状态：");
                viewholder.fundsDealItem6SR.setTextColor(context.getResources().getColor(R.color.red));
                viewholder.fundsDealItem6SR.setText((listData.get(i).getState().equals("1")? "成功":"失败"));
                viewholder.fundsDealItem7Linear.setVisibility(View.GONE);
                break;
            case TYPE_PUTMONEY:
                viewholder.fundsDealItem1T.setText("提款流水号：");
                viewholder.fundsDealItem1SR.setText(listData.get(i).getSerialNum());
                viewholder.fundsDealItem2T.setText("美东时间：");
                viewholder.fundsDealItem2SR.setText(listData.get(i).getUsaTime());
                viewholder.fundsDealItem3T.setText("北京时间：");
                viewholder.fundsDealItem3SR.setText(listData.get(i).getChinaTime());
                viewholder.fundsDealItem4T.setText("提款金额：");
                viewholder.fundsDealItem4SR.setText(listData.get(i).getSum());
                viewholder.fundsDealItem5T.setText("手续费：");
                viewholder.fundsDealItem5SR.setText(listData.get(i).getPoints());
                viewholder.fundsDealItem6T.setText("提款状态：");
                viewholder.fundsDealItem6SR.setTextColor(context.getResources().getColor(R.color.red));
                viewholder.fundsDealItem6SR.setText(listData.get(i).getState());
                viewholder.fundsDealItem7Linear.setVisibility(View.GONE);
                break;
            case TYPE_OTHER:
                viewholder.fundsDealItem1T.setText("流水号：");
                viewholder.fundsDealItem1SR.setText(listData.get(i).getSerialNum());
                viewholder.fundsDealItem2T.setText("美东时间：");
                viewholder.fundsDealItem2SR.setText(listData.get(i).getUsaTime());
                viewholder.fundsDealItem3T.setText("北京时间：");
                viewholder.fundsDealItem3SR.setText(listData.get(i).getChinaTime());
                viewholder.fundsDealItem4T.setText("金额  ：");
                viewholder.fundsDealItem4SR.setText(listData.get(i).getSum());
                viewholder.fundsDealItem5T.setText("类型：");
                viewholder.fundsDealItem5SR.setText(listData.get(i).getType());
                viewholder.fundsDealItem6T.setText("备注说明：");
                viewholder.fundsDealItem6SR.setText(listData.get(i).getNotes());
                viewholder.fundsDealItem7T.setText("状态：");
                viewholder.fundsDealItem7SR.setTextColor(context.getResources().getColor(R.color.red));
                viewholder.fundsDealItem7SR.setText(listData.get(i).getState());
                break;
        }
    }


     class ViewHolder {
        @BindView(R.id.funds_deal_item1_T)
        TextView fundsDealItem1T;
        @BindView(R.id.funds_deal_item1_SR)
        TextView fundsDealItem1SR;
        @BindView(R.id.funds_deal_item2_T)
        TextView fundsDealItem2T;
        @BindView(R.id.funds_deal_item2_SR)
        TextView fundsDealItem2SR;
        @BindView(R.id.funds_deal_item3_T)
        TextView fundsDealItem3T;
        @BindView(R.id.funds_deal_item3_SR)
        TextView fundsDealItem3SR;
        @BindView(R.id.funds_deal_item4_T)
        TextView fundsDealItem4T;
        @BindView(R.id.funds_deal_item4_SR)
        TextView fundsDealItem4SR;
        @BindView(R.id.funds_deal_item5_T)
        TextView fundsDealItem5T;
        @BindView(R.id.funds_deal_item5_SR)
        TextView fundsDealItem5SR;
        @BindView(R.id.funds_deal_item6_T)
        TextView fundsDealItem6T;
        @BindView(R.id.funds_deal_item6_SR)
        TextView fundsDealItem6SR;
        @BindView(R.id.funds_deal_item7_T)
        TextView fundsDealItem7T;
        @BindView(R.id.funds_deal_item7_SR)
        TextView fundsDealItem7SR;
        @BindView(R.id.funds_deal_item7_Linear)
        LinearLayout fundsDealItem7Linear;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
