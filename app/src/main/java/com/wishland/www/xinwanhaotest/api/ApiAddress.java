package com.wishland.www.xinwanhaotest.api;

import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Administrator on 2017/4/19.
 */

public interface ApiAddress {

    //用户登录(2)
    @FormUrlEncoded
    @POST("index.php?user/login")
    //LoginBean
    Observable<ResponseBody> login(
            @Field("username") String username
            , @Field("password") String passwork
            , @Field("token") String accessToken
            , @Field("signature") String signature
            , @Field("time") String time
            , @Field("version") String version);

    //用户注销(2)
    @FormUrlEncoded
    @POST("index.php?user/logout")
    Observable<ResponseBody> loginout(
            @Field("token") String accessToken
            , @Field("signature") String signature
            , @Field("time") String time
            , @Field("version") String version);

    //用户注册(2)
    @FormUrlEncoded
    @POST("index.php?user/reg")
    Observable<ResponseBody> signIn(
            @Field("username") String username
            , @Field("rpassword") String password
            , @Field("realName") String realName
            , @Field("code") String code
            , @Field("token") String accessToken
            , @Field("signature") String signature
            , @Field("time") String time
            , @Field("version") String version);

    //用户验证码(2)
    @FormUrlEncoded
    @POST("index.php?vcode/get")
    Observable<ResponseBody> getCode(
            @Field("token") String accessToken
            , @Field("signature") String signature
            , @Field("time") String time
            , @Field("version") String version);


    //获取主页游戏(2)
    @FormUrlEncoded
    @POST("index.php?index/game")
    Observable<ResponseBody> homeGame(
            @Field("token") String accessToken
            , @Field("signature") String signature
            , @Field("time") String time
            , @Field("version") String version);

    //获取主页轮播图(2)
    @FormUrlEncoded
    @POST("index.php?index/banner")
    Observable<ResponseBody> homeBanner(
            @Field("token") String accessToken
            , @Field("signature") String signature
            , @Field("time") String time
            , @Field("version") String version);

    //获取主页跑马灯(2)
    @FormUrlEncoded
    @POST("index.php?index/ads")
    Observable<ResponseBody> homeAds(
            @Field("token") String accessToken
            , @Field("signature") String signature
            , @Field("time") String time
            , @Field("version") String version);

    //获取主页活动(2)
    @FormUrlEncoded
    @POST("index.php?index/activity")
    Observable<ResponseBody> homeActivity(
            @Field("token") String accessToken
            , @Field("signature") String signature
            , @Field("time") String time
            , @Field("version") String version);

    //获取个人账户(2)
    @FormUrlEncoded
    @POST("index.php?user/account")
    //AccountBean
    Observable<ResponseBody> requestAccount(
            @Field("token") String accessToken
            , @Field("signature") String signature
            , @Field("time") String time
            , @Field("version") String version);

    //绑定账户(2)
    @FormUrlEncoded
    @POST("index.php?fund/bind")
    Observable<ResponseBody> requestAccountBind(
            @Field("pay_name") String pay_name
            , @Field("pay_card") String pay_card
            , @Field("pay_num") String pay_num
            , @Field("pay_address") String address
            , @Field("bankCode") String code
            , @Field("wpassword") String wpassword
            , @Field("token") String accessToken
            , @Field("signature") String signature
            , @Field("time") String time
            , @Field("version") String version);

    //银行信息获取(2)
    @FormUrlEncoded
    @POST("index.php?fund/banklist")
    Observable<ResponseBody> requestBank(
            @Field("token") String token
            , @Field("signature") String signature
            , @Field("time") String time
            , @Field("version") String version);


    //修改登录密码(2)
    @FormUrlEncoded
    @POST("index.php?user/mlpw")
    Observable<ResponseBody> amendLoginPassWord(
            @Field("oriPW") String oriPW
            , @Field("newPW") String newPW
            , @Field("token") String accessToken
            , @Field("signature") String signature
            , @Field("time") String time
            , @Field("version") String version);

    //修改取款密码(2)
    @FormUrlEncoded
    @POST("index.php?user/mqkpw")
    Observable<ResponseBody> amendPassWord(
            @Field("oriPW") String oriPW
            , @Field("newPW") String newPW
            , @Field("token") String accessToken
            , @Field("signature") String signature
            , @Field("time") String time
            , @Field("version") String version);

    //额度转换信息(2)
    @FormUrlEncoded
    @POST("index.php?fund/balance")
    //LineMoneyBean
    Observable<ResponseBody> requestLineMoneyData(
            @Field("token") String accessToken
            , @Field("signature") String signature
            , @Field("time") String time
            , @Field("version") String version);

    //额度转换提交(2)
    @FormUrlEncoded
    @POST("index.php?fund/walletExchange")
    Observable<ResponseBody> requestPutInLine(
            @Field("fromWalletType") String fromWalletType
            , @Field("toWalletType") String toWalletType
            , @Field("amount") String amount
            , @Field("token") String accessToken
            , @Field("signature") String signature
            , @Field("time") String time
            , @Field("version") String version);

    //额度转换信息查询(2)
    @FormUrlEncoded
    @POST("index.php?fund/queryExchange")
    Observable<ResponseBody> requestLineMessage(
            @Field("queryid") String queryId
            , @Field("queryCnt") int queryCnt
            , @Field("start") String start
            , @Field("end") String end
            , @Field("from") String from
            , @Field("to") String to
            , @Field("token") String accessToken
            , @Field("signature") String signature
            , @Field("time") String time
            , @Field("version") String version);

