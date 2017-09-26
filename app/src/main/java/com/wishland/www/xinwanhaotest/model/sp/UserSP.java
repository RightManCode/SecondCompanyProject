package com.wishland.www.xinwanhaotest.model.sp;

import android.content.Context;
import android.content.SharedPreferences;

import static com.wishland.www.xinwanhaotest.view.MyApplication.Mcontext;


/**
 * Created by Administrator on 2017/4/21.
 */

public class UserSP {
    public static final String LOGIN_CHECKED = "checked";
    public static final String LOGIN_USERNAME = "username";
    public static final String LOGIN_PASSWORD = "password";
    public static final String LOGIN_TOKEN = "token";
    public static final String LOGIN_SUCCESS = "success";
    public static final String LOGIN_SITE = "site";
    public static final String USERSP = "nqusersp";
    private SharedPreferences sp = Mcontext.getSharedPreferences(USERSP, Context.MODE_PRIVATE);
    private static UserSP usersp;


    private UserSP() {
    }

    public static UserSP getSPInstance() {
        if (usersp == null) {
            synchronized (UserSP.class) {
                if (usersp == null) {
                    usersp = new UserSP();
                }
            }
        }
        return usersp;
    }

    /**
     * @param key
     * @param value 保存是否记住密码状态
     */
    public void setCheckedSP(String key, boolean value) {
        SharedPreferences.Editor edit = sp.edit();
        edit.putBoolean(key, value);
        edit.apply();
    }

    /**
     * @param key
     * @param value 保存用戶名
     */
    public void setUserName(String key, String value) {
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(key, value);
        edit.apply();
    }


    /**
     * 保存密码
     */
    public void setPassWord(String loginPassword, String keypassword) {
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(loginPassword, keypassword);
        edit.apply();
    }

    /**
     * 保存token
     */
    public void setToken(String key, String token) {
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(key, token);
        edit.apply();
    }

    /**
     * 保存token
     */
    public void setSuccess(String key, int token) {
        SharedPreferences.Editor edit = sp.edit();
        edit.putInt(key, token);
        edit.apply();
    }

    /**
     * 保存pc_url
     */
    public void setSite(String key, String site) {
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(key, site);
        edit.apply();
    }


    /**
     * @param key
     * @return 返回保存密码状态
     */
    public boolean getBoolean(String key) {
        boolean aBoolean = sp.getBoolean(key, false);
        return aBoolean;
    }

    public String getString(String key) {
        String string = sp.getString(key, "");
        return string;
    }

    public String getToken(String key) {
        String string = sp.getString(key, "");
        return string;
    }

    public int getInt(String key) {
        int uid = sp.getInt(key, -1);
        return uid;
    }
}
