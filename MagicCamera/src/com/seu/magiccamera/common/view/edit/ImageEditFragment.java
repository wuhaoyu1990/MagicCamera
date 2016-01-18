package com.seu.magiccamera.common.view.edit;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

import com.seu.magicfilter.display.MagicImageDisplay;

public abstract class ImageEditFragment extends Fragment{
	protected MagicImageDisplay mMagicDisplay;
	protected Context mContext;
	protected onHideListener mOnHideListener;
	public ImageEditFragment(Context context, MagicImageDisplay magicDisplay){
		this.mMagicDisplay = magicDisplay;
		this.mContext = context;
	}
	
	public void onHide(){
		if(isChanged()){
			AlertDialog.Builder builder = new Builder(mContext);
			builder.setTitle("提示").setMessage("是否应用修改？").setNegativeButton("是", new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {	
					onDialogButtonClick(dialog);
					mMagicDisplay.commit();					
				}
			}).setPositiveButton("否", new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {	
					onDialogButtonClick(dialog);
					mMagicDisplay.restore();
				}
			}).create().show();
		}else{
			mOnHideListener.onHide();
		}
	}
	
	public void setOnHideListener(onHideListener l){
		this.mOnHideListener = l;
	}
	
	protected abstract boolean isChanged();
	
	protected void onDialogButtonClick(DialogInterface dialog){
		if(mOnHideListener != null)
			mOnHideListener.onHide();
		dialog.dismiss();
	}
	
	public interface onHideListener{
		void onHide();
	}
}
