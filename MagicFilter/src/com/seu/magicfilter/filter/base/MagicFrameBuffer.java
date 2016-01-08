package com.seu.magicfilter.filter.base;

import android.opengl.GLES20;

public class MagicFrameBuffer {
	protected static int[] mFrameBuffers = null;
    protected static int[] mFrameBufferTextures = null;
    private int mFrameWidth = -1;
    private int mFrameHeight = -1;
    
    private static MagicFrameBuffer instance;
    
    private  MagicFrameBuffer(){
    	
    }
    
    public static MagicFrameBuffer getInstance(){
    	if(instance == null)
    		instance = new MagicFrameBuffer();
    	return instance;
    }
    
    public static int[] getFrameBuffers() {
		return mFrameBuffers;
	}

	public static int[] getFrameBufferTextures() {
		return mFrameBufferTextures;
	}
	
	public void onDestroy() {
        destroyFramebuffers();
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
	
	public void onInit(final int width, final int height, final int size) {
		if(mFrameBuffers != null && (mFrameWidth != width || mFrameHeight != height || mFrameBuffers.length != size)){
			destroyFramebuffers();
		}
        if (mFrameBuffers == null) {
        	mFrameBuffers = new int[size];
            mFrameBufferTextures = new int[size];

            for (int i = 0; i < size; i++) {
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
}
