package com.shenjing.dengyuejinfu.ui.contract;



import com.shenjing.dengyuejinfu.base.BaseContract;
import com.shenjing.dengyuejinfu.respondModule.BannerModel;


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

        void getBannerSuccess(BannerModel bannerModel);
        void getBannerFailure();
        void Refresh(boolean Refresh);
    }

    /**
     * 逻辑
     */
   public interface Presenter extends BaseContract.BasePresenter<View>{

       void Banner();
    }
}
