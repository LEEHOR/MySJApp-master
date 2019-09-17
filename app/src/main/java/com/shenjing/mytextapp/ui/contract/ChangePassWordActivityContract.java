package com.shenjing.mytextapp.ui.contract;

import com.shenjing.mytextapp.base.BaseContract;
import com.shenjing.mytextapp.entity.ChangePassBean;
import com.shenjing.mytextapp.respondModule.ChangePassModule;

import java.util.Map;

/**
 * author : Leehor
 * date   : 2019/7/2612:56
 * version: 1.0
 * desc   :
 */
public class ChangePassWordActivityContract {
    /**
     * view类 操作Ui
     */
    public interface View extends BaseContract.BaseView {
        void showSuccess(ChangePassModule changePassModule);

        void showFailure(String msg);

    }

    /**
     * 逻辑
     */
    public interface Presenter extends BaseContract.BasePresenter<View> {
        //修改密码
        void changePassWord(Map<String,Object> map);
    }
}
