
-keep public class com.leehor.simple.lightPay.entity.** {*;}
# 微信支付和分享
-dontwarn com.tencent.mm.**
-dontwarn com.tencent.wxop.stat.**
-keep class com.tencent.mm.** {*;}
-keep class com.tencent.wxop.stat.**{*;}

#QQ支付和分享
-dontwarn com.tencent.connect.**
-dontwarn com.tencent.open.**
-dontwarn com.tencent.tauth.**
-keep class com.tencent.connect.**{*;}
-keep class com.tencent.open.**{*;}
-keep class com.tencent.tauth.**{*;}

#支付宝
-keep class com.alipay.android.app.IAlixPay{*;}
-keep class com.alipay.android.app.IAlixPay$Stub{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback$Stub{*;}
-keep class com.alipay.sdk.app.PayTask{ public *;}
-keep class com.alipay.sdk.app.AuthTask{ public *;}
-keep class com.alipay.sdk.app.H5PayCallback {
    <fields>;
    <methods>;
}
-keep class com.alipay.android.phone.mrpc.core.** { *; }
-keep class com.alipay.apmobilesecuritysdk.** { *; }
-keep class com.alipay.mobile.framework.service.annotation.** { *; }
-keep class com.alipay.mobilesecuritysdk.face.** { *; }
-keep class com.alipay.tscenter.biz.rpc.** { *; }
-keep class org.json.alipay.** { *; }
-keep class com.alipay.tscenter.** { *; }
-keep class com.ta.utdid2.** { *;}
-keep class com.ut.device.** { *;}


##有盾
##gson
#-keep class com.lianlian.** { *; }
#-keep class com.google.gson.examples.android.model.** { *; }
#
#-keepparameternames
#-keepattributes Exceptions,InnerClasses,Signature,Deprecated,SourceFile,LineNumberTable,*Annotation*,EnclosingMethod
#
#-dontwarn cn.fraudmetrix.android.**
#-dontwarn cn.com.bsfit.volley.toolbox.**
#-dontwarn org.apache.http.**
#-dontwarn android.net.http.AndroidHttpClient
#
#-keep class com.udcredit.** {*;}
#-keep class cn.com.bsfit.** {*;}
#-keep class io.card.** {*;}
#-keep class com.android.snetjob** {*;}
#-keep class com.face.** {*;}
#-keep class com.hotvision.** {*;}
#-keep class com.android.volley.**{*;}
#-keep class com.authreal.api.** {*;}
#-keep class com.authreal.component.** {*;}
#-keep class com.authreal.module.** {*;}
#-keep class com.authreal.util.ErrorCode {*;}
#-keep class com.authreal.util.FVSdk {*;}
#
#-keep public class com.udcredit.android.fingerprint.UDCREDIT {*;}
#-keep public class com.udcredit.android.fingerprint.FingerCallBack {*;}
#-keep public class com.udcredit.android.function.FingerPrintData {*;}
#-keep public class com.udcredit.android.entity.FingerprintException {*;}
#-keep public class com.udcredit.android.controller.FingerprintUpdateReceiver {*;}
#-keep public class com.udcredit.android.tool.UDHttpsTrustManager {*; }
#-keep public class com.udcredit.android.collection.EnvValidate {
#   private boolean isEmulator1();
#}