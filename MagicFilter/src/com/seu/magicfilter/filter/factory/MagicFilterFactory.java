package com.seu.magicfilter.filter.factory;

import java.util.ArrayList;

import android.content.Context;

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
import com.seu.magicfilter.filter.base.MagicBilateralFilter;
import com.seu.magicfilter.filter.base.MagicBaseGroupFilter;
import com.seu.magicfilter.filter.base.gpuimage.GPUImageFilter;
import com.seu.magicfilter.filter.helper.MagicFilterType;

public class MagicFilterFactory extends MagicBaseGroupFilter{
	
	private static int mFilterType = MagicFilterType.NONE;
	private Context mContext;
	
	public MagicFilterFactory(Context context) {
		super();
		this.mContext = context;
		setFilters(MagicFilterType.NONE);
	}
	
	public void setFilters(int type){
		if(mFilters == null)
			mFilters = new ArrayList<GPUImageFilter>();
		else
			mFilters.clear();
		mFilterType = type;
		switch (type) {
		case MagicFilterType.WHITECAT:
			addFilter(new MagicWhiteCatFilter(mContext));
			break;
		case MagicFilterType.BLACKCAT:
			addFilter(new MagicBlackCatFilter(mContext));
			break;
		case MagicFilterType.BEAUTY:
			addFilter(new MagicBilateralFilter(mContext));
			addFilter(new MagicBeautyFilter(mContext));
			break;
		case MagicFilterType.ROMANCE:
			addFilter(new MagicRomanceFilter(mContext));
			break;
		case MagicFilterType.SAKURA:
			addFilter(new MagicSakuraFilter(mContext));
			break;
		case MagicFilterType.AMARO:
			addFilter(new MagicAmaroFilter(mContext));
			break;
		case MagicFilterType.WALDEN:
			addFilter(new MagicWaldenFilter(mContext));
			break;
		case MagicFilterType.ANTIQUE:
			addFilter(new MagicAntiqueFilter(mContext));
			break;
		case MagicFilterType.CALM:
			addFilter(new MagicCalmFilter(mContext));
			break;
		case MagicFilterType.BRANNAN:
			addFilter(new MagicBrannanFilter(mContext));
			break;
		case MagicFilterType.BROOKLYN:
			addFilter(new MagicBrooklynFilter(mContext));
			break;
		case MagicFilterType.EARLYBIRD:
			addFilter(new MagicEarlyBirdFilter(mContext));
			break;
		case MagicFilterType.FREUD:
			addFilter(new MagicFreudFilter(mContext));
			break;
		case MagicFilterType.HEFE:
			addFilter(new MagicHefeFilter(mContext));
			break;
		case MagicFilterType.HUDSON:
			addFilter(new MagicHudsonFilter(mContext));
			break;
		case MagicFilterType.INKWELL:
			addFilter(new MagicInkwellFilter(mContext));
			break;
		case MagicFilterType.KEVIN:
			addFilter(new MagicKevinFilter(mContext));
			break;
		case MagicFilterType.LOMO:
			addFilter(new MagicLomoFilter(mContext));
			break;
		case MagicFilterType.N1977:
			addFilter(new MagicN1977Filter(mContext));
			break;
		case MagicFilterType.NASHVILLE:
			addFilter(new MagicNashvilleFilter(mContext));
			break;
		case MagicFilterType.PIXAR:
			addFilter(new MagicPixarFilter(mContext));
			break;
		case MagicFilterType.RISE:
			addFilter(new MagicRiseFilter(mContext));
			break;
		case MagicFilterType.SIERRA:
			addFilter(new MagicSierraFilter(mContext));
			break;
		case MagicFilterType.SUTRO:
			addFilter(new MagicSutroFilter(mContext));
			break;
		case MagicFilterType.TOASTER2:
			addFilter(new MagicToasterFilter(mContext));
			break;
		case MagicFilterType.VALENCIA:
			addFilter(new MagicValenciaFilter(mContext));
			break;
		case MagicFilterType.XPROII:
			addFilter(new MagicXproIIFilter(mContext));
			break;
		case MagicFilterType.EVERGREEN:
			addFilter(new MagicEvergreenFilter(mContext));
			break;
		case MagicFilterType.HEALTHY:
			addFilter(new MagicHealthyFilter(mContext));
			break;
		case MagicFilterType.COOL:
			addFilter(new MagicCoolFilter(mContext));
			break;
		case MagicFilterType.EMERALD:
			addFilter(new MagicEmeraldFilter(mContext));
			break;
		case MagicFilterType.LATTE:
			addFilter(new MagicLatteFilter(mContext));
			break;
		case MagicFilterType.WARM:
			addFilter(new MagicWarmFilter(mContext));
			break;
		case MagicFilterType.TENDER:
			addFilter(new MagicTenderFilter(mContext));
			break;
		case MagicFilterType.SWEETS:
			addFilter(new MagicSweetsFilter(mContext));
			break;
		case MagicFilterType.NOSTALGIA:
			addFilter(new MagicNostalgiaFilter(mContext));
			break;
		case MagicFilterType.FAIRYTALE:
			addFilter(new MagicFairytaleFilter(mContext));
			break;
		case MagicFilterType.SUNRISE:
			addFilter(new MagicSunriseFilter(mContext));
			break;
		case MagicFilterType.SUNSET:
			addFilter(new MagicSunsetFilter(mContext));
			break;
		default:
			break;
		}
	}
	
	public int getFilterType(){
		return mFilterType;
	}
}
