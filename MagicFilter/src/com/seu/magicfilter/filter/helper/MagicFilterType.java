package com.seu.magicfilter.filter.helper;

public class MagicFilterType {	
	public static final int NONE = 0x00;
	
	public static final int FAIRYTALE = NONE + 1;
	
	public static final int SUNRISE = FAIRYTALE + 1;
	public static final int SUNSET = SUNRISE + 1;
	public static final int WHITECAT = SUNSET + 1;
	public static final int BLACKCAT = WHITECAT + 1;
	
	public static final int BEAUTY = BLACKCAT + 1;
	public static final int HEALTHY = BEAUTY + 1;
	
	public static final int SWEETS = HEALTHY + 1;
	
	public static final int ROMANCE = SWEETS + 1;
	public static final int SAKURA = ROMANCE + 1;
	public static final int WARM = SAKURA + 1;
	
	public static final int ANTIQUE = WARM + 1;
	public static final int NOSTALGIA = ANTIQUE + 1;
	
	public static final int CALM = NOSTALGIA + 1;
	public static final int LATTE = CALM + 1;
	public static final int TENDER = LATTE + 1;
	
	public static final int COOL = TENDER + 1;
	
	public static final int EMERALD = COOL + 1;
	public static final int EVERGREEN = EMERALD + 1;	
		
	public static final int AMARO = EVERGREEN + 1;
	public static final int BRANNAN = AMARO + 1;
	public static final int BROOKLYN = BRANNAN + 1;
	public static final int EARLYBIRD = BROOKLYN + 1;
	public static final int FREUD = EARLYBIRD + 1;
	public static final int HEFE = FREUD + 1;
	public static final int HUDSON = HEFE + 1;
	public static final int INKWELL = HUDSON + 1;
	public static final int KEVIN = INKWELL + 1;
	public static final int LOMO = KEVIN + 1;
	public static final int N1977 = LOMO + 1;
	public static final int NASHVILLE = N1977 + 1;
	public static final int PIXAR = NASHVILLE + 1;
	public static final int RISE = PIXAR + 1;
	public static final int SIERRA = RISE + 1;
	public static final int SUTRO = SIERRA + 1;
	public static final int TOASTER2 = SUTRO + 1;
	public static final int VALENCIA = TOASTER2 + 1;
	public static final int WALDEN = VALENCIA + 1;
	public static final int XPROII = WALDEN + 1;
	
	public static final int FILTER_COUNT = XPROII - NONE;
}
