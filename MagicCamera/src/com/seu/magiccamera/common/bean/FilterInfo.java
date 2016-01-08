package com.seu.magiccamera.common.bean;

public class FilterInfo {
	private boolean bSelected = false;
	private int filterType;
	private boolean bFavourite = false;
	
	public void setFilterType(int id){
		this.filterType = id;
	}
	
	public int getFilterType(){
		return this.filterType;
	}
	
	public boolean isSelected(){
		return bSelected;
	}
	
	public void setSelected(boolean bSelected){
		this.bSelected = bSelected;
	}
	
	public boolean isFavourite(){
		return bFavourite;
	}
	
	public void setFavourite(boolean bFavourite){
		this.bFavourite = bFavourite;
	}
}