package com.hkm.ezwebviewsample;

import android.os.Bundle;
import android.support.annotation.StringRes;
import android.view.View;

import com.hkm.ezwebview.app.WebviewCommentBox;

/**
 * Created by hesk on 6/8/15.
 */
public class commentboxfragment extends WebviewCommentBox {

    //private String url_id;
    private int fragment_comment_box_type;

    public static commentboxfragment B(final Bundle b) {
        final commentboxfragment t = new commentboxfragment();
        t.setArguments(b);
        return t;
    }

    public static Bundle fbIntent(final @StringRes int title, final String id_comment) {
        final Bundle n = new Bundle();
        n.putInt(FRAGMENTTITLE_RESID, title);
        n.putString(COMMENT_BOX_ID, id_comment);
        n.putInt(REQUEST_TYPE, FB_COMMENT);
        return n;
    }

    @Override
    protected int commentbox_layout_id() {
        return R.layout.webviewsimple;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        fragment_comment_box_type = getArguments().getInt(WebviewCommentBox.REQUEST_TYPE);
        //  url_id = getArguments().getString(COMMENT_BOX_ID);
        initBinding(view);
        setup_commentbox(getArguments().getString(COMMENT_BOX_ID));
    }

    public void complete() {
        completeloading();
    }
}
