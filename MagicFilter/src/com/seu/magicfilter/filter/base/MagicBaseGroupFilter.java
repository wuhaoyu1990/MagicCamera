package com.seu.magicfilter.filter.base;


import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.List;

import com.seu.magicfilter.filter.base.gpuimage.GPUImageFilter;
import com.seu.magicfilter.utils.OpenGLUtils;
import com.seu.magicfilter.utils.Rotation;
import com.seu.magicfilter.utils.TextureRotationUtil;

import android.opengl.GLES20;


public class MagicBaseGroupFilter extends GPUImageFilter{
   
    protected List<GPUImageFilter> mFilters;
    protected FloatBuffer mGLCubeBuffer;
    protected FloatBuffer mGLTextureBuffer;
    
    public MagicBaseGroupFilter(List<GPUImageFilter> filters){
    	this.mFilters = filters;
    	mGLCubeBuffer = ByteBuffer.allocateDirect(TextureRotationUtil.CUBE.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        mGLCubeBuffer.put(TextureRotationUtil.CUBE).position(0);

        mGLTextureBuffer = ByteBuffer.allocateDirect(TextureRotationUtil.TEXTURE_NO_ROTATION.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        mGLTextureBuffer.put(TextureRotationUtil.getRotation(Rotation.NORMAL, false, true)).position(0);
    }
    	
	@Override
    public void onDestroy() {
        for (GPUImageFilter filter : mFilters) {
            filter.destroy();
        }
        super.onDestroy();
    }
    
    @Override
    public void onInit() {
        for (GPUImageFilter filter : mFilters) {
            filter.init();
        }
    }
    
    @Override
    public void onOutputSizeChanged(final int width, final int height) {
        super.onOutputSizeChanged(width, height);
        int size = mFilters.size();
        for (int i = 0; i < size; i++) {
            mFilters.get(i).onOutputSizeChanged(width, height);
        }
    }
    
    /*
     * for camera display
     */
    public int onDrawFrame(final int textureId) {
    	if (MagicFrameBuffer.getFrameBuffers() == null || MagicFrameBuffer.getFrameBufferTextures() == null) {
            return OpenGLUtils.NOT_INIT;
        }
    	int size = mFilters.size();
        int previousTexture = textureId;
        for (int i = 0; i < size; i++) {
        	GPUImageFilter filter = mFilters.get(i);
            boolean isNotLast = i < size - 1;
            if (isNotLast) {
                GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, MagicFrameBuffer.getFrameBuffers()[i+1]);
                GLES20.glClearColor(0, 0, 0, 0);
            }
            filter.onDrawFrame(previousTexture, mGLCubeBuffer, mGLTextureBuffer);
            if (isNotLast) {
                GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, 0);
                previousTexture = MagicFrameBuffer.getFrameBufferTextures()[i+1];
            }
        }
    	return OpenGLUtils.ON_DRAWN;  	
    }
    
    /*
     * for image display
     */
    public int onDrawFrame(final int textureId, final FloatBuffer cubeBuffer,
                       final FloatBuffer textureBuffer) {
    	if (MagicFrameBuffer.getFrameBuffers() == null || MagicFrameBuffer.getFrameBufferTextures() == null) {
            return OpenGLUtils.NOT_INIT;
        }
    	int size = mFilters.size();
        int previousTexture = textureId;
        for (int i = 0; i < size; i++) {
        	GPUImageFilter filter = mFilters.get(i);
            boolean isNotLast = i < size - 1;
            if (isNotLast) {
                GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, MagicFrameBuffer.getFrameBuffers()[i]);
                GLES20.glClearColor(0, 0, 0, 0);
            }
            filter.onDrawFrame(previousTexture, cubeBuffer, textureBuffer);
            if (isNotLast) {
                GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, 0);
                previousTexture = MagicFrameBuffer.getFrameBufferTextures()[i];
            }
        }
    	return OpenGLUtils.ON_DRAWN;  	
    }
    
    public int getFilterCount(){
    	return mFilters.size();
    }
}
