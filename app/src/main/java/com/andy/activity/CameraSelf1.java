package com.andy.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.Button;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

import com.andy.Interface.CameraInterface;
import com.andy.features.R;
import com.andy.util.DisplayUtil;
import com.andy.view.CameraSurfaceView;

/**
 * Created by Administrator on 2016/9/7 0007.
 */
public class CameraSelf1 extends Activity implements CameraInterface.CamOpenOverCallback {
    private static final String TAG = "yanzi";
    CameraSurfaceView surfaceView = null;
    Button shutterBtn;
    float previewRate = -1f;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread openThread = new Thread() {
            @Override
            public void run() {
                CameraInterface.getInstance().doOpenCamera(CameraSelf1.this);
            }
        };
        openThread.start();
        setContentView(R.layout.activity_camera1);
        initUI();
        initViewParams();
    }

    private void initUI() {
        surfaceView = (CameraSurfaceView) findViewById(R.id.camera_surfaceview);
        shutterBtn = (Button) findViewById(R.id.btn_shutter);
        shutterBtn.setOnClickListener(new BtnListeners());
    }

    private void initViewParams() {
        LayoutParams params = surfaceView.getLayoutParams();
        Point p = DisplayUtil.getScreenMetrics(this);
        params.width = p.x;
        params.height = p.y;
        previewRate = DisplayUtil.getScreenRate(this); //默认全屏的比例预览
        surfaceView.setLayoutParams(params);
    }

    @Override
    public void cameraHasOpened() {
        SurfaceHolder holder = surfaceView.getSurfaceHolder();
        CameraInterface.getInstance().doStartPreview(holder, previewRate);
    }

    @Override
    public void photoDisplay(String path) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_image);
        ;
        ImageView imageView = (ImageView) dialog.findViewById(R.id.image);
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        imageView.setImageBitmap(bitmap);
        dialog.show();
    }

    private class BtnListeners implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_shutter:
                    CameraInterface.getInstance().doTakePicture();
                    break;
                default:
                    break;
            }
        }

    }

}
