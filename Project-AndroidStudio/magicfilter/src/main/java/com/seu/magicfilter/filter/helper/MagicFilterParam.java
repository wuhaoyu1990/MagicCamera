package com.seu.magicfilter.filter.helper;

import javax.microedition.khronos.opengles.GL10;

public class MagicFilterParam {

	public static final int HIGH_GPU_PERFORMACE = 2;
	public static final int AVERAGE_GPU_PERFORMACE = 1;
	public static final int LOW_GPU_PERFORMACE =0;

	public static int GPUPower = HIGH_GPU_PERFORMACE;


	public static void initMagicFilterParam(GL10 gl){
		GPUPower = getGPUPower(gl.glGetString(GL10.GL_RENDERER));
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
			return AVERAGE_GPU_PERFORMACE;
		else if(gpu.contains("Mali-T760"))
			return AVERAGE_GPU_PERFORMACE;
		else if(gpu.contains("Mali-T628"))
			return AVERAGE_GPU_PERFORMACE;
		else if(gpu.contains("Mali-T624"))
			return AVERAGE_GPU_PERFORMACE;
		else if(gpu.contains("Mali"))		
			return LOW_GPU_PERFORMACE;
		
		//for PowerVR
		if(gpu.contains("PowerVR SGX 544"))
			return LOW_GPU_PERFORMACE;
		else if(gpu.contains("PowerVR"))
			return AVERAGE_GPU_PERFORMACE;
		
		if(gpu.contains("Exynos 8"))
			return HIGH_GPU_PERFORMACE;
		else if(gpu.contains("Exynos 7"))
			return AVERAGE_GPU_PERFORMACE;
		else if(gpu.contains("Exynos"))
			return LOW_GPU_PERFORMACE;
		
		if(gpu.contains("Adreno 330"))
			return AVERAGE_GPU_PERFORMACE;
		else if(gpu.contains("Adreno 510"))
			return AVERAGE_GPU_PERFORMACE;
		else if(gpu.contains("Adreno 320"))
			return AVERAGE_GPU_PERFORMACE;
		else if(gpu.contains("Adreno 306"))
			return LOW_GPU_PERFORMACE;
		else if(gpu.contains("Adreno 405"))
			return LOW_GPU_PERFORMACE;
		return AVERAGE_GPU_PERFORMACE;
	}

	private static int getGlVersion(String string){
		if(string.contains("3."))
			return 3;
		return 2;
	}
}
