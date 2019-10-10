package com.shenjing.dengyuejinfu.base;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.shenjing.dengyuejinfu.App;
import com.shenjing.dengyuejinfu.R;
import com.shenjing.dengyuejinfu.di.component.DaggerDialogComponent;
import com.shenjing.dengyuejinfu.di.component.DialogComponent;
import com.shenjing.dengyuejinfu.di.module.DialogModule;
import com.shenjing.dengyuejinfu.utils.ScreenUtils;
import com.shenjing.dengyuejinfu.utils.ViewUtils;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.components.support.RxAppCompatDialogFragment;
import java.util.Objects;
import javax.inject.Inject;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * author : Leehor
 * date   : 2019/9/2016:55
 * version: 1.0
 * desc   :
 */
public abstract class BaseDialogFragment<T extends BaseContract.BasePresenter> extends RxAppCompatDialogFragment implements BaseContract.BaseView {
    @Nullable
    @Inject
    protected T mPresenter;
    @Nullable
    private Unbinder unbinder;

    protected ProgressDialog mProgressDialog;

    protected abstract int getLayoutId();

    protected abstract void initInjector();

    protected abstract void initView();

    public abstract void iniWidow(Window window);

    /*** 初始化数据以及其他请求操作 ***/
    protected abstract void initFunc();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initInjector();
        attachView();
        //ActivityManage.push(this);
//        if (ObjectUtils.isNotEmpty(getSupportActionBar())) {
//            getSupportActionBar().hide();
//        }
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ScreenUtils.setTextColorStatusBar(Objects.requireNonNull(this.getActivity()), false);
        if (!NetworkUtils.isConnected()) showNoNet();
        initView();
        initFunc();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
       Dialog dialog= super.onCreateDialog(savedInstanceState);
        iniWidow(dialog.getWindow());
        return dialog;
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
    public void showLoading(String title) {
        mProgressDialog = new ProgressDialog(this.getActivity());
        if (mProgressDialog != null) {
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.setMessage(title==null?"正在加载..":title);
            mProgressDialog.show();
        }
    }
    @Override
    public void showLoading() {
        mProgressDialog = new ProgressDialog(this.getActivity());
        if (mProgressDialog != null) {
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.setMessage("正在加载..");
            mProgressDialog.show();
        }
    }
    @Override
    public void hideLoading() {
        if (mProgressDialog!=null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }



    @Override
    public void onDetach() {
        super.onDetach();
        unbinder.unbind();
        detachView();
       // clearMemory();
    }

    @Override
    public <T> LifecycleTransformer<T> bindToLife() {
        return this.bindToLifecycle();
    }

    protected void clearMemory() {
        if (mPresenter != null) mPresenter.detachView();
       // ViewUtils.clearAllChildViews(this);
    }

    /**
     * dagger2 注入
     */
    protected DialogComponent initDialogComponent() {
        return DaggerDialogComponent.builder()
                .applicationComponent(App.getInstance().getApplicationComponent())
                .dialogModule(new DialogModule(this))
                .build();
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
    public void onRetry() {
        ToastUtils.showShort("onRetry");
    }

    /**
     * 设置状态栏透明度
     * @param alpha
     */
    protected  void  setStatusBarTextAlpha(int alpha){
        BarUtils.setStatusBarColor(Objects.requireNonNull(getActivity()), Color.argb(alpha, 0, 0, 0));
        //  BarUtils.setStatusBarVisibility(getActivity(),true);
        //BarUtils.setStatusBarAlpha(getActivity(),alpha,true);
    }

    /**
     * 设置状态栏文字颜色
     * @param color
     */
    protected  void  setStatusBarTextColor(int color){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            BarUtils.setStatusBarColor(Objects.requireNonNull(getActivity()),getResources().getColor(color,null));
        } else {
            BarUtils.setStatusBarColor(Objects.requireNonNull(getActivity()),getResources().getColor(color));
        }
    }
}
