package com.leehor.simple.youdun;

import android.content.Context;
import android.util.Log;

import com.authreal.api.AuthBuilder;
import com.authreal.api.FormatException;
import com.authreal.api.OnResultListener;
import com.authreal.component.AuthComponentFactory;
import com.authreal.component.CompareItemFactory;
import com.authreal.component.CompareItemSession;
import com.authreal.component.LivingComponent;
import com.authreal.component.OCRBankComponent;
import com.authreal.component.OCRComponent;
import com.authreal.component.VerifyCompareComponent;
import com.leehor.simple.Config;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * author : Leehor
 * date   : 2019/9/2614:23
 * version: 1.0
 * desc   :友盾的ocr检测
 */
public class YouDunHelper {

    public YouDunListener youDunListener;
    //单例模式
    public static YouDunHelper youDunHelper = null;

    public static YouDunHelper getInstance() {
        if (youDunHelper == null) {
            youDunHelper = new YouDunHelper();
        }
        return youDunHelper;
    }

    public YouDunHelper() {

    }

    /**
     * 身份证识别ocr
     *
     * @param mContext
     * @param OrderId  订单
     */
    public void optionIdOCR(Context mContext, String OrderId) {
        AuthBuilder authBuilder = getAuthBuilder(OrderId);
        OCRComponent ocrComponenet = AuthComponentFactory.getOcrComponent();
        //开始调用
        authBuilder.addFollow(ocrComponenet).start(mContext);
    }

    /**
     * 银行卡识别ocr
     *
     * @param mContext
     * @param OrderId
     */
    public void optionBankOCR(Context mContext, String OrderId) {
        AuthBuilder authBuilder = getAuthBuilder(OrderId);
        OCRBankComponent ocrBankComponent = AuthComponentFactory.getOCRBankComponent();
        //开始调用
        authBuilder.addFollow(ocrBankComponent).start(mContext);
    }

    /**
     * 活体检测
     *
     * @param mContext
     * @param OrderId
     */
    public void optionLiveLiness(Context mContext, String OrderId) {
        LivingComponent livingComponent = AuthComponentFactory.getLivingComponent();
        AuthBuilder authBuilder = getAuthBuilder(OrderId);
        authBuilder.addFollow(livingComponent).start(mContext);
    }

    /**
     * 人脸比对
     *
     * @param mContext
     * @param OrderId         订单
     * @param OcrSessionId    身份证识别的SessionId
     * @param livingSessionId 活体检测成功后返回的 livingId
     */
    public void optionCompareFace(Context mContext, String OrderId, String OcrSessionId, String livingSessionId) {
        getAuthBuilder(OrderId)
                .addFollow(AuthComponentFactory.getCompareFaceComponent()
                        // 此示例对比项A为OCR人像图片
                        .setCompareItemA(CompareItemFactory.getCompareItemBySessionId(
                                OcrSessionId,
                                CompareItemSession.SessionType.PHOTO_IDENTIFICATION))
                        //此示例对比项B为活体过程中截图
                        .setCompareItemB(CompareItemFactory.getCompareItemBySessionId(
                                livingSessionId,
                                CompareItemSession.SessionType.PHOTO_LIVING))
                )
                .start(mContext);

    }

    /**
     * 实人认证
     *
     * @param mContext
     * @param OrderId
     * @param livingSessionId 活体检测成功后返回的 livingId
     * @param idCardName      身份证姓名
     * @param idCardNumber    身份证号码
     */
    public void optionVerifyCompare(Context mContext, String OrderId, String livingSessionId, String idCardName, String idCardNumber) {
        AuthBuilder authBuilder = getAuthBuilder(OrderId);
        VerifyCompareComponent verifyComponent = AuthComponentFactory.getVerifyCompareComponent();
        try {
            verifyComponent.setNameAndNumber(idCardName, idCardNumber);
            verifyComponent.setCompareItem(CompareItemFactory.getCompareItemBySessionId(
                    livingSessionId,
                    CompareItemSession.SessionType.PHOTO_LIVING));
        } catch (FormatException e) {
            e.printStackTrace();
        }
        authBuilder.addFollow(verifyComponent);
        authBuilder.start(mContext);
    }

