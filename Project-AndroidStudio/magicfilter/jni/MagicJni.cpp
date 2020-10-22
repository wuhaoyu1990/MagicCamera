#include <jni.h>
#include <android/log.h>
#include <stdio.h>
#include "bitmap/BitmapOperation.h"
#include "beautify/MagicBeautify.h"

#define  LOG_TAG    "MagicJni"
#define  LOGD(...)  __android_log_print(ANDROID_LOG_DEBUG,LOG_TAG,__VA_ARGS__)
#define  LOGE(...)  __android_log_print(ANDROID_LOG_ERROR,LOG_TAG,__VA_ARGS__)

#ifdef __cplusplus
extern "C" {
#endif

JNIEXPORT void JNICALL
Java_com_seu_magicfilter_beautify_MagicJni_jniInitMagicBeautify(JNIEnv *env, jobject instance,
                                                                jobject handler) {
    JniBitmap* jniBitmap = (JniBitmap*) env->GetDirectBufferAddress(handler);
    if (jniBitmap->_storedBitmapPixels == NULL){
        LOGE("no bitmap data was stored. returning null...");
        return;
    }
    MagicBeautify::getInstance()->initMagicBeautify(jniBitmap);
}

JNIEXPORT void JNICALL
Java_com_seu_magicfilter_beautify_MagicJni_jniStartWhiteSkin(JNIEnv *env, jobject instance,
                                                             jfloat whiteLevel){
    MagicBeautify::getInstance()->startWhiteSkin(whiteLevel);
}

JNIEXPORT void JNICALL
Java_com_seu_magicfilter_beautify_MagicJni_jniStartSkinSmooth(JNIEnv *env, jobject instance,
                                                              jobject obj, jfloat DenoiseLevel){
    float sigema = 10 + DenoiseLevel * DenoiseLevel * 5;
    MagicBeautify::getInstance()->startSkinSmooth(sigema);
}

JNIEXPORT void JNICALL
Java_com_seu_magicfilter_beautify_MagicJni_jniUnInitMagicBeautify(JNIEnv *env, jobject instance){
    MagicBeautify::getInstance()->unInitMagicBeautify();
}

JNIEXPORT jobject JNICALL
Java_com_seu_magicfilter_beautify_MagicJni_jniStoreBitmapData(JNIEnv *env, jobject instance,
                                                              jobject bitmap){
    return BitmapOperation::jniStoreBitmapData(env, instance, bitmap);
}

JNIEXPORT void JNICALL
Java_com_seu_magicfilter_beautify_MagicJni_jniFreeBitmapData(JNIEnv *env, jobject instance,
                                                             jobject handle){
    BitmapOperation::jniFreeBitmapData(env, instance, handle);
}

JNIEXPORT jobject JNICALL
Java_com_seu_magicfilter_beautify_MagicJni_jniGetBitmapFromStoredBitmapData(JNIEnv *env, jobject instance,
                                                                            jobject handle){
    return BitmapOperation::jniGetBitmapFromStoredBitmapData(env, instance, handle);
}
#ifdef __cplusplus
}
#endif
