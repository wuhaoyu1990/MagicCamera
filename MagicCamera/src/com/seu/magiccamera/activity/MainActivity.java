package com.seu.magiccamera.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import com.seu.magiccamera.R;
import com.seu.magiccamera.common.utils.Constants;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initConstants();
		this.findViewById(R.id.button1).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, CameraActivity.class);
				startActivity(intent);
			}
		});
		this.findViewById(R.id.button2).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, ImageActivity.class);
				startActivity(intent);
			}
		});
	}
	
	private void initConstants() {
		Point outSize = new Point();
		getWindowManager().getDefaultDisplay().getRealSize(outSize);
		Constants.mScreenWidth = outSize.x;
		Constants.mScreenHeight = outSize.y;
	}		
}
