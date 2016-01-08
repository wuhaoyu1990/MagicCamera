LOCAL_PATH := $(call my-dir)

#MagicSDK module
include $(CLEAR_VARS)

LOCAL_MODULE    := MagicSDK
LOCAL_SRC_FILES := MagicSDK.cpp \
				bitmap/BitmapOperation.cpp \
				bitmap/Conversion.cpp \
				skinsmooth/MagicBeauty.cpp 
				
LOCAL_LDLIBS := -llog 

LOCAL_LDFLAGS += -ljnigraphics

APP_STL:= stlport_static

include $(BUILD_SHARED_LIBRARY)

#if you need to add more module, do the same as the one we started with (the one with the CLEAR_VARS)
