#include "MagicBeauty.h"
#include "Math.h"
#include "bitmap/BitmapOperation.h"
#include "bitmap/Conversion.h"

#define  LOG_TAG    "MagicBeauty"
#define  LOGD(...)  __android_log_print(ANDROID_LOG_DEBUG,LOG_TAG,__VA_ARGS__)
#define  LOGE(...)  __android_log_print(ANDROID_LOG_ERROR,LOG_TAG,__VA_ARGS__)

#define div255(x) (x * 0.003921F)
#define abs(x) (x>=0 ? x:(-x))

MagicBeauty* MagicBeauty::instance;

MagicBeauty* MagicBeauty::getInstance()
{
	if (instance == NULL)
		instance = new MagicBeauty();
	return instance;
}

MagicBeauty::MagicBeauty()
{
	mIntegralMatrix = NULL;
	mIntegralMatrixSqr = NULL;
	mImageData_yuv = NULL;
	mImageData_yuv_y = NULL;
	mSkinMatrix = NULL;
	mImageData_rgb = NULL;
}

MagicBeauty::~MagicBeauty()
{
	instance = NULL;
}

void MagicBeauty::initMagicBeauty(JniBitmap* jniBitmap){
	unInitMagicBeauty();

	mImageData_rgb = jniBitmap->_storedBitmapPixels;
	mImageWidth = jniBitmap->_bitmapInfo.width;
	mImageHeight = jniBitmap->_bitmapInfo.height;

	initSkinMatrix();
}

void MagicBeauty::unInitMagicBeauty(){
	LOGE("unInitMagicBeauty");
	if(mIntegralMatrix != NULL)
		delete[] mIntegralMatrix;
	if(mIntegralMatrixSqr != NULL)
		delete[] mIntegralMatrixSqr;
	if(mImageData_yuv != NULL)
		delete[] mImageData_yuv;
	if(mImageData_yuv_y != NULL)
		delete[] mImageData_yuv_y;
	if(mSkinMatrix != NULL)
		delete[] mSkinMatrix;
}

void MagicBeauty::endSkinSmooth(){
	Conversion::YCbCrToRGB(mImageData_yuv, (uint8_t*)mImageData_rgb,
					mImageWidth * mImageHeight);
}

void MagicBeauty::restore(){

}

void MagicBeauty::startSkinSmooth(float sigema){
	if(mIntegralMatrix == NULL || mIntegralMatrixSqr == NULL ||
			mImageData_yuv_y == NULL || mSkinMatrix == NULL || mImageData_yuv == NULL){
		LOGE("not init correctly");
		return;
	}
	int radius = mImageWidth > mImageHeight ? mImageWidth * 0.02 : mImageHeight * 0.02;

	for(int i = 1; i < mImageHeight; i++){
		for(int j = 1; j < mImageWidth; j++){
			int offset = i * mImageWidth + j;
			if(mSkinMatrix[offset] == 255){
				int iMax = i + radius >= mImageHeight-1 ? mImageHeight-1 : i + radius;
				int jMax = j + radius >= mImageWidth-1 ? mImageWidth-1 :j + radius;
				int iMin = i - radius <= 1 ? 1 : i - radius;
				int jMin = j - radius <= 1 ? 1 : j - radius;

				int squar = (iMax - iMin + 1)*(jMax - jMin + 1);
				int i4 = iMax*mImageWidth+jMax;
				int i3 = (iMin-1)*mImageWidth+(jMin-1);
				int i2 = iMax*mImageWidth+(jMin-1);
				int i1 = (iMin-1)*mImageWidth+jMax;

				float m = (mIntegralMatrix[i4]
						+ mIntegralMatrix[i3]
						- mIntegralMatrix[i2]
						- mIntegralMatrix[i1]) / squar;

				float v = (mIntegralMatrixSqr[i4]
						+ mIntegralMatrixSqr[i3]
						- mIntegralMatrixSqr[i2]
						- mIntegralMatrixSqr[i1]) / squar - m*m;
				float k = v / (v + sigema);

				mImageData_yuv[offset * 3] = ceil(m - k * m + k * mImageData_yuv_y[offset]);
			}
		}
	}
	endSkinSmooth();
}

