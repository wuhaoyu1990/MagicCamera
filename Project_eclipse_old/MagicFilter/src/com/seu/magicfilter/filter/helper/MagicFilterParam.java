package com.seu.magicfilter.filter.helper;

import javax.microedition.khronos.opengles.GL10;

public class MagicFilterParam {

	public static int mGPUPower = 1;
	
	public static void initMagicFilterParam(GL10 gl){	
		mGPUPower = getGPUPower(gl.glGetString(GL10.GL_RENDERER));		
	}
	
	/**
	 * 
	 * @param gpu gpu's name
	 *  GPU Performance Ranking:
	 * Adreno 430
	 * Adreno 420
	 * Adreno 420
	 * Mali-T628 MP6
	 * Mali-T760 MP4
	 * Mali-T628 MP4
	 * Adreno 320
	 * Mali-T624 MP4
	 * PowerVR GC6200
	 * PowerVR SGX544 MP3
	 * Mali-T760 MP2
	 * Adreno 405
	 * Adreno 306
	 * Mali-450
	 * Mali-400
	 * 
	 * Examples:
	 * Huawei Mate8:Mali-T880 3000RMB+
	 * Huawei Mate7:Mali-T628 MP4 2000RMB+
	 * Huawei 5X Adreno 405 1000RMB+
	 * @return
	 */
	private static int getGPUPower(String gpu){
		//for Mali GPU
		if(gpu.contains("Mali-T880"))
			return 1;
		else if(gpu.contains("Mali-T760"))
			return 1;
		else if(gpu.contains("Mali-T628"))
			return 1;
		else if(gpu.contains("Mali-T624"))
			return 1;
		else if(gpu.contains("Mali"))		
			return 0;
		
		//for PowerVR
		if(gpu.contains("PowerVR SGX 544"))
			return 0;
		else if(gpu.contains("PowerVR"))
			return 1;
		
		if(gpu.contains("Exynos 8"))
			return 2;
		else if(gpu.contains("Exynos 7"))
			return 1;
		else if(gpu.contains("Exynos"))
			return 0;
		
		if(gpu.contains("Adreno 330"))
			return 1;
		else if(gpu.contains("Adreno 510"))
			return 1;
		else if(gpu.contains("Adreno 320"))
			return 1;
		else if(gpu.contains("Adreno 306"))
			return 0;
		else if(gpu.contains("Adreno 405"))
			return 0;
		return 1;
	}
}
