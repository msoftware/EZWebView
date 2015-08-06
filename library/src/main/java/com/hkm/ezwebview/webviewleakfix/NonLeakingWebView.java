package com.hkm.ezwebview.webviewleakfix;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.webkit.WebView;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * see http://stackoverflow.com/questions/3130654/memory-leak-in-webview and http://code.google.com/p/android/issues/detail?id=9375
 * Note that the bug does NOT appear to be fixed in android 2.2 as romain claims
 * <p/>
 * Also, you must call {@link #destroy()} from your activity's onDestroy method.
 * <p/>
 * Author Heskeyo Kam
 */

public class NonLeakingWebView<T extends PreventLeakClient> extends WebView {
    private static Field sConfigCallback;
    private OnScrollChangedCallback mOnScrollChangedCallback;

    static {
        try {
            sConfigCallback = Class.forName("android.webkit.BrowserFrame").getDeclaredField("sConfigCallback");
            sConfigCallback.setAccessible(true);
        } catch (Exception e) {
            // ignored
        }
    }

    public void setWebViewClient(Class<T> client, AppCompatActivity context) {
        try {
            Class[] cArg = new Class[1]; //Our constructor has 3 arguments
            cArg[0] = AppCompatActivity.class; //First argument is of *object* type Long
            T clientInstance = (T) client.getDeclaredConstructor(cArg).newInstance((AppCompatActivity) context);
            super.setWebViewClient(clientInstance);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public NonLeakingWebView(Context context, Class<T> client) {
        super(context.getApplicationContext());
        //  super.setWebViewClient(new HBClient(this, b));
    }

    public NonLeakingWebView(Context context, AttributeSet attrs) {
        super(context.getApplicationContext(), attrs);
        // super.setWebViewClient(new HBClient(this, b));
    }

    public NonLeakingWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context.getApplicationContext(), attrs, defStyle);
        //  super.setWebViewClient(new HBClient(this, b));
    }

    @Override
    public void destroy() {
        super.destroy();
        try {
            if (sConfigCallback != null)
                sConfigCallback.set(null, null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    protected void onScrollChanged(final int l, final int t, final int oldl, final int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mOnScrollChangedCallback != null) mOnScrollChangedCallback.onScroll(l, t);
    }

    public OnScrollChangedCallback getOnScrollChangedCallback() {
        return mOnScrollChangedCallback;
    }

    public void setOnScrollChangedCallback(final OnScrollChangedCallback onScrollChangedCallback) {
        mOnScrollChangedCallback = onScrollChangedCallback;
    }

    /**
     * Impliment in the activity/fragment/view that you want to listen to the webview
     */
    public static interface OnScrollChangedCallback {
        void onScroll(int l, int t);
    }

}