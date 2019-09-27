package com.shenjing.dengyuejinfu.ui.presenter;

import com.blankj.utilcode.util.ToastUtils;
import com.shenjing.dengyuejinfu.base.BasePresenter;
import com.shenjing.dengyuejinfu.ui.contract.CertificationFaceActivityContract;
import java.util.Map;
import javax.inject.Inject;

/**
 * author : Leehor
 * date   : 2019/7/2613:03
 * version: 1.0
 * desc   :
 */
public class CertificationFaceActivityPresenter extends BasePresenter<CertificationFaceActivityContract.View>
        implements CertificationFaceActivityContract.Presenter {

    @Inject
    public CertificationFaceActivityPresenter() {

    }



    @Override
    public void uploadFaceInfo(Map<String, Object> map) {

    }

    @Override
    public void getFaceStatus(String userId) {

    }


    private void loadUploadError(Throwable throwable) {
        throwable.printStackTrace();
        mView.hideLoading();
        ToastUtils.showShort("加载错误..");
        mView.isCanNext(false);
        mView.isCanEditor(true);
        mView.isCanUpLoad(true);
    }

    private void loadStatusError(Throwable throwable) {
        throwable.printStackTrace();
        mView.hideLoading();
        ToastUtils.showShort("加载错误..");
        mView.isCanNext(false);
        mView.isCanEditor(true);
        mView.isCanUpLoad(true);
    }
}
