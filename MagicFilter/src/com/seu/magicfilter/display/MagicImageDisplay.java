package com.seu.magicfilter.display;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.os.Handler;
import android.os.Message;

import com.seu.magicfilter.filter.base.MagicImageInputFilter;
import com.seu.magicfilter.filter.factory.MagicFilterFactory;
import com.seu.magicfilter.filter.helper.MagicFilterType;
import com.seu.magicfilter.filter.helper.SaveTask;
import com.seu.magicfilter.filter.helper.SaveTask.onPictureSaveListener;
import com.seu.magicfilter.utils.MagicSDK;
import com.seu.magicfilter.utils.OpenGLUtils;
import com.seu.magicfilter.utils.TextureRotationUtil;

public class MagicImageDisplay extends MagicDisplay{
	private final MagicImageInputFilter mImageInput;
   
    private final MagicSDK mMagicSDK;
    
    private int mImageWidth = 0;
    private int mImageHeight = 0;
    
    private boolean isChanged = false;
    
    private Bitmap mOriginBitmap;
    
    private boolean mIsSaving = false;
    
    @SuppressLint("HandlerLeak")
	private class MagicSDKHandler extends Handler{

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MagicSDK.MESSAGE_OPERATION_END:
				isChanged = true;
				deleteTextures();
				mGLSurfaceView.requestRender();
				break;
			default:
				break;
			}
			super.handleMessage(msg);
		}
    	
    }
    
    public MagicImageDisplay(Context context, GLSurfaceView glSurfaceView){
    	super(context, glSurfaceView);
    	mImageInput = new MagicImageInputFilter();
    	mMagicSDK = MagicSDK.getInstance();
		mMagicSDK.setMagicSDKHandler(new MagicSDKHandler());
    }      
    
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		GLES20.glDisable(GL10.GL_DITHER);
        GLES20.glClearColor(0,0,0,0);
        GLES20.glEnable(GL10.GL_CULL_FACE);
        GLES20.glEnable(GL10.GL_DEPTH_TEST);	
        mImageInput.init();
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		GLES20.glViewport(0, 0, width, height);
		mSurfaceWidth = width;
		mSurfaceHeight = height;
		adjustImageDisplaySize();
	}

	@Override
	public void onDrawFrame(GL10 gl) {
		GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
		if(mTextureId == OpenGLUtils.NO_TEXTURE)
			mTextureId = OpenGLUtils.loadTexture(mMagicSDK.getBitmap(), OpenGLUtils.NO_TEXTURE);
		if(isSetFilters())
			mFilters.onDrawFrame(mTextureId, mGLCubeBuffer, mGLTextureBuffer);
		else
			mImageInput.onDrawFrame(mTextureId, mGLCubeBuffer, mGLTextureBuffer);
	}
	
	public void setImageBitmap(Bitmap bitmap) {
		if (bitmap == null || bitmap.isRecycled())
			return;
		mImageWidth = bitmap.getWidth();
		mImageHeight = bitmap.getHeight();
		mOriginBitmap = bitmap;
		mMagicSDK.storeBitmap(mOriginBitmap, false);
		deleteTextures();
		mGLSurfaceView.requestRender();
	}
	
	public void setFilter(int filterType){
		final MagicFilterFactory filter = new MagicFilterFactory(filterType,mContext);
		mGLSurfaceView.queueEvent(new Runnable() {
       		
            @Override
            public void run() {
            	if(mFilters != null)
            		mFilters.destroy();
            	mFilters = null;
            	mFilters = filter;
            	mFilters.onInit();
            	mFilters.onOutputSizeChanged(mImageWidth, mImageHeight);
            	if(mFilters.getFilterCount() >= 1)
            		mFrameBuffer.onInit(mImageWidth, mImageHeight, mFilters.getFilterCount()-1);
            	isChanged = ((MagicFilterFactory) mFilters).getFilterType() == MagicFilterType.NONE ? false : true;
            }
        });
		mGLSurfaceView.requestRender();
    }
	
	public void onResume(){
		super.onResume();
	}
	
	public void onPause(){
		super.onPause();
	}
	
	public void onDestroy(){
		super.onDestroy();
		if(mMagicSDK != null)
			mMagicSDK.onDestroy();	
	}
	
	private void adjustImageDisplaySize() {
		if(mImageWidth > 0 && mImageHeight > 0){
			float ratio1 = (float)mSurfaceWidth / mImageWidth;
	        float ratio2 = (float)mSurfaceHeight / mImageHeight;
	        float ratioMax = Math.max(ratio1, ratio2);
	        int imageWidthNew = Math.round(mImageWidth * ratioMax);
	        int imageHeightNew = Math.round(mImageHeight * ratioMax);
	
	        float ratioWidth = imageWidthNew / (float)mSurfaceWidth;
	        float ratioHeight = imageHeightNew / (float)mSurfaceHeight;
	
	        float[] cube = new float[]{
	        		TextureRotationUtil.CUBE[0] / ratioHeight, TextureRotationUtil.CUBE[1] / ratioWidth,
	        		TextureRotationUtil.CUBE[2] / ratioHeight, TextureRotationUtil.CUBE[3] / ratioWidth,
	        		TextureRotationUtil.CUBE[4] / ratioHeight, TextureRotationUtil.CUBE[5] / ratioWidth,
	        		TextureRotationUtil.CUBE[6] / ratioHeight, TextureRotationUtil.CUBE[7] / ratioWidth,
	        };
	        mGLCubeBuffer.clear();
	        mGLCubeBuffer.put(cube).position(0);
		}
    }
	
	public boolean isChanged(){
		return isChanged;
	}
	
	protected void onGetBitmapFromGL(Bitmap bitmap){
		mOriginBitmap = bitmap;
		if(mIsSaving){
			mSaveTask.execute(mMagicSDK.getBitmap());
			mIsSaving = false;
		}else{
			mMagicSDK.storeBitmap(mOriginBitmap, false);
		}
	}
	
	//还原
	public void restore(){
		if(isSetFilters()){
			setFilter(MagicFilterType.NONE);
		}else{
			setImageBitmap(mOriginBitmap);
		}	
	}

	//应用
	public void commit(){
		if(isSetFilters()){
			getBitmapFromGL(mOriginBitmap, false);
			deleteTextures();
			setFilter(MagicFilterType.NONE);
		}else{
			mOriginBitmap.recycle();
			mOriginBitmap = mMagicSDK.getBitmap();
		}
	}
	
	public void savaImage(onPictureSaveListener l){
		mSaveTask = new SaveTask(mContext, l);
		mIsSaving = true;
		if(isSetFilters())
			getBitmapFromGL(mOriginBitmap, false);
		else
			onGetBitmapFromGL(mOriginBitmap);
	}
}
