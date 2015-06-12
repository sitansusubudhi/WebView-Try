package com.sitansu.design.imageviewflipkart;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class ShowWebView extends Activity {

    //private Button button;
    private WebView webView;
    private String mUrl;
    private final String LOG_TAG = ShowWebView.class.getSimpleName();

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_web_view);


        //Get webview
        webView = (WebView) findViewById(R.id.webView1);

        startWebView("http://www.ebay.in/");

    }

    private void startWebView(String url) {

        //Create new webview Client to show progress dialog
        //When opening a url or click on link

        webView.setWebViewClient(new WebViewClient() {
            ProgressDialog progressDialog;

            //If you will not use this method url links are open in new brower not in webview
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //Most Important
                String newUA = "Mozilla/4.0 (compatible; MSIE 5.01; Windows NT 5.0)";
                webView.getSettings().setUserAgentString(newUA);
                webView.getSettings().setBuiltInZoomControls(true);
                webView.getSettings().setSupportZoom(true);
                webView.getSettings().setUseWideViewPort(true);
                view.loadUrl(url);

                return true;
            }

            //Show loader on url load
            public void onLoadResource(WebView view, String url) {
                if (progressDialog == null) {
                    // in standard case YourActivity.this
                    progressDialog = new ProgressDialog(ShowWebView.this);
                    progressDialog.setMessage("Loading...");
                    progressDialog.show();
                }
            }

            public void onPageFinished(WebView view, String url) {
                try {
                    if (progressDialog.isShowing()) {
                        mUrl = url;
                        //Log.v(LOG_TAG, url);


                        Toast.makeText(getApplicationContext(), mUrl, Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        progressDialog = null;

                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }

        });


        //Load url in webview
        webView.loadUrl(url);


    }

    // Open previous opened link from history on webview when back button pressed

    @Override
    // Detect when the back button is pressed
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            // Let the system handle the back button
            super.onBackPressed();
        }
    }


    private class VideoWebViewClient extends WebViewClient {
        ProgressDialog progressDialog;

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            String newUA = "Mozilla/4.0 (compatible; MSIE 5.01; Windows NT 5.0)";
            webView.getSettings().setUserAgentString(newUA);
            webView.getSettings().setBuiltInZoomControls(true);
            webView.getSettings().setSupportZoom(true);
            webView.getSettings().setUseWideViewPort(true);
            view.loadUrl(url);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    WebView.HitTestResult hr = ((WebView)view).getHitTestResult();

                    Log.i(LOG_TAG,"getExtra = " + hr.getExtra() + "\t\t Type=" + hr.getType());
                }
            });

            return false;
        }

        @Override
        public void onLoadResource(WebView view, String url) {
            if (progressDialog == null) {
                // in standard case YourActivity.this
                progressDialog = new ProgressDialog(ShowWebView.this);
                progressDialog.setMessage("Loading...");
                progressDialog.show();
            }
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            try {
                if (progressDialog.isShowing()) {
                    mUrl = url;
                    //Log.v(LOG_TAG, url);


                    //Toast.makeText(getApplicationContext(), mUrl, Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    progressDialog = null;

                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }

        }
    }
}