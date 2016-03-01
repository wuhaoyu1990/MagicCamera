# MagicCamera 

Idea from:android-gpuimage

Real-time Filter Camera&VideoRecorder And ImageEditor With Face Beauty For Android

包含美颜等40余种实时滤镜相机，可拍照、录像、图片修改 

#Log:

2016-01-08

First release

2016-01-14

1.Fix bugs

2.Add Bilateral filte before beauty filter

2016-01-18

1.Fix bugs

2.Add real time face beautify （Based on MeiTu's patent)

3.Add ImageAdjustFilter(Hue,Contrast,Sharpenness,Saturation,Exposure,Brightness)

2016-01-28

1.Try to fix beauty filter by reduce sampling point in low performance GPU and remove bilateral filter temporarily,if it's still lag please set MagicFilterParam.mGPUPower to 0

2016-02-28

Add Crayon Filter and Sketch Filter（Based on MeiTu's patent)

2016-01-01

Add Android Studio Project(not finished)

Add Video Recording Support in Android Studio Project(not finished and have unknow bugs,still lag when take video of 1080P),Based on https://github.com/google/grafika

#Introduction：

a Camera APP which inlcudes：

40 kinds of real-time filters 

Image editor with face beauty(Smooth Skin and White skin)

This project is still under coding and will be updated later,only used eclipse because my computer too poor to run AS T-T

#Click "Star" if this is helpful for you!

#1.相机预览模式
示例见：com.seu.magiccamera.activity.CameraActivity
需要自行创建一个glsurfaceview
GLSurfaceView glSurfaceView = (GLSurfaceView)findViewById(R.id.glsurfaceView);
FrameLayout.LayoutParams params = new LayoutParams(Constants.mScreenWidth, Constants.mScreenHeight);
glSurfaceView.setLayoutParams(params);	
mMagicCameraDisplay = new MagicCameraDisplay(this, glSurfaceView);
通过setFilter(int)来更换滤镜
拍照采用将图片绘制到等大小的framebuffer，并读取像素数据，封装成Bitmap并保存。

#2.照片模式：
示例：com.seu.magiccamera.activity.ImageActivity
方法同相机模式，需要通过setImageBitmap(bitmap)来载入图像。
import com.seu.magicfilter.filter.advance.image
------可调节对比、色调、锐化、曝光、亮度、饱和度6个参数
保存采用将图片绘制到等大小的framebuffer，并读取像素数据，封装成Bitmap并保存。


#3.相机输入：
import com.seu.magicfilter.filter.base.MagicCameraInputFilter
更改GPUImageView中在JNI将YUV转换RGB的模式，减少时间消耗约20-50ms。
通过surfaceTexture与OpenGL ES绑定获取纹理id，实现预览数据与屏幕输出的绑定
若无滤镜选择，将该纹理输出到屏幕
若有滤镜选择，将该纹理输出到FrameBuffer，之后所有的滤镜的绘制纹理为该framebuffertexture。

#4.实时美颜滤镜：
import com.seu.magicfilter.filter.advance.common.MagicBeautyFilter
可与MagicCameraInputFilter合并作为相机输入层，将每个滤镜都加上美颜效果
可设置美颜强度（1-5），方法：setBeautyLevel(int level)
参见：Android平台Camera实时滤镜实现方法探讨(十一)--实时美颜滤镜

#5.其他：
参见：Android平台Camera实时滤镜实现方法探讨(七)--滤镜基本制作方法(一)
import com.seu.magicfilter.filter.advance.common.MagicAmaroFilter

------Instagram中Amaro滤镜

import com.seu.magicfilter.filter.advance.common.MagicAntiqueFilter

------“复古”滤镜

import com.seu.magicfilter.filter.advance.common.MagicBlackCatFilter

------“黑猫”滤镜，增强阴影与色调，颜色加深

import com.seu.magicfilter.filter.advance.common.MagicBrannanFilter

------Instagram中Brannan滤镜

import com.seu.magicfilter.filter.advance.common.MagicBrooklynFilter

------Instagram中Brooklyn滤镜

import com.seu.magicfilter.filter.advance.common.MagicCalmFilter

------“平静”滤镜，偏棕灰

import com.seu.magicfilter.filter.advance.common.MagicCoolFilter

------“冰冷”滤镜，偏蓝

