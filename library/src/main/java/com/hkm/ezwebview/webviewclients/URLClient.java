package com.hkm.ezwebview.webviewclients;

import android.app.Activity;
import android.app.Fragment;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;


/**
 * Created by hesk on 6/8/15.
 */
public class URLClient extends HBCart {
    public interface cb {
        void triggerNative(Uri trigger_url);
    }

    public static <T> URLClient with(
            T context,
            WebView w) throws Exception {
        if (context instanceof AppCompatActivity) {
            Activity g = (Activity) context;
            return new URLClient(g, w);
        }
        if (context instanceof Fragment) {
            AppCompatActivity g = (AppCompatActivity) context;
            return new URLClient(g, w);
        }
        if (context instanceof android.support.v4.app.Fragment) {
            android.support.v4.app.Fragment g = (android.support.v4.app.Fragment) context;
            return new URLClient(g.getActivity(), w);
        }
        throw new Exception("please enter an activity or fragment");
    }

    private cb mxb;

    public URLClient(Activity context, WebView fmWebView) {
        super(context, fmWebView);
    }


    @Override
    protected void triggerNative(Uri trigger_url) {
        if (mxb != null) mxb.triggerNative(trigger_url);
    }
}
