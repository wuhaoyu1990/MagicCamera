package com.seu.magicfilter.utils;

import java.nio.ByteBuffer;
import android.graphics.Bitmap;
import android.os.Handler;
import android.util.Log;

public class MagicSDK {
	private Handler mHandler = null;
	
	public static final int MESSAGE_OPERATION_END = 0;
	
	private static ByteBuffer _bitmapHandler = null;
	
	private MagicSDKListener mMagicSDKListeners;
	
	private boolean mIsMagicBeautyInit = false;
	
	public void setMagicSDKListener(MagicSDKListener l){
		mMagicSDKListeners = l;
	}
	
	static{
		System.loadLibrary("MagicSDK");
	}
	
	private static MagicSDK mMagicSDK = null;  
	
	private MagicSDK() {}
	
	public static MagicSDK getInstance(){
		if(mMagicSDK == null)
			mMagicSDK = new MagicSDK();
		return mMagicSDK;
	}
	
	public void setMagicSDKHandler(Handler handler){
		if(mHandler == null)
			this.mHandler = handler;
	}
	
	public void onStartSkinSmooth(float level){
		if(_bitmapHandler == null)
			return;
		if(level > 10 || level < 0){
			Log.e("MagicSDK","Skin Smooth level must in [0,10]");
			return;
		}
		jniStartSkinSmooth(level);
		mHandler.sendEmptyMessage(MESSAGE_OPERATION_END);
		if(mMagicSDKListeners != null)
			mMagicSDKListeners.onEnd();
	}
	
	public void onStartWhiteSkin(float level){
		if(_bitmapHandler == null)
			return;
		if(level > 5 || level < 0){
			Log.e("MagicSDK","Skin white level must in [1,5]");
			return;
		}
		jniStartWhiteSkin(level);
		mHandler.sendEmptyMessage(MESSAGE_OPERATION_END);
		if(mMagicSDKListeners != null)
			mMagicSDKListeners.onEnd();
	}
	
	public void initMagicBeauty(){
		if(_bitmapHandler == null){
			Log.e("MagicSDK","please storeBitmap first!!");
			return;
		}
		jniInitMagicBeauty(_bitmapHandler);
	}
	
	public void uninitMagicBeauty(){
		jniUninitMagicBeauty();
	}
	
	public void storeBitmap(Bitmap bitmap, boolean recyle){
		if(_bitmapHandler != null)
			freeBitmap();
		_bitmapHandler = jniStoreBitmapData(bitmap);
		if(recyle)
			bitmap.recycle();
	}
	
	public void freeBitmap(){
		if(_bitmapHandler == null)
			return;
		jniFreeBitmapData(_bitmapHandler);
		_bitmapHandler = null;
    }
	
	public Bitmap getBitmap(){
		if(_bitmapHandler == null)
			return null;
		return jniGetBitmapFromStoredBitmapData(_bitmapHandler);
    }
	
	public ByteBuffer getHandler(){
		return _bitmapHandler;
	}
	
	public Bitmap getBitmapAndFree(){
		final Bitmap bitmap = getBitmap();
		freeBitmap();
		return bitmap;
    }
	
	public void onDestroy() {
		freeBitmap();
		uninitMagicBeauty();
		mMagicSDK = null;
	}
	
	@Override
	protected void finalize() throws Throwable{
		super.finalize();
	    if(_bitmapHandler == null)
	    	return;
	    Log.w("MagicSDK","JNI bitmap wasn't freed nicely.please remember to free the bitmap as soon as you can");
	    freeBitmap();
	    Log.w("MagicSDK","MagicSDK wasn't uninit nicely.please remember to uninit");
	    uninitMagicBeauty();
	}
	
	//初始化
	private native void jniUninitMagicBeauty();	
	private native void jniInitMagicBeauty(ByteBuffer handler);	
	
	//局部均方差磨皮
	private native void jniStartSkinSmooth(float DenoiseLevel);	
	
	//log曲线美白
	private native void jniStartWhiteSkin(float WhiteLevel);	
	
	//Bitmap操作
	private native ByteBuffer jniStoreBitmapData(Bitmap bitmap);	
	private native void jniFreeBitmapData(ByteBuffer handler);	
	private native Bitmap jniGetBitmapFromStoredBitmapData(ByteBuffer handler);
	
	public interface MagicSDKListener{	
		void onEnd();	
	}
}
