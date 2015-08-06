package com.hkm.ezwebview.webviewclients;

import android.app.Activity;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.hkm.ezwebview.webviewleakfix.PreventLeakClientChrome;
import com.lsjwzh.widget.materialloadingprogressbar.CircleProgressBar;

/**
 * Created by hesk on 3/13/15.
 */
public class ChromeLoader extends PreventLeakClientChrome {
    private CircleProgressBar cb;
    private boolean control_webview_show_hide_onload = false;
    private Activity context;
    private ProgressBar cpb;
    private boolean withLoadingText = false;
    private String loadingText;
    private CharSequence barTitle;

    public ChromeLoader(CircleProgressBar circlebar) {
        cb = circlebar;
        cb.setCircleBackgroundEnabled(true);
        cb.setVisibility(View.VISIBLE);
        cb.setShowProgressText(true);
    }

    public ChromeLoader(Activity c) {
        context = c;
        context.setProgressBarVisibility(true);
        barTitle = c.getTitle();
    }

    public ChromeLoader(Activity c, ProgressBar bar) {
        cpb = bar;
        context = c;
        barTitle = c.getTitle();
    }

    public ChromeLoader setShowHideWebViewOnload(boolean b) {
        control_webview_show_hide_onload = b;
        return this;
    }

    public ChromeLoader setLoadingText(String b) {
        loadingText = b;
        withLoadingText = true;
        return this;
    }

    @Override
    public void onProgressChanged(WebView view, int progress) {
        if (cb != null) {
            if (progress < 100) {
                cb.setProgress(progress);
                if (cb.getVisibility() == View.GONE) {
                    cb.setVisibility(View.VISIBLE);
                }
                if (control_webview_show_hide_onload && view.getVisibility() == View.VISIBLE)
                    view.setVisibility(View.GONE);
            } else {
                cb.setVisibility(View.GONE);
                if (control_webview_show_hide_onload && view.getVisibility() == View.GONE)
                    view.setVisibility(View.VISIBLE);
            }

        } else if (context != null) {
            //context.setProgressBarIndeterminateVisibility(true);
            cpb.setVisibility(View.VISIBLE);
            if (withLoadingText) {
                context.setTitle(loadingText);
            }
            cpb.setProgress(progress);
            if (control_webview_show_hide_onload && view.getVisibility() == View.VISIBLE)
                view.setVisibility(View.GONE);
            if (progress == 100) {
                if (withLoadingText) {
                    if (barTitle == null)
                        context.setTitle("no name");
                    else context.setTitle(barTitle);
                }
                cpb.setVisibility(View.GONE);
                if (control_webview_show_hide_onload && view.getVisibility() == View.GONE)
                    view.setVisibility(View.VISIBLE);
            }
        }

    }

}
