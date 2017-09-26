package com.wishland.www.xinwanhaotest.controller.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wishland.www.xinwanhaotest.R;

import com.wishland.www.xinwanhaotest.view.customgridview.Y_ItemEntityList;
import com.zhy.autolayout.utils.AutoUtils;

/**
 * Created by Administrator on 2017/5/24.
 */

public class HomeMenuAdapter extends RecyclerView.Adapter<HomeMenuAdapter.GeneralRecyclerViewHolder> {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private Y_ItemEntityList itemList;


    public HomeMenuAdapter(Context ct, Y_ItemEntityList itemList) {
        this.mContext = ct;
        this.itemList = itemList;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public GeneralRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new GeneralRecyclerViewHolder(mLayoutInflater.inflate(viewType, parent, false));
    }

    @Override
    public void onBindViewHolder(HomeMenuAdapter.GeneralRecyclerViewHolder holder, final int position) {
        if (itemList.getOnBind(position) != null) {
            itemList.getOnBind(position).onBindChildViewData(holder, itemList.getItemData(position), position);
           // holder.mItemView.setTag(position);
            TextView tv = (TextView) holder.mItemView.findViewById(R.id.tv_home_game_menu);
            tv.setTag(position);
        }

    }

    @Override
    public int getItemViewType(int position) {
        return itemList.getItemLayoutId(position);
    }

    @Override
    public int getItemCount() {
        return itemList.getItemCount();
    }



    public class GeneralRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final View mItemView;
        private final SparseArray<View> childViews;

        public GeneralRecyclerViewHolder(View itemView) {
            super(itemView);
            AutoUtils.autoSize(itemView);
            this.mItemView = itemView;
            this.childViews = new SparseArray<>(20);
        }

        public <T extends View> T getChildView(int childViewId) {
            View view = childViews.get(childViewId);
            if (view == null) {
                view = mItemView.findViewById(childViewId);
                childViews.put(childViewId, view);
            }
            return (T) view;
        }

        public GeneralRecyclerViewHolder setText(int childViewId, String text) {
            TextView textView = getChildView(childViewId);
            textView.setText(text);
            textView.setOnClickListener(this);
            return this;
        }

        @Override
        public void onClick(View view) {
            if(mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(view,(int)view.getTag());
            }
        }
    }

    private OnItemClickListener mOnItemClickListener;

    public  interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
}
