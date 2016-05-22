#ifndef _BITMAP_OPERATION_H_
#define _BITMAP_OPERATION_H_

#include <jni.h>
#include <android/log.h>
#include <stdio.h>
#include <android/bitmap.h>
#include <cstring>
#include <unistd.h>
#include <bitmap/JniBitmap.h>

class BitmapOperation
{
public:

	static int32_t convertArgbToInt(ARGB argb);
	static void convertIntToArgb(uint32_t pixel, ARGB* argb);

	static jobject jniStoreBitmapData(
		JNIEnv * env, jobject obj, jobject bitmap);
	static void jniFreeBitmapData(
		JNIEnv * env, jobject obj, jobject handle);
	static jobject jniGetBitmapFromStoredBitmapData(
		JNIEnv * env, jobject obj, jobject handle);
};
#endif
