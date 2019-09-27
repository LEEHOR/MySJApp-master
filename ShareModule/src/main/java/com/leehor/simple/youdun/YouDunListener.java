package com.leehor.simple.youdun;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * author : Leehor
 * date   : 2019/9/2615:10
 * version: 1.0
 * desc   :
 */
public interface YouDunListener {

    void optionBankOcrSuccess(JSONObject jsonObject);

    void optionIdOcrSuccess(JSONObject jsonObject);

    void optionLivelinessSuccess(JSONObject jsonObject);

    void optionCompareFace(JSONObject jsonObject);

    void optionVerifyCompare(JSONObject jsonObject);

    void optionError(JSONObject jsonObject);

    void JSONExceptionError(JSONException e);
}
