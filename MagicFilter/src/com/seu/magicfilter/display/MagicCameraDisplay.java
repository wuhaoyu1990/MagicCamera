package com.seu.magicfilter.display;

import java.io.File;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.SurfaceTexture;
import android.graphics.SurfaceTexture.OnFrameAvailableListener;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.hardware.Camera.Size;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import com.seu.magicfilter.camera.CameraEngine;
import com.seu.magicfilter.filter.base.MagicCameraInputFilter;
import com.seu.magicfilter.filter.helper.SaveTask;
import com.seu.magicfilter.filter.helper.SaveTask.onPictureSaveListener;
import com.seu.magicfilter.utils.OpenGLUtils;
import com.seu.magicfilter.utils.Rotation;
import com.seu.magicfilter.utils.TextureRotationUtil;

/**
 * MagicCameraDisplay is used for camera preview
 */
public class MagicCameraDisplay extends MagicDisplay{	
	/**
	 * 用于绘制相机预览数据，当无滤镜及mFilters为Null或者大小为0时，绘制到屏幕中，
	 * 否则，绘制到FrameBuffer中纹理
	 */
	private final MagicCameraInputFilter mCameraInputFilter;
	
	/**
	 * Camera预览数据接收层，必须和OpenGL绑定
	 * 过程见{@link OpenGLUtils.getExternalOESTextureID()};
	 */
	private SurfaceTexture mSurfaceTexture;
    
	public MagicCameraDisplay(Context context, GLSurfaceView glSurfaceView){
		super(context, glSurfaceView);
		mCameraInputFilter = new MagicCameraInputFilter();
	}
	
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		GLES20.glDisable(GL10.GL_DITHER);
        GLES20.glClearColor(0,0,0,0);
        GLES20.glEnable(GL10.GL_CULL_FACE);
        GLES20.glEnable(GL10.GL_DEPTH_TEST);
        mCameraInputFilter.init();
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		GLES20.glViewport(0, 0, width, height);
		mSurfaceWidth = width;
		mSurfaceHeight = height;
		onFilterChanged();
	}

	@Override
	public void onDrawFrame(GL10 gl) {
		GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);	
		mSurfaceTexture.updateTexImage();
		float[] mtx = new float[16];
		mSurfaceTexture.getTransformMatrix(mtx);
		mCameraInputFilter.setTextureTransformMatrix(mtx);
		if(mFilters == null){
			mCameraInputFilter.onDrawFrame(mTextureId, mGLCubeBuffer, mGLTextureBuffer);
		}else{
			int textureID = mCameraInputFilter.onDrawToTexture(mTextureId);	
			mFilters.onDrawFrame(textureID, mGLCubeBuffer, mGLTextureBuffer);
		}
	}
	
	private OnFrameAvailableListener mOnFrameAvailableListener = new OnFrameAvailableListener() {
		
		@Override
		public void onFrameAvailable(SurfaceTexture surfaceTexture) {
			// TODO Auto-generated method stub
			mGLSurfaceView.requestRender();
		}
	};
	
	private void setUpCamera(){
		mGLSurfaceView.queueEvent(new Runnable() {
       		
            @Override
            public void run() {
            	if(mTextureId == OpenGLUtils.NO_TEXTURE){
        			mTextureId = OpenGLUtils.getExternalOESTextureID();	
        			mSurfaceTexture = new SurfaceTexture(mTextureId);
    				mSurfaceTexture.setOnFrameAvailableListener(mOnFrameAvailableListener);   
            	}
            	Size size = CameraEngine.getPreviewSize();
    			int orientation = CameraEngine.getOrientation();
    			if(orientation == 90 || orientation == 270){
    				mImageWidth = size.height;
    				mImageHeight = size.width;
    			}else{
    				mImageWidth = size.width;
    				mImageHeight = size.height;
    			} 
    			mCameraInputFilter.onOutputSizeChanged(mImageWidth, mImageHeight);
            	CameraEngine.startPreview(mSurfaceTexture);
            }
        });
    }
	
	protected void onFilterChanged(){
		super.onFilterChanged();
		mCameraInputFilter.onDisplaySizeChanged(mSurfaceWidth, mSurfaceHeight);
		if(mFilters != null)
			mCameraInputFilter.initCameraFrameBuffer(mImageWidth, mImageHeight);
		else
			mCameraInputFilter.destroyFramebuffers();
	}
	
	public void onResume(){
		super.onResume();
		if(CameraEngine.getCamera() == null)
        	CameraEngine.openCamera();
		if(CameraEngine.getCamera() != null){
			boolean flipHorizontal = CameraEngine.isFlipHorizontal();
			adjustPosition(CameraEngine.getOrientation(),flipHorizontal,!flipHorizontal);
		}
		setUpCamera();
	}
	
	public void onPause(){
		super.onPause();
		CameraEngine.releaseCamera();
	}

	public void onDestroy(){
		super.onDestroy();
	}

	public void onTakePicture(File file, onPictureSaveListener listener,ShutterCallback shutterCallback){
		CameraEngine.setRotation(90);
		mSaveTask = new SaveTask(mContext, file, listener);
		CameraEngine.takePicture(shutterCallback, null, mPictureCallback);
	}
	
	private PictureCallback mPictureCallback = new PictureCallback() {
		
		@Override
		public void onPictureTaken(final byte[] data,Camera camera) {
			Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
			if(mFilters != null){
				getBitmapFromGL(bitmap, true);
			}else{
				mSaveTask.execute(bitmap);   
			}
		}
	};
	
	protected void onGetBitmapFromGL(Bitmap bitmap){
		mSaveTask.execute(bitmap);
	}
	
	private void adjustPosition(int orientation, boolean flipHorizontal,boolean flipVertical) {
        Rotation mRotation = Rotation.fromInt(orientation);
        float[] textureCords = TextureRotationUtil.getRotation(mRotation, flipHorizontal, flipVertical);
        mGLTextureBuffer.clear();
        mGLTextureBuffer.put(textureCords).position(0);
    }			
}
