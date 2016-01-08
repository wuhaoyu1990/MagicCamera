package com.seu.magiccamera.common.view.edit.filter;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.seu.magiccamera.R;
import com.seu.magiccamera.common.view.FilterLayoutUtils;
import com.seu.magiccamera.common.view.edit.ImageEditFragment;
import com.seu.magicfilter.display.MagicImageDisplay;

public class ImageEditFilterView extends ImageEditFragment{
	
	private FilterLayoutUtils mFilterLayoutUtils;
	
	public ImageEditFilterView(Context context, MagicImageDisplay mMagicDisplay) {
		super(context, mMagicDisplay);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_image_edit_filter, container, false);  
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		mFilterLayoutUtils = new FilterLayoutUtils(getActivity(), mMagicDisplay);
		mFilterLayoutUtils.init(getView());
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		if(!hidden)
			mFilterLayoutUtils.init(getView());
		super.onHiddenChanged(hidden);
	}
	
	public void onHide(){
		if(mMagicDisplay.isChanged()){
			AlertDialog.Builder builder = new Builder(mContext);
			builder.setTitle("提示").setMessage("是否应用修改？").setNegativeButton("是", new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					mMagicDisplay.commit();
					dialog.dismiss();
					if(mOnHideListener != null)
						mOnHideListener.onHide();
				}
			}).setPositiveButton("否", new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					mMagicDisplay.restore();
					dialog.dismiss();
					if(mOnHideListener != null)
						mOnHideListener.onHide();
				}
			}).create().show();
		}else{
			mOnHideListener.onHide();
		}
	}
}
