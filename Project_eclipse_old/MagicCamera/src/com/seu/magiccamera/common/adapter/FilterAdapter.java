package com.seu.magiccamera.common.adapter;

import java.util.List;

import com.seu.magiccamera.R;
import com.seu.magiccamera.common.bean.FilterInfo;
import com.seu.magiccamera.common.utils.FilterTypeHelper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;


public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.FilterHolder>{
	
	private LayoutInflater mInflater;
	private int lastSelected = 0;
	private Context context;
	private List<FilterInfo> filterInfos;
	
	public FilterAdapter(Context context){
		this.context = context;
		this.mInflater = LayoutInflater.from(context);
	}
	
	@Override
	public int getItemCount() {
		return filterInfos.size();
	}
	
	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		return filterInfos.get(position).getFilterType();
	}
	
	@Override
	public void onBindViewHolder(FilterHolder arg0, final int arg1) {
		if(filterInfos.get(arg1).getFilterType() != -1){
			arg0.thumbImage.setImageResource(FilterTypeHelper.FilterType2Thumb(filterInfos.get(arg1).getFilterType()));  
			arg0.filterName.setText(FilterTypeHelper.FilterType2Name(filterInfos.get(arg1).getFilterType())); 
			arg0.filterName.setBackgroundColor(context.getResources().getColor(
					FilterTypeHelper.FilterType2Color(filterInfos.get(arg1).getFilterType())));
			if(filterInfos.get(arg1).isSelected()){
				arg0.thumbSelected.setVisibility(View.VISIBLE);
				arg0.thumbSelected_bg.setBackgroundColor(context.getResources().getColor(
						FilterTypeHelper.FilterType2Color(filterInfos.get(arg1).getFilterType())));
				arg0.thumbSelected_bg.setAlpha(0.7f);
			}
			else
				arg0.thumbSelected.setVisibility(View.GONE);
			
			if(!filterInfos.get(arg1).isFavourite() || arg1 == 0)
				arg0.filterFavourite.setVisibility(View.GONE);
			else
				arg0.filterFavourite.setVisibility(View.VISIBLE);
			
			arg0.filterRoot.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(onFilterChangeListener!= null && filterInfos.get(arg1).getFilterType() != -1 
							&& arg1!= lastSelected
							&& !filterInfos.get(arg1).isSelected()){
						filterInfos.get(lastSelected).setSelected(false);
						filterInfos.get(arg1).setSelected(true);
						notifyItemChanged(lastSelected);
						notifyItemChanged(arg1);
						lastSelected = arg1;		
		
						onFilterChangeListener.onFilterChanged(filterInfos.get(arg1).getFilterType(), arg1);
					}
				}
			});
		}
	}

	@Override
	public FilterHolder onCreateViewHolder(ViewGroup arg0, int arg1) {
		if(arg1 != -1){
			View view = mInflater.inflate(R.layout.filter_item_layout,  
					arg0, false);  
			FilterHolder viewHolder = new FilterHolder(view);
			viewHolder.thumbImage = (ImageView) view  
	                .findViewById(R.id.filter_thumb_image);  
			viewHolder.filterName = (TextView) view  
	                .findViewById(R.id.filter_thumb_name);   
			viewHolder.filterRoot = (FrameLayout)view
					.findViewById(R.id.filter_root);
			viewHolder.thumbSelected = (FrameLayout) view  
	                .findViewById(R.id.filter_thumb_selected); 
			viewHolder.filterFavourite = (FrameLayout)view.
					findViewById(R.id.filter_thumb_favorite_layout);
			viewHolder.thumbSelected_bg = (View)view.
					findViewById(R.id.filter_thumb_selected_bg);
			return viewHolder;
		}else{
			View view = mInflater.inflate(R.layout.filter_division_layout,  
					arg0, false);  
			FilterHolder viewHolder = new FilterHolder(view);
			return viewHolder;
		}
	}
	
	public void setLastSelected(int arg){
		lastSelected = arg;
	}
	
	public int getLastSelected(){
		return lastSelected;
	}
	
	public void setFilterInfos(List<FilterInfo> filterInfos){
		this.filterInfos = filterInfos;		
		notifyDataSetChanged();
	}
	
	class FilterHolder extends ViewHolder{		
		ImageView thumbImage;
		TextView filterName;
		FrameLayout thumbSelected;
		FrameLayout filterRoot;
		FrameLayout filterFavourite;
		View thumbSelected_bg;
		
		public FilterHolder(View itemView) {
			super(itemView);
		}
	}
	
	public interface onFilterChangeListener{
		void onFilterChanged(int filterType,int position);
	}
	
	private onFilterChangeListener onFilterChangeListener;
	
	public void setOnFilterChangeListener(onFilterChangeListener onFilterChangeListener){
		this.onFilterChangeListener = onFilterChangeListener;
	}
}
