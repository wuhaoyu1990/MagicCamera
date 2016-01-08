package com.seu.magicfilter.filter.base;

import java.nio.FloatBuffer;

import android.opengl.GLES11Ext;
import android.opengl.GLES20;

import com.seu.magicfilter.filter.base.gpuimage.GPUImageFilter;
import com.seu.magicfilter.utils.OpenGLUtils;

public class MagicCameraInputFilter extends GPUImageFilter{
	private static final String CAMERA_INPUT_VERTEX_SHADER = ""+
			"attribute vec4 position;\n" +    
            "attribute vec4 inputTextureCoordinate;\n" +    
            "\n" +  
            "uniform mat4 textureTransform;\n" +    
            "varying vec2 textureCoordinate;\n" +    
            "\n" +  
            "void main()\n" +    
            "{\n" +    
            "	textureCoordinate = (textureTransform * inputTextureCoordinate).xy;\n" +    
            "	gl_Position = position;\n" +    
            "}";  
	
	private static final String CAMERA_INPUT_FRAGMENT_SHADER = ""+
			"#extension GL_OES_EGL_image_external : require\n" +    
			"varying highp vec2 textureCoordinate;\n" +    
			"\n" +  
			"uniform samplerExternalOES inputImageTexture;\n" +    
			"\n" +  
			"void main()\n" +    
			"{\n" +    
			"	gl_FragColor = texture2D(inputImageTexture, textureCoordinate);\n" +    
			"}";
	
	private float[] mTextureTransformMatrix;
    private int mTextureTransformMatrixLocation;
	public MagicCameraInputFilter(){
		super(CAMERA_INPUT_VERTEX_SHADER, CAMERA_INPUT_FRAGMENT_SHADER);
	}
	
	public void onInit() {
        super.onInit();
        mTextureTransformMatrixLocation = GLES20.glGetUniformLocation(mGLProgId, "textureTransform");
    }
	
	public void setTextureTransformMatrix(float[] mtx){
		mTextureTransformMatrix = mtx;
    }
	
	public int onDrawFrame(int textureId, FloatBuffer vertexBuffer, FloatBuffer textureBuffer) {
        GLES20.glUseProgram(mGLProgId);
        if(!isInitialized()) {
            return OpenGLUtils.NOT_INIT;
        }
        vertexBuffer.position(0);
        GLES20.glVertexAttribPointer(mGLAttribPosition, 2, GLES20.GL_FLOAT, false, 0, vertexBuffer);
        GLES20.glEnableVertexAttribArray(mGLAttribPosition);
        textureBuffer.position(0);
        GLES20.glVertexAttribPointer(mGLAttribTextureCoordinate, 2, GLES20.GL_FLOAT, false, 0, textureBuffer);
        GLES20.glEnableVertexAttribArray(mGLAttribTextureCoordinate);
        GLES20.glUniformMatrix4fv(mTextureTransformMatrixLocation, 1, false, mTextureTransformMatrix, 0);
        
        if(textureId != OpenGLUtils.NO_TEXTURE){
	        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
	        GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, textureId);
	        GLES20.glUniform1i(mGLUniformTexture, 0);
        }
        
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);
        GLES20.glDisableVertexAttribArray(mGLAttribPosition);
        GLES20.glDisableVertexAttribArray(mGLAttribTextureCoordinate);
        GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, 0);
        return OpenGLUtils.ON_DRAWN;
    }
}
