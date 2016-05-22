#include "bitmap/BitmapOperation.h"
#include "bitmap/Conversion.h"
#define  LOG_TAG    "BitmapOperation"
#define  LOGD(...)  __android_log_print(ANDROID_LOG_DEBUG,LOG_TAG,__VA_ARGS__)
#define  LOGE(...)  __android_log_print(ANDROID_LOG_ERROR,LOG_TAG,__VA_ARGS__)

int32_t BitmapOperation::convertArgbToInt(ARGB argb)
{
	return (argb.alpha << 24) | (argb.red << 16) | (argb.green << 8) | argb.blue;
}

void BitmapOperation::convertIntToArgb(uint32_t pixel, ARGB* argb)
{
	argb->red = ((pixel >> 16) & 0xff);
	argb->green = ((pixel >> 8) & 0xff);
	argb->blue = (pixel & 0xff);
	argb->alpha = (pixel >> 24);
}

/**store java bitmap as JNI data*/ //
jobject BitmapOperation::jniStoreBitmapData(
	JNIEnv * env, jobject obj, jobject bitmap)
{
	//LOGE("reading bitmap info...");
    AndroidBitmapInfo bitmapInfo;
    uint32_t* storedBitmapPixels = NULL;
    int ret;
    if ((ret = AndroidBitmap_getInfo(env, bitmap, &bitmapInfo)) < 0)
	{
    	LOGE("AndroidBitmap_getInfo() failed ! error=%d", ret);
    	return NULL;
	}
    //LOGE("width:%d height:%d stride:%d", bitmapInfo.width, bitmapInfo.height, bitmapInfo.stride);
    if (bitmapInfo.format != ANDROID_BITMAP_FORMAT_RGBA_8888)
	{
    	LOGE("Bitmap format is not RGBA_8888!");
    	return NULL;
	}
    //
    //read pixels of bitmap into native memory :
    //
    void* bitmapPixels;
	if ((ret = AndroidBitmap_lockPixels(env, bitmap, &bitmapPixels)) < 0)
	{
		LOGE("AndroidBitmap_lockPixels() failed ! error=%d", ret);
		return NULL;
	}
	uint32_t* src = (uint32_t*) bitmapPixels;
    storedBitmapPixels = new uint32_t[bitmapInfo.height * bitmapInfo.width];
    int pixelsCount = bitmapInfo.height * bitmapInfo.width;
    memcpy(storedBitmapPixels, src, sizeof(uint32_t) * pixelsCount);
    AndroidBitmap_unlockPixels(env, bitmap);
    JniBitmap *jniBitmap = new JniBitmap();
    jniBitmap->_bitmapInfo = bitmapInfo;
    jniBitmap->_storedBitmapPixels = storedBitmapPixels;
    //LOGE("return NewDirectByteBuffer");
    return env->NewDirectByteBuffer(jniBitmap, 0);
}

/**free bitmap*/ //
void BitmapOperation::jniFreeBitmapData(
	JNIEnv * env, jobject obj, jobject handle)
{
    JniBitmap* jniBitmap = (JniBitmap*) env->GetDirectBufferAddress(handle);
    if (jniBitmap->_storedBitmapPixels == NULL)
    	return;
    delete[] jniBitmap->_storedBitmapPixels;
    jniBitmap->_storedBitmapPixels = NULL;
    delete jniBitmap;
}

/**restore java bitmap (from JNI data)*/ //
jobject BitmapOperation::jniGetBitmapFromStoredBitmapData(
	JNIEnv * env, jobject obj, jobject handle)
{
    JniBitmap* jniBitmap = (JniBitmap*) env->GetDirectBufferAddress(handle);
    if (jniBitmap->_storedBitmapPixels == NULL)
	{
    	LOGD("no bitmap data was stored. returning null...");
    	return NULL;
	}
    //
    //creating a new bitmap to put the pixels into it - using Bitmap Bitmap.createBitmap (int width, int height, Bitmap.Config config) :
    //
    jclass bitmapCls = env->FindClass("android/graphics/Bitmap");
    jmethodID createBitmapFunction = env->GetStaticMethodID(bitmapCls,
	    "createBitmap",
	    "(IILandroid/graphics/Bitmap$Config;)Landroid/graphics/Bitmap;");
    jstring configName = env->NewStringUTF("ARGB_8888");
    jclass bitmapConfigClass = env->FindClass("android/graphics/Bitmap$Config");
    jmethodID valueOfBitmapConfigFunction = env->GetStaticMethodID(
	    bitmapConfigClass, "valueOf",
	    "(Ljava/lang/String;)Landroid/graphics/Bitmap$Config;");
    jobject bitmapConfig = env->CallStaticObjectMethod(bitmapConfigClass,
	    valueOfBitmapConfigFunction, configName);
    jobject newBitmap = env->CallStaticObjectMethod(bitmapCls,
	    createBitmapFunction, jniBitmap->_bitmapInfo.width,
	    jniBitmap->_bitmapInfo.height, bitmapConfig);
    //
    // putting the pixels into the new bitmap:
    //
    int ret;
    void* bitmapPixels;
    if ((ret = AndroidBitmap_lockPixels(env, newBitmap, &bitmapPixels)) < 0)
	{
    	LOGE("AndroidBitmap_lockPixels() failed ! error=%d", ret);
    	return NULL;
	}
    uint32_t* newBitmapPixels = (uint32_t*) bitmapPixels;
    int pixelsCount = jniBitmap->_bitmapInfo.height
	    * jniBitmap->_bitmapInfo.width;
    memcpy(newBitmapPixels, jniBitmap->_storedBitmapPixels,
	    sizeof(uint32_t) * pixelsCount);
    AndroidBitmap_unlockPixels(env, newBitmap);
    //LOGD("returning the new bitmap");
    return newBitmap;
}
