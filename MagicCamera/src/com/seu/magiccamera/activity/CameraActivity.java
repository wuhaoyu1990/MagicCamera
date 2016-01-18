package com.seu.magiccamera.activity;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;
import com.seu.magiccamera.R;
import com.seu.magiccamera.common.utils.Constants;
import com.seu.magiccamera.common.view.FilterLayoutUtils;
import com.seu.magicfilter.display.MagicCameraDisplay;

public class CameraActivity extends Activity{
	private MagicCameraDisplay mMagicCameraDisplay;
	private LinearLayout mFilterLayout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camera);
		
		initMagicPreview();
		initFilterLayout();
	}
	
	private void initFilterLayout(){
		findViewById(R.id.btn_camera_filter).setOnClickListener(btn_camera_filter_listener);
		findViewById(R.id.btn_camera_closefilter).setOnClickListener(btn_camera_filter_close_listener);
		findViewById(R.id.btn_camera_shutter).setOnClickListener(btn_camera_shutter_listener);
		findViewById(R.id.btn_camera_album).setOnClickListener(btn_camera_album_listener);
		
		mFilterLayout = (LinearLayout)findViewById(R.id.layout_filter);
		FilterLayoutUtils mFilterLayoutUtils = new FilterLayoutUtils(this, mMagicCameraDisplay);
		mFilterLayoutUtils.init();
	}
	
	private void initMagicPreview(){
		GLSurfaceView glSurfaceView = (GLSurfaceView)findViewById(R.id.glsurfaceView);
		FrameLayout.LayoutParams params = new LayoutParams(Constants.mScreenWidth, Constants.mScreenHeight);
		glSurfaceView.setLayoutParams(params);	
		mMagicCameraDisplay = new MagicCameraDisplay(this, glSurfaceView);
	}
		
	@Override
	protected void onPause() {
		super.onPause();
		if(mMagicCameraDisplay != null)
			mMagicCameraDisplay.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		if(mMagicCameraDisplay != null)
			mMagicCameraDisplay.onResume();
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if(mMagicCameraDisplay != null)
			mMagicCameraDisplay.onDestroy();
	}
	
	private OnClickListener btn_camera_shutter_listener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			mMagicCameraDisplay.onTakePicture(Constants.getOutputMediaFile(), null, null);
		}
	};
	
	private OnClickListener btn_camera_album_listener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			
		}
	};
	
	private OnClickListener btn_camera_filter_listener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			ObjectAnimator animator = ObjectAnimator.ofFloat(mFilterLayout, "translationY", mFilterLayout.getHeight(), 0);
			animator.setDuration(200);
			animator.addListener(new AnimatorListener() {
				
				@Override
				public void onAnimationStart(Animator animation) {
					findViewById(R.id.btn_camera_shutter).setClickable(false);
					mFilterLayout.setVisibility(View.VISIBLE);
				}
				
				@Override
				public void onAnimationRepeat(Animator animation) {
					
				}
				
				@Override
				public void onAnimationEnd(Animator animation) {
					
				}
				
				@Override
				public void onAnimationCancel(Animator animation) {
					
				}
			});
			animator.start();
		}
	};
	
	private OnClickListener btn_camera_filter_close_listener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			ObjectAnimator animator = ObjectAnimator.ofFloat(mFilterLayout, "translationY", 0 ,  mFilterLayout.getHeight());
			animator.setDuration(200);
			animator.addListener(new AnimatorListener() {
				
				@Override
				public void onAnimationStart(Animator animation) {
					// TODO Auto-generated method stub
				}
				
				@Override
				public void onAnimationRepeat(Animator animation) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onAnimationEnd(Animator animation) {
					// TODO Auto-generated method stub
					mFilterLayout.setVisibility(View.INVISIBLE);
					findViewById(R.id.btn_camera_shutter).setClickable(true);
				}
				
				@Override
				public void onAnimationCancel(Animator animation) {
					// TODO Auto-generated method stub
					mFilterLayout.setVisibility(View.INVISIBLE);
					findViewById(R.id.btn_camera_shutter).setClickable(true);
				}
			});
			animator.start();
		}
	};
}
