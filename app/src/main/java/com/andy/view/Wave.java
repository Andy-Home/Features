package com.andy.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2016/9/12 0012.
 */
public class Wave extends View {

    public Wave(Context context) {
        super(context);
    }

    public Wave(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Wave(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private Paint paint = new Paint();

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float mid = getWidth() / 2f;
        float radius = getHeight() / 2f;
        int width = getWidth();

        //画矩形
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(0, 0, getWidth(), getHeight(), paint);

        //画半圆条
        paint.setColor(Color.WHITE);
        //左半边
        for (float i = mid; i >= 0; i -= 2 * radius) {
            canvas.drawCircle(i - radius, getHeight(), radius, paint);
        }
        //右半边
        for (float i = mid; i <= width; i += 2 * radius) {
            canvas.drawCircle(i + radius, getHeight(), radius, paint);
        }
    }
}
