package com.shenjing.dengyuejinfu.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.android.material.snackbar.Snackbar;
import com.shenjing.dengyuejinfu.App;
import com.shenjing.dengyuejinfu.R;
import com.shenjing.dengyuejinfu.di.component.ActivityComponent;
import com.shenjing.dengyuejinfu.di.component.DaggerActivityComponent;
import com.shenjing.dengyuejinfu.di.module.ActivityModule;
import com.shenjing.dengyuejinfu.utils.ActivityManage;
import com.shenjing.dengyuejinfu.utils.ActivityStack;
import com.shenjing.dengyuejinfu.utils.ScreenUtils;
import com.shenjing.dengyuejinfu.widgte.OnOnceClickListener;
import com.shenjing.dengyuejinfu.widgte.TitleBar;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public abstract class BaseActivity<T extends BaseContract.BasePresenter> extends RxAppCompatActivity implements BaseContract.BaseView {

    @Nullable
    @Inject
    protected T mPresenter;
    protected Context mContext;
    private Snackbar snackbar;
    @Nullable
    private Unbinder unbinder;

    protected abstract int getLayoutId();

    protected abstract void initInjector();

    protected abstract void initView();

    /*** 初始化数据以及其他请求操作 ***/
    protected abstract void initFunc();

    protected ProgressDialog mProgressDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        unbinder = ButterKnife.bind(this);
        mContext = this;
        initInjector();
        attachView();
        ActivityManage.push(this);
        if (ObjectUtils.isNotEmpty(getSupportActionBar())) {
            getSupportActionBar().hide();
        }
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ScreenUtils.setTextColorStatusBar((Activity) mContext, false);

        if (!NetworkUtils.isConnected()) showNoNet();
        //添加Activity到堆栈
        ActivityStack.getInstance().addActivity(this);
        initView();
        initFunc();
    }

    /**
     * 隐藏系统导航栏
     */
    protected void hideStatusBar() {
        ScreenUtils.translateStatusBar((Activity) mContext);
        BarUtils.setNavBarVisibility(BaseActivity.this, false);
        setTheme(R.style.TranslucentTheme);
    }

    /**
     * 设置状态栏文字颜色
     *
     * @param color
     */
    protected void setStatusBarTextColor(int color) {
        BarUtils.setStatusBarColor(this, getResources().getColor(color, null));
    }

    protected void setStatusBarTextAlpha(int alpha) {
        BarUtils.setStatusBarColor(this, Color.argb(alpha, 0, 0, 0));
        //BarUtils.setStatusBarVisibility(this,false);
        // BarUtils.setStatusBarAlpha(this, alpha, true);
    }

    /**
     * 自定义toolbar颜色和高度
     */
    protected void setStatusBar(View view, int color) {
        view.setVisibility(View.VISIBLE);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = ScreenUtils.getStatusHeight(mContext);
        view.setLayoutParams(layoutParams);
        view.setBackgroundResource(color);
    }

    /*** 隐藏自定义titleBar ***/
    protected void hideTitleBar(TitleBar titleBar) {
        titleBar.setVisibility(View.GONE);
    }

    /*** 设置左侧页面返回 ***/
    protected void setPageBack(TitleBar titleBar, int color) {
        titleBar.setVisibility(View.VISIBLE);
        titleBar.setLeftMenu(R.mipmap.icon_common_back_white, null, getResources().getColor(color, null), new OnOnceClickListener() {
            @Override
            public void onOnceClick(View v) {
                onBackPressed();
            }
        });
    }

    //=========================自定义tittle 按钮设置======================================\\
    protected void setPageBack(TitleBar titleBar, int res, int color) {
        titleBar.setVisibility(View.VISIBLE);
        titleBar.setLeftMenu(res, null, getResources().getColor(color, null), new OnOnceClickListener() {
            @Override
            public void onOnceClick(View v) {
                onBackPressed();
            }
        });
    }

    /*** 设置页面标题以及左返回 ***/
    protected void setPageTitleBack(TitleBar titleBar, String title, int color) {
        titleBar.setVisibility(View.VISIBLE);
        titleBar.setLeftMenu(R.mipmap.icon_common_back_white, title, getResources().getColor(color, null), new OnOnceClickListener() {
            @Override
            public void onOnceClick(View v) {
                onBackPressed();
            }
        });
    }

    /**
     * 设置左侧返回
     *
     * @param titleBar
     * @param title
     * @param color
     */
    protected void setPageLeftText(TitleBar titleBar, String title, int color, OnOnceClickListener onceClickListener) {
        titleBar.setVisibility(View.VISIBLE);
        titleBar.setLeftText(title, getResources().getColor(color, null), onceClickListener);
    }

    /**
     * 左侧返回
     *
     * @param titleBar
     * @param title
     */
    protected void setPageTitle(TitleBar titleBar, String title) {
        titleBar.setVisibility(View.VISIBLE);
        titleBar.setTitleText(title);
    }

    /**
     * 标题标题内容、颜色
     *
     * @param titleBar
     * @param title
     */
    protected void setPageTitle(TitleBar titleBar, String title, int Color) {
        titleBar.setVisibility(View.VISIBLE);
        titleBar.setTitleText(title, getResources().getColor(Color, null));
    }

    /**
     * tittlebar背景颜色
     *
     * @param titleBar
     */
    protected void setTittleBarBackground(TitleBar titleBar, int color) {
        titleBar.setVisibility(View.VISIBLE);
        titleBar.setTitleBarBackground(getResources().getColor(color, null));

    }

    /*** 设置标题右侧图片按钮 ***/
    protected void setTitleRight(TitleBar titleBar, int rightIcon, String rightText, OnOnceClickListener onceClickListener) {
        titleBar.setVisibility(View.VISIBLE);
        titleBar.setRightMenu(rightIcon, rightText, onceClickListener);
    }

    /**
     * 设置标题右侧图片按钮
     *
     * @param titleBar
     * @param rightIcon
     * @param onceClickListener
     */
    protected void setTitleRight(TitleBar titleBar, int rightIcon, OnOnceClickListener onceClickListener) {
        titleBar.setVisibility(View.VISIBLE);
        titleBar.setRightMenu(rightIcon, onceClickListener);
    }

    /**
     * 设置标题右侧文字按钮
     *
     * @param titleBar
     * @param title
     * @param onceClickListener
     */
    protected void setTitleRight(TitleBar titleBar, String title, int color, OnOnceClickListener onceClickListener) {
        titleBar.setVisibility(View.VISIBLE);
        titleBar.setRightText(title, getResources().getColor(color, null), onceClickListener);
    }

    @Override
    public void showLoading(String title) {

        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.setMessage(title == null ? "正在加载.." : title);
            mProgressDialog.show();
        }
    }

    @Override
    public void showLoading() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.setMessage("正在加载..");
            mProgressDialog.show();
        }
    }

    @Override
    public void hideLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

    @Override
    public void onBackPressed() {
        releaseMemory();
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        releaseMemory();
        super.onDestroy();
        unbinder.unbind();
        detachView();
        ActivityManage.remove(this);

    }

    /*** 页面关闭时释放内存 ***/
    public void releaseMemory() {
        detachView();
        ActivityStack.getInstance().finishActivity(this);
    }

    /**
     * 关闭指定Activity
     *
     * @param activity
     */
    public void closeActivity(Activity activity) {
        ActivityStack.getInstance().finishActivity(activity);
    }

    /**
     * 获取前一个activity
     */
    public Activity getPreActivity() {
        return ActivityStack.getInstance().preActivity();
    }

    /**
     * 根据名字获取activity
     *
     * @param ativityName
     * @return
     */
    public Activity getActivityByName(String ativityName) {
        return ActivityStack.getInstance().getActivity(ativityName);
    }

    /**
     * 分离view
     */
    private void detachView() {
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    private void attachView() {
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }

    @Override
    public void onRetry() {

    }

    @Override
    public <T> LifecycleTransformer<T> bindToLife() {
        return this.bindToLifecycle();
    }

    @Override
    public void showSuccess(String successMsg) {
        ToastUtils.showShort(successMsg);
    }

    @Override
    public void showFail(String errorMsg) {
        ToastUtils.showShort(errorMsg);
    }

    @Override
    public void showNoNet() {
        ToastUtils.showShort(R.string.no_network_connection);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    finishAfterTransition();
                } else {
                    finish();
                }
                break;
        }
        return true;
    }

    /**
     * dagger2 注入
     */
    protected ActivityComponent initActivityComponent() {
        return DaggerActivityComponent.builder()
                .applicationComponent(App.getInstance().getApplicationComponent())
                .activityModule(new ActivityModule(this))
                .build();
    }

    public void showSnackBar(View view, String msg) {
        showSnackBar(view, msg, Snackbar.LENGTH_SHORT);
    }

    public void showSnackBar(View view, String msg, int length) {
        if (snackbar == null) {
            snackbar = Snackbar.make(view, msg, length);
        } else {
            snackbar.setText(msg);
            snackbar.setDuration(length);
        }
        snackbar.show();
    }

    public void hideSnackBar() {
        if (snackbar != null) {
            snackbar.dismiss();
        }
    }

}
