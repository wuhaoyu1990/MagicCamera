#ifndef _JNIBITMAP_H_
#define _JNIBITMAP_H_
#include <android/bitmap.h>

typedef struct
{
	uint8_t alpha, red, green, blue;
} ARGB;

class JniBitmap
{
public:
    uint32_t* _storedBitmapPixels;
    AndroidBitmapInfo _bitmapInfo;
    JniBitmap()
	{
    	_storedBitmapPixels = NULL;
	}
};
#endif
