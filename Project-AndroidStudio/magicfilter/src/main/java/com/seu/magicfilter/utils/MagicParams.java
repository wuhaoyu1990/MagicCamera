package com.seu.magicfilter.utils;

import android.os.Environment;

/**
 * Created by why8222 on 2016/2/26.
 */
public class MagicParams {
    public static int previewWidth = 1280;
    public static int previewHeight = 720;

    public static int videoWidth = 640;
    public static int videoHeight = 480;

    public static String videoPath = Environment.getExternalStorageDirectory().getPath();

    public MagicParams() {

    }
}
