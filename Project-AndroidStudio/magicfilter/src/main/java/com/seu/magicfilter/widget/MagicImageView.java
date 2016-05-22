package com.seu.magicfilter.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.util.Log;

import com.seu.magicfilter.beautify.MagicJni;
import com.seu.magicfilter.filter.base.gpuimage.GPUImageFilter;
import com.seu.magicfilter.utils.OpenGlUtils;
import com.seu.magicfilter.utils.TextureRotationUtil;
import com.seu.magicfilter.helper.SavePictureTask;
import com.seu.magicfilter.widget.base.MagicBaseView;

import java.nio.ByteBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by why8222 on 2016/2/25.
 */
public class MagicImageView extends MagicBaseView{

    private final GPUImageFilter imageInput;

    private ByteBuffer _bitmapHandler = null;

    private Bitmap originBitmap = null;

    public MagicImageView(Context context) {
        this(context, null);
    }

    public MagicImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        imageInput = new GPUImageFilter();
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        super.onSurfaceCreated(gl, config);
        imageInput.init();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        super.onSurfaceChanged(gl, width, height);
        adjustSize(0, false, false);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        super.onDrawFrame(gl);
        if(textureId == OpenGlUtils.NO_TEXTURE)
            textureId = OpenGlUtils.loadTexture(getBitmap(), OpenGlUtils.NO_TEXTURE);
        if(filter == null)
            imageInput.onDrawFrame(textureId, gLCubeBuffer, gLTextureBuffer);
        else
            filter.onDrawFrame(textureId, gLCubeBuffer, gLTextureBuffer);
    }

    @Override
    public void savePicture(SavePictureTask savePictureTask) {

    }

    public void setImageBitmap(Bitmap bitmap) {
        if (bitmap == null || bitmap.isRecycled())
            return;
        setBitmap(bitmap);
        imageWidth = bitmap.getWidth();
        imageHeight = bitmap.getHeight();
        adjustSize(0, false, false);
        requestRender();
    }

    public void initMagicBeautify(){
        if(_bitmapHandler == null){
            Log.e("MagicSDK", "please storeBitmap first!!");
            return;
        }
        MagicJni.jniInitMagicBeautify(_bitmapHandler);
    }

    public void setBitmap(Bitmap bitmap){
        if(_bitmapHandler != null)
            freeBitmap();
        _bitmapHandler = MagicJni.jniStoreBitmapData(bitmap);
        originBitmap = bitmap;
    }

    public void freeBitmap(){
        if(_bitmapHandler == null)
            return;
        MagicJni.jniFreeBitmapData(_bitmapHandler);
        _bitmapHandler = null;
    }

    public Bitmap getBitmap(){
        if(_bitmapHandler == null)
            return null;
        return MagicJni.jniGetBitmapFromStoredBitmapData(_bitmapHandler);
    }
}
