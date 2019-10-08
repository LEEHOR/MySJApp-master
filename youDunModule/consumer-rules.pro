#有盾
#gson
-keep class com.lianlian.** { *; }
-keep class com.google.gson.examples.android.model.** { *; }

-keepparameternames
-keepattributes Exceptions,InnerClasses,Signature,Deprecated,SourceFile,LineNumberTable,*Annotation*,EnclosingMethod

-dontwarn cn.fraudmetrix.android.**
-dontwarn cn.com.bsfit.volley.toolbox.**
-dontwarn org.apache.http.**
-dontwarn android.net.http.AndroidHttpClient

-keep class com.udcredit.** {*;}
-keep class cn.com.bsfit.** {*;}
-keep class io.card.** {*;}
-keep class com.android.snetjob** {*;}
-keep class com.face.** {*;}
-keep class com.hotvision.** {*;}
-keep class com.android.volley.**{*;}
-keep class com.authreal.api.** {*;}
-keep class com.authreal.component.** {*;}
-keep class com.authreal.module.** {*;}
-keep class com.authreal.util.ErrorCode {*;}
-keep class com.authreal.util.FVSdk {*;}

-keep public class com.udcredit.android.fingerprint.UDCREDIT {*;}
-keep public class com.udcredit.android.fingerprint.FingerCallBack {*;}
-keep public class com.udcredit.android.function.FingerPrintData {*;}
-keep public class com.udcredit.android.entity.FingerprintException {*;}
-keep public class com.udcredit.android.controller.FingerprintUpdateReceiver {*;}
-keep public class com.udcredit.android.tool.UDHttpsTrustManager {*; }
-keep public class com.udcredit.android.collection.EnvValidate {
   private boolean isEmulator1();
}