package com.andy.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by Administrator on 2016/10/8 0008.
 */

public class SlideItemLayout extends ViewGroup {
    private float MAX_SLIDE_SPEED = 1000f;  //最大滑动速度
    private float deleteWidth;

    public SlideItemLayout(Context context) {
        super(context);
    }

    public SlideItemLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SlideItemLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        // 计算自定义的ViewGroup中所有子控件的大小
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        setMeasuredDimension(measureSize(widthMode, widthSize), measureSize(heightMode, heightSize));
    }

    /**
     * 测量高度与宽度
     *
     * @param mode
     * @return
     */
    private int measureSize(int mode, int s) {
        int size = 0;
        switch (mode) {
            case MeasureSpec.AT_MOST:
            case MeasureSpec.EXACTLY:
                size = s;
                break;
        }
        return size;
    }

    @Override
    protected void onLayout(boolean change, int l, int t, int r, int b) {
        int right = 0;
        int size = getChildCount();
        //获取删除按钮的宽度
        deleteWidth = getChildAt(size - 1).getMeasuredWidth();
        for (int i = 0; i < size; i++) {
            View view = getChildAt(i);

            int childHeight = view.getMeasuredHeight();
            int chileWidth = view.getMeasuredWidth();

            view.layout(right, 0, right + chileWidth, childHeight);
            right += chileWidth;
        }
    }

    private float startX = 0;
    private float endX = 0;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = ev.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                if (slideSpeed(ev) >= MAX_SLIDE_SPEED) {
                    return true;
                }
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                endX = event.getX();
                if (endX - startX < 0 && startX - endX <= deleteWidth) {      //从右向左滑动
                    scrollTo((int) (startX - endX), 0);
                }
                break;
            case MotionEvent.ACTION_UP:
                endX = event.getX();
                if (startX - endX < deleteWidth / 2) {
                    scrollTo(0, 0);
                } else {
                    scrollTo((int) deleteWidth, 0);
                }
                releaseVelocityTracker();
                break;
            case MotionEvent.ACTION_CANCEL:
                endX = event.getX();
                if (startX - endX < deleteWidth / 2) {
                    scrollTo(0, 0);
                } else {
                    scrollTo((int) deleteWidth, 0);
                }
                releaseVelocityTracker();
                break;
        }
        return super.onTouchEvent(event);
    }

    private VelocityTracker mVelocityTracker;

    /**
     * 计算侧滑速度
     *
     * @param event
     * @return
     */
    private float slideSpeed(MotionEvent event) {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);

        final VelocityTracker velocityTracker = mVelocityTracker;
        velocityTracker.computeCurrentVelocity(1000, MAX_SLIDE_SPEED);

        return velocityTracker.getXVelocity();
    }

    /**
     * 释放 VelocityTracker
     */
    private void releaseVelocityTracker() {
        if (mVelocityTracker != null) {
            mVelocityTracker.clear();
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }

    /**
     * 返回到初始状态
     */
    public void reset() {
        scrollTo(0, 0);
    }
}
