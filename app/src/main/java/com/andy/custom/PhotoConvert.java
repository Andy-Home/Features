package com.andy.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.ImageView;


/**
 * Created by Administrator on 2016/7/29 0029.
 */
public class PhotoConvert extends ImageView {
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

    /**
     * 图片的左上角坐标
     */
    private PointF leftPointF;

    /**
     * 图片的右下角坐标
     */
    private PointF rightPointF;

    /**
     * 当前的 matrix 值
     */
    float[] values=new float[9];

    public PhotoConvert(Context context) {
        super(context, null);
    }

    public PhotoConvert(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public PhotoConvert(Context context, AttributeSet attrs, int defStyleAttr) {
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
            matrix.getValues(values);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK){

            case MotionEvent.ACTION_MOVE:
                if(current_status == MODE_DRAG){
                    matrix.set(currentMatrix);
                    float dx = event.getX() - startPoint.x;
                    float dy = event.getY() - startPoint.y;
                    matrix.postTranslate(dx, dy);
                    Log.e("移动","x = " + dx + " y = " + dy);
                }else if(current_status == MODE_ZOOM){
                    endDis = distance(event);
                    //横竖轴放大倍数一样，保证图片比例
                    float scale = (float) (endDis/startDis);
                    Log.e("放大倍数",scale + "倍");
                    matrix.set(currentMatrix);
                    matrix.postScale(scale,scale,midPoint.x,midPoint.y);
                    //checkMinimum();
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

                checkOutBounds();

                current_status = -1;
                invalidate();
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 检查图片移动时候的越界情况
     */
    private void checkOutBounds(){
        leftPointF = getLeftPointF();
        rightPointF = getRightPointF();
        //缩小
        if(values[Matrix.MSCALE_X] < 1f){
            if(leftPointF.x < 0){
                matrix.postTranslate(-leftPointF.x, 0);
            }
            if(leftPointF.y < 0){
                matrix.postTranslate(0, -leftPointF.y);
            }
            if(rightPointF.x > this.getWidth()){
                matrix.postTranslate(this.getWidth() - rightPointF.x, 0);
            }
            if(rightPointF.y > this.getHeight()){
                matrix.postTranslate(0, this.getHeight()-rightPointF.y);
            }
        }else{
            if(leftPointF.x > 0){
                matrix.postTranslate(-leftPointF.x, 0);
            }
            if(leftPointF.y > 0){
                matrix.postTranslate(0, -leftPointF.y);
            }
            if(rightPointF.x < this.getWidth()){
                matrix.postTranslate(this.getWidth() - rightPointF.x, 0);
            }
            if(rightPointF.y < this.getHeight()){
                matrix.postTranslate(0, this.getHeight()-rightPointF.y);
            }
        }
    }

    /**
     * 判断图片的缩小是否小于原图
     */
    private void checkMinimum(){
        matrix.getValues(values);
        //防止缩放图片比原图片更小
        if(values[Matrix.MSCALE_X] < 1f){
            matrix.reset();
        }
    }

    /**
     * 设置图片资源
     *
     * @param bitmap
     */
    public void setBitmap(Bitmap bitmap){
        source = bitmap;

        source = resizeBitmap(bitmap);
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

    /**
     * 改变图片的初始大小
     *
     * @param bitmap
     * @return
     */
    public Bitmap resizeBitmap(Bitmap bitmap)
    {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        //获取屏幕长宽
        WindowManager manager = (WindowManager) getContext().getSystemService(
                Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        //计算充满屏幕需要缩放的倍数
        float scaleWight = ((float)outMetrics.widthPixels)/width;
        float scaleHeight = ((float)outMetrics.heightPixels)/height;
        //等比例缩放
        Matrix matrix = new Matrix();
        float scale = scaleWight > scaleHeight ? scaleHeight:scaleWight;
        matrix.postScale(scale, scale);

        Bitmap res = Bitmap.createBitmap(bitmap, 0,0,width, height, matrix, true);
        return res;
    }

    /**
     * 获取图片左上角坐标
     *
     * @return
     */
    private PointF getLeftPointF()
    {
        float[] values = new float[9];
        matrix.getValues(values);
        float leftX=values[2];
        float leftY=values[5];
        return new PointF(leftX,leftY);
    }

    /**
     * 获取图片右下角坐标
     *
     * @return
     */
    private PointF getRightPointF()
    {
        float[] values = new float[9];
        matrix.getValues(values);
        float leftX= values[2]+  source.getWidth()*values[0];
        float leftY=values[5]+ source.getHeight()*values[4];
        return new PointF(leftX,leftY);
    }
}
