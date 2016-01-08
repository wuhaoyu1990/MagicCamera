package com.seu.magicfilter.filter.factory;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;

import com.seu.magicfilter.filter.advance.common.MagicAmaroFilter;
import com.seu.magicfilter.filter.advance.common.MagicAntiqueFilter;
import com.seu.magicfilter.filter.advance.common.MagicBeautyFilter;
import com.seu.magicfilter.filter.advance.common.MagicBlackCatFilter;
import com.seu.magicfilter.filter.advance.common.MagicBrannanFilter;
import com.seu.magicfilter.filter.advance.common.MagicBrooklynFilter;
import com.seu.magicfilter.filter.advance.common.MagicCalmFilter;
import com.seu.magicfilter.filter.advance.common.MagicCoolFilter;
import com.seu.magicfilter.filter.advance.common.MagicEarlyBirdFilter;
import com.seu.magicfilter.filter.advance.common.MagicEmeraldFilter;
import com.seu.magicfilter.filter.advance.common.MagicEvergreenFilter;
import com.seu.magicfilter.filter.advance.common.MagicFairytaleFilter;
import com.seu.magicfilter.filter.advance.common.MagicFreudFilter;
import com.seu.magicfilter.filter.advance.common.MagicHealthyFilter;
import com.seu.magicfilter.filter.advance.common.MagicHefeFilter;
import com.seu.magicfilter.filter.advance.common.MagicHudsonFilter;
import com.seu.magicfilter.filter.advance.common.MagicInkwellFilter;
import com.seu.magicfilter.filter.advance.common.MagicKevinFilter;
import com.seu.magicfilter.filter.advance.common.MagicLatteFilter;
import com.seu.magicfilter.filter.advance.common.MagicLomoFilter;
import com.seu.magicfilter.filter.advance.common.MagicN1977Filter;
import com.seu.magicfilter.filter.advance.common.MagicNashvilleFilter;
import com.seu.magicfilter.filter.advance.common.MagicNostalgiaFilter;
import com.seu.magicfilter.filter.advance.common.MagicPixarFilter;
import com.seu.magicfilter.filter.advance.common.MagicRiseFilter;
import com.seu.magicfilter.filter.advance.common.MagicRomanceFilter;
import com.seu.magicfilter.filter.advance.common.MagicSakuraFilter;
import com.seu.magicfilter.filter.advance.common.MagicSierraFilter;
import com.seu.magicfilter.filter.advance.common.MagicSunriseFilter;
import com.seu.magicfilter.filter.advance.common.MagicSunsetFilter;
import com.seu.magicfilter.filter.advance.common.MagicSutroFilter;
import com.seu.magicfilter.filter.advance.common.MagicSweetsFilter;
import com.seu.magicfilter.filter.advance.common.MagicTenderFilter;
import com.seu.magicfilter.filter.advance.common.MagicToasterFilter;
import com.seu.magicfilter.filter.advance.common.MagicValenciaFilter;
import com.seu.magicfilter.filter.advance.common.MagicWaldenFilter;
import com.seu.magicfilter.filter.advance.common.MagicWarmFilter;
import com.seu.magicfilter.filter.advance.common.MagicWhiteCatFilter;
import com.seu.magicfilter.filter.advance.common.MagicXproIIFilter;
import com.seu.magicfilter.filter.base.MagicBaseGroupFilter;
import com.seu.magicfilter.filter.base.gpuimage.GPUImageFilter;
import com.seu.magicfilter.filter.helper.MagicFilterType;

public class MagicFilterFactory extends MagicBaseGroupFilter{
	
	private static int mFilterType = MagicFilterType.NONE;
	
	public MagicFilterFactory(int type,Context context) {
		super(initFilters(type,context));
	}
	
