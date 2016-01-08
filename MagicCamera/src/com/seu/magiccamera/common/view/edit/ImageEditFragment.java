package com.seu.magiccamera.common.view.edit;

import android.app.Fragment;
import android.content.Context;
import com.seu.magicfilter.display.MagicImageDisplay;

public class ImageEditFragment extends Fragment{
	protected MagicImageDisplay mMagicDisplay;
	protected Context mContext;
	protected onHideListener mOnHideListener;
	public ImageEditFragment(Context context, MagicImageDisplay mMagicDisplay){
		this.mMagicDisplay = mMagicDisplay;
		this.mContext = context;
	}
	
	public void onHide(){
		
	}
	
	public void setOnHideListener(onHideListener l){
		this.mOnHideListener = l;
	}
	
	public interface onHideListener{
		void onHide();
	}
}
