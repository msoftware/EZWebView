package com.hkm.ezwebviewsample;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.hkm.ezwebview.Util.Fx9C;
import com.hkm.ezwebview.webviewclients.HClient;
import com.hkm.ezwebview.webviewleakfix.NonLeakingWebView;
import com.lsjwzh.widget.materialloadingprogressbar.CircleProgressBar;

/**
 * Created by hesk on 6/8/15.
 */
public class basicWVIFragment extends Fragment {

    private TextView line1, line2, line3, block_tv;
    private ProgressBar mprogressbar;
    private CircleProgressBar betterCircleBar;
    private ImageView single_static_feature_image_holder;
    private NonLeakingWebView mVideo, block;
    private RelativeLayout sliderframe, video_frameview, content_article_frame;
    private ScrollView sv;

    public basicWVIFragment() {
    }

    @SuppressLint("ResourceAsColor")
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    protected void initBinding(View v) {
        line1 = (TextView) v.findViewById(R.id.article_title);
        block = (NonLeakingWebView) v.findViewById(R.id.content_block);
        block_tv = (TextView) v.findViewById(R.id.content_block_text);

        //  pagerIndicator = (PagerIndicator) findViewById(R.id.custom_indicator);
        mprogressbar = (ProgressBar) v.findViewById(R.id.progressc);
        mVideo = (NonLeakingWebView) v.findViewById(R.id.videoplayer);

        video_frameview = (RelativeLayout) v.findViewById(R.id.framevideoplayer);

        content_article_frame = (RelativeLayout) v.findViewById(R.id.content_article_frame);
        betterCircleBar = (CircleProgressBar) v.findViewById(R.id.progressloadingbarpx);
        sv = (ScrollView) v.findViewById(R.id.scroller_container);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.webviewarticle, container, false);
    }

    @Override
    public void onViewCreated(View v, Bundle b) {
        initBinding(v);
        final String contentc = getString(R.string.demo_html_content);
        try {
            Fx9C.setup_content_block_wb(this, content_article_frame, block, contentc, new HClient.control() {
                @Override
                public void startNewActivity(String packagename, String url, String brandname, Context context) {

                }

                @Override
                public void startFeedList(String url, Context context) {

                }

                @Override
                public void openUri(String url, Context context) {

                }

                @Override
                public void retrieveCookie(String cookie_string) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        // if (bloadlistenr != null)
        //   bloadlistenr.onLoad();
    }
}
