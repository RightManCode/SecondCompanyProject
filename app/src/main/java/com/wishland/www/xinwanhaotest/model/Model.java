package com.wishland.www.xinwanhaotest.model;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;

import com.google.gson.Gson;
import com.wishland.www.xinwanhaotest.api.BaseApi;
import com.wishland.www.xinwanhaotest.model.sp.AccountDataSP;
import com.wishland.www.xinwanhaotest.model.sp.UserSP;
import com.wishland.www.xinwanhaotest.utils.AppUtils;
import com.wishland.www.xinwanhaotest.utils.LogUtil;
import com.wishland.www.xinwanhaotest.utils.Utils_time;
import com.wishland.www.xinwanhaotest.view.activity.WelcomeActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by admin on 2017/9/20.
 */

public class Model {
    private static Model model;
    private JSONObject jsonObject;
    private Gson gson = new Gson();

    /**
     * @return 返回Model实例
     */
    public static Model getInstance() {

        if (model == null) {
            synchronized (Model.class) {
                if (model == null) {
                    model = new Model();
                }
            }
        }

        return model;
    }

    //创建一个缓存线程池
    private ExecutorService executorService = Executors.newFixedThreadPool(12);

    /**
     * @return 提供公共的访问方法
     */
    public ExecutorService getExecutorService() {
        return executorService;
    }

    public JSONObject getJsonObject(String json) throws JSONException {
        return jsonObject = new JSONObject(json);
    }

    public Gson getGson() {
        return gson;
    }

    public AlertDialog.Builder getAlertDialog(Context context) {
        return new AlertDialog.Builder(context);
    }

    /**
     * @return 返回登录信息
     */
    public UserSP getUserSP() {
        return UserSP.getSPInstance();
    }

    /**
     * @return 返回个人信息
     */
    public AccountDataSP getAccountDataSP() {
        return AccountDataSP.getSPInstance();
    }

    public void setToken_SP(String token) {
        if (!token.isEmpty()) {
            getUserSP().setToken(UserSP.LOGIN_TOKEN, token);
        }

    }


    public void skipLoginActivity(Activity activity, Class zlass) {
        getUserSP().setSuccess(UserSP.LOGIN_SUCCESS, -1);
        Intent intent = new Intent(activity, zlass);
        activity.startActivityForResult(intent, 2);

    }


    /**
     * 进入浏览器
     */
    public void toBrowser(Context activity) {
        String string = getUserSP().getString(UserSP.LOGIN_SITE);
        if (!string.isEmpty()) {
            LogUtil.e("进入浏览器");
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            Uri content_url = Uri.parse(string);
            intent.setData(content_url);
            activity.startActivity(intent);
        }
    }


    /**
     * 登录页面请求
     */
    public void api(String username, String password, String token, String signtrue,
                    Subscriber<?> subscriber) {
        Map<String, Object> params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
        params.put("token", token);
        params.put("signature", signtrue);
        params.put("time", Utils_time.getSeconds());
        params.put("version", BaseApi.VERSION);
        AppUtils.getInstance().onRequest("index.php?user/login ", params);
        Observable<ResponseBody> login = WelcomeActivity.observable.login(username, password, token, signtrue, Utils_time.getSeconds(), BaseApi.VERSION);
        setObservable(login, subscriber);
    }

    /**
     * 注销页面
     */
    public void apiLoginOut(String token, String signtrue,
                            Subscriber<?> subscriber) {
        Map<String, Object> params = new HashMap<>();
        params.put("token", token);
        params.put("signature", signtrue);
        params.put("time", Utils_time.getSeconds());
        params.put("version", BaseApi.VERSION);
        AppUtils.getInstance().onRequest("index.php?user/logout", params);
        Observable<ResponseBody> loginout = WelcomeActivity.observable
                .loginout(token, signtrue, Utils_time.getSeconds(), BaseApi.VERSION);
        setObservable(loginout, subscriber);
    }

    /**
     * 注册页面
     */
    public void apiSignIn(String username, String password, String realName, String code, String token, String signtrue, Subscriber<?> subscriber) {
        Map<String, Object> params = new HashMap<>();
        params.put("username", username);
        params.put("rpassword", password);
        params.put("realName", realName);
        params.put("code", code);
        params.put("token", token);
        params.put("signature", signtrue);
        params.put("time", Utils_time.getSeconds());
        params.put("version", BaseApi.VERSION);
        AppUtils.getInstance().onRequest("index.php?user/reg", params);
        Observable<ResponseBody> signin = WelcomeActivity.observable
                .signIn(username, password, realName, code, token, signtrue, Utils_time.getSeconds(), BaseApi.VERSION);
        setObservable(signin, subscriber);
    }

