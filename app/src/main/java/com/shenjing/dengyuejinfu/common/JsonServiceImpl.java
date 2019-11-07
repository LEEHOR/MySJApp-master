package com.shenjing.dengyuejinfu.common;

import android.content.Context;
import android.util.Log;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.service.SerializationService;
import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * author : Leehor
 * date   : 2019/11/716:02
 * version: 1.0
 * desc   :
 */
@Route(path = "/service/json")
public class JsonServiceImpl implements SerializationService {
    @Override
    public void init(Context context) {

    }

    @Override
    public <T> T json2Object(String text, Class<T> clazz) {
        Log.w("TAG", "text---" + text);
        return new Gson().fromJson(text, clazz);
    }

    @Override
    public String object2Json(Object instance) {
        Log.w("TAG", "Object---" + instance);
        return new Gson().toJson(instance);
    }

    @Override
    public <T> T parseObject(String input, Type clazz) {
        return new Gson().fromJson(input,clazz);
    }
}
