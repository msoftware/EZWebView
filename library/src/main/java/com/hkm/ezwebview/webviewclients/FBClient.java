package com.hkm.ezwebview.webviewclients;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.hkm.ezwebview.webviewleakfix.PreventLeakClient;


/**
 * Created by hesk on 23/7/15.
 */
public class FBClient extends PreventLeakClient {

    private String loginCookie;

    private WebView mWebView;
    public static String TAG = "hypebeastPathsWatcher";
    private String authCallback = "...";

    public FBClient(Activity context, WebView webview) {
        super(context);
        mWebView = webview;
    }

    @Override
    public void onLoadResource(WebView view, String url) {
        // CookieManager cookieManager = CookieManager.getInstance();
        //loginCookie = cookieManager.getCookie(url);
    }

}
