package com.wishland.www.xinwanhaotest.model.sp;

import android.content.Context;
import android.content.SharedPreferences;

import static com.wishland.www.xinwanhaotest.view.MyApplication.Mcontext;


/**
 * Created by Administrator on 2017/5/1.
 */

public class AccountDataSP {
    public static final String ACCOUNT_USER_NAME = "Account_User_Name";
    public static final String ACCOUNT_BANK_NAME = "Account_Bank_Name";
    public static final String ACCOUNT_BANK_NUMBER = "Account_Bank_Number";
    public static final String ACCOUNT_BANK_ADDRESS = "Account_Bank_Address";
    public static final String ACCOUNT_BANK_MONEY = "Account_Bank_Money";
    public static final String ACCOUNT_GLOBAL = "Global";
    public static final String ACCOUNTDATASP = "AccountDataSP";
    private SharedPreferences sp = Mcontext.getSharedPreferences(ACCOUNTDATASP, Context.MODE_PRIVATE);
    private  static AccountDataSP accountdatasp;



    private AccountDataSP() {
    }

    public static   AccountDataSP getSPInstance() {
        if (accountdatasp == null) {
            synchronized (AccountDataSP.class) {
                if (accountdatasp == null) {
                    accountdatasp = new AccountDataSP();
                }
            }
        }
        return accountdatasp;
    }

    /**
     * 收款人姓名
     */
    public  void setA_User_Name(String key, String value) {
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(key, value);
        edit.apply();
    }

    /**
     * 收款银行
     */
    public  void setA_Bank_Name(String key, String value) {
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(key, value);
        edit.apply();
    }

    /**
     * 银行卡号
     */
    public  void setA_Bank_Number(String key, String value) {
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(key, value);
        edit.apply();
    }

    /**
     * 开户行地址
     */
    public  void setA_Bank_Address(String key, String value) {
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(key, value);
        edit.apply();
    }

    /**
     * 账户余额
     */
    public void setA_Bank_money(String key, String value) {
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(key, value);
        edit.apply();
    }

    /**
     * 全局信息
     */
    public  void setA_Global(String key, String value) {
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(key, value);
        edit.apply();
    }


    public  String getAccountData(String key) {
        String value = sp.getString(key, "");
        return value;
    }


}