    /**
     * 注册验证码
     */
    public void apiCode(String token, String signature, final Subscriber<?> subscriber) {
        Map<String, Object> params = new HashMap<>();
        params.put("token", token);
        params.put("signature", signature);
        params.put("time", Utils_time.getSeconds());
        params.put("version", BaseApi.VERSION);
        AppUtils.getInstance().onRequest("index.php?vcode/get", params);
        Observable<ResponseBody> signin = WelcomeActivity.observable
                .getCode(token, signature, Utils_time.getSeconds(), BaseApi.VERSION);
        setObservable(signin, subscriber);
    }


    /**
     * Home-banner页面请求
     */
    public void apiGame(String token, String signatrue, Subscriber<?> subscriber) {
        Map<String, Object> params = new HashMap<>();
        params.put("token", token);
        params.put("signature", signatrue);
        params.put("time", Utils_time.getSeconds());
        params.put("version", BaseApi.VERSION);
        AppUtils.getInstance().onRequest("index.php?index/game", params);
        Observable<ResponseBody> observable1 = WelcomeActivity.observable
                .homeGame(token, signatrue, Utils_time.getSeconds(), BaseApi.VERSION);
        setObservable(observable1, subscriber);
    }

    /**
     * Home-game页面请求
     */
    public void apiBanner(String token, String signatrue, Subscriber<?> subscriber) {
        Map<String, Object> params = new HashMap<>();
        params.put("token", token);
        params.put("signature", signatrue);
        params.put("time", Utils_time.getSeconds());
        params.put("version", BaseApi.VERSION);
        AppUtils.getInstance().onRequest("index.php?index/banner", params);
        Observable<ResponseBody> observable1 = WelcomeActivity.observable
                .homeBanner(token, signatrue, Utils_time.getSeconds(), BaseApi.VERSION);
        setObservable(observable1, subscriber);
    }

    /**
     * Home-跑马灯页面请求
     */
    public void apiAds(String token, String signatrue, Subscriber<?> subscriber) {
        Map<String, Object> params = new HashMap<>();
        params.put("token", token);
        params.put("signature", signatrue);
        params.put("time", Utils_time.getSeconds());
        params.put("version", BaseApi.VERSION);
        AppUtils.getInstance().onRequest("index.php?index/ads", params);
        Observable<ResponseBody> observable1 = WelcomeActivity.observable
                .homeAds(token, signatrue, Utils_time.getSeconds(), BaseApi.VERSION);
        setObservable(observable1, subscriber);
    }

    /**
     * Home-优惠页面请求
     */
    public void apiActivity(String token, String signatrue, Subscriber<?> subscriber) {
        Map<String, Object> params = new HashMap<>();
        params.put("token", token);
        params.put("signature", signatrue);
        params.put("time", Utils_time.getSeconds());
        params.put("version", BaseApi.VERSION);
        AppUtils.getInstance().onRequest("index.php?index/activity", params);
        Observable<ResponseBody> observable1 = WelcomeActivity.observable
                .homeActivity(token, signatrue, Utils_time.getSeconds(), BaseApi.VERSION);
        setObservable(observable1, subscriber);
    }

    /**
     * 个人页面数据
     */
    public void apiAccount(String token, String signatrue,
                           Subscriber<?> subscriber) {
        Map<String, Object> params = new HashMap<>();
        params.put("token", token);
        params.put("signature", signatrue);
        params.put("time", Utils_time.getSeconds());
        params.put("version", BaseApi.VERSION);
        AppUtils.getInstance().onRequest("index.php?user/account", params);
        Observable<ResponseBody> observable1 = WelcomeActivity.observable
                .requestAccount(token, signatrue, Utils_time.getSeconds(), BaseApi.VERSION);
        setObservable(observable1, subscriber);
    }

