package com.shenjing.dengyuejinfu.net.services;

import com.shenjing.dengyuejinfu.respondModule.BannerModel;


import io.reactivex.Observable;
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
     * @return
     */
    @POST("/homePageInfo")
    Observable<BannerModel> getBanner();
}
