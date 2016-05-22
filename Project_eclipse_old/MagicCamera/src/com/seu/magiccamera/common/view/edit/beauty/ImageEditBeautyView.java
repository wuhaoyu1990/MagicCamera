package com.seu.magiccamera.common.view.edit.beauty;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import com.seu.magiccamera.R;
import com.seu.magiccamera.common.view.edit.ImageEditFragment;
import com.seu.magiccamera.widget.BubbleSeekBar;
import com.seu.magiccamera.widget.BubbleSeekBar.OnBubbleSeekBarChangeListener;
import com.seu.magicfilter.display.MagicImageDisplay;
import com.seu.magicfilter.utils.MagicSDK;
import com.seu.magicfilter.utils.MagicSDK.MagicSDKListener;

public class ImageEditBeautyView extends ImageEditFragment{

	private RadioGroup mRadioGroup;
	private MagicSDK mMagicSDK;
	private RelativeLayout mSkinSmoothView;
	private RelativeLayout mSkinColorView;
	private BubbleSeekBar mSmoothBubbleSeekBar;
	private BubbleSeekBar mWhiteBubbleSeekBar;
	private boolean mIsSmoothed = false;
	private boolean mIsWhiten = false;
	public ImageEditBeautyView(Context context, MagicImageDisplay mMagicDisplay) {
		super(context, mMagicDisplay);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_image_edit_beauty, container, false);  
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		mSkinSmoothView = (RelativeLayout) getView().findViewById(R.id.fragment_beauty_skin);
		mSkinColorView = (RelativeLayout) getView().findViewById(R.id.fragment_beauty_color);
		mRadioGroup = (RadioGroup)getView().findViewById(R.id.fragment_beauty_radiogroup);
		mRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch (checkedId) {
				case R.id.fragment_beauty_btn_skinsmooth:
					mSkinSmoothView.setVisibility(View.VISIBLE);
					mSkinColorView.setVisibility(View.GONE);
					break;
				case R.id.fragment_beauty_btn_skincolor:
					mSkinColorView.setVisibility(View.VISIBLE);
					mSkinSmoothView.setVisibility(View.GONE);
					break;
				default:
					break;
				}
			}
		});
		mMagicSDK = MagicSDK.getInstance();
		mMagicSDK.setMagicSDKListener(mMagicSDKListener);	
		mSmoothBubbleSeekBar = (BubbleSeekBar) view.findViewById(R.id.fragment_beauty_skin_seekbar);
		mSmoothBubbleSeekBar.setOnBubbleSeekBarChangeListener(mOnSmoothBubbleSeekBarChangeListener);
		mWhiteBubbleSeekBar = (BubbleSeekBar) view.findViewById(R.id.fragment_beauty_white_seekbar);
		mWhiteBubbleSeekBar.setOnBubbleSeekBarChangeListener(mOnColorBubbleSeekBarChangeListener);
		init();
		super.onViewCreated(view, savedInstanceState);
	}
	
	private void init(){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				mMagicSDK.initMagicBeauty();
			}
		}).start();
	}
	
	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);
		if(!hidden){			
			mSmoothBubbleSeekBar.setProgress(0);
			mWhiteBubbleSeekBar.setProgress(0);
			init();
		}else{
			mMagicSDK.uninitMagicBeauty();
		}
	}

	private OnBubbleSeekBarChangeListener mOnSmoothBubbleSeekBarChangeListener = new OnBubbleSeekBarChangeListener() {
		
		@Override
		public void onStopTrackingTouch(final SeekBar seekBar) {
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					float level = seekBar.getProgress() / 10.0f;
					if(level < 0)
						level = 0;
					mMagicSDK.onStartSkinSmooth(level);
					if(seekBar.getProgress() != 0)
						mIsSmoothed = true;
					else
						mIsSmoothed = false;
				}
			}).start();
		}
		
		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			
		}
		
		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
		}
	};
	
	private OnBubbleSeekBarChangeListener mOnColorBubbleSeekBarChangeListener = new OnBubbleSeekBarChangeListener() {
		
		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			float level = seekBar.getProgress() / 20.0f;
			if(level < 1)
				level = 1;
			mMagicSDK.onStartWhiteSkin(level);
			if(seekBar.getProgress() != 0)
				mIsWhiten = true;
			else
				mIsWhiten = false;
		}
		
		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			
		}
		
		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
		}
	};
	
	private MagicSDKListener mMagicSDKListener = new MagicSDKListener() {
		
		@Override
		public void onEnd() {
			
		}
	};

	@Override
	protected boolean isChanged() {
		return mIsWhiten || mIsSmoothed;
	}

	@Override
	protected void onDialogButtonClick(DialogInterface dialog) {
		mMagicSDK.uninitMagicBeauty();
		super.onDialogButtonClick(dialog);
	}
}
