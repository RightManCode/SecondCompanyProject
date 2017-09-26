package com.wishland.www.xinwanhaotest.view.customgridview;


import com.wishland.www.xinwanhaotest.controller.adapter.HomeMenuAdapter;

/**
 * Created by mac on 16/6/18.
 */
public interface Y_OnBind<T> {

    /**
     * @param holder
     * @param position
     */
    void onBindChildViewData(HomeMenuAdapter.GeneralRecyclerViewHolder holder, Object itemData, int position);
}