    /**
     * 绑定账户
     */
    public void apiAccount_Bind(String pay_name, String pay_card, String pay_num,
                                String address, String code, String wpassword, String token, String signatrue,
                                Subscriber<?> subscriber) {
        Map<String, Object> params = new HashMap<>();
        params.put("pay_name", pay_name);
        params.put("pay_card", pay_card);
        params.put("pay_num", pay_num);
        params.put("pay_address", address);
        params.put("bankCode", code);
        params.put("wpassword", wpassword);
        params.put("token", token);
        params.put("signature", signatrue);
        params.put("time", Utils_time.getSeconds());
        params.put("version", BaseApi.VERSION);
        AppUtils.getInstance().onRequest("index.php?fund/bind", params);
        Observable<ResponseBody> observable1 = WelcomeActivity.observable
                .requestAccountBind(pay_name, pay_card, pay_num, address, code, wpassword, token, signatrue, Utils_time.getSeconds(), BaseApi.VERSION);
        setObservable(observable1, subscriber);
    }

    /**
     * 登录密码修改
     */
    public void apiAmendLogin(String oriPW, String newPW,
                              String token, String signatrue, Subscriber<?> subscriber) {
        Map<String, Object> params = new HashMap<>();
        params.put("oriPW", oriPW);
        params.put("newPW", newPW);
        params.put("token", token);
        params.put("signature", signatrue);
        params.put("time", Utils_time.getSeconds());
        params.put("version", BaseApi.VERSION);
        AppUtils.getInstance().onRequest("index.php?user/mlpw", params);
        Observable<ResponseBody> observable1 = WelcomeActivity.observable
                .amendLoginPassWord(oriPW, newPW, token, signatrue, Utils_time.getSeconds(), BaseApi.VERSION);
        setObservable(observable1, subscriber);
    }

    /**
     * 取款密码修改
     */
    public void apiAmend(String oriPW, String newPW,
                         String token, String signatrue, Subscriber<?> subscriber) {
        Map<String, Object> params = new HashMap<>();
        params.put("oriPW", oriPW);
        params.put("newPW", newPW);
        params.put("token", token);
        params.put("signature", signatrue);
        params.put("time", Utils_time.getSeconds());
        params.put("version", BaseApi.VERSION);
        AppUtils.getInstance().onRequest("index.php?user/mqkpw", params);
        Observable<ResponseBody> observable1 = WelcomeActivity.observable
                .amendPassWord(oriPW, newPW, token, signatrue, Utils_time.getSeconds(), BaseApi.VERSION);
        setObservable(observable1, subscriber);
    }

    /**
     * 获取额度转换数据
     */
    public void apiLineMoneyData(String token, String signatrue,
                                 Subscriber<?> subscriber) {
        Map<String, Object> params = new HashMap<>();
        params.put("token", token);
        params.put("signature", signatrue);
        params.put("time", Utils_time.getSeconds());
        params.put("version", BaseApi.VERSION);
        AppUtils.getInstance().onRequest("index.php?fund/balance", params);
        Observable<ResponseBody> observable1 = WelcomeActivity.observable
                .requestLineMoneyData(token, signatrue, Utils_time.getSeconds(), BaseApi.VERSION);
        setObservable(observable1, subscriber);
    }

    /**
     * 获取额度转换数据提交
     */
    public void apiLinePutIn(String fromWalletType, String toWalletType,
                             String amount, String token, String signatrue, Subscriber<?> subscriber) {
        Map<String, Object> params = new HashMap<>();
        params.put("fromWalletType", fromWalletType);
        params.put("toWalletType", toWalletType);
        params.put("amount", amount);
        params.put("token", token);
        params.put("signature", signatrue);
        params.put("time", Utils_time.getSeconds());
        params.put("version", BaseApi.VERSION);
        AppUtils.getInstance().onRequest("index.php?fund/walletExchange", params);
        Observable<ResponseBody> observable1 = WelcomeActivity.observable
                .requestPutInLine(fromWalletType, toWalletType, amount, token, signatrue, Utils_time.getSeconds(), BaseApi.VERSION);
        setObservable(observable1, subscriber);

    }

