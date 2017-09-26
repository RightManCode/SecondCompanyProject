package com.wishland.www.xinwanhaotest.api;

import retrofit2.Retrofit;

/**
 * Created by Administrator on 2017/4/20.
 */
public abstract class BaseApi {
    public static final String VERSION = "1";
    public static final String KEYSTORE = "4414c5d94ca24942bad650c18ecf49a5";
    //    public static final String CustomHtml5 = "https://chat6.livechatvalue.com/chat/chatClient/chatbox.jsp?companyID=17779&configID=48708&jid=&s=1";
    public static final String HTML5DATA = "HTML5";
    public static final String SIXURI = "SIXURI";
    public static final String SIXTEXT = "SIXTEXT";
    public static String CustomHtml5="";
    /*基础url*/
    public String url ="https://tpfw.083075.com:8080";
    public  String baseUrl;
    public static String NEWHTML = "com.wanhaohui.www.html";

    public BaseApi(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    /**
     * 设置参数
     *
     * @param retrofit
     * @return
     */
    public abstract ApiAddress getObservable(Retrofit retrofit);

    public String getBaseUrl() {
        return baseUrl;
    }
}
