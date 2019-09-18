package com.shenjing.dengyuejinfu.ui.activity;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.shenjing.dengyuejinfu.R;
import com.shenjing.dengyuejinfu.base.BaseActivity;
import com.shenjing.dengyuejinfu.common.ARouterUrl;
import com.shenjing.dengyuejinfu.common.BaseParams;
import com.shenjing.dengyuejinfu.widgte.OnOnceClickListener;
import com.shenjing.dengyuejinfu.widgte.TitleBar;
import com.shenjing.dengyuejinfu.widgte.webView.RdWebView;

import butterknife.BindView;

/**
 * author : Leehor
 * date   : 2019/9/1717:57
 * version: 1.0
 * desc   : 公共的webview
 */
@Route(path = ARouterUrl.WebViewActivityUrl)
public class WebViewActivity extends BaseActivity {
    @Autowired(name = BaseParams.webViewTitle)
    String webViewTitle;
    @Autowired(name = BaseParams.webViewUrl)
    String webViewUrl;
    @BindView(R.id.webView_mStatusBar)
    View webViewMStatusBar;
    @BindView(R.id.WebView_titleBar)
    TitleBar WebViewTitleBar;
    @BindView(R.id.webView_web)
    RdWebView webViewWeb;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_webview;
    }

    @Override
    protected void initInjector() {
        ARouter.getInstance().inject(this);
    }

    @Override
    protected void initView() {
        setStatusBarTextColor(R.color.white);
        setStatusBarTextAlpha(0);
        setStatusBar(webViewMStatusBar, R.color.blue1);
        setPageTitle(WebViewTitleBar, webViewTitle != null ? webViewTitle : "", R.color.white);
        setTittleBarBackground(WebViewTitleBar, R.color.blue1);
        setPageLeftText(WebViewTitleBar, "返回", R.color.white, new OnOnceClickListener() {
            @Override
            public void onOnceClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void initFunc() {
        if (webViewUrl != null) {
            webViewWeb.loadUrl(webViewUrl);
        }
    }

    @Override
    protected void onDestroy() {
        webViewWeb.destroy();
        super.onDestroy();

    }

    @Override
    protected void onPause() {
        super.onPause();
        webViewWeb.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        webViewWeb.onResume();
    }
}
