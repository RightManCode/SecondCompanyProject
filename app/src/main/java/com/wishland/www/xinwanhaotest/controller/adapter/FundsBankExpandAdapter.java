package com.wishland.www.xinwanhaotest.controller.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import com.wishland.www.xinwanhaotest.R;
import com.wishland.www.xinwanhaotest.model.bean.FundsBankBank;
import com.wishland.www.xinwanhaotest.view.activity.AlipayActivity;
import com.wishland.www.xinwanhaotest.view.activity.HKActivity;
import com.wishland.www.xinwanhaotest.view.activity.OnlinePaymentActivity;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 存款
 */

public class FundsBankExpandAdapter extends BaseExpandableListAdapter {

    private List<FundsBankBank.DataBean.PayListBean> pay_list;
    private Context mContext;
    private Intent intent;
    private String url;


    public FundsBankExpandAdapter(Context bastcontext) {
        this.mContext = bastcontext;
    }

    public void setExpandableData(FundsBankBank fundsBankBank) {
        this.url = fundsBankBank.getData().getUrl();
        this.pay_list = fundsBankBank.getData().getPay_list();
    }


    @Override

    public int getGroupCount() {
        return pay_list.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return pay_list.get(i).getItem().size();
    }

    @Override
    public Object getGroup(int i) {
        return pay_list.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return pay_list.get(i).getItem().get(i);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        GroupHolder groupholder;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.fundsgroupitem, viewGroup, false);
            groupholder = new GroupHolder(view);
            view.setTag(groupholder);
            AutoUtils.autoSize(view);
        } else {
            groupholder = (GroupHolder) view.getTag();
        }

        Uri uri = Uri.parse(pay_list.get(i).getTitle_img());
        groupholder.fgImageview.setImageURI(uri);

        groupholder.fgTextview.setText(pay_list.get(i).getTitle());

        return view;
    }

    @Override
    public View getChildView(final int i, final int i1, boolean b, View view, ViewGroup viewGroup) {
        ChildHolder childholder;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.fundschilditem, viewGroup, false);
            childholder = new ChildHolder(view);
            view.setTag(childholder);
            AutoUtils.autoSize(view);
        } else {
            childholder = (ChildHolder) view.getTag();
        }
        List<FundsBankBank.DataBean.PayListBean.ItemBean> item = pay_list.get(i).getItem();
        final String name = item.get(i1).getName();
        if (item.size() != 0 || item != null) {
            childholder.fcImageview.setText(name);
            childholder.fcImageview.setBackgroundResource(R.drawable.funds_button1_green_selector);
        }

        childholder.fcImageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FundsBankBank.DataBean.PayListBean payListBean = pay_list.get(i);//父类
                String type = payListBean.getType();
                switch (type) {
                    case OnlinePaymentActivity.PAY:
                        String dataurl = url.replace("[para]", payListBean.getItem().get(i1).getPara());
                        intent = new Intent(mContext, OnlinePaymentActivity.class);
                        intent.putExtra("SAVE_TITLE", name);
                        intent.putExtra("URI", dataurl);
                        mContext.startActivity(intent);
                        break;

                    case HKActivity.HK:
                        String replace = url.replace("[para]",payListBean.getItem().get(i1).getPara());
                        intent = new Intent(mContext, HKActivity.class);
                        intent.putExtra("SAVE_TITLE", name);
                        intent.putExtra("URI", replace);
                        mContext.startActivity(intent);
                        break;

                    case AlipayActivity.ALIPAY:
                        String replace1 = url.replace("[para]", payListBean.getItem().get(i1).getPara());
                        intent = new Intent(mContext, AlipayActivity.class);
                        intent.putExtra("TYPE",AlipayActivity.ALIPAY);
                        intent.putExtra("TITLE","支付宝");
                        intent.putExtra("SAVE_TITLE", name);
                        intent.putExtra("URI", replace1);
                        mContext.startActivity(intent);
                        break;

                    case AlipayActivity.WEIXIN:
                        String replace2 = url.replace("[para]", payListBean.getItem().get(i1).getPara());
                        intent = new Intent(mContext, AlipayActivity.class);
                        intent.putExtra("TYPE",AlipayActivity.WEIXIN);
                        intent.putExtra("TITLE","微信");
                        intent.putExtra("SAVE_TITLE", name);
                        intent.putExtra("URI", replace2);
                        mContext.startActivity(intent);
                        break;

                    case AlipayActivity.CAIFUTONG:
                        String replace3 = url.replace("[para]", payListBean.getItem().get(i1).getPara());
                        intent = new Intent(mContext, AlipayActivity.class);
                        intent.putExtra("TYPE",AlipayActivity.CAIFUTONG);
                        intent.putExtra("TITLE","财付通");
                        intent.putExtra("SAVE_TITLE", name);
                        intent.putExtra("URI", replace3);
                        mContext.startActivity(intent);
                        break;
                }

            }
        });

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }

    class ChildHolder {
        @BindView(R.id.fc_imageview)
        Button fcImageview;

        ChildHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    class GroupHolder {
        @BindView(R.id.fg_imageview)
        SimpleDraweeView fgImageview;
        @BindView(R.id.fg_textview)
        TextView fgTextview;

        GroupHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
