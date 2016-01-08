package com.seu.magicfilter.filter.helper;

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
	
	public SaveTask(Context context, onPictureSaveListener listener){
		this.mContext = context;
		this.mListener = listener;
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
		return saveBitmap(params[0]);
	}
	
	private String saveBitmap(Bitmap bitmap) {
		File file = getOutputMediaFile();
		if (file.exists()) {
			file.delete();
		}
		try {
			FileOutputStream out = new FileOutputStream(file);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
			out.flush();
			out.close();
			bitmap.recycle();
			return file.toString();
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
	
	private File getOutputMediaFile() {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "MagicCamera");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",Locale.CHINESE).format(new Date());
        File mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_" + timeStamp + ".jpg");

        return mediaFile;
    }
}