    /**
     * 身份证识别、活体检测 、人脸比对、活体照对比
     *
     * @param mContext
     * @param OrderId
     */
    public void AllOcr(Context mContext, String OrderId) {
        AuthBuilder authBuilder = getAuthBuilder(OrderId);
        authBuilder
                /** 添加 身份证ocr识别 模块 */
                .addFollow(AuthComponentFactory.getOcrComponent().showConfirm(true))
                /** 添加 活体检测 模块 */
                .addFollow(AuthComponentFactory.getLivingComponent())
                /** 添加 实人认证  模块*/
                .addFollow(AuthComponentFactory.getVerifyCompareComponent())
                /** 添加 人脸比对 模块 */
                .addFollow(AuthComponentFactory.getCompareFaceComponent()
                        /** 设置A比对象为 身份证OCR识别 证件照 */
                        .setCompareItemA(CompareItemFactory.getCompareItemBySessionId(CompareItemSession.SessionType.PHOTO_IDENTIFICATION))
                        /** 设置B比对象为 活体检测 活体照 */
                        .setCompareItemB(CompareItemFactory.getCompareItemBySessionId(CompareItemSession.SessionType.PHOTO_LIVING)))
                /** 开始流程 */
                .start(mContext);

    }

    /**
     * 创建识别类
     *
     * @param orderId
     * @return
     */
    public AuthBuilder getAuthBuilder(String orderId) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmsss");
        String sign_time = sdf.format(date);
        //生成 签名
        String sign = MD5Util.getMD5Sign(Config.YOUDUN_PUB_KEY, orderId, sign_time, Config.YOUDUN_SRCURITY_KEY);
        AuthBuilder authBuilder = new AuthBuilder(orderId, Config.YOUDUN_SRCURITY_KEY, sign_time, sign, new OnResultListener() {
            @Override
            public void onResult(int i, String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    Log.d("友盾","解析"+jsonObject.toString());
//                    if (jsonObject.has("success") && (
//                            jsonObject.getString("success").equals("true")
//                            || jsonObject.getString("success").equals("1")
//                    )) {
                        switch (i) {
                            //银行卡识别Ocr
                            case AuthBuilder.OPTION_OCR_BANK:
                                Log.d("友盾","银行卡识别");
                                if (youDunListener != null) {
                                    youDunListener.optionBankOcrSuccess(jsonObject);
                                }
                                break;
                            //数字活体
                            case AuthBuilder.OPTION_LIVING_LIP:

                                break;
                            case AuthBuilder.OPTION_ERROR:
                                Log.d("友盾","错误_OPTION_ERROR");
                                if (youDunListener != null) {
                                    youDunListener.optionError(jsonObject);
                                }
                                break;
                            // 身份证OCR识别
                            case AuthBuilder.OPTION_OCR:
                                Log.d("友盾","身份证识别");
                                if (youDunListener != null) {
                                    youDunListener.optionIdOcrSuccess(jsonObject);
                                }
                                break;
                            // 活体检测 回调
                            case AuthBuilder.OPTION_LIVENESS:
                                Log.d("友盾","活体检测识别");
                                if (youDunListener != null) {
                                    youDunListener.optionLivelinessSuccess(jsonObject);
                                }
                                break;
                            // 人脸比对 回调
                            case AuthBuilder.OPTION_COMPARE_FACE:
                                Log.d("友盾","人脸比对");
                                if (youDunListener != null) {
                                    youDunListener.optionCompareFace(jsonObject);
                                }
                                break;
                            // 实人认证
                            case AuthBuilder.OPTION_VERIFY_COMPARE:
                                Log.d("友盾","实人认证");
                                if (youDunListener != null) {
                                    youDunListener.optionVerifyCompare(jsonObject);
                                }
                                break;
                        }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("友盾","解析错误"+e.toString());
                    if (youDunListener != null) {
                        youDunListener.JSONExceptionError(e);
                    }
                }
            }
        });
        return authBuilder;

    }

    public void setYouDunHelperListerer(YouDunListener youDunHelperListerer) {
        this.youDunListener = youDunHelperListerer;
    }
}
