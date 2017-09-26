package com.wishland.www.xinwanhaotest.view.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.DownloadListener;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.wishland.www.xinwanhaotest.R;
import com.wishland.www.xinwanhaotest.api.BaseApi;
import com.wishland.www.xinwanhaotest.utils.ActivityManager;
import com.wishland.www.xinwanhaotest.utils.ToastUtil;


import java.util.HashMap;
import java.util.Map;

import static android.os.Build.VERSION;
import static android.os.Build.VERSION_CODES;

/**
 * html5全屏网页
 */


public class DetailsHtmlPageActivity extends Activity {


//    @BindView(R.id.top_fanhui)
//    ImageView topFanhui;
//    @BindView(R.id.top_welcome)
//    TextView topWelcome;
    private String stringExtra;
    private WebView webview;
    private ProgressBar pb;
    private Map<Integer, Integer> index = new HashMap<>();

    @RequiresApi(api = VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
        getWindow().setContentView(R.layout.detailspagel);
        ActivityManager.getActivityManager().addActivity(this);
//        ButterKnife.bind(this);
//        topWelcome.setText("");
        webview = (WebView) findViewById(R.id.webview);
        pb = (ProgressBar) findViewById(R.id.pb);
        init(webview);
        if (intent.getStringExtra(BaseApi.HTML5DATA).isEmpty()) {
            ToastUtil.showShort(this, "暂未开放");
        } else {
            stringExtra = intent.getStringExtra(BaseApi.HTML5DATA);
            webview.loadUrl(stringExtra);
        }

    }

    @SuppressLint("SetJavaScriptEnabled")
    private void init(final WebView webView) {
        webview.requestFocusFromTouch();
        WebSettings settings = webView.getSettings();
        webview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setJavaScriptEnabled(true);
        settings.setBlockNetworkImage(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setDisplayZoomControls(true);
        settings.setDomStorageEnabled(true);
        settings.setDisplayZoomControls(true);
        settings.setDatabaseEnabled(true);
        settings.setAllowFileAccess(true);
        //WebView.setWebContentsDebuggingEnabled(true);
        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                view.getSettings().setBlockNetworkImage(false);
                int currentIndex = view.copyBackForwardList().getCurrentIndex();
                boolean b = index.containsKey(currentIndex);
                if (b) {
                    int i = index.get(currentIndex) + 1;
                    index.put(currentIndex, i);
                } else {
                    index.put(currentIndex, 1);
                }
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int progress) {
                pb.setVisibility(View.VISIBLE);
                pb.setProgress(progress);
                if (progress == 100)
                    pb.setVisibility(View.GONE);
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message,
                                     JsResult result) {
                //加这段可以证webview中的alert弹出来
                return super.onJsAlert(view, url, message, result);
            }

        });
        CookieManager cookieManager = CookieManager.getInstance();
        if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
            CookieManager.setAcceptFileSchemeCookies(true);
            cookieManager.setAcceptThirdPartyCookies(webView, true);
        }
    }


    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            hideSystemNavigationBar();
        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            // 加入竖屏要处理的代码
            showSystemNavigationBar();
        }
    }

    private void showSystemNavigationBar() {
        View decorView = getWindow().getDecorView();
        int uiOptions = View.VISIBLE;
        decorView.setSystemUiVisibility(uiOptions);
    }

    private void hideSystemNavigationBar() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
    }

    @Override
    public void onBackPressed() {
        try {
            if (webview.canGoBack()) {
                int currentIndex = webview.copyBackForwardList().getCurrentIndex();
                if (currentIndex == 1 && index.get(0) > 1) {
                    finish();
                } else if (index.get(currentIndex) > 1) {
                    while (currentIndex >= 0 && index.get(currentIndex) > 0) {
                        currentIndex--;
                    }
                    webview.goBackOrForward(currentIndex - 1);
                } else {
                    webview.goBack();
                }
            } else {
                finish();
            }
        } catch (Exception e) {
            finish();
        }
    }

    public static void clearCookies(Context context) {
        // Edge case: an illegal state exception is thrown if an instance of
        // CookieSyncManager has not be created.  CookieSyncManager is normally
        // created by a WebKit view, but this might happen if you start the
        // app, restore saved state, and click logout before running a UI
        // dialog in a WebView -- in which case the app crashes
        @SuppressWarnings("unused")
        CookieSyncManager cookieSyncMngr =
                CookieSyncManager.createInstance(context);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webview != null) {
            webview.removeAllViews();
            webview.destroy();
        }
        clearCookies(this);
        System.exit(0);
        ActivityManager.getActivityManager().deleteActivity(this);
    }

//    @OnClick({R.id.top_fanhui})
//    public void onViewClicked(View view) {
//        LogUtil.e("test,111111111111111111111111");
//        switch (view.getId()) {
//            case R.id.top_fanhui:
//                LogUtil.e("test"+view.getId());
//                this.finish();
//                break;
//            /*case R.id.button_pc:  //进入浏览器PC版
//                instance.toBrowser(this);
//                break;*/
//        }
//    }
}
