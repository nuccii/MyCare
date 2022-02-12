package com.edwinabrenda.mycare.Web;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.edwinabrenda.mycare.Medical.MedicinesController;
import com.edwinabrenda.mycare.R;

public class WebsiteView extends AppCompatActivity {

    String message = "Loading...";
    private WebView webview = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_website_view);
        webview = (WebView) findViewById(R.id.webView);
        setUpWebView();
    }

    void setUpWebView(){
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webview.setWebViewClient(new MyWebViewClient());
        webSettings.setGeolocationEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            webSettings.setMediaPlaybackRequiresUserGesture(true);
        }

        webSettings.setLoadsImagesAutomatically(true);

        webSettings.setUseWideViewPort(true);
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webSettings.setAppCacheEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setUseWideViewPort(true);
        webSettings.setSavePassword(true);
        webSettings.setSaveFormData(true);
        webSettings.setEnableSmoothTransition(true);
        webview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        try {
            webview.loadUrl(getResources().getString(MedicinesController.getWebUrl()));
        }catch (Exception e){
            webview.setWebChromeClient(new WebChromeClient(){

                @Override
                public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                    callback.invoke(origin, true, false);
                }
            });
            message = "Scanning Location...";
            webview.loadUrl("file:///android_asset/myfile.html");
        }

    }

    @Override
    public void onBackPressed() {
        if(webview.canGoBack())
            webview.goBack();
        super.onBackPressed();
    }

    class MyWebViewClient extends WebViewClient{
        ProgressDialog progressDialog;

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            progressDialog = new ProgressDialog(com.edwinabrenda.mycare.Web.WebsiteView.this);
            progressDialog.setTitle("Please wait");
            progressDialog.setMessage(message);
            progressDialog.show();

            super.onPageStarted(view, url, favicon);
        }
        @Override
        public void onPageFinished(WebView view, String url) {
            progressDialog.dismiss();
            super.onPageFinished(view, url);
        }
    }
}