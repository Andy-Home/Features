package com.andy.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

/**
 * Created by Administrator on 2016/10/9 0009.
 */

public class SlideListView extends ListView {
    private SlideItemLayout mCurrentItemView;
    private int prePosition = -1;

    public SlideListView(Context context) {
        super(context);
    }

    public SlideListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SlideListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                int position = pointToPosition((int) ev.getX(), (int) ev.getY());
                if (position >= 0 && position != prePosition) {
                    View currentItemView = getChildAt(position - getFirstVisiblePosition());
                    if (mCurrentItemView != null) {
                        mCurrentItemView.reset();
                    }
                    mCurrentItemView = (SlideItemLayout) currentItemView;
                    prePosition = position;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                //当设置状态为不能启用时不再向下传递滑动事件
                if (!isEnabled()) {
                    return true;
                }
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }
}