	private static ArrayList<GPUImageFilter> initFilters(int type,Context context){
		mFilterType = type;
		ArrayList<GPUImageFilter> filters = new ArrayList<GPUImageFilter>();
		switch (type) {
		case MagicFilterType.WHITECAT:
			filters.add(new MagicWhiteCatFilter(context));
			break;
		case MagicFilterType.BLACKCAT:
			filters.add(new MagicBlackCatFilter(context));
			break;
		case MagicFilterType.BEAUTY:
			filters.add(new MagicBeautyFilter(context));
			break;
		case MagicFilterType.ROMANCE:
			filters.add(new MagicRomanceFilter(context));
			break;
		case MagicFilterType.SAKURA:
			filters.add(new MagicSakuraFilter(context));
			break;
		case MagicFilterType.AMARO:
			filters.add(new MagicAmaroFilter(context));
			break;
		case MagicFilterType.WALDEN:
			filters.add(new MagicWaldenFilter(context));
			break;
		case MagicFilterType.ANTIQUE:
			filters.add(new MagicAntiqueFilter(context));
			break;
		case MagicFilterType.CALM:
			filters.add(new MagicCalmFilter(context));
			break;
		case MagicFilterType.BRANNAN:
			filters.add(new MagicBrannanFilter(context));
			break;
		case MagicFilterType.BROOKLYN:
			filters.add(new MagicBrooklynFilter(context));
			break;
		case MagicFilterType.EARLYBIRD:
			filters.add(new MagicEarlyBirdFilter(context));
			break;
		case MagicFilterType.FREUD:
			filters.add(new MagicFreudFilter(context));
			break;
		case MagicFilterType.HEFE:
			filters.add(new MagicHefeFilter(context));
			break;
		case MagicFilterType.HUDSON:
			filters.add(new MagicHudsonFilter(context));
			break;
		case MagicFilterType.INKWELL:
			filters.add(new MagicInkwellFilter(context));
			break;
		case MagicFilterType.KEVIN:
			filters.add(new MagicKevinFilter(context));
			break;
		case MagicFilterType.LOMO:
			filters.add(new MagicLomoFilter(context));
			break;
		case MagicFilterType.N1977:
			filters.add(new MagicN1977Filter(context));
			break;
		case MagicFilterType.NASHVILLE:
			filters.add(new MagicNashvilleFilter(context));
			break;
		case MagicFilterType.PIXAR:
			filters.add(new MagicPixarFilter(context));
			break;
		case MagicFilterType.RISE:
			filters.add(new MagicRiseFilter(context));
			break;
		case MagicFilterType.SIERRA:
			filters.add(new MagicSierraFilter(context));
			break;
		case MagicFilterType.SUTRO:
			filters.add(new MagicSutroFilter(context));
			break;
		case MagicFilterType.TOASTER2:
			filters.add(new MagicToasterFilter(context));
			break;
		case MagicFilterType.VALENCIA:
			filters.add(new MagicValenciaFilter(context));
			break;
		case MagicFilterType.XPROII:
			filters.add(new MagicXproIIFilter(context));
			break;
		case MagicFilterType.EVERGREEN:
			filters.add(new MagicEvergreenFilter(context));
			break;
		case MagicFilterType.HEALTHY:
			filters.add(new MagicHealthyFilter(context));
			break;
		case MagicFilterType.COOL:
			filters.add(new MagicCoolFilter(context));
			break;
		case MagicFilterType.EMERALD:
			filters.add(new MagicEmeraldFilter(context));
			break;
		case MagicFilterType.LATTE:
			filters.add(new MagicLatteFilter(context));
			break;
		case MagicFilterType.WARM:
			filters.add(new MagicWarmFilter(context));
			break;
		case MagicFilterType.TENDER:
			filters.add(new MagicTenderFilter(context));
			break;
		case MagicFilterType.SWEETS:
			filters.add(new MagicSweetsFilter(context));
			break;
		case MagicFilterType.NOSTALGIA:
			filters.add(new MagicNostalgiaFilter(context));
			break;
		case MagicFilterType.FAIRYTALE:
			filters.add(new MagicFairytaleFilter(context));
			break;
		case MagicFilterType.SUNRISE:
			filters.add(new MagicSunriseFilter(context));
			break;
		case MagicFilterType.SUNSET:
			filters.add(new MagicSunsetFilter(context));
			break;
		default:
			break;
		}
		return filters;
	}
	
	public int getFilterType(){
		return mFilterType;
	}
}
