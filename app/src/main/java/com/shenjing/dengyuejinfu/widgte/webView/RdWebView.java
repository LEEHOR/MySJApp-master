package com.shenjing.dengyuejinfu.widgte.webView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * Email: hbh@erongdu.com
 * Created by hebihe on 6/1/16.
 */
public class RdWebView extends WebView {
    private final static String DIALOG_TITLE = "提示";

    public RdWebView(Context context) {
        super(context);
        intWebView(context);
    }

    public RdWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        intWebView(context);
    }

    public RdWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        intWebView(context);
    }

    private void intWebView(final Context context) {
        WebSettings setting = this.getSettings();
        setting.setSupportZoom(false);//不支持缩放
        setting.setBuiltInZoomControls(false);//设置不显示缩放按钮

        setting.setUseWideViewPort(false);///关键点

        setting.setJavaScriptEnabled(true);//设置支持javascript脚本

        setting.setSavePassword(false);//不保存密码

        this.addJavascriptInterface(new WebReturn(context), "webReturn");

        this.setWebChromeClient(new WebChromeClient() { //webview 对话框
            @Override
            public boolean onJsAlert(WebView view, String url, String message,
                                     final JsResult result) {
                // 构建一个Builder来显示网页中的alert对话框
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(DIALOG_TITLE);
                builder.setMessage(message);
                builder.setPositiveButton(android.R.string.ok,
                        new AlertDialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                result.confirm();
                            }
                        });
                builder.setCancelable(false);
                builder.create();
                builder.show();
                return true;
            }

            // JS调用和反调用
            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
                if (message.equals("1")) {
                    // 解析参数defaultValue
                    // 调用java方法并得到结果
                }
                // 返回结果
                result.confirm("result");

            /*
            function showHtmlCallJava() {
                var ret = prompt( "1", "param1;param2" );
                // ret值即为java传回的”result”
                // 根据返回内容作相应处理
            }
            */
                return true;
            }
        });

        this.setWebViewClient(new RDWebViewClient());
    }
}
