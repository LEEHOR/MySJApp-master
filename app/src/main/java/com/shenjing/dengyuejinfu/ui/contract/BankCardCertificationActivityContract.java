package com.shenjing.dengyuejinfu.ui.contract;

import android.widget.TextView;

import com.shenjing.dengyuejinfu.base.BaseContract;
import com.shenjing.dengyuejinfu.entity.BankInfoBean;

import java.io.File;
import java.util.Map;

/**
 * author : Leehor
 * date   : 2019/7/2612:56
 * version: 1.0
 * desc   :
 */
public class BankCardCertificationActivityContract {
    /**
     * view类 操作Ui
     */
    public interface View extends BaseContract.BaseView {

        void getBankInfoSuccess(BankInfoBean model);
        void getBankInfoFailure();
        void upLoadSuccess();
        void upLoadFailure();

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

         TextView submit();
    }

    /**
     * 逻辑
     */
   public interface Presenter extends BaseContract.BasePresenter<View>{

        void  getBankInfo(String userId);

        void upLoadBankCardInfo(Map<String,String> map);


    }

}
