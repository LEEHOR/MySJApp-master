package com.shenjing.dengyuejinfu.ui.contract;

import android.widget.TextView;

import com.shenjing.dengyuejinfu.base.BaseContract;
import com.shenjing.dengyuejinfu.entity.BaseBean;
import com.shenjing.dengyuejinfu.entity.PeopleCertificationStatusBean;

import java.io.File;
import java.util.Map;

/**
 * author : Leehor
 * date   : 2019/7/2612:56
 * version: 1.0
 * desc   :
 */
public class CertificationActivityContract {
    /**
     * view类 操作Ui
     */
    public interface View extends BaseContract.BaseView {


        void getStatusSuccess(PeopleCertificationStatusBean certificationStatus);

        void getStatusFailure();

        void getVerifyTokenSuccess(String token);

        void getVerifyTokenFailure();

        void getOcrResultSuccess();

        void getOcrResultFailure();

//        void downLoadImgSuccess(int type, byte[] bytes);
//
//        void downLoadImgFailure(int type);

        TextView submitText();


        /**
         * 是否可以跳转
         *
         * @param isCanNext
         */
        void isCanNext(boolean isCanNext);

        /**
         * 控件是否可编辑
         *
         * @param isCanEditor
         */
        void isCanEditor(boolean isCanEditor);
    }

    /**
     * 逻辑
     */
    public interface Presenter extends BaseContract.BasePresenter<View> {


        /**
         * 获取阿里云认证Token
         */
        void verifyToken(String userId);

        /**
         * 发送结果通知后台
         * @param userId
         * @param result
         */
        void sendOcrResult(String userId,String result,String token);

//        void downLoadImg(String url,int type);

        /**
         * 获取认证状态
         *
         * @param userId
         */
        void getPeopleStatus(String userId);




    }
}
