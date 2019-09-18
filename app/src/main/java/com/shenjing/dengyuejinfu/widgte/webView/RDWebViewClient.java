package com.shenjing.dengyuejinfu.widgte.webView;

import android.graphics.Bitmap;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Email: hbh@erongdu.com
 * Created by hebihe on 6/1/16.
 */
public class RDWebViewClient extends WebViewClient {
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
    }

    // 开始加载网页时要做的工作
    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
    }

    // 加载完成时要做的工作
    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
    }

    // 加载错误时要做的工作
    @Override
    public void onReceivedError(WebView view, int errorCode,
                                String description, String failingUrl) {
        super.onReceivedError(view, errorCode, description, failingUrl);
    }
}