void MagicBeauty::startWhiteSkin(float beta){
	if(beta > 1.0){
		float a = log(beta);
		for(int i = 0; i < mImageHeight; i++){
			for(int j = 0; j < mImageWidth; j++){
				int offset = i*mImageWidth+j;
				ARGB RGB;
				BitmapOperation::convertIntToArgb(mImageData_rgb[offset],&RGB);
				RGB.red = 255 * (log(div255(RGB.red) * (beta - 1) + 1) / a);
				RGB.green = 255 * (log(div255(RGB.green) * (beta - 1) + 1) / a);
				RGB.blue = 255 * (log(div255(RGB.blue) * (beta - 1) + 1) / a);
				mImageData_rgb[offset] = BitmapOperation::convertArgbToInt(RGB);
			}
		}
	}
}

void MagicBeauty::initSkinMatrix(){
	if(mSkinMatrix == NULL)
		mSkinMatrix = new uint8_t[mImageWidth * mImageHeight];
	for(int i = 0; i < mImageHeight; i++){
		for(int j = 0; j < mImageWidth; j++){
			int offset = i*mImageWidth+j;
			ARGB RGB;
			BitmapOperation::convertIntToArgb(mImageData_rgb[offset],&RGB);
			if ((RGB.blue>95 && RGB.green>40 && RGB.red>20 &&
					RGB.blue-RGB.red>15 && RGB.blue-RGB.green>15)||
					(RGB.blue>200 && RGB.green>210 && RGB.red>170 &&
					abs(RGB.blue-RGB.red)<=15 && RGB.blue>RGB.red&& RGB.green>RGB.red))
				mSkinMatrix[offset] = 255;
			else
				mSkinMatrix[offset] = 0;
		}
	}
}

void MagicBeauty::initWhiteSkin(){
	Conversion::YCbCrToRGB(mImageData_yuv, (uint8_t*)mImageData_rgb,
						mImageWidth * mImageHeight);
}

void MagicBeauty::initSkinSmooth(){
	if(mImageData_rgb == NULL){
		LOGE("bitmap is freed");
		return;
	}
	mImageData_yuv = new uint8_t[mImageWidth * mImageHeight * 3];
	mImageData_yuv_y = new uint8_t[mImageWidth * mImageHeight];
	Conversion::RGBToYCbCr((uint8_t*)mImageData_rgb, mImageData_yuv, mImageWidth * mImageHeight);

	for(int i = 0; i < mImageWidth * mImageHeight; i++){
		mImageData_yuv_y[i] = mImageData_yuv[i * 3];
	}
	initIntegral(mImageData_yuv_y);
}

void MagicBeauty::initIntegral(uint8_t* inputMatrix){
	if(mIntegralMatrix == NULL)
		mIntegralMatrix = new uint64_t[mImageWidth * mImageHeight];
	if(mIntegralMatrixSqr == NULL)
		mIntegralMatrixSqr = new uint64_t[mImageWidth * mImageHeight];

	uint64_t *columnSum = new uint64_t[mImageWidth];
	uint64_t *columnSumSqr = new uint64_t[mImageWidth];

	columnSum[0] = inputMatrix[0];
	columnSumSqr[0] = inputMatrix[0] * inputMatrix[0];

	mIntegralMatrix[0] = columnSum[0];
	mIntegralMatrixSqr[0] = columnSumSqr[0];

    for(int i = 1;i < mImageWidth;i++){

    	columnSum[i] = inputMatrix[i];
    	columnSumSqr[i] = inputMatrix[i] * inputMatrix[i];

    	mIntegralMatrix[i] = columnSum[i];
    	mIntegralMatrix[i] += mIntegralMatrix[i-1];
    	mIntegralMatrixSqr[i] = columnSumSqr[i];
    	mIntegralMatrixSqr[i] += mIntegralMatrixSqr[i-1];
    }
    for (int i = 1;i < mImageHeight; i++){
        int offset = i * mImageWidth;

        columnSum[0] += inputMatrix[offset];
        columnSumSqr[0] += inputMatrix[offset] * inputMatrix[offset];

        mIntegralMatrix[offset] = columnSum[0];
        mIntegralMatrixSqr[offset] = columnSumSqr[0];
         // other columns
        for(int j = 1; j < mImageWidth; j++){
        	columnSum[j] += inputMatrix[offset+j];
        	columnSumSqr[j] += inputMatrix[offset+j] * inputMatrix[offset+j];

        	mIntegralMatrix[offset+j] = mIntegralMatrix[offset+j-1] + columnSum[j];
        	mIntegralMatrixSqr[offset+j] = mIntegralMatrixSqr[offset+j-1] + columnSumSqr[j];
        }
    }
    delete[] columnSum;
    delete[] columnSumSqr;
}

