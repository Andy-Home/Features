package com.andy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.andy.features.R;

/**
 * Created by Administrator on 2016/8/31 0031.
 */
public class Camera extends AppCompatActivity implements View.OnClickListener {
    private Button button1, button2, button3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        getSupportActionBar().setTitle("相机");
        findViewById();
        setListener();
    }

    private void findViewById() {
        button1 = (Button) findViewById(R.id.camera1);
        button2 = (Button) findViewById(R.id.camera2);
        button3 = (Button) findViewById(R.id.camera3);
    }

    private void setListener() {
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.camera1:
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivity(intent);
                break;
            case R.id.camera2:
                Intent intent1 = new Intent(this, CameraSelf.class);
                startActivity(intent1);
                break;
            case R.id.camera3:
                break;
            default:
        }
    }
}