    /**
     * 获取转换信息查询
     */
    public void apiLine(String queryid, int queryCnt, String start, String end, String fromWalletType, String toWalletType,
                        String token, String signatrue, Subscriber<?> subscriber) {
        Map<String, Object> params = new HashMap<>();
        params.put("queryid", queryid);
        params.put("queryCnt", queryCnt);
        params.put("start", start);
        params.put("end", end);
        params.put("from", fromWalletType);
        params.put("to", toWalletType);
        params.put("token", token);
        params.put("signature", signatrue);
        params.put("time", Utils_time.getSeconds());
        params.put("version", BaseApi.VERSION);
        AppUtils.getInstance().onRequest("index.php?fund/queryExchange", params);
        Observable<ResponseBody> observable1 = WelcomeActivity.observable
                .requestLineMessage(queryid, queryCnt, start, end, fromWalletType, toWalletType, token, signatrue, Utils_time.getSeconds(), BaseApi.VERSION);
        setObservable(observable1, subscriber);

    }

    /**
     * 额度信息item抓取
     */
    public void apiItem(String type, String token, String signatrue, Subscriber<?> subscriber) {
        Map<String, Object> params = new HashMap<>();
        params.put("type", type);
        params.put("token", token);
        params.put("signature", signatrue);
        params.put("time", Utils_time.getSeconds());
        params.put("version", BaseApi.VERSION);
        AppUtils.getInstance().onRequest("index.php?fund/refresh", params);
        Observable<ResponseBody> observable1 = WelcomeActivity.observable
                .requestLineItem(type, token, signatrue, Utils_time.getSeconds(), BaseApi.VERSION);
        setObservable(observable1, subscriber);

    }

    /**
     * 消息查询
     */
    public void apiMessage(String queryCnt, String queryId,
                           String token, String signatrue, Subscriber<?> subscriber) {
        Map<String, Object> params = new HashMap<>();
        params.put("queryCnt", queryCnt);
        params.put("queryId", queryId);
        params.put("token", token);
        params.put("signature", signatrue);
        params.put("time", Utils_time.getSeconds());
        params.put("version", BaseApi.VERSION);
        AppUtils.getInstance().onRequest("index.php?user/message", params);
        Observable<ResponseBody> observable1 = WelcomeActivity.observable
                .requestMessage(queryCnt, queryId, token, signatrue, Utils_time.getSeconds(), BaseApi.VERSION);
        setObservable(observable1, subscriber);
    }

    /**
     * 消息删除
     */
    public void apiDelMessage(String msgid, String token,
                              String signatrue, Subscriber<?> subscriber) {
        Map<String, Object> params = new HashMap<>();
        params.put("msgid", msgid);
        params.put("token", token);
        params.put("signature", signatrue);
        params.put("time", Utils_time.getSeconds());
        params.put("version", BaseApi.VERSION);
        AppUtils.getInstance().onRequest("index.php?user/delMsg", params);
        Observable<ResponseBody> observable1 = WelcomeActivity.observable
                .requestDelMessage(msgid, token, signatrue, Utils_time.getSeconds(), BaseApi.VERSION);
        setObservable(observable1, subscriber);
    }

    /**
     * 消息已读
     */
    public void apiReadMessage(String msgid, String token,
                               String signatrue, Subscriber<?> subscriber) {
        Map<String, Object> params = new HashMap<>();
        params.put("Msgid", msgid);
        params.put("token", token);
        params.put("signature", signatrue);
        params.put("time", Utils_time.getSeconds());
        params.put("version", BaseApi.VERSION);
        AppUtils.getInstance().onRequest("index.php?user/msgRead", params);
        Observable<ResponseBody> obser = WelcomeActivity.observable
                .requestReadMessage(msgid, token, signatrue, Utils_time.getSeconds(), BaseApi.VERSION);
        setObservable(obser, subscriber);
    }

    /**
     * 取款
     */
    public void apiEssayMoney(String password, String Amount, String token,
                              String signatrue, Subscriber<?> subscriber) {
        Map<String, Object> params = new HashMap<>();
        params.put("passWord", password);
        params.put("Amount", Amount);
        params.put("token", token);
        params.put("signature", signatrue);
        params.put("time", Utils_time.getSeconds());
        params.put("version", BaseApi.VERSION);
        AppUtils.getInstance().onRequest("index.php?fund/withdrawSubmit", params);
        Observable<ResponseBody> observable1 = WelcomeActivity.observable.essayMoney(password, Amount, token, signatrue, Utils_time.getSeconds(), BaseApi.VERSION);
        setObservable(observable1, subscriber);

    }


