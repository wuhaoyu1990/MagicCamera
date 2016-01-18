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
	public static final String BILATERAL_VERTEX_SHADER = "" +
			"attribute vec4 position;\n" + 
			"attribute vec4 inputTextureCoordinate;\n" + 
		 
			"uniform vec2 singleStepOffset;\n" +  
	 
			"varying vec2 textureCoordinate;\n" + 
			"varying vec2 blurCoordinates[17];\n" + 
	 
			"void main()\n" + 
			"{\n" + 
	     	"	gl_Position = position;\n" + 
			"	textureCoordinate = inputTextureCoordinate.xy;\n" + 
	     
			"	vec2 blurStep;\n" +             
	        "	blurCoordinates[0] = inputTextureCoordinate.xy + singleStepOffset * vec2(-4.,-4.);\n" + 
	        "	blurCoordinates[1] = inputTextureCoordinate.xy + singleStepOffset * vec2(4.,-4.);\n" + 
	        "	blurCoordinates[2] = inputTextureCoordinate.xy + singleStepOffset * vec2(-4.,4.);\n" + 
	        "	blurCoordinates[3] = inputTextureCoordinate.xy + singleStepOffset * vec2(4.,4.);\n" + 	        	        
	        "	blurCoordinates[4] = inputTextureCoordinate.xy + singleStepOffset * vec2(-3.,-3.);\n" + 
	        "	blurCoordinates[5] = inputTextureCoordinate.xy + singleStepOffset * vec2(3.,-3.);\n" + 
	        "	blurCoordinates[6] = inputTextureCoordinate.xy + singleStepOffset * vec2(-3.,3.);\n" + 
	        "	blurCoordinates[7] = inputTextureCoordinate.xy + singleStepOffset * vec2(3.,3.);\n" + 	      	        
	        "	blurCoordinates[8] = inputTextureCoordinate.xy + singleStepOffset * vec2(-2.,-2.);\n" + 
	        "	blurCoordinates[9] = inputTextureCoordinate.xy + singleStepOffset * vec2(2.,-2.);\n" + 
	        "	blurCoordinates[10] = inputTextureCoordinate.xy + singleStepOffset * vec2(2.,2.);\n" + 
	        "	blurCoordinates[11] = inputTextureCoordinate.xy + singleStepOffset * vec2(-2.,2.);\n" + 	        	        
	        "	blurCoordinates[12] = inputTextureCoordinate.xy + singleStepOffset * vec2(1.,1.);\n" + 
	        "	blurCoordinates[13] = inputTextureCoordinate.xy + singleStepOffset * vec2(1.,-1.);\n" + 
	        "	blurCoordinates[14] = inputTextureCoordinate.xy + singleStepOffset * vec2(-1.,1.);\n" + 
	        "	blurCoordinates[15] = inputTextureCoordinate.xy + singleStepOffset * vec2(-1.,-1.);\n" + 	                
	        "	blurCoordinates[16] = inputTextureCoordinate.xy;\n" + 
	 		"}";
	
	private float mDistanceNormalizationFactor = 4.0f;
	private int mDisFactorLocation;
	private int mSingleStepOffsetLocation;
	
	public MagicBilateralFilter(Context context) {
		super(BILATERAL_VERTEX_SHADER,OpenGLUtils.readShaderFromRawResource(context, R.raw.bilateralfilter));
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
