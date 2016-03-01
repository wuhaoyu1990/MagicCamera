package com.seu.magicfilter.filter.helper;

import com.seu.magicfilter.filter.advanced.MagicAmaroFilter;
import com.seu.magicfilter.filter.advanced.MagicAntiqueFilter;
import com.seu.magicfilter.filter.advanced.MagicBeautyFilter;
import com.seu.magicfilter.filter.advanced.MagicBlackCatFilter;
import com.seu.magicfilter.filter.advanced.MagicBrannanFilter;
import com.seu.magicfilter.filter.advanced.MagicBrooklynFilter;
import com.seu.magicfilter.filter.advanced.MagicCalmFilter;
import com.seu.magicfilter.filter.advanced.MagicCoolFilter;
import com.seu.magicfilter.filter.advanced.MagicCrayonFilter;
import com.seu.magicfilter.filter.advanced.MagicEarlyBirdFilter;
import com.seu.magicfilter.filter.advanced.MagicEmeraldFilter;
import com.seu.magicfilter.filter.advanced.MagicEvergreenFilter;
import com.seu.magicfilter.filter.advanced.MagicFairytaleFilter;
import com.seu.magicfilter.filter.advanced.MagicFreudFilter;
import com.seu.magicfilter.filter.advanced.MagicHealthyFilter;
import com.seu.magicfilter.filter.advanced.MagicHefeFilter;
import com.seu.magicfilter.filter.advanced.MagicHudsonFilter;
import com.seu.magicfilter.filter.advanced.MagicImageAdjustFilter;
import com.seu.magicfilter.filter.advanced.MagicInkwellFilter;
import com.seu.magicfilter.filter.advanced.MagicKevinFilter;
import com.seu.magicfilter.filter.advanced.MagicLatteFilter;
import com.seu.magicfilter.filter.advanced.MagicLomoFilter;
import com.seu.magicfilter.filter.advanced.MagicN1977Filter;
import com.seu.magicfilter.filter.advanced.MagicNashvilleFilter;
import com.seu.magicfilter.filter.advanced.MagicNostalgiaFilter;
import com.seu.magicfilter.filter.advanced.MagicPixarFilter;
import com.seu.magicfilter.filter.advanced.MagicRiseFilter;
import com.seu.magicfilter.filter.advanced.MagicRomanceFilter;
import com.seu.magicfilter.filter.advanced.MagicSakuraFilter;
import com.seu.magicfilter.filter.advanced.MagicSierraFilter;
import com.seu.magicfilter.filter.advanced.MagicSketchFilter;
import com.seu.magicfilter.filter.advanced.MagicSkinWhitenFilter;
import com.seu.magicfilter.filter.advanced.MagicSunriseFilter;
import com.seu.magicfilter.filter.advanced.MagicSunsetFilter;
import com.seu.magicfilter.filter.advanced.MagicSutroFilter;
import com.seu.magicfilter.filter.advanced.MagicSweetsFilter;
import com.seu.magicfilter.filter.advanced.MagicTenderFilter;
import com.seu.magicfilter.filter.advanced.MagicToasterFilter;
import com.seu.magicfilter.filter.advanced.MagicValenciaFilter;
import com.seu.magicfilter.filter.advanced.MagicWaldenFilter;
import com.seu.magicfilter.filter.advanced.MagicWarmFilter;
import com.seu.magicfilter.filter.advanced.MagicWhiteCatFilter;
import com.seu.magicfilter.filter.advanced.MagicXproIIFilter;
import com.seu.magicfilter.filter.base.gpuimage.GPUImageBrightnessFilter;
import com.seu.magicfilter.filter.base.gpuimage.GPUImageContrastFilter;
import com.seu.magicfilter.filter.base.gpuimage.GPUImageExposureFilter;
import com.seu.magicfilter.filter.base.gpuimage.GPUImageFilter;
import com.seu.magicfilter.filter.base.gpuimage.GPUImageHueFilter;
import com.seu.magicfilter.filter.base.gpuimage.GPUImageSaturationFilter;
import com.seu.magicfilter.filter.base.gpuimage.GPUImageSharpenFilter;

public class MagicFilterFactory{
	
	private static MagicFilterType filterType = MagicFilterType.NONE;
	
	public static GPUImageFilter initFilters(MagicFilterType type){
		filterType = type;
		switch (type) {
		case WHITECAT:
			return new MagicWhiteCatFilter();
		case BLACKCAT:
			return new MagicBlackCatFilter();
		case BEAUTY:
			return new MagicBeautyFilter();
		case SKINWHITEN:
			return new MagicSkinWhitenFilter();
		case ROMANCE:
			return new MagicRomanceFilter();
		case SAKURA:
			return new MagicSakuraFilter();
		case AMARO:
			return new MagicAmaroFilter();
		case WALDEN:
			return new MagicWaldenFilter();
		case ANTIQUE:
			return new MagicAntiqueFilter();
		case CALM:
			return new MagicCalmFilter();
		case BRANNAN:
			return new MagicBrannanFilter();
		case BROOKLYN:
			return new MagicBrooklynFilter();
		case EARLYBIRD:
			return new MagicEarlyBirdFilter();
		case FREUD:
			return new MagicFreudFilter();
		case HEFE:
			return new MagicHefeFilter();
		case HUDSON:
			return new MagicHudsonFilter();
		case INKWELL:
			return new MagicInkwellFilter();
		case KEVIN:
			return new MagicKevinFilter();
		case LOMO:
			return new MagicLomoFilter();
		case N1977:
			return new MagicN1977Filter();
		case NASHVILLE:
			return new MagicNashvilleFilter();
		case PIXAR:
			return new MagicPixarFilter();
		case RISE:
			return new MagicRiseFilter();
		case SIERRA:
			return new MagicSierraFilter();
		case SUTRO:
			return new MagicSutroFilter();
		case TOASTER2:
			return new MagicToasterFilter();
		case VALENCIA:
			return new MagicValenciaFilter();
		case XPROII:
			return new MagicXproIIFilter();
		case EVERGREEN:
			return new MagicEvergreenFilter();
		case HEALTHY:
			return new MagicHealthyFilter();
		case COOL:
			return new MagicCoolFilter();
		case EMERALD:
			return new MagicEmeraldFilter();
		case LATTE:
			return new MagicLatteFilter();
		case WARM:
			return new MagicWarmFilter();
		case TENDER:
			return new MagicTenderFilter();
		case SWEETS:
			return new MagicSweetsFilter();
		case NOSTALGIA:
			return new MagicNostalgiaFilter();
		case FAIRYTALE:
			return new MagicFairytaleFilter();
		case SUNRISE:
			return new MagicSunriseFilter();
		case SUNSET:
			return new MagicSunsetFilter();
		case CRAYON:
			return new MagicCrayonFilter();
		case SKETCH:
			return new MagicSketchFilter();
		//image adjust
		case BRIGHTNESS:
			return new GPUImageBrightnessFilter();
		case CONTRAST:
			return new GPUImageContrastFilter();
		case EXPOSURE:
			return new GPUImageExposureFilter();
		case HUE:
			return new GPUImageHueFilter();
		case SATURATION:
			return new GPUImageSaturationFilter();
		case SHARPEN:
			return new GPUImageSharpenFilter();
		case IMAGE_ADJUST:
			return new MagicImageAdjustFilter();
		default:
			return null;
		}
	}
	
	public MagicFilterType getCurrentFilterType(){
		return filterType;
	}
}