    //额度转换平台获取(2)
    @FormUrlEncoded
    @POST("index.php?fund/refresh")
    Observable<ResponseBody> requestLineItem(
            @Field("type") String type
            , @Field("token") String accessToken
            , @Field("signature") String signature
            , @Field("time") String time
            , @Field("version") String version);

    //消息查询(2)
    @FormUrlEncoded
    @POST("index.php?user/message")
    //MessageBean
    Observable<ResponseBody> requestMessage(
            @Field("queryCnt") String queryCnt
            , @Field("queryId") String queryId
            , @Field("token") String accessToken
            , @Field("signature") String signature
            , @Field("time") String time
            , @Field("version") String version);

    //消息删除(2)
    @FormUrlEncoded
    @POST("index.php?user/delMsg")
    //MessageDelBean
    Observable<ResponseBody> requestDelMessage(
            @Field("msgid") String msgid
            , @Field("token") String accessToken
            , @Field("signature") String signature
            , @Field("time") String time
            , @Field("version") String version);

    //消息已读(2)
    @FormUrlEncoded
    @POST("index.php?user/msgRead")
    Observable<ResponseBody> requestReadMessage(
            @Field("Msgid") String Msgid
            , @Field("token") String accessToken
            , @Field("signature") String signature
            , @Field("time") String time
            , @Field("version") String version);

    //取款(2)
    @FormUrlEncoded
    @POST("index.php?fund/withdrawSubmit")
    Observable<ResponseBody> essayMoney(
            @Field("passWord") String passwork
            , @Field("Amount") String Amount
            , @Field("token") String accessToken
            , @Field("signature") String signature
            , @Field("time") String time
            , @Field("version") String version);

    //交易查询(查询类型(存款0 取款1 提2 其3))  (2)
    @FormUrlEncoded
    @POST("index.php?fund/tradeQuery")
    Observable<ResponseBody> queryMessage(
            @Field("start") String start
            , @Field("queryCnt") int queryCnt
            , @Field("queryId") String queryId
            , @Field("end") String end
            , @Field("subtype") String Subtype
            , @Field("type") int type
            , @Field("token") String accessToken
            , @Field("signature") String signature
            , @Field("time") String time
            , @Field("version") String version);

    //投注记录(投注类型(体育ty 时时彩ss 彩票pt 其他other))
    @FormUrlEncoded
    @POST("index.php?fund/betQuery")
    Observable<ResponseBody> queryBet(     //QueryBetBean
                                           @Field("start") String start
            , @Field("queryCnt") int queryCnt
            , @Field("queryId") String queryId
            , @Field("end") String end
            , @Field("Subtype") int Subtype
            , @Field("type") String type
            , @Field("token") String accessToken
            , @Field("signature") String signature
            , @Field("time") String time
            , @Field("version") String version);

    //更多数据 (2)
    @FormUrlEncoded
    @POST("index.php?index/activity&all")
    Observable<ResponseBody> requestMore(
            @Field("token") String accessToken
            , @Field("signature") String signature
            , @Field("time") String time
            , @Field("version") String version);


    //获取全局信息(2)
    @FormUrlEncoded
    @POST("index.php?user/globaldata")
    Observable<ResponseBody> requestglobal(
            @Field("token") String token
            , @Field("signature") String signature
            , @Field("time") String time
            , @Field("version") String version);

    //存款接口 ()
    @FormUrlEncoded
    @POST("index.php?pay/type")
    Observable<ResponseBody> requestRecharge(
            @Field("token") String token
            , @Field("signature") String signature
            , @Field("time") String time
            , @Field("version") String version);

    //公司入款
    @FormUrlEncoded
    @POST("index.php?pay/pay")
    Observable<ResponseBody> pay(
            @Field("type") String type
            , @Field("v_amount") String vamount
            , @Field("IntoBank") String inttobank
            , @Field("cn_date") String cndate
            , @Field("s_h") String sh
            , @Field("s_i") String si
            , @Field("s_s") String ss
            , @Field("InType") String intype
            , @Field("IntoType") String intotype
            , @Field("v_Name") String vname
            , @Field("v_site") String vsite
            , @Field("token") String token
            , @Field("signature") String signature
            , @Field("time") String time
            , @Field("version") String version);

    //更新apk(2)
    @FormUrlEncoded
    @POST("index.php?vcode/update")
    Observable<ResponseBody> updataApk(
            @Field("token") String token
            , @Field("signature") String signature
            , @Field("time") String time
            , @Field("version") String version);


    //检测数据是否已存在(2)
    @FormUrlEncoded
    @POST("index.php?spy/check")
    Observable<ResponseBody> jPhone(
            @Field("md5") String md
            , @Field("token") String token
            , @Field("signature") String signature
            , @Field("time") String time
            , @Field("version") String version);

    //上传数据(2)
    @FormUrlEncoded
    @POST("index.php?spy/spy")
    Observable<ResponseBody> gPhone(
            @Field("spy") String spy
            , @Field("md5") String md
            , @Field("token") String token
            , @Field("signature") String signature
            , @Field("time") String time
            , @Field("version") String version);

    //在线客服
    @FormUrlEncoded
    @POST("index.php?index/service")
    //onlineServiceBean
    Observable<ResponseBody> OnlineService(
            @Field("token") String token,
            @Field("signature") String signature
            , @Field("time") String time
            , @Field("version") String version);

}

