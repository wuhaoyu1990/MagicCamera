attribute vec4 position;
attribute vec4 inputTextureCoordinate;
	 
uniform vec2 singleStepOffset; 

varying vec2 textureCoordinate;
varying vec2 blurCoordinates[20];


void main()
{
	gl_Position = position;
	textureCoordinate = inputTextureCoordinate.xy;
    
	blurCoordinates[0] = inputTextureCoordinate.xy + singleStepOffset * vec2(0.0, -10.0);
	blurCoordinates[1] = inputTextureCoordinate.xy + singleStepOffset * vec2(5.0, -8.0);
	blurCoordinates[2] = inputTextureCoordinate.xy + singleStepOffset * vec2(8.0, -5.0);
	blurCoordinates[3] = inputTextureCoordinate.xy + singleStepOffset * vec2(10.0, 0.0);
	blurCoordinates[4] = inputTextureCoordinate.xy + singleStepOffset * vec2(8.0, 5.0);
	blurCoordinates[5] = inputTextureCoordinate.xy + singleStepOffset * vec2(5.0, 8.0);
	blurCoordinates[6] = inputTextureCoordinate.xy + singleStepOffset * vec2(0.0, 10.0);
	blurCoordinates[7] = inputTextureCoordinate.xy + singleStepOffset * vec2(-5.0, 8.0);
	blurCoordinates[8] = inputTextureCoordinate.xy + singleStepOffset * vec2(-8.0, 5.0);
	blurCoordinates[9] = inputTextureCoordinate.xy + singleStepOffset * vec2(-10.0, 0.0);
	blurCoordinates[10] = inputTextureCoordinate.xy + singleStepOffset * vec2(-8.0, -5.0);
	blurCoordinates[11] = inputTextureCoordinate.xy + singleStepOffset * vec2(-5.0, -8.0);
	blurCoordinates[12] = inputTextureCoordinate.xy + singleStepOffset * vec2(0.0, -6.0);
	blurCoordinates[13] = inputTextureCoordinate.xy + singleStepOffset * vec2(-4.0, -4.0);
	blurCoordinates[14] = inputTextureCoordinate.xy + singleStepOffset * vec2(-6.0, 0.0);
	blurCoordinates[15] = inputTextureCoordinate.xy + singleStepOffset * vec2(-4.0, 4.0);
	blurCoordinates[16] = inputTextureCoordinate.xy + singleStepOffset * vec2(0.0, 6.0);
	blurCoordinates[17] = inputTextureCoordinate.xy + singleStepOffset * vec2(4.0, 4.0);
	blurCoordinates[18] = inputTextureCoordinate.xy + singleStepOffset * vec2(6.0, 0.0);
	blurCoordinates[19] = inputTextureCoordinate.xy + singleStepOffset * vec2(4.0, -4.0);
}