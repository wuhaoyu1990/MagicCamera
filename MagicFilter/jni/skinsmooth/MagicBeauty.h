#ifndef _MAGIC_BEAUTY_H_
#define _MAGIC_BEAUTY_H_

#include <stdio.h>
#include <cstring>
#include <unistd.h>
#include "bitmap/JniBitmap.h"


class MagicBeauty
{
public:
	void initMagicBeauty(JniBitmap* jniBitmap);
	void unInitMagicBeauty();
	void initSkinSmooth();
    void startSkinSmooth(float sigema);
    void endSkinSmooth();

    void initWhiteSkin();
    void startWhiteSkin(float beta);

    static MagicBeauty* getInstance();
    ~MagicBeauty();

private:
    static MagicBeauty * instance;
    MagicBeauty();

    uint64_t *mIntegralMatrix;
	uint64_t *mIntegralMatrixSqr;

	uint32_t *mImageData_rgb;

	uint8_t *mImageData_yuv;
	uint8_t *mImageData_yuv_y;
	uint8_t *mSkinMatrix;

	int mImageWidth;
	int mImageHeight;

	//创建积分图
	void initIntegral(uint8_t* inputMatrix);

	//创建肤色区域
	void initSkinMatrix();

	void restore();
};
#endif