import com.seu.magicfilter.filter.advance.common.MagicEarlyBirdFilter

------Instagram中EarlyBird滤镜

import com.seu.magicfilter.filter.advance.common.MagicEmeraldFilter

------“祖母绿”滤镜

import com.seu.magicfilter.filter.advance.common.MagicEvergreenFilter

------“常青”滤镜

import com.seu.magicfilter.filter.advance.common.MagicFairytaleFilter

------“童话”滤镜

import com.seu.magicfilter.filter.advance.common.MagicFreudFilter

------Instagram中Freud滤镜

import com.seu.magicfilter.filter.advance.common.MagicHealthyFilter

------“健康”滤镜

import com.seu.magicfilter.filter.advance.common.MagicHefeFilter

------Instagram中Hefe滤镜

import com.seu.magicfilter.filter.advance.common.MagicHudsonFilter

------Instagram中Hudson滤镜

import com.seu.magicfilter.filter.advance.common.MagicInkwellFilter

------Instagram中Inkwell滤镜

import com.seu.magicfilter.filter.advance.common.MagicKevinFilter

------Instagram中Kevin滤镜

import com.seu.magicfilter.filter.advance.common.MagicLatteFilter

------“拿铁”滤镜

import com.seu.magicfilter.filter.advance.common.MagicLomoFilter

------Instagram中Lomo滤镜

import com.seu.magicfilter.filter.advance.common.MagicN1977Filter

------Instagram中N1977滤镜

import com.seu.magicfilter.filter.advance.common.MagicNashvilleFilter

------Instagram中Nashville滤镜

import com.seu.magicfilter.filter.advance.common.MagicNostalgiaFilter

------“怀旧”滤镜，偏绿系

import com.seu.magicfilter.filter.advance.common.MagicPixarFilter

------Instagram中Pixar滤镜

import com.seu.magicfilter.filter.advance.common.MagicRiseFilter

------Instagram中Rise滤镜

import com.seu.magicfilter.filter.advance.common.MagicRomanceFilter

------"浪漫"滤镜，粉红系

import com.seu.magicfilter.filter.advance.common.MagicSakuraFilter

------"樱花“滤镜，粉红系

import com.seu.magicfilter.filter.advance.common.MagicSierraFilter

------Instagram中Sierra滤镜

import com.seu.magicfilter.filter.advance.common.MagicSkinWhitenFilter

------”美白“滤镜，增白皮肤

import com.seu.magicfilter.filter.advance.common.MagicSunriseFilter

------”日出“滤镜

import com.seu.magicfilter.filter.advance.common.MagicSunsetFilter

------”日落“滤镜

import com.seu.magicfilter.filter.advance.common.MagicSutroFilter

------Instagram中Sutro滤镜

import com.seu.magicfilter.filter.advance.common.MagicSweetsFilter

------”甜美“滤镜

import com.seu.magicfilter.filter.advance.common.MagicTenderFilter

------”温和“滤镜

import com.seu.magicfilter.filter.advance.common.MagicToasterFilter

------Instagram中Toaster滤镜

import com.seu.magicfilter.filter.advance.common.MagicValenciaFilter

------Instagram中Valencia滤镜

import com.seu.magicfilter.filter.advance.common.MagicWarmFilter

------”温暖“滤镜

import com.seu.magicfilter.filter.advance.common.MagicWhiteCatFilter

------”白猫“滤镜

import com.seu.magicfilter.filter.advance.common.MagicXproIIFilter

------Instagram中XproII滤镜

import com.seu.magicfilter.filter.advance.common.MagicCrayonFilter

------蜡笔画滤镜

import com.seu.magicfilter.filter.advance.common.MagicSketchFilter

------素描滤镜


#6.JNI部分（jni文件夹下）：
包含一个磨皮算法与一个美白算法
参见Android平台Camera实时滤镜实现方法探讨(九)--磨皮算法探讨(一)

#ScreenShot

![alt text](https://github.com/wuhaoyu1990/MagicCamera/blob/master/Screenshot_1.png)![alt text](https://github.com/wuhaoyu1990/MagicCamera/blob/master/Screenshot_2.png)![alt text](https://github.com/wuhaoyu1990/MagicCamera/blob/master/Screenshot_3.png)![alt text](https://github.com/wuhaoyu1990/MagicCamera/blob/master/Screenshot_4.png)
