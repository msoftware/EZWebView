package com.hkm.ezwebview.webviewleakfix;

import android.app.Activity;
import android.webkit.WebViewClient;

import java.lang.ref.WeakReference;

/**
 * Created by hesk on 21/5/15.
 */
public class PreventLeakClient<A extends Activity> extends WebViewClient {
    protected WeakReference<A> activityRef;
    protected A activity;

    public PreventLeakClient(A activity) {
        activityRef = new WeakReference<A>(activity);
        this.activity = activityRef.get();
    }

   /* @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        try {
            if (activity != null)
                activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        } catch (RuntimeException ignored) {
            // ignore any url parsing exceptions
        }
        return true;
    }*/


}
