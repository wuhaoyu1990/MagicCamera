package com.seu.magiccamera.activity;

import java.io.InputStream;
import java.net.URL;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.seu.magiccamera.R;
import com.seu.magiccamera.common.utils.Constants;
import com.seu.magiccamera.common.view.edit.ImageEditFragment;
import com.seu.magiccamera.common.view.edit.ImageEditFragment.onHideListener;
import com.seu.magiccamera.common.view.edit.adds.ImageEditAddsView;
import com.seu.magiccamera.common.view.edit.adjust.ImageEditAdjustView;
import com.seu.magiccamera.common.view.edit.beauty.ImageEditBeautyView;
import com.seu.magiccamera.common.view.edit.filter.ImageEditFilterView;
import com.seu.magiccamera.common.view.edit.frame.ImageEditFrameView;
import com.seu.magicfilter.display.MagicImageDisplay;
import com.seu.magicfilter.filter.helper.SaveTask.onPictureSaveListener;

public class ImageActivity extends Activity{
	private RadioGroup mRadioGroup;
	
	private Fragment[] mFragments;
	private int mFragmentTag = -1;

	private MagicImageDisplay mMagicImageDisplay;
	
	private final int REQUEST_PICK_IMAGE = 1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image);
		initMagicPreview();
		initFragments();
		initRadioButtons();
		
		findViewById(R.id.image_edit_back).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		findViewById(R.id.image_edit_save).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mMagicImageDisplay.savaImage(Constants.getOutputMediaFile(), new onPictureSaveListener() {
					
					@Override
					public void onSaved(String result) {
						// TODO Auto-generated method stub
						Intent intent = new Intent();
						intent.setClass(ImageActivity.this, ShareActivity.class);
						startActivity(intent);
					}
				});
			}
		});
	}
	
	private void initFragments(){
		mFragments = new Fragment[5];
		ImageEditBeautyView mImageEditBeautyView = new ImageEditBeautyView(this, mMagicImageDisplay);
		mImageEditBeautyView.setOnHideListener(mOnHideListener);
		mFragments[0] = mImageEditBeautyView;
		ImageEditAddsView mImageEditAddsView = new ImageEditAddsView(this, mMagicImageDisplay);
		mImageEditAddsView.setOnHideListener(mOnHideListener);
		mFragments[1] = mImageEditAddsView;
		ImageEditAdjustView mImageEditAdjustView = new ImageEditAdjustView(this, mMagicImageDisplay);
		mImageEditAdjustView.setOnHideListener(mOnHideListener);
		mFragments[2] = mImageEditAdjustView;
		ImageEditFilterView mImageEditFilterView = new ImageEditFilterView(this, mMagicImageDisplay);
		mImageEditFilterView.setOnHideListener(mOnHideListener);
		mFragments[3] = mImageEditFilterView;
		ImageEditFrameView mImageEditFrameView = new ImageEditFrameView(this, mMagicImageDisplay);
		mImageEditFrameView.setOnHideListener(mOnHideListener);
		mFragments[4] = mImageEditFrameView;
	}
	
	private void initRadioButtons(){
		mRadioGroup = (RadioGroup)findViewById(R.id.image_edit_radiogroup);
		mRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub

				switch (checkedId) {
				case R.id.image_edit_adjust:
					if(!mFragments[2].isAdded())
						getFragmentManager().beginTransaction().add(R.id.image_edit_fragment_container, mFragments[2])
							.show(mFragments[2]).commit();		
					else
						getFragmentManager().beginTransaction().show(mFragments[2]).commit();
					mFragmentTag = 2;
					break;
				case R.id.image_edit_filter:
					if(!mFragments[3].isAdded())
						getFragmentManager().beginTransaction().add(R.id.image_edit_fragment_container, mFragments[3])
							.show(mFragments[3]).commit();		
					else	
						getFragmentManager().beginTransaction().show(mFragments[3]).commit();
					mFragmentTag = 3;
					break;
				case R.id.image_edit_frame:
					if(!mFragments[4].isAdded())
						getFragmentManager().beginTransaction().add(R.id.image_edit_fragment_container, mFragments[4])
							.show(mFragments[4]).commit();		
					else
						getFragmentManager().beginTransaction().show(mFragments[4]).commit();
					mFragmentTag = 4;
					break;
				case R.id.image_edit_adds:
					if(!mFragments[1].isAdded())
						getFragmentManager().beginTransaction().add(R.id.image_edit_fragment_container, mFragments[1])
							.show(mFragments[1]).commit();		
					else
						getFragmentManager().beginTransaction().show(mFragments[1]).commit();
					mFragmentTag = 1;
					break;
				case R.id.image_edit_beauty:	
					if(!mFragments[0].isAdded())
						getFragmentManager().beginTransaction().add(R.id.image_edit_fragment_container, mFragments[0])
							.show(mFragments[0]).commit();		
					else
						getFragmentManager().beginTransaction().show(mFragments[0]).commit();
					mFragmentTag = 0;
					break;
				default:
					if(mFragmentTag != -1)
						getFragmentManager().beginTransaction()
							.hide(mFragments[mFragmentTag])
							.commit();
					mFragmentTag = -1;
					break;
				}
			}
		});
	}
	
	private void hideFragment(){
		((ImageEditFragment) mFragments[mFragmentTag]).onHide();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			if(mFragmentTag != -1){
				hideFragment();
				return true;
			}
			break;
		default:
			break;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	private void initMagicPreview(){
		GLSurfaceView glSurfaceView = (GLSurfaceView)findViewById(R.id.glsurfaceView_image);
		mMagicImageDisplay = new MagicImageDisplay(this, glSurfaceView);

		Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
		photoPickerIntent.setType("image/*");
		startActivityForResult(photoPickerIntent, REQUEST_PICK_IMAGE);
	}

	@Override
	protected void onPause() {
		super.onPause();
		if(mMagicImageDisplay != null)
			mMagicImageDisplay.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		if(mMagicImageDisplay != null)
			mMagicImageDisplay.onResume();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if(mMagicImageDisplay != null)
			mMagicImageDisplay.onDestroy();
	}

	@Override
	protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
	    switch (requestCode) {
	        case REQUEST_PICK_IMAGE:
	            if (resultCode == RESULT_OK) {
	            	 try {
	            		 Uri mUri = data.getData();
	                     InputStream inputStream;
	                     if (mUri.getScheme().startsWith("http") || mUri.getScheme().startsWith("https")) {
	                         inputStream = new URL(mUri.toString()).openStream();
	                     } else {
	                         inputStream = ImageActivity.this.getContentResolver().openInputStream(mUri);
	                     }
	                     Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
	                     mMagicImageDisplay.setImageBitmap(bitmap);
	                 } catch (Exception e) {
	                     e.printStackTrace();
	                 }
	            
	            } else {
	                finish();
	            }
	            break;
	
	        default:
	            super.onActivityResult(requestCode, resultCode, data);
	            break;
	    }
	}
	
	private onHideListener mOnHideListener = new onHideListener() {
		
		@Override
		public void onHide() {
			mRadioGroup.check(View.NO_ID);		
		}
	};
}
