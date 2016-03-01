package com.seu.magiccamera;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.seu.magicfilter.MagicEngine;
import com.seu.magicfilter.filter.helper.MagicFilterType;
import com.seu.magicfilter.widget.MagicCameraView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private MagicEngine magicEngine;

    private boolean isRecording = false;

    private Button btn_record;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MagicEngine.Builder builder = new MagicEngine.Builder((MagicCameraView) findViewById(R.id.test));
        magicEngine = builder
                .setVideoSize(720, 1280)
                .build();
        btn_record = (Button) findViewById(R.id.btn_record);
        btn_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isRecording = !isRecording;
                magicEngine.changeRecordingState(isRecording);
                btn_record.setText(isRecording ? "Stop" : "Start");
            }
        });
        findViewById(R.id.btn_filter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(MainActivity.this).setTitle("Choose a filter")
                        .setSingleChoiceItems(filters, 0,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        magicEngine.setFilter(types[which]);
                                        dialog.dismiss();
                                    }
                                }).setNegativeButton("Cancel", null).show();
            }
        });
        findViewById(R.id.btn_capture).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                magicEngine.savePicture(getOutputMediaFile(),null);
            }
        });
    }

    public static File getOutputMediaFile() {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "MagicCamera");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINESE).format(new Date());
        File mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                "IMG_" + timeStamp + ".jpg");

        return mediaFile;
    }
    @Override
    protected void onResume() {
        super.onResume();
        magicEngine.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        magicEngine.onPause();
    }

    private final String[] filters = new String[]{"NONE",
            "FAIRYTALE",
            "SUNRISE",
            "SUNSET",
            "WHITECAT",
            "BLACKCAT",
            "BEAUTY",
            "SKINWHITEN",
            "HEALTHY",
            "SWEETS",
            "ROMANCE",
            "SAKURA",
            "WARM",
            "ANTIQUE",
            "NOSTALGIA",
            "CALM",
            "LATTE",
            "TENDER",
            "COOL",
            "EMERALD",
            "EVERGREEN",
            "CRAYON",
            "SKETCH",
            "AMARO",
            "BRANNAN",
            "BROOKLYN",
            "EARLYBIRD",
            "FREUD",
            "HEFE",
            "HUDSON",
            "INKWELL",
            "KEVIN",
            "LOMO",
            "N1977",
            "NASHVILLE",
            "PIXAR",
            "RISE",
            "SIERRA",
            "SUTRO",
            "TOASTER2",
            "VALENCIA",
            "WALDEN",
            "XPROII"};

    private final MagicFilterType[] types = new MagicFilterType[]{
            MagicFilterType.NONE,
            MagicFilterType.FAIRYTALE,
            MagicFilterType.SUNRISE,
            MagicFilterType.SUNSET,
            MagicFilterType.WHITECAT,
            MagicFilterType.BLACKCAT,
            MagicFilterType.BEAUTY,
            MagicFilterType.SKINWHITEN,
            MagicFilterType.HEALTHY,
            MagicFilterType.SWEETS,
            MagicFilterType.ROMANCE,
            MagicFilterType.SAKURA,
            MagicFilterType.WARM,
            MagicFilterType.ANTIQUE,
            MagicFilterType.NOSTALGIA,
            MagicFilterType.CALM,
            MagicFilterType.LATTE,
            MagicFilterType.TENDER,
            MagicFilterType.COOL,
            MagicFilterType.EMERALD,
            MagicFilterType.EVERGREEN,
            MagicFilterType.CRAYON,
            MagicFilterType.SKETCH,
            MagicFilterType.AMARO,
            MagicFilterType.BRANNAN,
            MagicFilterType.BROOKLYN,
            MagicFilterType.EARLYBIRD,
            MagicFilterType.FREUD,
            MagicFilterType.HEFE,
            MagicFilterType.HUDSON,
            MagicFilterType.INKWELL,
            MagicFilterType.KEVIN,
            MagicFilterType.LOMO,
            MagicFilterType.N1977,
            MagicFilterType.NASHVILLE,
            MagicFilterType.PIXAR,
            MagicFilterType.RISE,
            MagicFilterType.SIERRA,
            MagicFilterType.SUTRO,
            MagicFilterType.TOASTER2,
            MagicFilterType.VALENCIA,
            MagicFilterType.WALDEN,
            MagicFilterType.XPROII
    };
}
