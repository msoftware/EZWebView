Advanced Webview
=====

Feature list
- [x] no more memory leak
- [x] easy web page margin fixed
- [x] easy web video implementation
- [x] easy web content display implementation
- [x] took care of life cycle issues
- [x] fantasy animation reveal fade in


For initiating a webview, simply thing to do this to do it once for all.

Remote Repo
```gradle
    repositories {
        maven { url "http://dl.bintray.com/jjhesk/maven" }
    }
```
Dependency
```gradle
    compile 'com.android.support:appcompat-v7:22.2.0'
```

This is the fast simple implementation without any callbacks:

```java

  @Override
    public void onViewCreated(View v, Bundle b) {
        initBinding(v);
        final String contentc = fromFileRaw(getActivity(), R.raw.sample_html);
        try {
            Fx9C.setup_content_block_wb(this, content_article_frame, block, contentc);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // if (bloadlistenr != null)
        //   bloadlistenr.onLoad();
    }

```

This is the advanced implementation:

```java


  @Override
    public void onViewCreated(View v, Bundle b) {
        initBinding(v);
        final String contentc = fromFileRaw(getActivity(), R.raw.sample_html);
        try {
            Fx9C.setup_content_block_wb(this, content_article_frame, block, contentc, new HClient.Callback() {
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
            }, new Runnable() {
                @Override
                public void run() {
                    ViewCompat.animate(mprogressbar).alpha(0f).withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            mprogressbar.setVisibility(View.GONE);
                        }
                    });
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        // if (bloadlistenr != null)
        //   bloadlistenr.onLoad();
    }



```