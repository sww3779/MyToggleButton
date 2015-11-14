package com.sww.myToggleButton.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.sww.myToggleButton.R;

/**
 * Created by sww on 2015/10/14.
 */
public class ToggleButton extends View {


    private Boolean currentState=false;
    private Bitmap mSlideButtonBackground;
    private Bitmap mSwitchBackground;
    private int currentX;
    private boolean isTouching=false;
    private OnToggleChangedListener mOnToggleChangedListener;

    public ToggleButton(Context context) {
        super(context);
    }
   /* public ToggleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }*/

      public ToggleButton(Context context, AttributeSet attrs) {
          super(context, attrs);
        TypedArray ta=context.obtainStyledAttributes(attrs,R.styleable.ToggleButton);
        int switchBGResId=ta.getResourceId(R.styleable.ToggleButton_switchBackgroundID,-1);
        int slideBGStateId=ta.getResourceId(R.styleable.ToggleButton_slideButtonBackgroundID,-1);
       boolean currentState=ta.getBoolean(R.styleable.ToggleButton_slideButtonBackgroundID,false);
       ta.recycle();

           setSwitchBackground(switchBGResId);
           setSlideBackground(slideBGStateId);
           setCurrentState(currentState);
    }

    public void setSwitchBackground(int switch_background) {
        mSwitchBackground = BitmapFactory.decodeResource(getResources(), switch_background);
    }

    public void setSlideBackground(int slide_button_background) {
        mSlideButtonBackground = BitmapFactory.decodeResource(getResources(), slide_button_background);
    }

    public void setCurrentState(boolean state) {
        this.currentState = state;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(mSwitchBackground.getWidth(),mSwitchBackground.getHeight());
        //设置测量后的宽高给这个布局

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //1.把背景图片平铺在控件中
        canvas.drawBitmap(mSwitchBackground, 0, 0, null);
        if(isTouching){
            /**
             * 根据滑块位置绘制图形
             */
            int left=currentX-mSlideButtonBackground.getWidth()/2;
            if(left<0){
                left=0;
            }else if(left>mSwitchBackground.getWidth()-mSlideButtonBackground.getWidth()){
                left=mSwitchBackground.getWidth()-mSlideButtonBackground.getWidth();
            }
            canvas.drawBitmap(mSlideButtonBackground,left,0,null);

        }else{
            //2.根据当前的状态来绘制滑动块的位置
            if(currentState){
                //开关属于打开的状态，把滑动块移动到控件的右边
                int left=mSwitchBackground.getWidth()-mSlideButtonBackground.getWidth();
                canvas.drawBitmap(mSlideButtonBackground,left,0,null);
            }else{
                //开关处于关闭的状态，滑块在左边
                canvas.drawBitmap(mSlideButtonBackground,0,0,null);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                isTouching = true;
                currentX = (int) event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                currentX = (int) event.getX();
                break;
            case MotionEvent.ACTION_UP:
                isTouching=false;
                currentX = (int) event.getX();
                //触摸结束后更改当前滑块的状态
                int center=mSwitchBackground.getWidth()/2;
                boolean state=currentX>center;

                if(state!=currentState&&mOnToggleChangedListener!=null){
                    mOnToggleChangedListener.onToggleStateChanged(state);
                }
                currentState=state;


                break;
            default:
                break;

        }

        invalidate();//刷新当前控件

        return true;
    }
    public void setOnToggleButtonChangeListener(OnToggleChangedListener listener){
        this.mOnToggleChangedListener = listener;
    }
    public interface OnToggleChangedListener{
        /**
         * 开关状态改变时，触发此方法
         * true 打开，false关闭
         */
       public void onToggleStateChanged(boolean currentState);
    }


}
