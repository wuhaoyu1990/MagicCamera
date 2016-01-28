package com.seu.magicfilter.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

public class SaveTask extends AsyncTask<Bitmap, Integer, String>{
	
	private onPictureSaveListener mListener;
	private Context mContext;
	private File mFile;
	public SaveTask(Context context, File file, onPictureSaveListener listener){
		this.mContext = context;
		this.mListener = listener;
		this.mFile = file;
	}
	
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
	}

	@Override
	protected void onPostExecute(final String result) {
		// TODO Auto-generated method stub
		if(result != null)
			MediaScannerConnection.scanFile(mContext,
	                new String[] {result}, null,
	                new MediaScannerConnection.OnScanCompletedListener() {
	                    @Override
	                    public void onScanCompleted(final String path, final Uri uri) {
	                        if (mListener != null) 
	                        	mListener.onSaved(result);                      
	                    }
            	});
	}

	@Override
	protected String doInBackground(Bitmap... params) {
		// TODO Auto-generated method stub
		if(mFile == null)
			return null;
		return saveBitmap(params[0]);
	}
	
	private String saveBitmap(Bitmap bitmap) {
		if (mFile.exists()) {
			mFile.delete();
		}
		try {
			FileOutputStream out = new FileOutputStream(mFile);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
			out.flush();
			out.close();
			bitmap.recycle();
			return mFile.toString();
		} catch (FileNotFoundException e) {
		   // TODO Auto-generated catch block
		   e.printStackTrace();
		} catch (IOException e) {
		   // TODO Auto-generated catch block
		   e.printStackTrace();
		}
		return null;
	}
	
	public interface onPictureSaveListener{
		void onSaved(String result);
	}
}
