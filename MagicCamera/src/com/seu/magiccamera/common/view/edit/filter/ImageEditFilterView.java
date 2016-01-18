package com.seu.magiccamera.common.view.edit.filter;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.seu.magiccamera.R;
import com.seu.magiccamera.common.view.FilterLayoutUtils;
import com.seu.magiccamera.common.view.edit.ImageEditFragment;
import com.seu.magicfilter.display.MagicImageDisplay;
import com.seu.magicfilter.filter.helper.MagicFilterType;

public class ImageEditFilterView extends ImageEditFragment{
	
	private FilterLayoutUtils mFilterLayoutUtils;
	
	public ImageEditFilterView(Context context, MagicImageDisplay magicDisplay) {
		super(context, magicDisplay);
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
	}

	@Override
	protected boolean isChanged() {
		return mFilterLayoutUtils.getFilterType() != MagicFilterType.NONE;
	}
}
