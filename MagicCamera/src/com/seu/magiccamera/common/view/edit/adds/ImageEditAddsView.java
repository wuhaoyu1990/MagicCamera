package com.seu.magiccamera.common.view.edit.adds;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.seu.magiccamera.R;
import com.seu.magiccamera.common.view.edit.ImageEditFragment;
import com.seu.magicfilter.display.MagicImageDisplay;

public class ImageEditAddsView extends ImageEditFragment{

	public ImageEditAddsView(Context context, MagicImageDisplay mMagicDisplay) {
		super(context, mMagicDisplay);
	}

	@Override
	protected boolean isChanged() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void onDialogButtonClick(DialogInterface dialog) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_image_edit_adds, container, false);  
	}
}
