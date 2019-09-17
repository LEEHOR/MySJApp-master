package com.shenjing.mytextapp.ui.contract;



import com.shenjing.mytextapp.base.BaseContract;
import com.shenjing.mytextapp.respondModule.BannerModule;


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

        void getBannerSuccess(BannerModule bannerModule);
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
