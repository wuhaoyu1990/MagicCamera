package com.seu.magiccamera.widget.bubble;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.SeekBar;

public class BubbleSeekBar extends SeekBar{
	private Drawable mThumbDrawable;
	private BubbleIndicator mBubbleIndicator;
	private boolean mIsListenerSet = false;
	private OnBubbleSeekBarChangeListener mOnBubbleSeekBarChangeListener;
	public BubbleSeekBar(Context context) {
		this(context, null);
	}

	public BubbleSeekBar(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public BubbleSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		mBubbleIndicator = new BubbleIndicator(context, attrs, defStyleAttr, "100");
		setOnSeekBarChangeListener(mOnSeekBarChangeListener);
	}
	
    @Override
    public void setThumb(Drawable thumb) {
        super.setThumb(thumb);
        mThumbDrawable = thumb;
    }
	  
	private OnSeekBarChangeListener mOnSeekBarChangeListener = new OnSeekBarChangeListener() {
		
		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			mBubbleIndicator.hideIndicator();
			if(mOnBubbleSeekBarChangeListener != null)
				mOnBubbleSeekBarChangeListener.onStopTrackingTouch(seekBar);
		}
		
		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			mBubbleIndicator.showIndicator(seekBar, mThumbDrawable.getBounds());
			if(mOnBubbleSeekBarChangeListener != null)
				mOnBubbleSeekBarChangeListener.onStartTrackingTouch(seekBar);
		}
		
		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			if(fromUser)
				mBubbleIndicator.moveIndicator(mThumbDrawable.getBounds(), progress);
			if(mOnBubbleSeekBarChangeListener != null)
				mOnBubbleSeekBarChangeListener.onProgressChanged(seekBar, progress, fromUser);
		}
	};
	
	/**
     * Use OnBubbleSeekBarChangeListener instead of OnSeekBarChangeListener
     * 
     * @param l The seek bar notification listener
     * 
     * @see BubbleSeekBar.OnBubbleSeekBarChangeListener
     */
	public void setOnSeekBarChangeListener(OnSeekBarChangeListener l) {
		if(mIsListenerSet)
			Log.e("BubbleSeekBar","Use OnBubbleSeekBarChangeListener instead of OnSeekBarChangeListener!!!!!");
		else
			super.setOnSeekBarChangeListener(l);
        mIsListenerSet = true;
    }
     
	public void setOnBubbleSeekBarChangeListener(OnBubbleSeekBarChangeListener l){
		this.mOnBubbleSeekBarChangeListener = l;
	}
	
	public interface OnBubbleSeekBarChangeListener{
		void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser);
		    
        void onStartTrackingTouch(SeekBar seekBar);
	        
        void onStopTrackingTouch(SeekBar seekBar);
	}
	
}
