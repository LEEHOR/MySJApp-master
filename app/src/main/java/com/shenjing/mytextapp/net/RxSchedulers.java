package com.shenjing.mytextapp.net;

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author ：leehor
 * date   : 2019/7/2613:03
 * version: 1.0
 * desc   :通用的Rx线程转换类
 * 参考:http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2015/0819/3327.html
 */
public class RxSchedulers {
    static final ObservableTransformer schedulersTransformer= upstream -> (upstream).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());
    public static <T> ObservableTransformer<T, T> applySchedulers() {
        return  (ObservableTransformer<T, T>)schedulersTransformer;
    }
}
