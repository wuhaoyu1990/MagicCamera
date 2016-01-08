package com.seu.magicfilter.filter.base;

import android.opengl.GLES20;

import com.seu.magicfilter.filter.base.gpuimage.GPUImageFilter;

public class MagicImageInputFilter extends GPUImageFilter{
	
	private int mSingleStepOffsetLocation;
	
	public MagicImageInputFilter(){
		super(NO_FILTER_VERTEX_SHADER,NO_FILTER_FRAGMENT_SHADER);
	}	
	
	@Override
	public void onInit() {
		super.onInit();
		mSingleStepOffsetLocation = GLES20.glGetUniformLocation(getProgram(), "singleStepOffset");
	}
	
	private void setTexelSize(final float w, final float h) {
		setFloatVec2(mSingleStepOffsetLocation, new float[] {1.0f / w, 1.0f / h});
	}
	
	@Override
    public void onOutputSizeChanged(final int width, final int height) {
        super.onOutputSizeChanged(width, height);
        setTexelSize(width, height);
    }
}
