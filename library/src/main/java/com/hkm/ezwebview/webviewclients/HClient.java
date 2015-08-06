package com.hkm.ezwebview.webviewclients;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

/**
 * Created by hesk on 6/8/15.
 */
public class HClient extends HBClient {
    public interface Callback {
        void retrieveCookie(String cookie_string);

        boolean overridedefaultlogic(String url, Activity activity);
    }

    public HClient(Activity app, WebView w) {
        super(app, w);
    }

    public static <T> HClient with(T context, WebView w) throws Exception {
        if (context instanceof AppCompatActivity) {
            Activity g = (Activity) context;
            return new HClient(g, w);
        }
        if (context instanceof Fragment) {
            Fragment g = (Fragment) context;
            return new HClient(g.getActivity(), w);
        }
        if (context instanceof android.support.v4.app.Fragment) {
            android.support.v4.app.Fragment g = (android.support.v4.app.Fragment) context;
            return new HClient(g.getActivity(), w);
        }
        throw new Exception("please enter an activity or fragment");
    }

    private Callback mc;

    public HClient setController(Callback mc) {
        this.mc = mc;
        return this;
    }

    @Override
    protected void retrieveCookie(String cookie_string) {
        if (this.mc != null) mc.retrieveCookie(cookie_string);
    }

    @Override
    protected boolean overridedefaultHBlogic(String url, Activity context) {
        if (this.mc != null)
            return mc.overridedefaultlogic(url, context);
        else
            return super.overridedefaultHBlogic(url, context);
    }
}
