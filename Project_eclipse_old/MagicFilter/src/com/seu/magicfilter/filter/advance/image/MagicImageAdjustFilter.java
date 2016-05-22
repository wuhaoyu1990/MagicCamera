package com.seu.magicfilter.filter.advance.image;

import java.util.ArrayList;
import java.util.List;

import com.seu.magicfilter.filter.base.MagicBaseGroupFilter;
import com.seu.magicfilter.filter.base.gpuimage.GPUImageBrightnessFilter;
import com.seu.magicfilter.filter.base.gpuimage.GPUImageContrastFilter;
import com.seu.magicfilter.filter.base.gpuimage.GPUImageExposureFilter;
import com.seu.magicfilter.filter.base.gpuimage.GPUImageFilter;
import com.seu.magicfilter.filter.base.gpuimage.GPUImageHueFilter;
import com.seu.magicfilter.filter.base.gpuimage.GPUImageSaturationFilter;
import com.seu.magicfilter.filter.base.gpuimage.GPUImageSharpenFilter;

public class MagicImageAdjustFilter extends MagicBaseGroupFilter{
	
	public MagicImageAdjustFilter() {
		super(initFilters());
	}
	
	private static List<GPUImageFilter> initFilters(){
		List<GPUImageFilter> filters = new ArrayList<GPUImageFilter>();
		filters.add(new GPUImageContrastFilter());
		filters.add(new GPUImageBrightnessFilter());
		filters.add(new GPUImageExposureFilter());
		filters.add(new GPUImageHueFilter());
		filters.add(new GPUImageSaturationFilter());
		filters.add(new GPUImageSharpenFilter());
		return filters;		
	}
	
	public void setSharpness(final float range){
		((GPUImageSharpenFilter) mFilters.get(5)).setSharpness(range);
	}
	
	public void setHue(final float range){
		((GPUImageHueFilter) mFilters.get(3)).setHue(range);
	}
	
	public void setBrightness(final float range){
		((GPUImageBrightnessFilter) mFilters.get(1)).setBrightness(range);
	}
	
	public void setContrast(final float range){
		((GPUImageContrastFilter) mFilters.get(0)).setContrast(range);
	}
	
	public void setSaturation(final float range){
		((GPUImageSaturationFilter) mFilters.get(4)).setSaturation(range);
	}
	
	public void setExposure(final float range){
		((GPUImageExposureFilter) mFilters.get(2)).setExposure(range);
	}
}
