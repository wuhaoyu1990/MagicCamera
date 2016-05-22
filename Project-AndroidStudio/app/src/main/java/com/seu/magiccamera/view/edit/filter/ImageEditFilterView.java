package com.seu.magiccamera.view.edit.filter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.seu.magiccamera.R;
import com.seu.magiccamera.view.edit.ImageEditFragment;

public class ImageEditFilterView extends ImageEditFragment {
	
	public ImageEditFilterView(Context context) {
		super(context);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_image_edit_filter, container, false);  
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onHiddenChanged(boolean hidden) {

	}

	@Override
	protected boolean isChanged() {

		return false;
	}
}
