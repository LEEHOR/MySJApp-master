package com.shenjing.dengyuejinfu.net.services;

import com.shenjing.dengyuejinfu.entity.BankListBean;
import com.shenjing.dengyuejinfu.entity.BannerBean;
import com.shenjing.dengyuejinfu.entity.CardListBean;
import com.shenjing.dengyuejinfu.entity.LoanListBean;


import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * author : Leehor
 * date   : 2019/9/1715:31
 * version: 1.0
 * desc   : 首页的接口
 */
public interface IndexFragmentApi {
    /**
     * 首页滚动新闻
     * @param actionScope
     *          1：首页轮播图
     *          2：提额秘籍
     * @return
     */
    @POST("/homePageInfo")
    @FormUrlEncoded
    Observable<BannerBean> getBanner(@Field("actionScope") String actionScope);

    /**
     * 获取银行列表
     * @return
     */
    @POST("/getBankList")
    Observable<BankListBean> getBankList();

    /**
     * 获取银行卡列表
     * @return
     */
    @POST("/getCardList")
    @FormUrlEncoded
    Observable<CardListBean> getCardList(@Field("bankId") long bankId);

    /**
     * 获取贷款列表
     * @return
     */
    @POST("/getLoanList")
    Observable<LoanListBean> getLoanList();
}
