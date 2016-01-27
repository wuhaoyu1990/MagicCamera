/**
 * @author wysaid
 * @mail admin@wysaid.org
 *
*/

package com.seu.magicfilter.filter.base;

import android.content.Context;
import android.opengl.GLES20;

import com.seu.magicfilter.R;
import com.seu.magicfilter.filter.base.gpuimage.GPUImageFilter;
import com.seu.magicfilter.utils.OpenGLUtils;


public class MagicBilateralFilter extends GPUImageFilter {
	
	private float mDistanceNormalizationFactor = 4.0f;
	private int mDisFactorLocation;
	private int mSingleStepOffsetLocation;
	
	public MagicBilateralFilter(Context context) {
		super(NO_FILTER_VERTEX_SHADER, OpenGLUtils.readShaderFromRawResource(context, R.raw.bilateralfilter));
	}
	
	@Override
	protected void onInit() {
		super.onInit();
		mDisFactorLocation = GLES20.glGetUniformLocation(getProgram(), "distanceNormalizationFactor");
		mSingleStepOffsetLocation = GLES20.glGetUniformLocation(getProgram(), "singleStepOffset");
	}
	
	@Override
	protected void onInitialized() {
		super.onInitialized();
		setDistanceNormalizationFactor(mDistanceNormalizationFactor);
	}
	
	public void setDistanceNormalizationFactor(final float newValue) {
		mDistanceNormalizationFactor = newValue;
		setFloat(mDisFactorLocation, newValue);
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