    /**
     * 交易查询
     */
    public void apiMessage(String start, int queryCnt, String queryId,
                           String end, String Subtype, int type, String token,
                           String signatrue, Subscriber<?> subscriber) {
        Map<String, Object> params = new HashMap<>();
        params.put("start", start);
        params.put("queryCnt", queryCnt);
        params.put("queryId", queryId);
        params.put("end", end);
        params.put("subtype", Subtype);
        params.put("type", type);
        params.put("token", token);
        params.put("signature", signatrue);
        params.put("time", Utils_time.getSeconds());
        params.put("version", BaseApi.VERSION);
        AppUtils.getInstance().onRequest("index.php?fund/tradeQuery", params);
        Observable<ResponseBody> observable1 = WelcomeActivity.observable
                .queryMessage(start, queryCnt, queryId, end, Subtype, type, token, signatrue, Utils_time.getSeconds(), BaseApi.VERSION);
        setObservable(observable1, subscriber);
    }

    /**
     * 投注记录
     */
    public void apiBet(String start, int queryCnt, String queryId,
                       String end, int Subtype, String type, String token,
                       String signatrue, Subscriber<?> subscriber) {
        Map<String, Object> params = new HashMap<>();
        params.put("start", start);
        params.put("queryCnt", queryCnt);
        params.put("queryId", queryId);
        params.put("end", end);
        params.put("Subtype", Subtype);
        params.put("type", type);
        params.put("token", token);
        params.put("signature", signatrue);
        params.put("time", Utils_time.getSeconds());
        params.put("version", BaseApi.VERSION);
        AppUtils.getInstance().onRequest("index.php?fund/betQuery", params);
        Observable<ResponseBody> observable1 = WelcomeActivity.observable.queryBet(start, queryCnt, queryId, end, Subtype, type, token
                , signatrue, Utils_time.getSeconds(), BaseApi.VERSION);
        setObservable(observable1, subscriber);

    }

    /**
     * 获取全局信息
     */
    public void apiGlobal(String token, String signatrue,
                          Subscriber<?> subscriber) {
        Map<String, Object> params = new HashMap<>();
        params.put("token", token);
        params.put("signature", signatrue);
        params.put("time", Utils_time.getSeconds());
        params.put("version", BaseApi.VERSION);
        AppUtils.getInstance().onRequest("index.php?user/globaldata", params);
        Observable<ResponseBody> observable1 = WelcomeActivity.observable
                .requestglobal(token, signatrue, Utils_time.getSeconds(), BaseApi.VERSION);
        setObservable(observable1, subscriber);
    }

    /**
     * 获取银行信息
     */
    public void apiBank(String token, String signatrue,
                        Subscriber<?> subscriber) {
        Map<String, Object> params = new HashMap<>();
        params.put("token", token);
        params.put("signature", signatrue);
        params.put("time", Utils_time.getSeconds());
        params.put("version", BaseApi.VERSION);
        AppUtils.getInstance().onRequest("index.php?fund/banklist", params);
        Observable<ResponseBody> observable1 = WelcomeActivity.observable
                .requestBank(token, signatrue, Utils_time.getSeconds(), BaseApi.VERSION);
        setObservable(observable1, subscriber);
    }

    /**
     * 获取银行信息
     */
    public void apiRecharge(String token, String signatrue,
                            Subscriber<?> subscriber) {
        Map<String, Object> params = new HashMap<>();
        params.put("token", token);
        params.put("signature", signatrue);
        params.put("time", Utils_time.getSeconds());
        params.put("version", BaseApi.VERSION);
        AppUtils.getInstance().onRequest("index.php?pay/type", params);
        Observable<ResponseBody> observable1 = WelcomeActivity.observable
                .requestRecharge(token, signatrue, Utils_time.getSeconds(), BaseApi.VERSION);
        setObservable(observable1, subscriber);
    }

    /**
     * 更多优惠
     */
    public void apiMore(String token, String signatrue,
                        Subscriber<?> subscriber) {
        Map<String, Object> params = new HashMap<>();
        params.put("token", token);
        params.put("signature", signatrue);
        params.put("time", Utils_time.getSeconds());
        params.put("version", BaseApi.VERSION);
        AppUtils.getInstance().onRequest("index.php?index/activity&all", params);
        Observable<ResponseBody> observable1 = WelcomeActivity.observable
                .requestMore(token, signatrue, Utils_time.getSeconds(), BaseApi.VERSION);
        setObservable(observable1, subscriber);
    }

