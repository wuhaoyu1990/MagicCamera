package com.seu.magicfilter;

import android.content.Context;

import com.seu.magicfilter.filter.helper.MagicFilterType;
import com.seu.magicfilter.utils.MagicParams;
import com.seu.magicfilter.helper.SavePictureTask;
import com.seu.magicfilter.widget.MagicCameraView;
import com.seu.magicfilter.widget.base.MagicBaseView;

import java.io.File;

/**
 * Created by why8222 on 2016/2/25.
 */
public class MagicEngine {

    private static Context context;

    private final MagicBaseView magicBaseView;

    private MagicEngine(Builder builder){
        magicBaseView = builder.magicBaseView;
        context = magicBaseView.getContext();
    }

    public static Context getContext(){
        return context;
    }

    public void onResume(){
        magicBaseView.onResume();
    }

    public void onDestroy(){
        magicBaseView.onDestroy();
    }

    public void onPause(){
        magicBaseView.onPause();
    }

    public void setFilter(MagicFilterType type){
        magicBaseView.setFilter(type);
    }

    public void savePicture(File file, SavePictureTask.OnPictureSaveListener listener){
        SavePictureTask savePictureTask = new SavePictureTask(file, listener);
        magicBaseView.savePicture(savePictureTask);
    }

    public void changeRecordingState(boolean isRecording){
        ((MagicCameraView)magicBaseView).changeRecordingState(isRecording);
    }

    public static class Builder{
        private MagicBaseView magicBaseView;

        public Builder(MagicBaseView magicBaseView){
            this.magicBaseView = magicBaseView;
        }

        public MagicEngine build() {
            return new MagicEngine(this);
        }

        public Builder setVideoSize(int width, int height){
            MagicParams.videoWidth = width;
            MagicParams.videoHeight = height;
            return this;
        }

        public Builder setVideoPath(String path){
            MagicParams.videoPath = path;
            return this;
        }
    }
}
