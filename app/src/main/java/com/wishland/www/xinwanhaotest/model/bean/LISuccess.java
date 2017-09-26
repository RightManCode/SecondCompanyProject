package com.wishland.www.xinwanhaotest.model.bean;

import android.text.TextUtils;

/**
 * Created by Administrator on 2017/8/7.
 */

public class LISuccess {
   private  String status;



    public LISuccess(String status) {
        this.status = status;
    }

    public boolean getStatus() {
        return TextUtils.equals(status, "success");
    }
}