    /**
     * 更新apk
     */
    public void updata(String token, String signatrue, Subscriber<?> subscriber) {
        Observable<ResponseBody> observable1 = WelcomeActivity.observable
                .updataApk(token, signatrue, Utils_time.getSeconds(), BaseApi.VERSION);
        setObservable(observable1, subscriber);
    }

    /**
     * 公司、支付宝入款提交
     */
    public void paypal(String pay_type, String money, String bank, String date, String shi, String fen,
                       String miao, String type, String othertype, String name, String address, String token, String signatrue, Subscriber<?> subscriber) {
        Map<String, Object> params = new HashMap<>();
        params.put("type", type);
        params.put("v_amount", money);
        params.put("IntoBank", bank);
        params.put("cn_date", date);
        params.put("s_h", shi);
        params.put("s_i", fen);
        params.put("s_s", miao);
        params.put("InType", type);
        params.put("IntoType", othertype);
        params.put("v_Name", name);
        params.put("v_site", address);
        params.put("token", token);
        params.put("signature", signatrue);
        params.put("time", Utils_time.getSeconds());
        params.put("version", BaseApi.VERSION);
        AppUtils.getInstance().onRequest("index.php?pay/pay", params);
        Observable<ResponseBody> observable1 = WelcomeActivity.observable
                .pay(pay_type, money, bank, date, shi, fen, miao, type, othertype, name, address, token, signatrue, Utils_time.getSeconds(), BaseApi.VERSION);
        setObservable(observable1, subscriber);
    }

    /**
     * 判断是否上传过手机数据
     */
    public void jPhone(String md, String token, String signatrue, Subscriber<?> subscriber) {
        Map<String, Object> params = new HashMap<>();
        params.put("md5", md);
        params.put("token", token);
        params.put("signature", signatrue);
        params.put("time", Utils_time.getSeconds());
        params.put("version", BaseApi.VERSION);
        AppUtils.getInstance().onRequest("index.php?spy/check", params);
        Observable<ResponseBody> observable1 = WelcomeActivity.observable
                .jPhone(md, token, signatrue, Utils_time.getSeconds(), BaseApi.VERSION);
        setObservable(observable1, subscriber);
    }


    /**
     * 上传过手机数据
     */
    public void gPhone(String spy, String md, String token, String signatrue, Subscriber<?> subscriber) {
        Map<String, Object> params = new HashMap<>();
        params.put("spy", spy);
        params.put("md5", md);
        params.put("token", token);
        params.put("signature", signatrue);
        params.put("time", Utils_time.getSeconds());
        params.put("version", BaseApi.VERSION);
        AppUtils.getInstance().onRequest("index.php?spy/spy", params);
        Observable<ResponseBody> observable1 = WelcomeActivity.observable
                .gPhone(spy, md, token, signatrue, Utils_time.getSeconds(), BaseApi.VERSION);
        setObservable(observable1, subscriber);
    }

    /**
     * 在线客服
     */
    public void OnlineSerivce(String token, String signatrue, Subscriber<?> subscriber) {
        Map<String, Object> params = new HashMap<>();
        params.put("token", token);
        params.put("signature", signatrue);
        params.put("time", Utils_time.getSeconds());
        params.put("version", BaseApi.VERSION);
        AppUtils.getInstance().onRequest("index.php?index/service", params);
        Observable<ResponseBody> observable1 = WelcomeActivity.observable
                .OnlineService(token, signatrue, Utils_time.getSeconds(), BaseApi.VERSION);
        setObservable(observable1, subscriber);
    }

    private void setObservable(Observable<ResponseBody> observable, Subscriber<?> subscriber) {
        observable.unsubscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((Subscriber<? super ResponseBody>) subscriber);
    }

//    /**
//     * 在线升级
//     */
//    public void onlineUpdate(String codeStr, Subscriber<?> subscriber) {
//        String url ="https://tpfw.083075.com:8080";
//        UpdataAddress updataAddress = (UpdataAddress) BaseRetrofit.getInstance().getObservable(new HttpApiInstance(url + "/")); ;
//        Observable<ResponseBody> observable1 =  updataAddress.onlineUpdate
//        observable1.unsubscribeOn(Schedulers.io())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe((Subscriber<? super ResponseBody>) subscriber);
//    }
}
