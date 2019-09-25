package com.shenjing.dengyuejinfu.ui.contract;

import com.shenjing.dengyuejinfu.base.BaseContract;
import com.shenjing.dengyuejinfu.entity.ChangePassBean;

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
        void showSuccess(ChangePassBean changePassBean);

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
