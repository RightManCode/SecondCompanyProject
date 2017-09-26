package com.wishland.www.xinwanhaotest.controller.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wishland.www.xinwanhaotest.R;

import com.wishland.www.xinwanhaotest.model.bean.SixSpacesBean;
import com.wishland.www.xinwanhaotest.utils.FrescoUtil;
import com.wishland.www.xinwanhaotest.utils.LogUtil;
import com.wishland.www.xinwanhaotest.view.activity.SixSpacesListAcitivity;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/5/15.
 */

public class SixSpacesAdapter extends BaseAdapter {
    private Context Lcontext;
    private List<SixSpacesBean.DataBean.GameBean> list;
    private String imgPath;


    public SixSpacesAdapter(SixSpacesListAcitivity sixSpacesListAcitivity) {
        this.Lcontext = sixSpacesListAcitivity;
    }

    public void setListData(List<SixSpacesBean.DataBean.GameBean> list, String imgPath) {
        this.list = list;
        this.imgPath = imgPath;
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
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        ViewHolder viewholder;
        if (convertView == null) {
            convertView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.sixspaceslist_item, null);
            viewholder = new ViewHolder(convertView);
            convertView.setTag(viewholder);
            AutoUtils.autoSize(convertView);
        } else {
            viewholder = (ViewHolder) convertView.getTag();
        }

        String url = imgPath + list.get(position).getImage().toString();
        LogUtil.e("二级页面加载URL："+url);
        FrescoUtil.loadGifPicOnNet(viewholder.sixspacesListItemView,url);

        viewholder.sixspacesListItemText.setText(list.get(position).getName());
        viewholder.sixspacesListItemButton.setOnClickListener(new View.OnClickListener() {
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
        void setItemCount(int positon);
    }

    private OnItemOnClickListener onitemonclicklistener;

    public void setOnItemOnClickListener(OnItemOnClickListener l) {
        onitemonclicklistener = l;
    }


    static class ViewHolder {
        @BindView(R.id.sixspaces_list_item_view)
        SimpleDraweeView sixspacesListItemView;
        @BindView(R.id.sixspaces_list_item_text)
        TextView sixspacesListItemText;
        @BindView(R.id.sixspaces_list_item_button)
        Button sixspacesListItemButton;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
