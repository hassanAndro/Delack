package com.delack.Authentication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.delack.FilesListingView;
import com.delack.R;
import com.delack.utils.Constants;

public class AuthenticationView extends AppCompatActivity {

    WebView webView;
    Context context = this;
    String code_;

    boolean loadingFinished = true;
    boolean redirect = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication_view);

        webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.canGoBack();

        final ProgressDialog progressDialog = new ProgressDialog(context,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                if (!loadingFinished) {
                    redirect = true;
                }

                loadingFinished = false;
                view.loadUrl(url);

                return true;
            }


            @Override
            public void onPageStarted(WebView view, String url, Bitmap facIcon) {
                loadingFinished = false;
                //SHOW LOADING IF IT ISNT ALREADY VISIBLE
            }

            @Override
            public void onPageFinished(WebView view, String url) {

                if (!redirect) {
                    loadingFinished = true;
                }

                if (loadingFinished && !redirect) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                } else {

                    if (url.contains("https://onebyte.slack.com/?code=")) {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        webView.stopLoading();

                        code_ = url.substring(url.indexOf("=") + 1, url.indexOf("&"));
                        Intent i = new Intent(AuthenticationView.this, FilesListingView.class);
                        i.putExtra("code", code_);
                        startActivity(i);
                        finish();


                    }
                    redirect = false;
                }

            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Constants.toast(context, "Error:" + description);

            }
        });


        webView.loadUrl(Constants.singInUrl);

    }


}
