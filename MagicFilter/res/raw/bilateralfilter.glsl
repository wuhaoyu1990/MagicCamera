precision highp float;
uniform sampler2D inputImageTexture;
varying vec2 textureCoordinate;

uniform float distanceNormalizationFactor;
uniform vec2 singleStepOffset; 
void main()
{
	vec4 centralColor;
	float gaussianWeightTotal;
	vec4 sum;
	vec4 sampleColor;
	float distanceFromCentralColor;
	float gaussianWeight;
	
	vec2 blurCoordinates[17];
	vec2 blurStep;   
	         
    blurCoordinates[0] = textureCoordinate.xy + singleStepOffset * vec2(-4.,-4.);
    blurCoordinates[1] = textureCoordinate.xy + singleStepOffset * vec2(4.,-4.);
    blurCoordinates[2] = textureCoordinate.xy + singleStepOffset * vec2(-4.,4.);
    blurCoordinates[3] = textureCoordinate.xy + singleStepOffset * vec2(4.,4.);	        	        
    blurCoordinates[4] = textureCoordinate.xy + singleStepOffset * vec2(-3.,-3.);
    blurCoordinates[5] = textureCoordinate.xy + singleStepOffset * vec2(3.,-3.);
    blurCoordinates[6] = textureCoordinate.xy + singleStepOffset * vec2(-3.,3.);
    blurCoordinates[7] = textureCoordinate.xy + singleStepOffset * vec2(3.,3.);	      	        
    blurCoordinates[8] = textureCoordinate.xy + singleStepOffset * vec2(-2.,-2.);
    blurCoordinates[9] = textureCoordinate.xy + singleStepOffset * vec2(2.,-2.);
    blurCoordinates[10] = textureCoordinate.xy + singleStepOffset * vec2(2.,2.);
    blurCoordinates[11] = textureCoordinate.xy + singleStepOffset * vec2(-2.,2.);	        	        
    blurCoordinates[12] = textureCoordinate.xy + singleStepOffset * vec2(1.,1.);
    blurCoordinates[13] = textureCoordinate.xy + singleStepOffset * vec2(1.,-1.);
    blurCoordinates[14] = textureCoordinate.xy + singleStepOffset * vec2(-1.,1.);
    blurCoordinates[15] = textureCoordinate.xy + singleStepOffset * vec2(-1.,-1.);	                
    blurCoordinates[16] = textureCoordinate.xy;
	
	centralColor = texture2D(inputImageTexture, blurCoordinates[16]);
	if(centralColor.r > 0.078431 && centralColor.g > 0.156862 && centralColor.b > 0.196078)
	{
		gaussianWeightTotal = 0.36;
		sum = centralColor * 0.36;
		 
		sampleColor = texture2D(inputImageTexture, blurCoordinates[0]);
		distanceFromCentralColor = min(distance(centralColor, sampleColor) * distanceNormalizationFactor, 1.0);
		gaussianWeight = 0.05 * (1.0 - distanceFromCentralColor);
		gaussianWeightTotal += gaussianWeight;
		sum += sampleColor * gaussianWeight;
		sampleColor = texture2D(inputImageTexture, blurCoordinates[1]);
		distanceFromCentralColor = min(distance(centralColor, sampleColor) * distanceNormalizationFactor, 1.0);
		gaussianWeight = 0.05 * (1.0 - distanceFromCentralColor);
		gaussianWeightTotal += gaussianWeight;
		sum += sampleColor * gaussianWeight;
		sampleColor = texture2D(inputImageTexture, blurCoordinates[2]);
		distanceFromCentralColor = min(distance(centralColor, sampleColor) * distanceNormalizationFactor, 1.0);
		gaussianWeight = 0.05 * (1.0 - distanceFromCentralColor);
		gaussianWeightTotal += gaussianWeight;
		sum += sampleColor * gaussianWeight;
		sampleColor = texture2D(inputImageTexture, blurCoordinates[3]);
		distanceFromCentralColor = min(distance(centralColor, sampleColor) * distanceNormalizationFactor, 1.0);
		gaussianWeight = 0.05 * (1.0 - distanceFromCentralColor);
		gaussianWeightTotal += gaussianWeight;
		sum += sampleColor * gaussianWeight;


		sampleColor = texture2D(inputImageTexture, blurCoordinates[4]);
		distanceFromCentralColor = min(distance(centralColor, sampleColor) * distanceNormalizationFactor, 1.0);
		gaussianWeight = 0.09 * (1.0 - distanceFromCentralColor);
		gaussianWeightTotal += gaussianWeight;
		sum += sampleColor * gaussianWeight;
		sampleColor = texture2D(inputImageTexture, blurCoordinates[5]);
		distanceFromCentralColor = min(distance(centralColor, sampleColor) * distanceNormalizationFactor, 1.0);
		gaussianWeight = 0.09 * (1.0 - distanceFromCentralColor);
		gaussianWeightTotal += gaussianWeight;
		sum += sampleColor * gaussianWeight;
		sampleColor = texture2D(inputImageTexture, blurCoordinates[6]);
		distanceFromCentralColor = min(distance(centralColor, sampleColor) * distanceNormalizationFactor, 1.0);
		gaussianWeight = 0.09 * (1.0 - distanceFromCentralColor);
		gaussianWeightTotal += gaussianWeight;
		sum += sampleColor * gaussianWeight;
		sampleColor = texture2D(inputImageTexture, blurCoordinates[7]);
		distanceFromCentralColor = min(distance(centralColor, sampleColor) * distanceNormalizationFactor, 1.0);
		gaussianWeight = 0.09 * (1.0 - distanceFromCentralColor);
		gaussianWeightTotal += gaussianWeight;
		sum += sampleColor * gaussianWeight;

		sampleColor = texture2D(inputImageTexture, blurCoordinates[8]);
		distanceFromCentralColor = min(distance(centralColor, sampleColor) * distanceNormalizationFactor, 1.0);
		gaussianWeight = 0.12 * (1.0 - distanceFromCentralColor);
		gaussianWeightTotal += gaussianWeight;
		sum += sampleColor * gaussianWeight;
		sampleColor = texture2D(inputImageTexture, blurCoordinates[9]);
		distanceFromCentralColor = min(distance(centralColor, sampleColor) * distanceNormalizationFactor, 1.0);
		gaussianWeight = 0.12 * (1.0 - distanceFromCentralColor);
		gaussianWeightTotal += gaussianWeight;
		sum += sampleColor * gaussianWeight;
		sampleColor = texture2D(inputImageTexture, blurCoordinates[10]);
		distanceFromCentralColor = min(distance(centralColor, sampleColor) * distanceNormalizationFactor, 1.0);
		gaussianWeight = 0.12 * (1.0 - distanceFromCentralColor);
		gaussianWeightTotal += gaussianWeight;
		sum += sampleColor * gaussianWeight;
		sampleColor = texture2D(inputImageTexture, blurCoordinates[11]);
		distanceFromCentralColor = min(distance(centralColor, sampleColor) * distanceNormalizationFactor, 1.0);
		gaussianWeight = 0.12 * (1.0 - distanceFromCentralColor);
		gaussianWeightTotal += gaussianWeight;
		sum += sampleColor * gaussianWeight;

		sampleColor = texture2D(inputImageTexture, blurCoordinates[12]);
		distanceFromCentralColor = min(distance(centralColor, sampleColor) * distanceNormalizationFactor, 1.0);
		gaussianWeight = 0.15 * (1.0 - distanceFromCentralColor);
		gaussianWeightTotal += gaussianWeight;
		sum += sampleColor * gaussianWeight;
		sampleColor = texture2D(inputImageTexture, blurCoordinates[13]);
		distanceFromCentralColor = min(distance(centralColor, sampleColor) * distanceNormalizationFactor, 1.0);
		gaussianWeight = 0.15 * (1.0 - distanceFromCentralColor);
		gaussianWeightTotal += gaussianWeight;
		sum += sampleColor * gaussianWeight;
		sampleColor = texture2D(inputImageTexture, blurCoordinates[14]);
		distanceFromCentralColor = min(distance(centralColor, sampleColor) * distanceNormalizationFactor, 1.0);
		gaussianWeight = 0.15 * (1.0 - distanceFromCentralColor);
		gaussianWeightTotal += gaussianWeight;
		sum += sampleColor * gaussianWeight;
		sampleColor = texture2D(inputImageTexture, blurCoordinates[15]);
		distanceFromCentralColor = min(distance(centralColor, sampleColor) * distanceNormalizationFactor, 1.0);
		gaussianWeight = 0.15 * (1.0 - distanceFromCentralColor);
		gaussianWeightTotal += gaussianWeight;
		sum += sampleColor * gaussianWeight;
		 
		gl_FragColor = sum / gaussianWeightTotal;
	}else{
		gl_FragColor = centralColor;
	}
}