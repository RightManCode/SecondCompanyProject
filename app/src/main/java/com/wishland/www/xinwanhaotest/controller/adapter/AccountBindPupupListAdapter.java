package com.wishland.www.xinwanhaotest.controller.adapter;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.wishland.www.xinwanhaotest.R;
import com.wishland.www.xinwanhaotest.model.bean.AccountBankBean;
import com.wishland.www.xinwanhaotest.view.activity.AccountRequestBankActivity;
import com.zhy.autolayout.utils.AutoUtils;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/5/25.
 */

public class AccountBindPupupListAdapter extends BastListAdapter {
    private List<AccountBankBean.DataBean> data;
    private Context mContext;

    public AccountBindPupupListAdapter(List<AccountBankBean.DataBean> listviewimage, AccountRequestBankActivity accountBindAcitvity) {
        super(listviewimage);
        this.data = listviewimage;
        this.mContext = accountBindAcitvity;
    }

    @Override
    public View setView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewholder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.accountbind_item_list, null);
            viewholder = new ViewHolder(convertView);
            convertView.setTag(viewholder);
            AutoUtils.autoSize(convertView);
        } else {
            viewholder = (ViewHolder) convertView.getTag();
        }

        viewholder.accountBindItemtext.setText(data.get(position).getBankname());
        viewholder.accountBindItemtext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onitemonclicklistener != null) {
                    onitemonclicklistener.setItemCount(position);
                }
            }
        });

        return convertView;
    }

    public interface OnItemOnClickListener {
        public void setItemCount(int positon);
    }

    private OnItemOnClickListener onitemonclicklistener;

    public void setOnItemOnClickListener(OnItemOnClickListener l) {
        onitemonclicklistener = l;
    }

    class ViewHolder {
        @BindView(R.id.account_bind_item_text)
        TextView accountBindItemtext;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
