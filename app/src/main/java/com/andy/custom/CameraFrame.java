package com.andy.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2016/9/7 0007.
 */
public class CameraFrame extends View {

    public CameraFrame(Context context) {
        super(context);
    }

    public CameraFrame(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CameraFrame(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();
        paint.setStrokeWidth(6);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.RED);

        float baseWidth = getWidth() / 6;
        float baseHight = (baseWidth * 4 * 1.58f) / 2;

        canvas.drawRect(baseWidth, getHeight() / 2 - baseHight, baseWidth * 5, getHeight() / 2 + baseHight, paint);
    }
}
