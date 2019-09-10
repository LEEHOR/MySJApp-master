package com.shenjing.mytextapp.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.shenjing.mytextapp.App;
import com.shenjing.mytextapp.R;
import com.shenjing.mytextapp.di.component.DaggerFragmentComponent;
import com.shenjing.mytextapp.di.component.FragmentComponent;
import com.shenjing.mytextapp.di.module.FragmentModule;
import com.shenjing.mytextapp.utils.ScreenUtils;
import com.shenjing.mytextapp.utils.ViewUtils;
import com.shenjing.mytextapp.widgte.OnOnceClickListener;
import com.shenjing.mytextapp.widgte.TitleBar;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.components.support.RxFragment;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public abstract class BaseFragment<T extends BaseContract.BasePresenter> extends RxFragment implements BaseContract.BaseView {
    private static final String STATE_SAVE_IS_HIDDEN = "STATE_SAVE_IS_HIDDEN";
    @Nullable
    @Inject
    protected T mPresenter;
    protected FragmentComponent mFragmentComponent;
    private Unbinder unbinder;
    private View mRootView, mErrorView, mEmptyView;

    protected Context mContext;

    protected abstract int getLayoutId();

    protected abstract void initInjector();

    protected abstract void initView();

    /*** 初始化数据以及其他请求操作 ***/
    protected abstract void initFunc();

    protected ProgressDialog mProgressDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFragmentComponent();
        ARouter.getInstance().inject(this);
        initInjector();
        attachView();
        mContext=getActivity();
        if (!NetworkUtils.isConnected()) showNoNet();
        if (savedInstanceState != null) {
            boolean isSupportHidden = savedInstanceState.getBoolean(STATE_SAVE_IS_HIDDEN);
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            if (isSupportHidden) {
                ft.hide(this);
            } else {
                ft.show(this);
            }
            ft.commit();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(STATE_SAVE_IS_HIDDEN, isHidden());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        inflaterView(inflater, container);
        unbinder = ButterKnife.bind(this, mRootView);
        initView();
        initFunc();
        return mRootView;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        detachView();
        clearMemory();
    }

    @Override
    public void showLoading() {
        mProgressDialog=new ProgressDialog(getActivity());
        if (mProgressDialog != null) {
            mProgressDialog.setMessage("正在加载数据....");
            mProgressDialog.show();
        }
    }

    @Override
    public void hideLoading() {
       if(mProgressDialog.isShowing()){
           mProgressDialog.dismiss();
       }
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
    public void onRetry() {
        ToastUtils.showShort("onRetry");
    }

    @Override
    public <T> LifecycleTransformer<T> bindToLife() {
        return this.bindToLifecycle();
    }


    /**
     * 初始化FragmentComponent
     */
    private void initFragmentComponent() {
        mFragmentComponent = DaggerFragmentComponent.builder()
                .applicationComponent(((App) getActivity().getApplication()).getApplicationComponent())
                .fragmentModule(new FragmentModule(this))
                .build();
    }

    /**
     * 贴上view
     */
    private void attachView() {
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }

    /**
     * 分离view
     */
    private void detachView() {
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }


    /**
     * 设置View
     *
     * @param inflater
     * @param container
     */
    private void inflaterView(LayoutInflater inflater, @Nullable ViewGroup container) {
        if (mRootView == null) {
            mRootView = inflater.inflate(getLayoutId(), container, false);
        }
    }

    /** 设置自定义状态栏高度 */
    protected void setStatusBar(View view,int color) {
        view.setVisibility(View.VISIBLE);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = ScreenUtils.getStatusHeight(mContext);
        view.setLayoutParams(layoutParams);
        view.setBackgroundResource(color);
    }

    /**
     * 设置状态栏文字颜色
     * @param color
     */
    protected  void  setStatusBarTextColor(int color){
        BarUtils.setStatusBarColor(getActivity(),getResources().getColor(color,null));
    }
    protected  void  setStatusBarTextAlpha(int alpha){
        BarUtils.setStatusBarAlpha(getActivity(),alpha,true);
    }

    protected void hideTitleBar(TitleBar titleBar) {
        titleBar.setVisibility(View.VISIBLE);
        titleBar.setVisibility(View.GONE);
    }

    /**
     * 设置左侧返回
     * @param titleBar
     * @param title
     * @param color
     */
    protected void setPageLeftText(TitleBar titleBar, String title, int color, OnOnceClickListener onceClickListener){
        titleBar.setVisibility(View.VISIBLE);
        titleBar.setLeftText(title,getResources().getColor(color,null),onceClickListener);
    }
    /*** 设置左侧页面返回 ***/
    protected void setTitleLeft(TitleBar titleBar,int leftIcon, String leftText,int color, OnOnceClickListener onceClickListener) {
        titleBar.setVisibility(View.VISIBLE);
        titleBar.setLeftMenu(leftIcon, leftText,getResources().getColor(color,null), onceClickListener);
    }

    /**
     * 设置标题
     * @param titleBar
     * @param title
     */
    protected void setPageTitle(TitleBar titleBar,String title) {
        titleBar.setVisibility(View.VISIBLE);
        titleBar.setTitleText(title);
    }

    /**
     * 设置标题和字体颜色
     * @param titleBar
     * @param title
     */
    protected void setPageTitle(TitleBar titleBar,String title,int color) {
        titleBar.setVisibility(View.VISIBLE);
        titleBar.setTitleText(title,getResources().getColor(color,null));
    }

    /**
     * tittlebar背景颜色
     * @param titleBar
     */
    protected void setTittleBarBackgraound(TitleBar titleBar,int color) {
        titleBar.setVisibility(View.VISIBLE);
        titleBar.setTitleBarBackground(getResources().getColor(color,null));

    }

    /*** 设置标题右侧按钮 ***/
    protected void setTitleRight(TitleBar titleBar,int rightIcon, String rightText, OnOnceClickListener onceClickListener) {
        titleBar.setVisibility(View.VISIBLE);
        titleBar.setRightMenu(rightIcon, rightText, onceClickListener);
    }

    /**
     * 设置标题右侧图片按钮
     * @param titleBar
     * @param rightIcon
     * @param onceClickListener
     */
    protected void setTitleRight(TitleBar titleBar, int rightIcon, OnOnceClickListener onceClickListener) {
        titleBar.setVisibility(View.VISIBLE);
        titleBar.setRightMenu(rightIcon,onceClickListener);
    }

    /**
     * 设置标题右侧文字按钮
     *
     * @param titleBar
     * @param onceClickListener
     */
    protected void setTitleRightImage(TitleBar titleBar, int rightIcon, OnOnceClickListener onceClickListener) {
        titleBar.setVisibility(View.VISIBLE);
        titleBar.setRightImage(rightIcon, onceClickListener);
    }

    protected void clearMemory() {
        if (mPresenter != null) mPresenter.detachView();
        ViewUtils.clearAllChildViews(getActivity());
    }


}
