H1. Advanced Webview
=====


For initiating a webview, simply thing to do this to do it once for all.

```java

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

```