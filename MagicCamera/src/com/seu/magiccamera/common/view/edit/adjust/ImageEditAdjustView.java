package com.seu.magiccamera.common.view.edit.adjust;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.seu.magiccamera.R;
import com.seu.magiccamera.common.view.edit.ImageEditFragment;
import com.seu.magiccamera.widget.TwoLineSeekBar;
import com.seu.magiccamera.widget.TwoLineSeekBar.OnSeekChangeListener;
import com.seu.magicfilter.display.MagicImageDisplay;
import com.seu.magicfilter.filter.helper.MagicFilterType;

public class ImageEditAdjustView extends ImageEditFragment{
	private TwoLineSeekBar mSeekBar;
	private float contrast = -50.0f;
	private float exposure = 0.0f; 
	private float saturation = 0.0f;
	private float sharpness = 0.0f;
	private float brightness = 0.0f;
	private float hue = 0.0f;
	private RadioGroup mRadioGroup;
	private int type = MagicFilterType.NONE;
	private ImageView mLabel;
	private TextView mVal;
	private LinearLayout mLinearLayout;
	
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
				mLinearLayout.setVisibility(View.VISIBLE);
				switch (checkedId) {
				case R.id.fragment_radio_contrast:
					type = MagicFilterType.CONTRAST;
					mSeekBar.reset();
					mSeekBar.setSeekLength(-100, 100, -50, 1);
					mSeekBar.setValue(contrast);
					mLabel.setBackgroundResource(R.drawable.selector_image_edit_adjust_contrast);
					break;
				case R.id.fragment_radio_exposure:
					type = MagicFilterType.EXPOSURE;		
					mSeekBar.reset();
					mSeekBar.setSeekLength(-100, 100, 0, 1);
					mSeekBar.setValue(exposure);		
					mLabel.setBackgroundResource(R.drawable.selector_image_edit_adjust_exposure);
					break;
				case R.id.fragment_radio_saturation:
					type = MagicFilterType.SATURATION;			
					mSeekBar.reset();
					mSeekBar.setSeekLength(-100, 100, 0, 1);
					mSeekBar.setValue(saturation);		
					mLabel.setBackgroundResource(R.drawable.selector_image_edit_adjust_saturation);
					break;
				case R.id.fragment_radio_sharpness:
					type = MagicFilterType.SHARPEN;			
					mSeekBar.reset();
					mSeekBar.setSeekLength(-100, 100, 0, 1);
					mSeekBar.setValue(sharpness);	
					mLabel.setBackgroundResource(R.drawable.selector_image_edit_adjust_saturation);
					break;
				case R.id.fragment_radio_bright:
					type = MagicFilterType.BRIGHTNESS;			
					mSeekBar.reset();
					mSeekBar.setSeekLength(-100, 100, 0, 1);
					mSeekBar.setValue(brightness);						
					break;
				case R.id.fragment_radio_hue:
					type = MagicFilterType.HUE;		
					mSeekBar.reset();
					mSeekBar.setSeekLength(0, 360, 0, 1);
					mSeekBar.setValue(hue);								
					break;
				default:
					break;
				}
			}
		});
		mSeekBar = (TwoLineSeekBar)view.findViewById(R.id.item_seek_bar);
		mSeekBar.setOnSeekChangeListener(mOnSeekChangeListener);
		mVal = (TextView)view.findViewById(R.id.item_val);
		mLabel = (ImageView)view.findViewById(R.id.item_label);
		mLinearLayout = (LinearLayout)view.findViewById(R.id.seek_bar_item_menu);
		mMagicDisplay.setFilter(MagicFilterType.IMAGE_ADJUST);
	}
	
	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);
		if(hidden){
			contrast = -50.0f;
			exposure = 0.0f; 
			saturation = 0.0f;
			sharpness = 0.0f;
			brightness = 0.0f;
			hue = 0.0f;
			mRadioGroup.clearCheck();
			mMagicDisplay.setFilter(MagicFilterType.NONE);
			mLinearLayout.setVisibility(View.INVISIBLE);
			type = MagicFilterType.NONE;
		}else{
			mMagicDisplay.setFilter(MagicFilterType.IMAGE_ADJUST);
		}
	}
	
	protected boolean isChanged(){
		return contrast != -50.0f || exposure != 0.0f || saturation != 0.0f
				|| sharpness != 0.0f || brightness != 0.0f || hue != 0.0f;
	}
	
	private int convertToProgress(float value){
		switch (mRadioGroup.getCheckedRadioButtonId()) {
			case R.id.fragment_radio_contrast:
				contrast = value;	
				return (int) Math.round((value + 100) / 2);
			case R.id.fragment_radio_exposure:
				exposure = value;
				return (int) Math.round((value + 100) / 2);
			case R.id.fragment_radio_saturation:
				saturation = value;
				return (int) Math.round((value + 100) / 2);
			case R.id.fragment_radio_sharpness:
				sharpness = value;
				return (int) Math.round((value + 100) / 2);
			case R.id.fragment_radio_bright:
				brightness = value;
				return (int) Math.round((value + 100) / 2);
			case R.id.fragment_radio_hue:
				hue = value;
				return (int) Math.round(100 * value / 360.0f);
			default:
				return 0;
		}
	}
	
	private OnSeekChangeListener mOnSeekChangeListener = new OnSeekChangeListener() {
		
		@Override
		public void onSeekStopped(float value, float step) {
			// TODO Auto-generated method stub

		}
		
		@Override
		public void onSeekChanged(float value, float step) {
			// TODO Auto-generated method stub
			mVal.setText(""+value);
			mLabel.setPressed(value != 0.0f);
			mMagicDisplay.adjustFilter(convertToProgress(value), type);
		}
	};
}
