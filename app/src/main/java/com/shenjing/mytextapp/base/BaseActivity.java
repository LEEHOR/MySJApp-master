package com.shenjing.mytextapp.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.EmptyUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.android.material.snackbar.Snackbar;
import com.shenjing.mytextapp.App;
import com.shenjing.mytextapp.R;
import com.shenjing.mytextapp.di.component.ActivityComponent;
import com.shenjing.mytextapp.di.component.DaggerActivityComponent;
import com.shenjing.mytextapp.di.module.ActivityModule;
import com.shenjing.mytextapp.utils.ActivityManage;
import com.shenjing.mytextapp.utils.ActivityStack;
import com.shenjing.mytextapp.utils.ScreenUtils;
import com.shenjing.mytextapp.widgte.OnOnceClickListener;
import com.shenjing.mytextapp.widgte.TitleBar;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public abstract class BaseActivity<T extends BasePresenter> extends RxAppCompatActivity implements BaseContract.BaseView {

    @Nullable
    protected T mPresenter;
    protected ActivityComponent mActivityComponent;
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
        initActivityComponent();
        initInjector();
        setContentView(getLayoutId());
        unbinder = ButterKnife.bind(this);
        mContext = this;
        ActivityManage.push(this);
        if (EmptyUtils.isNotEmpty(getSupportActionBar())) {
            getSupportActionBar().hide();
        }
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ScreenUtils.setTextColorStatusBar((Activity) mContext, false);
        attachView();
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
        BarUtils.hideNavBar(this);
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
        BarUtils.setStatusBarAlpha(this, alpha, true);
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
    public void showLoading() {
        mProgressDialog = new ProgressDialog(this);
        if (mProgressDialog != null) {
            mProgressDialog.setMessage("正在加载数据");
            mProgressDialog.show();
        }
    }

    @Override
    public void hideLoading() {
        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
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
        if (mPresenter != null) mPresenter.detachView();
//        if (mRxManager != null) mRxManager.clear();
        // 清除栈
        ActivityStack.getInstance().finishActivity(this);
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
    public void showFaild(String errorMsg) {
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
    protected void initActivityComponent() {
        mActivityComponent = DaggerActivityComponent.builder()
                .applicationComponent(((App) getApplication()).getApplicationComponent())
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
