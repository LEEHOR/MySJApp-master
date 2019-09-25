package com.shenjing.dengyuejinfu.ui.contract;

import android.widget.TextView;

import com.shenjing.dengyuejinfu.base.BaseContract;
import com.shenjing.dengyuejinfu.entity.PeopleCertificationStatusBean;

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

        void upLoadSuccess();
        void upLoadFailure();

        void getStatusSuccess(PeopleCertificationStatusBean certificationStatus);
        void getStatusFailure();

        TextView submitText();

        /**
         * 是否可以跳转
         * @param isCanNext
         */
        void  isCanNext(boolean isCanNext);

        /**
         * 是否需要上传
         * @param isCanUpLoad
         */
        void  isCanUpLoad(boolean isCanUpLoad);

        /**
         * 控件是否可编辑
         * @param isCanEditor
         */
        void isCanEditor(boolean isCanEditor);
    }

    /**
     * 逻辑
     */
   public interface Presenter extends BaseContract.BasePresenter<View>{
        /**
         * 认证
         * @param map
         */
       void uploadPeopleInfo(Map<String,Object> map);

        /**
         * 获取认证状态
         * @param userId
         */
       void getPeopleStatus(String userId);
    }
}
