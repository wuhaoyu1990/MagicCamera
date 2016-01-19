#include <jni.h>
#include <android/log.h>
#include <stdio.h>
#include <android/bitmap.h>
#include <cstring>
#include <unistd.h>
#include "bitmap/BitmapOperation.h"
#include "skinsmooth/MagicBeauty.h"

#define  LOG_TAG    "MagicSDK"
#define  LOGD(...)  __android_log_print(ANDROID_LOG_DEBUG,LOG_TAG,__VA_ARGS__)
#define  LOGE(...)  __android_log_print(ANDROID_LOG_ERROR,LOG_TAG,__VA_ARGS__)

#ifdef __cplusplus
extern "C" {
#endif

JNIEXPORT void JNICALL Java_com_seu_magicfilter_utils_MagicSDK_jniInitMagicBeauty(
	JNIEnv * env, jobject obj, jobject handle){
	JniBitmap* jniBitmap = (JniBitmap*) env->GetDirectBufferAddress(handle);
	if (jniBitmap->_storedBitmapPixels == NULL){
		LOGE("no bitmap data was stored. returning null...");
		return;
	}
	MagicBeauty::getInstance()->initMagicBeauty(jniBitmap);
}

JNIEXPORT void JNICALL Java_com_seu_magicfilter_utils_MagicSDK_jniStartWhiteSkin(
	JNIEnv * env, jobject obj, jfloat whiteLevel){
	MagicBeauty::getInstance()->startWhiteSkin(whiteLevel);
}

JNIEXPORT void JNICALL Java_com_seu_magicfilter_utils_MagicSDK_jniStartSkinSmooth(
	JNIEnv * env, jobject obj, jfloat DenoiseLevel){
	float sigema = 10 + DenoiseLevel * DenoiseLevel * 5;
	MagicBeauty::getInstance()->startSkinSmooth(sigema);
}

JNIEXPORT void JNICALL Java_com_seu_magicfilter_utils_MagicSDK_jniUninitMagicBeauty(
	JNIEnv * env, jobject obj){
	MagicBeauty::getInstance()->unInitMagicBeauty();
}

JNIEXPORT jobject JNICALL Java_com_seu_magicfilter_utils_MagicSDK_jniStoreBitmapData(
	JNIEnv * env, jobject obj, jobject bitmap){
	return BitmapOperation::jniStoreBitmapData(env, obj, bitmap);
}

/**free bitmap*/
JNIEXPORT void JNICALL Java_com_seu_magicfilter_utils_MagicSDK_jniFreeBitmapData(
	JNIEnv * env, jobject obj, jobject handle){
	BitmapOperation::jniFreeBitmapData(env, obj, handle);
}

/**restore java bitmap (from JNI data)*/ //
JNIEXPORT jobject JNICALL Java_com_seu_magicfilter_utils_MagicSDK_jniGetBitmapFromStoredBitmapData(
	JNIEnv * env, jobject obj, jobject handle){
	return BitmapOperation::jniGetBitmapFromStoredBitmapData(env, obj, handle);
}
#ifdef __cplusplus
}
#endif
