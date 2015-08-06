package com.hkm.ezwebview.webviewclients;


/**
 * Created by hesk on 6/2/2014.
 */

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hkm.ezwebview.Util.In32;
import com.hkm.ezwebview.webviewleakfix.PreventLeakClient;

import java.util.ArrayList;
import java.util.List;


public abstract class HBClient extends PreventLeakClient<Activity> {

    private String loginCookie;

    private WebView mWebView;
    public static String TAG = "hbclient_wat";

    public HBClient(Activity app, WebView w) {
        super(app);
        mWebView = w;
    }

    protected abstract void retrieveCookie(final String cookie_string);

    protected boolean overridedefaultHBlogic(final String url, final Activity context) {
        return false;
    }


    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        return overridedefaultHBlogic(url, activity) ? true : In32.interceptURL_HB(url, activity);
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        CookieManager cm = CookieManager.getInstance();
        String cookieString = cm.getCookie(url);
        // Log.d(TAG, "cookie got:" + cookieString);
        if (cookieString != null) {
            retrieveCookie(cookieString);
        }
        super.onPageStarted(view, url, favicon);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setCookie(url, loginCookie);
    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        Toast.makeText(view.getContext(), "Error in here", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLoadResource(WebView view, String url) {
        CookieManager cookieManager = CookieManager.getInstance();
        loginCookie = cookieManager.getCookie(url);
    }


    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        handler.proceed();
    }

    @Override
    public void onReceivedHttpAuthRequest(WebView view, final HttpAuthHandler handler, final String host, final String realm) {
        String userName = null;
        String userPass = null;

        if (handler.useHttpAuthUsernamePassword() && view != null) {
            String[] haup = view.getHttpAuthUsernamePassword(host, realm);
            if (haup != null && haup.length == 2) {
                userName = haup[0];
                userPass = haup[1];
            }
        }

        if (userName != null && userPass != null) {
            handler.proceed(userName, userPass);
        } else {
            showHttpAuthDialog(handler, host, realm, null, null, null);
        }
    }

    private void showHttpAuthDialog(final HttpAuthHandler handler, final String host, final String realm, final String title, final String name, final String password) {
        LinearLayout llayout = new LinearLayout(activity);
        final TextView textview1 = new TextView(activity);
        final EditText edittext1 = new EditText(activity);
        final TextView textview2 = new TextView(activity);
        final EditText edittext2 = new EditText(activity);
        llayout.setOrientation(LinearLayout.VERTICAL);
        textview1.setText("username:");
        textview2.setText("password:");
        llayout.addView(textview1);
        llayout.addView(edittext1);
        llayout.addView(textview2);
        llayout.addView(edittext2);

        final Builder mHttpAuthDialog = new Builder(activity);
        mHttpAuthDialog.setTitle("Basic Authentication")
                .setView(llayout)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        EditText etUserName = edittext1;
                        String userName = etUserName.getText().toString();
                        EditText etUserPass = edittext2;
                        String userPass = etUserPass.getText().toString();
                        mWebView.setHttpAuthUsernamePassword(host, realm, name, password);
                        handler.proceed(userName, userPass);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        handler.cancel();
                    }
                })
                .create().show();
    }


    public void newFeed(Context mctx, String url) {
        Bundle b = new Bundle();

    }
}
