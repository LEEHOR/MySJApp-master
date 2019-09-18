package com.shenjing.dengyuejinfu.ui.activity;

import android.os.Handler;
import android.os.Message;
import android.widget.FrameLayout;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.SPUtils;
import com.shenjing.dengyuejinfu.R;
import com.shenjing.dengyuejinfu.base.BaseActivity;
import com.shenjing.dengyuejinfu.common.ARouterUrl;
import com.shenjing.dengyuejinfu.common.BaseParams;
import com.shenjing.dengyuejinfu.utils.ActivityManage;
import com.shenjing.dengyuejinfu.widgte.webView.RdWebView;

import java.lang.ref.WeakReference;

import butterknife.BindView;


/**
 * author : Leehor
 * date   : 2019/8/139:16
 * version: 1.0
 * desc   : 开屏页
 */
public class SplashActivity extends BaseActivity {


    //    // 跳转引导页
    private static final int GO_GUIDE = 0x01;
    // 跳转首页
    private static final int GO_MAIN = 0x02;
    // 页面跳转逻辑
    private static final int DO_HANDLER = 0x99;
    // 最小显示时间
    private static final int SHOW_TIME_MIN = 700;
    // 开始时间
    private static long mStartTime;

    private SplashHandler mHandler;
    @BindView(R.id.splash_webView)
    RdWebView splashWebView;
    @BindView(R.id.splash_toolbar)
    FrameLayout splashToolbar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initInjector() {
        ARouter.getInstance().inject(this);
    }

    @Override
    protected void initView() {
        mHandler = new SplashHandler(this);
        //记录开始时间
        mStartTime = System.currentTimeMillis();

        somethingToDo();
    }

    @Override
    protected void initFunc() {
        //沉浸式标题栏
        hideStatusBar();
        setStatusBarTextColor(R.color.white);
        setStatusBarTextAlpha(0);
        int i = ConvertUtils.px2sp(BarUtils.getActionBarHeight());
        splashToolbar.setPadding(0, i, 0, 0);


    }

    private void somethingToDo() {

        mHandler.removeMessages(DO_HANDLER);
        mHandler.sendEmptyMessage(DO_HANDLER);

    }


    /**
     * Handler:跳转到不同界面
     */
    private static class SplashHandler extends Handler {
        WeakReference<SplashActivity> act;

        SplashHandler(SplashActivity act) {
            this.act = new WeakReference<>(act);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            // 计算一下总共花费的时间
            long loadingTime = System.currentTimeMillis() - mStartTime;
            switch (msg.what) {
                case DO_HANDLER:
                    // 取得相应的值，如果没有该值，说明还未写入，用true作为默认值
                    SPUtils spUtils = SPUtils.getInstance();
                    boolean isFirstIn = spUtils.getBoolean(BaseParams.ISFIRSTIN_KEY, true);
                    // 判断程序与第几次运行，如果是第一次运行则跳转到引导界面，否则跳转到主界面
                    if (!isFirstIn) {
                        sendEmptyMessage(GO_MAIN);
                    } else {
                        sendEmptyMessage(GO_GUIDE);
                    }
                    break;

                case GO_GUIDE:
                    // 如果比最小显示时间还短，就延时进入GuideActivity，否则直接进入
                    if (loadingTime < SHOW_TIME_MIN) {
                        postDelayed(goToGuideActivity, SHOW_TIME_MIN - loadingTime);
                    } else {
                        post(goToGuideActivity);
                    }
                    break;

                case GO_MAIN:
                    // 如果比最小显示时间还短，就延时进入HomeActivity，否则直接进入
                    if (loadingTime < SHOW_TIME_MIN) {
                        postDelayed(goToMainActivity, SHOW_TIME_MIN - loadingTime);
                    } else {
                        post(goToMainActivity);
                    }
                    break;
            }
        }

        // 进入 GuideAct
        Runnable goToGuideActivity = new Runnable() {
            @Override
            public void run() {
                ARouter.getInstance().build(ARouterUrl.MainActivityUrl).navigation();
                act.get().finish();
            }
        };

        // 进入 MainAct
        Runnable goToMainActivity = new Runnable() {
            @Override
            public void run() {
                ARouter.getInstance().build(ARouterUrl.MainActivityUrl).navigation();
                act.get().finish();
            }
        };
    }

    @Override
    public void onBackPressed() {
        ActivityManage.onExit();
    }
}
