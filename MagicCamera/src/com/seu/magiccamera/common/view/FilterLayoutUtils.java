package com.seu.magiccamera.common.view;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import com.seu.magiccamera.R;
import com.seu.magiccamera.common.adapter.FilterAdapter;
import com.seu.magiccamera.common.bean.FilterInfo;
import com.seu.magicfilter.display.MagicDisplay;
import com.seu.magicfilter.filter.helper.MagicFilterType;

public class FilterLayoutUtils{
	private Context mContext;
	private MagicDisplay mMagicDisplay;
	private FilterAdapter mAdapter;
	private ImageView btn_Favourite;

	private int position;
	private List<FilterInfo> filterInfos;
	private List<FilterInfo> favouriteFilterInfos;
	
	public FilterLayoutUtils(Context context,MagicDisplay magicDisplay) {
		mContext = context;	
		mMagicDisplay = magicDisplay;
	}

	public void init(){
		btn_Favourite = (ImageView) ((Activity) mContext).findViewById(R.id.btn_camera_favourite);  
		btn_Favourite.setOnClickListener(btn_Favourite_listener);
		
		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);  
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL); 
        RecyclerView mFilterListView = (RecyclerView)((Activity) mContext).findViewById(R.id.filter_listView);
        mFilterListView.setLayoutManager(linearLayoutManager);       
        
        mAdapter = new FilterAdapter(mContext);
        mFilterListView.setAdapter(mAdapter);
        initFilterInfos();
        mAdapter.setFilterInfos(filterInfos);
        mAdapter.setOnFilterChangeListener(onFilterChangeListener);          
	}
	
	public void init(View view){
		btn_Favourite = (ImageView) view.findViewById(R.id.btn_camera_favourite);  
		btn_Favourite.setOnClickListener(btn_Favourite_listener);
		
		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);  
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL); 
        RecyclerView mFilterListView = (RecyclerView) view.findViewById(R.id.filter_listView);
        mFilterListView.setLayoutManager(linearLayoutManager);       
        
        mAdapter = new FilterAdapter(mContext);
        mFilterListView.setAdapter(mAdapter);
        initFilterInfos();
        mAdapter.setFilterInfos(filterInfos);
        mAdapter.setOnFilterChangeListener(onFilterChangeListener);
        
        view.findViewById(R.id.btn_camera_closefilter).setVisibility(View.GONE);
	}
	
	private FilterAdapter.onFilterChangeListener onFilterChangeListener = new FilterAdapter.onFilterChangeListener(){

		@Override
		public void onFilterChanged(int filterType, int position) {
			// TODO Auto-generated method stub
			int Type = filterInfos.get(position).getFilterType();//获取类型
			FilterLayoutUtils.this.position = position;
			mMagicDisplay.setFilter(filterType);
			if(position != 0)
				btn_Favourite.setVisibility(View.VISIBLE);
			else
				btn_Favourite.setVisibility(View.INVISIBLE);
			btn_Favourite.setSelected(filterInfos.get(position).isFavourite());
			if(position <= favouriteFilterInfos.size()){//点击Favourite列表
				for(int i = favouriteFilterInfos.size() + 2; i < filterInfos.size(); i++){
					if(filterInfos.get(i).getFilterType() == Type){
						filterInfos.get(i).setSelected(true);
						mAdapter.setLastSelected(i);
						FilterLayoutUtils.this.position = i;
						mAdapter.notifyItemChanged(i);
					}else if(filterInfos.get(i).isSelected()){
						filterInfos.get(i).setSelected(false);
						mAdapter.notifyItemChanged(i);
					}
				}
			}
			for(int i = 1; i < favouriteFilterInfos.size() + 1; i++){
				if(filterInfos.get(i).getFilterType() == Type){
					filterInfos.get(i).setSelected(true);
					mAdapter.notifyItemChanged(i);
				}else if(filterInfos.get(i).isSelected()){
					filterInfos.get(i).setSelected(false);
					mAdapter.notifyItemChanged(i);
				}
			}
		}
		
	};
	
	private void initFilterInfos(){
		filterInfos = new ArrayList<FilterInfo>();
		//add original
		FilterInfo filterInfo = new FilterInfo();
		filterInfo.setFilterType(MagicFilterType.NONE);
		filterInfo.setSelected(true);
		filterInfos.add(filterInfo);
		
		//add Favourite
		loadFavourite();
		for(int i = 0;i < favouriteFilterInfos.size(); i++){
			filterInfo = new FilterInfo();
			filterInfo.setFilterType(favouriteFilterInfos.get(i).getFilterType());
			filterInfo.setFavourite(true);
			filterInfos.add(filterInfo);
		}
		//add Divider
		filterInfo = new FilterInfo();
		filterInfo.setFilterType(-1);
		filterInfos.add(filterInfo);
		
		//addAll
		for(int i = 1;i < MagicFilterType.FILTER_COUNT; i++){
			filterInfo = new FilterInfo();
			filterInfo.setFilterType(MagicFilterType.NONE + i);
			for(int j = 0;j < favouriteFilterInfos.size(); j++){
				if(MagicFilterType.NONE + i == favouriteFilterInfos.get(j).getFilterType()){
					filterInfo.setFavourite(true);
					break;
				}
			}
			filterInfos.add(filterInfo);
		}
	}
	
	private OnClickListener btn_Favourite_listener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			if(position != 0 && filterInfos.get(position).getFilterType() != -1){
				int Type = filterInfos.get(position).getFilterType();//获取类型
				if(filterInfos.get(position).isFavourite()){
					//取消Favourite------------------------------------
					btn_Favourite.setSelected(false);
					filterInfos.get(position).setFavourite(false);
						mAdapter.notifyItemChanged(position);
						int i = 0;
						for(i = 0; i < favouriteFilterInfos.size(); i++){
							if(Type == favouriteFilterInfos.get(i).getFilterType()){//取消对应Favourite列表中元素
								favouriteFilterInfos.remove(i);
								filterInfos.remove(i+1);//从filterInfos去除
								mAdapter.notifyItemRemoved(i+1);
								mAdapter.setLastSelected(mAdapter.getLastSelected() - 1);
								break;
							}
						}
					position --;
					mAdapter.notifyItemRangeChanged(i + 1, filterInfos.size() - i - 1);
				}else{//增加favourite
					btn_Favourite.setSelected(true);//更改状态
					filterInfos.get(position).setFavourite(true);
					mAdapter.notifyItemChanged(position);
					
					FilterInfo filterInfo = new FilterInfo();
					filterInfo.setFilterType(Type);
					filterInfo.setSelected(true);
					filterInfo.setFavourite(true);
					filterInfos.add(favouriteFilterInfos.size()+1 ,filterInfo);
					position ++;
					mAdapter.notifyItemInserted(favouriteFilterInfos.size() + 1);
					mAdapter.notifyItemRangeChanged(favouriteFilterInfos.size() + 1, filterInfos.size() - favouriteFilterInfos.size() - 1);
					favouriteFilterInfos.add(filterInfo);
					mAdapter.setLastSelected(mAdapter.getLastSelected() + 1);
				}
				saveFavourite();
			}
		}
	};	
	
	private void loadFavourite(){
		favouriteFilterInfos = new ArrayList<FilterInfo>();
		String[] typeList = ((Activity) mContext).getSharedPreferences("favourite_filter",Activity.MODE_PRIVATE)
				.getString("favourite_filter_list", "").split(",");
		for(int i = 0; i < typeList.length && typeList[i] != ""; i++){
			FilterInfo filterInfo = new FilterInfo();
			filterInfo.setFilterType(Integer.parseInt(typeList[i]));
			filterInfo.setFavourite(true);
			favouriteFilterInfos.add(filterInfo);
		}
	}
	
	private void saveFavourite(){
		SharedPreferences shared = ((Activity) mContext).getSharedPreferences("favourite_filter",Activity.MODE_PRIVATE);
		Editor editor = shared.edit();
		editor.remove("favourite_filter_list");
		editor.commit();
		String str = "";
		for(int i = 0; i < favouriteFilterInfos.size(); i++){
			str += favouriteFilterInfos.get(i).getFilterType() + ",";
		}
		editor.putString("favourite_filter_list", str);
		editor.commit();
	}
}
