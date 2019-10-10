package com.shenjing.dengyuejinfu.ui.fragmentDialog;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.ToastUtils;
import com.leehor.simple.lightShare.share.OnShareListener;
import com.leehor.simple.lightShare.share.ShareKeeper;
import com.shenjing.dengyuejinfu.R;
import com.shenjing.dengyuejinfu.base.BaseDialogFragment;
import com.shenjing.dengyuejinfu.common.ARouterUrl;
import com.shenjing.dengyuejinfu.common.BaseParams;
import com.shenjing.dengyuejinfu.ui.contract.ShareDialogFragmentContract;
import com.shenjing.dengyuejinfu.ui.presenter.ShareDialogFragmentPresenter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author : Leehor
 * date   : 2019/9/2017:41
 * version: 1.0
 * desc   :分享的Dialog
 */
@Route(path = ARouterUrl.ShareDialogFragmentUrl)
public class ShareDialogFragment extends BaseDialogFragment<ShareDialogFragmentPresenter> implements ShareDialogFragmentContract.View, OnShareListener {
    @Autowired(name = BaseParams.share_title)
    String title;
    @Autowired(name = BaseParams.share_describe)
    String describe;
    @Autowired(name = BaseParams.share_thumbnail)
    String thumbnail;
    @Autowired(name = BaseParams.share_url)
    String webUrl;

    @BindView(R.id.share_wx_friend)
    TextView shareWxFriend;
    @BindView(R.id.share_wx_space)
    TextView shareWxSpace;
    @BindView(R.id.share_qq_friend)
    TextView shareQqFriend;
    @BindView(R.id.share_qq_space)
    TextView shareQqSpace;
    @BindView(R.id.share_dismiss)
    View shareDismiss;
    private shareListener shareListener;


    @Override
    protected int getLayoutId() {
        return R.layout.dialog_share;
    }

    @Override
    protected void initInjector() {
        ARouter.getInstance().inject(this);
        initDialogComponent().inject(this);
    }

    @Override
    protected void initView() {
        mPresenter.getShareInfo(BaseParams.userId);
        setStatusBarTextAlpha(0);
    }

    @Override
    public void iniWidow(Window window) {
        if (window != null) {
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            window.getDecorView().setPadding(0, 0, 0, 0);
            window.setBackgroundDrawableResource(android.R.color.transparent);
            window.setLayout(lp.MATCH_PARENT, lp.MATCH_PARENT);
            window.setWindowAnimations(R.style.bottom_in_out_animation);
        }
    }

    @Override
    protected void initFunc() {

    }

    @Override
    public void getShareSuccess() {

    }

    @Override
    public void getShareFailure() {

    }

    @OnClick({R.id.share_wx_friend, R.id.share_wx_space, R.id.share_qq_friend, R.id.share_qq_space, R.id.share_dismiss})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.share_wx_friend:
                ToastUtils.showLong("微信好友");
                wechatDefault();
                break;
            case R.id.share_wx_space:
                ToastUtils.showLong("微信朋友圈");
                break;
            case R.id.share_qq_friend:
                ToastUtils.showLong("QQ好友");
                break;
            case R.id.share_qq_space:
                ToastUtils.showLong("QQ朋友圈");
                break;
            case R.id.share_dismiss:
                ToastUtils.showLong("关闭");
                dismiss();
                break;
        }
    }

    /**
     * 微信分享
     */
    public void wechatDefault() {
        ShareKeeper.getInstance()
                .builder(this.getActivity())
                .setPlatform(ShareKeeper.PLATFORM_WECHAT)
                .setShareType(ShareKeeper.TYPE_DEFAULT)
                .setTitle("我是标题")
                .setDesc("我是描述")
                .setImageUrl(thumbnail)
                .setWebUrl(webUrl)
                .setOnShareListener(this)
                .share();
    }

    @Override
    public void onShareSuccess(int sharePlatForm, int shareType) {
        ToastUtils.showLong("分享成功");
        if (shareListener != null) {
            shareListener.shareSuccess(sharePlatForm,shareType);
        }
    }

    @Override
    public void onShareFailed(int sharePlatForm, int shareType, String failedMessage) {
        ToastUtils.showLong("分享失败");
        if (shareListener != null) {
            shareListener.shareFailure(sharePlatForm, shareType, failedMessage);
        }
    }

    @Override
    public void onCancelShare(int sharePlatForm, int shareType, String message) {
        ToastUtils.showLong("分享失败");
        if (shareListener != null) {
            shareListener.shareCancel(sharePlatForm, shareType, message);
        }
    }

    @Override
    public void onDestroy() {
        ShareKeeper.getInstance().onDestroy();
        super.onDestroy();
    }

    public void setShareListener(shareListener listener){
        this.shareListener=listener;
    }


}
