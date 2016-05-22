package com.seu.magicfilter.filter.base;


import java.nio.FloatBuffer;
import java.util.List;

import com.seu.magicfilter.filter.base.gpuimage.GPUImageFilter;
import com.seu.magicfilter.utils.OpenGLUtils;
import android.opengl.GLES20;


public class MagicBaseGroupFilter extends GPUImageFilter{
	protected static int[] mFrameBuffers = null;
    protected static int[] mFrameBufferTextures = null;
    private int mFrameWidth = -1;
    private int mFrameHeight = -1;
    protected List<GPUImageFilter> mFilters;
    
    public MagicBaseGroupFilter(List<GPUImageFilter> filters){
    	this.mFilters = filters;
    }
    
	@Override
    public void onDestroy() {
        for (GPUImageFilter filter : mFilters) {
            filter.destroy();
        }
        destroyFramebuffers();
    }
    
    @Override
    public void init() {
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
        if(mFrameBuffers != null && (mFrameWidth != width || mFrameHeight != height || mFrameBuffers.length != size-1)){
			destroyFramebuffers();
			mFrameWidth = width;
			mFrameHeight = height;
		}
        if (mFrameBuffers == null) {
        	mFrameBuffers = new int[size-1];
            mFrameBufferTextures = new int[size-1];

            for (int i = 0; i < size-1; i++) {
                GLES20.glGenFramebuffers(1, mFrameBuffers, i);
                
                GLES20.glGenTextures(1, mFrameBufferTextures, i);
                GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mFrameBufferTextures[i]);
                GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGBA, width, height, 0,
                        GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, null);
                GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
                        GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
                GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
                        GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
                GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
                        GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
                GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
                        GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);

                GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, mFrameBuffers[i]);
                GLES20.glFramebufferTexture2D(GLES20.GL_FRAMEBUFFER, GLES20.GL_COLOR_ATTACHMENT0,
                        GLES20.GL_TEXTURE_2D, mFrameBufferTextures[i], 0);

                GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
                GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, 0);
            }
        }
    }
    
    @Override
    public int onDrawFrame(final int textureId, final FloatBuffer cubeBuffer,
    		final FloatBuffer textureBuffer) {
    	if (mFrameBuffers == null || mFrameBufferTextures == null) {
            return OpenGLUtils.NOT_INIT;
        }
    	int size = mFilters.size();
        int previousTexture = textureId;
        for (int i = 0; i < size; i++) {
        	GPUImageFilter filter = mFilters.get(i);
            boolean isNotLast = i < size - 1;
            if (isNotLast) {
            	GLES20.glViewport(0, 0, mOutputWidth, mOutputHeight);
                GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, mFrameBuffers[i]);
                GLES20.glClearColor(0, 0, 0, 0);
                filter.onDrawFrame(previousTexture, mGLCubeBuffer, mGLTextureBuffer);
                GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, 0);
                previousTexture = mFrameBufferTextures[i];
            }else{
            	GLES20.glViewport(0, 0, mSurfaceWidth, mSurfaceHeight);
            	filter.onDrawFrame(previousTexture, cubeBuffer, textureBuffer);
            }
        }
    	return OpenGLUtils.ON_DRAWN;  	
    }
    
    @Override
    public int onDrawFrame(final int textureId) {
    	if (mFrameBuffers == null || mFrameBufferTextures == null) {
            return OpenGLUtils.NOT_INIT;
        }
    	int size = mFilters.size();
        int previousTexture = textureId;
        for (int i = 0; i < size; i++) {
        	GPUImageFilter filter = mFilters.get(i);
            boolean isNotLast = i < size - 1;
            if (isNotLast) {
                GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, mFrameBuffers[i]);
                GLES20.glClearColor(0, 0, 0, 0);
                filter.onDrawFrame(previousTexture, mGLCubeBuffer, mGLTextureBuffer);
                GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, 0);
                previousTexture = mFrameBufferTextures[i];
            }else{
            	filter.onDrawFrame(previousTexture, mGLCubeBuffer, mGLTextureBuffer);
            }
        }
    	return OpenGLUtils.ON_DRAWN;  	
    }
    
    private void destroyFramebuffers() {
        if (mFrameBufferTextures != null) {
            GLES20.glDeleteTextures(mFrameBufferTextures.length, mFrameBufferTextures, 0);
            mFrameBufferTextures = null;
        }
        if (mFrameBuffers != null) {
            GLES20.glDeleteFramebuffers(mFrameBuffers.length, mFrameBuffers, 0);
            mFrameBuffers = null;
        }
    }
    
    public int getFilterCount(){
    	return mFilters.size();
    }
}
