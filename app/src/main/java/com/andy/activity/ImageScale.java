package com.andy.activity;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;

import com.andy.custom.PhotoConvert;
import com.andy.features.R;

/**
 * Created by Administrator on 2016/8/8 0008.
 */
public class ImageScale extends AppCompatActivity implements View.OnTouchListener {
    private PhotoConvert mImageConvert;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        findView();
        getData();
        initView();
    }

    private void findView() {
        mImageConvert = (PhotoConvert) findViewById(R.id.image_convert);
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
