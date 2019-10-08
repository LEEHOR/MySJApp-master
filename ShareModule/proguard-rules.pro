# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
#
##############################################
##
## 对于一些基本指令的添加
##
##############################################
## 代码混淆压缩比，在0~7之间，默认为5，一般不做修改
#-optimizationpasses 5
#
## 混合时不使用大小写混合，混合后的类名为小写
#-dontusemixedcaseclassnames
#
## 指定不去忽略非公共库的类
#-dontskipnonpubliclibraryclasses
#
## 这句话能够使我们的项目混淆后产生映射文件
## 包含有类名->混淆后类名的映射关系
#-verbose
#
## 指定不去忽略非公共库的类成员
#-dontskipnonpubliclibraryclassmembers
#
## 不做预校验，preverify是proguard的四个步骤之一，Android不需要preverify，去掉这一步能够加快混淆速度。
#-dontpreverify
#
## 保留Annotation不混淆
#-keepattributes *Annotation*,InnerClasses
#
## 避免混淆泛型
#-keepattributes Signature
#
## 抛出异常时保留代码行号
#-keepattributes SourceFile,LineNumberTable
#
## 保留我们使用的四大组件，自定义的Application等等这些类不被混淆
## 因为这些子类都有可能被外部调用
#-keep public class * extends android.app.Activity
#-keep public class * extends android.app.Appliction
#-keep public class * extends android.app.Service
#-keep public class * extends android.content.BroadcastReceiver
#-keep public class * extends android.content.ContentProvider
#-keep public class * extends android.app.backup.BackupAgentHelper
#-keep public class * extends android.preference.Preference
#-keep public class * extends android.view.View
#-keep public class com.android.vending.licensing.ILicensingService
#
##如果使用了androidX混淆
#-keep class com.google.android.material.** {*;}
#-keep class androidx.** {*;}
#-keep public class * extends androidx.**
#-keep interface androidx.** {*;}
#-dontwarn com.google.android.material.**
#-dontnote com.google.android.material.**
#-dontwarn androidx.**
#
## 保留support下的所有类及其内部类
#-keep class android.support.** {*;}
#
##support.v4
#-dontwarn android.support.v4.**
#-keep class android.support.v4.app.** { *; }
#-keep interface android.support.v4.app.** { *; }
#-keep class android.support.v4.** { *; }
##support.v7
#-dontwarn android.support.v7.**
#-keep class android.support.v7.internal.** { *; }
#-keep interface android.support.v7.internal.** { *; }
#-keep class android.support.v7.** { *; }
##support.design
#-dontwarn android.support.design.**
#-keep class android.support.design.** { *; }
#-keep interface android.support.design.** { *; }
#-keep public class android.support.design.R$* { *; }
#
## 保留继承的
#-keep public class * extends android.support.v4.**
#-keep public class * extends android.support.v7.**
#-keep public class * extends android.support.annotation.**

# 保留R下面的资源
-keep class **.R$* {*;}

## 保留本地native方法不被混淆
#-keepclasseswithmembernames class * {
#    native <methods>;
#}

## 保留枚举类不被混淆
#-keepclassmembers enum * {
#    public static **[] values();
#    public static ** valueOf(java.lang.String);
#}

# 保留在Activity中的方法参数是view的方法，
# 这样以来我们在layout中写的onClick就不会被影响
#-keepclassmembers class * extends android.app.Activity{
#    public void *(android.view.View);
#}
# 保留Parcelable序列化类不被混淆
#-keep class * implements android.os.Parcelable {
#    public static final android.os.Parcelable$Creator *;
#}

## 保留Serializable序列化的类不被混淆
#-keepclassmembers class * implements java.io.Serializable {
#    static final long serialVersionUID;
#    private static final java.io.ObjectStreamField[] serialPersistentFields;
#    !static !transient <fields>;
#    !private <fields>;
#    !private <methods>;
#    private void writeObject(java.io.ObjectOutputStream);
#    private void readObject(java.io.ObjectInputStream);
#    java.lang.Object writeReplace();
#    java.lang.Object readResolve();
#}
#
##对于带有回调函数的onXXEvent、**On*Listener的，不能被混淆
#-keepclassmembers class * {
#    void *(**On*Event);
#    void *(**On*Listener);
#}

##-----------处理实体类---------------
#-keepattributes Signature
## For using GSON @Expose annotation
#-keepattributes *Annotation*
#-keepattributes EnclosingMethod
## Gson specific classes
#-keep class sun.misc.Unsafe { *; }
#-keep class com.google.gson.stream.** { *; }
## Application classes that will be serialized/deserialized over Gson
#-keep class com.google.gson.examples.android.model.** { *; }
#-keepattributes Signature
## 在开发的时候我们可以将所有的实体类放在一个包内，这样我们写一次混淆就行了。
#-keep public class com.leehor.simple.lightPay.entity.** {*;}
#
## 微信支付和分享
#-dontwarn com.tencent.mm.**
#-dontwarn com.tencent.wxop.stat.**
#-keep class com.tencent.mm.** {*;}
#-keep class com.tencent.wxop.stat.**{*;}
#
##QQ支付和分享
#-dontwarn com.tencent.connect.**
#-dontwarn com.tencent.open.**
#-dontwarn com.tencent.tauth.**
#-keep class com.tencent.connect.**{*;}
#-keep class com.tencent.open.**{*;}
#-keep class com.tencent.tauth.**{*;}
#
##支付宝
#-keep class com.alipay.android.app.IAlixPay{*;}
#-keep class com.alipay.android.app.IAlixPay$Stub{*;}
#-keep class com.alipay.android.app.IRemoteServiceCallback{*;}
#-keep class com.alipay.android.app.IRemoteServiceCallback$Stub{*;}
#-keep class com.alipay.sdk.app.PayTask{ public *;}
#-keep class com.alipay.sdk.app.AuthTask{ public *;}
#-keep class com.alipay.sdk.app.H5PayCallback {
#    <fields>;
#    <methods>;
#}
#-keep class com.alipay.android.phone.mrpc.core.** { *; }
#-keep class com.alipay.apmobilesecuritysdk.** { *; }
#-keep class com.alipay.mobile.framework.service.annotation.** { *; }
#-keep class com.alipay.mobilesecuritysdk.face.** { *; }
#-keep class com.alipay.tscenter.biz.rpc.** { *; }
#-keep class org.json.alipay.** { *; }
#-keep class com.alipay.tscenter.** { *; }
#-keep class com.ta.utdid2.** { *;}
#-keep class com.ut.device.** { *;}
#
#
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

