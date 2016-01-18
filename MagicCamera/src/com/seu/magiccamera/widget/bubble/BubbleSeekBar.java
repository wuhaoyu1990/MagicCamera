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
	private int start = 0;
	private int	end= 100;
	
	public BubbleSeekBar(Context context) {
		this(context, null);
	}

	public BubbleSeekBar(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public BubbleSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		mBubbleIndicator = new BubbleIndicator(context);
		setOnSeekBarChangeListener(mOnSeekBarChangeListener);
	}
	
    @Override
    public void setThumb(Drawable thumb) {
        super.setThumb(thumb);
        mThumbDrawable = thumb;
    }
	
    public void setRange(int start, int end){
    	this.start = start;
    	this.end = end;
    }
    
    public void setMax(){
    	super.setMax(100);
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
				mBubbleIndicator.moveIndicator(mThumbDrawable.getBounds(), start + (int) ((end - start) * progress / 100.0f));
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
