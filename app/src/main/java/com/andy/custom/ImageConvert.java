package com.andy.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;


/**
 * Created by Administrator on 2016/7/29 0029.
 */
public class ImageConvert extends ImageView{
    /**
     * 图片放大
     */
    private static final int MODE_ZOOM = 0;

    /**
     * 图片移动
     */
    private static final int MODE_DRAG = 1;

    /**
     * 当前的状态值,默认为-1，-2代表初始为单点触控，-3代表初始为两点触控
     */
    private int current_status = -1;

    /**
     * 初始触屏点
     */
    private Point startPoint = new Point();

    /**
     * 缩放的两个值，用来计算两者之间的比例值
     */
    private double startDis;
    private double endDis;

    /**
     * 缩放的中点
     */
    private PointF midPoint;

    /**
     * 显示的图片资源
     */
    private Bitmap source;

    /**
     * 图片的变换状态
     */
    private Matrix matrix = new Matrix();;

    /**
     * 当前状态值
     */
    private Matrix currentMatrix = new Matrix();

    public ImageConvert(Context context) {
        super(context, null);
    }

    public ImageConvert(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public ImageConvert(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(matrix == null){
            // 画出原图像
            canvas.drawBitmap(source, 0, 0, null);
        }else{
            // 画出变换后的图像
            canvas.drawBitmap(source, matrix, null);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK){
            case MotionEvent.ACTION_MOVE:
                if(current_status == MODE_DRAG){
                    float dx = event.getX() - startPoint.x;
                    float dy = event.getY() - startPoint.y;
                    matrix.set(currentMatrix);
                    matrix.postTranslate(dx, dy);
                    Log.e("移动","x = " + dx + " y = " + dy);
                }else if(current_status == MODE_ZOOM){
                    endDis = distance(event);
                    //横竖轴放大倍数一样，保证图片比例
                    float scale = (float) (endDis/startDis);
                    Log.e("放大倍数",scale + "倍");
                    matrix.set(currentMatrix);
                    matrix.postScale(scale,scale,midPoint.x,midPoint.y);
                }
                invalidate();

                break;

            case MotionEvent.ACTION_DOWN:
                startPoint.set((int)event.getX(), (int)event.getY());
                currentMatrix.set(matrix);
                current_status = MODE_DRAG;
                return true;

            case MotionEvent.ACTION_POINTER_DOWN:
                startDis = distance(event);
                midPoint = mid(event);
                current_status = MODE_ZOOM;
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                current_status = -1;
                break;
        }
        return super.onTouchEvent(event);
    }

    public void setBitmap(Bitmap bitmap){
        source = bitmap;
    }

    /**
     * 计算双点触控的时候两触控点的距离
     *
     * @param event
     * @return
     */
    private double distance(MotionEvent event){
        float dx = event.getX(1) - event.getX(0);
        float dy = event.getY(1) - event.getY(0);
        return Math.sqrt( dx * dx + dy * dy);
    }

    /**
     * 计算两点之间的中点位置
     *
     * @param event
     * @return
     */
    private PointF mid(MotionEvent event) {
        float midX = (event.getX(1) + event.getX(0)) / 2;
        float midY = (event.getY(1) + event.getY(0)) / 2;
        return new PointF(midX, midY);
    }
}
