package com.shenjing.dengyuejinfu.ui.contract;



import com.shenjing.dengyuejinfu.base.BaseContract;
import com.shenjing.dengyuejinfu.entity.BannerBean;
import com.shenjing.dengyuejinfu.entity.VersionBean;


/**
 * author : Leehor
 * date   : 2019/7/2612:56
 * version: 1.0
 * desc   :
 */
public class IndexFragmentContract {
    /**
     * view类 操作Ui
     */
    public interface View extends BaseContract.BaseView{

        void getBannerSuccess(BannerBean bannerBean);
        void getBannerFailure();
        void Refresh(boolean Refresh);

        void getVersionSuccess(VersionBean versionBean);
        void getVersionFailure();
    }

    /**
     * 逻辑
     */
   public interface Presenter extends BaseContract.BasePresenter<View>{

       void Banner(String actionScope);

       void uploadCallRecord(String userId,String contacts);

       void getVersion();
    }
}
