#ifndef _MAGIC_BEAUTIFY_H_
#define _MAGIC_BEAUTIFY_H_

#include "../bitmap/JniBitmap.h"

class MagicBeautify
{
public:
	void initMagicBeautify(JniBitmap* jniBitmap);
	void unInitMagicBeautify();

    void startSkinSmooth(float smoothlevel);
    void startWhiteSkin(float whitenlevel);

    static MagicBeautify* getInstance();
    ~MagicBeautify();

private:
    static MagicBeautify * instance;
    MagicBeautify();

    uint64_t *mIntegralMatrix;
	uint64_t *mIntegralMatrixSqr;

	uint32_t *storedBitmapPixels;
	uint32_t *mImageData_rgb;

	uint8_t *mImageData_yuv;
	uint8_t *mSkinMatrix;

	int mImageWidth;
	int mImageHeight;
	float mSmoothLevel;
	float mWhitenLevel;
	
	void initIntegral();
	
	void initSkinMatrix();

	void _startBeauty(float smoothlevel, float whitenlevel);
	void _startSkinSmooth(float smoothlevel);
	void _startWhiteSkin(float whitenlevel);
};
#endif
