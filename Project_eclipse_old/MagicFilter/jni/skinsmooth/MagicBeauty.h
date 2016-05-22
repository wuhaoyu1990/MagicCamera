#ifndef _MAGIC_BEAUTY_H_
#define _MAGIC_BEAUTY_H_

#include "bitmap/JniBitmap.h"

class MagicBeauty
{
public:
	void initMagicBeauty(JniBitmap* jniBitmap);
	void unInitMagicBeauty();

    void startSkinSmooth(float smoothlevel);
    void startWhiteSkin(float whitenlevel);

    static MagicBeauty* getInstance();
    ~MagicBeauty();

private:
    static MagicBeauty * instance;
    MagicBeauty();

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

	//创建积分图
	void initIntegral();

	//创建肤色区域
	void initSkinMatrix();

	void _startBeauty(float smoothlevel, float whitenlevel);
	void _startSkinSmooth(float smoothlevel);
	void _startWhiteSkin(float whitenlevel);
};
#endif
