package com.andy.activity;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.andy.custom.ImageConvert;
import com.andy.features.R;

/**
 * Created by Administrator on 2016/8/8 0008.
 */
public class ImageActivity extends Activity implements View.OnTouchListener {
    private ImageConvert mImageConvert;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        findView();
        getData();
        initView();
    }

    private void findView() {
        mImageConvert = (ImageConvert) findViewById(R.id.image_convert);
    }

    private void getData() {

    }

    private void initView() {
        mImageConvert.setBitmap(BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.test));
        mImageConvert.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return false;
    }
}
