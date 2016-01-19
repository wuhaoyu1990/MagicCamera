package com.seu.magiccamera.common.view.edit.adjust;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.SeekBar;

import com.seu.magiccamera.R;
import com.seu.magiccamera.common.view.edit.ImageEditFragment;
import com.seu.magiccamera.widget.BubbleSeekBar;
import com.seu.magiccamera.widget.BubbleSeekBar.OnBubbleSeekBarChangeListener;
import com.seu.magicfilter.display.MagicImageDisplay;
import com.seu.magicfilter.filter.helper.MagicFilterType;

public class ImageEditAdjustView extends ImageEditFragment{
	private BubbleSeekBar mSeekBar;
	private int contrast = 25;
	private int exposure = 50; 
	private int saturation = 50;
	private int sharpness = 50;
	private int brightness = 50;
	private int hue = 0;
	private RadioGroup mRadioGroup;
	private int type = MagicFilterType.NONE;
	
	public ImageEditAdjustView(Context context, MagicImageDisplay mMagicDisplay) {
		super(context, mMagicDisplay);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_image_edit_adjust, container, false);  
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);	
		mRadioGroup = (RadioGroup)getView().findViewById(R.id.fragment_adjust_radiogroup);
		mRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				if(checkedId != -1)
				mSeekBar.setVisibility(View.VISIBLE);
				switch (checkedId) {
				case R.id.fragment_radio_contrast:
					type = MagicFilterType.CONTRAST;
					mSeekBar.setRange(0, 100);
					mSeekBar.setProgress(contrast);				
					break;
				case R.id.fragment_radio_exposure:
					type = MagicFilterType.EXPOSURE;
					mSeekBar.setRange(-100, 100);
					mSeekBar.setProgress(exposure);					
					break;
				case R.id.fragment_radio_saturation:
					type = MagicFilterType.SATURATION;
					mSeekBar.setRange(-100, 100);
					mSeekBar.setProgress(saturation);					
					break;
				case R.id.fragment_radio_sharpness:
					type = MagicFilterType.SHARPEN;
					mSeekBar.setRange(-100, 100);
					mSeekBar.setProgress(sharpness);				
					break;
				case R.id.fragment_radio_bright:
					type = MagicFilterType.BRIGHTNESS;
					mSeekBar.setRange(-100, 100);
					mSeekBar.setProgress(brightness);					
					break;
				case R.id.fragment_radio_hue:
					type = MagicFilterType.HUE;
					mSeekBar.setRange(0, 100);
					mSeekBar.setProgress(hue);					
					break;
				default:
					break;
				}
			}
		});
		mSeekBar = (BubbleSeekBar)view.findViewById(R.id.fragment_adjust_seekbar);
		mSeekBar.setOnBubbleSeekBarChangeListener(mOnColorBubbleSeekBarChangeListener);
		mMagicDisplay.setFilter(MagicFilterType.IMAGE_ADJUST);
	}
	
	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);
		if(hidden){
			contrast = 25;
			exposure = 50; 
			saturation = 50;
			sharpness = 50;
			brightness = 50;
			hue = 0;
			mRadioGroup.clearCheck();
			mMagicDisplay.setFilter(MagicFilterType.NONE);
			mSeekBar.setVisibility(View.GONE);
			type = MagicFilterType.NONE;
		}else{
			mMagicDisplay.setFilter(MagicFilterType.IMAGE_ADJUST);
		}
	}
	
	protected boolean isChanged(){
		return contrast != 25 || exposure != 50 || saturation != 50
				|| sharpness != 50 || brightness != 50 || hue != 0;
	}
	
	private void saveProgress(int progress){
		switch (mRadioGroup.getCheckedRadioButtonId()) {
		case R.id.fragment_radio_contrast:
			contrast = progress;
			break;
		case R.id.fragment_radio_exposure:
			exposure = progress;
			break;
		case R.id.fragment_radio_saturation:
			saturation = progress;
			break;
		case R.id.fragment_radio_sharpness:
			sharpness = progress;
			break;
		case R.id.fragment_radio_bright:
			brightness = progress;
			break;
		case R.id.fragment_radio_hue:
			hue = brightness;
			break;
		default:
			break;
		}
	}
	
	private OnBubbleSeekBarChangeListener mOnColorBubbleSeekBarChangeListener = new OnBubbleSeekBarChangeListener() {
		
		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {

		}
		
		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			
		}
		
		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			saveProgress(progress);
			mMagicDisplay.adjustFilter(progress, type);
		}
	};
}
