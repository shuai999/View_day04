package com.jack.view_day04;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Email 2185134304@qq.com
 * Created by JackChen on 2018/1/22.
 * Version 1.0
 * Description:
 */
public class ColorTrackTextView extends TextView {

    //1. 实现一个文字两种颜色中的 不变色的画笔、变色的画笔、当前的进度

    //绘制不变色的画笔
    private Paint mOriginPaint ;
    //绘制变色的画笔
    private Paint mChangePaint ;
    //当前的进度
    private float mCurrentProgress = 0.0f ; //0.5f：文字左边的一半是黑色，右边一半是红色

    //2. 实现不同的朝向  默认让它从左到右
    private Direction mDirection = Direction.LEFT_TO_RIGHT ;

    public enum Direction  {
        LEFT_TO_RIGHT , RIGHT_TO_LEFT
    }

    public ColorTrackTextView(Context context) {
        this(context,null);
    }

    public ColorTrackTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColorTrackTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initPaint(context, attrs) ;
    }


    /**
     * 初始化画笔
     * @param context
     * @param attrs
     */
    private void initPaint(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ColorTrackTextView);

        //此处颜色传递默认值，防止你在布局文件中没有指定颜色
        int originColor = typedArray.getColor(R.styleable.ColorTrackTextView_originColor, getTextColors().getDefaultColor());
        int changeColor = typedArray.getColor(R.styleable.ColorTrackTextView_changeColor, getTextColors().getDefaultColor());

        mOriginPaint = getPaintByColor(originColor) ;
        mChangePaint = getPaintByColor(changeColor) ;

        typedArray.recycle();
    }


    /**
     * 根据颜色获取画笔
     * @param color
     * @return
     */
    private Paint getPaintByColor(int color) {
        Paint paint = new Paint() ;
        //给画笔设置颜色
        paint.setColor(color);
        //设置抗锯齿
        paint.setAntiAlias(true);
        //防抖动
        paint.setDither(true);
        //设置字体的大小 就是TextView的大小
        paint.setTextSize(getTextSize());
        return paint;
    }


    /**
     *  一个文字两种颜色
     *   利用clipRect的 API，可以裁剪 左边用一个画笔去画，右边用另一个画笔去画，不断的改变中间值
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        //在这里，我们不能调用系统的super.onDraw(canvas) ，因为这里它调用的是系统TextView的画文字，我们需要自己去画，所以就注释掉它
//        super.onDraw(canvas);

        //获取中间位置
        int middle = (int)(mCurrentProgress*getWidth()) ;


        if (mDirection == Direction.LEFT_TO_RIGHT){  //从左到右 --> 左边是红色 右边是黑色
            //绘制不变色
            drawText(canvas , mChangePaint , 0 , middle);
            //绘制变色的  从中间值到整个宽度  左、上、右、下
            drawText(canvas , mOriginPaint , middle , getWidth());
        }else{                       //从右到左
            //绘制不变色
            drawText(canvas , mChangePaint , getWidth()-middle , getWidth());
            //绘制变色的  从中间值到整个宽度  左、上、右、下
            drawText(canvas , mOriginPaint , 0 , getWidth()-middle);
        }


    }



    /**
     *  画文字
     * @param canvas
     * @param paint  画笔
     * @param start  开始位置
     * @param end  结束位置
     */
    public void drawText(Canvas canvas , Paint paint , int start  , int end){

        canvas.save(); //保存画布

        Rect rect = new Rect(start, 0, end, getHeight());
        canvas.clipRect(rect);  //裁剪区域

        /** 画文字的套路 */
        // 我们自己来画
        String text = getText().toString();
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);

        /** 下边计算方法都是套路 */
        // 获取字体的宽度
        int x = getWidth() / 2 - bounds.width() / 2;
        // 基线baseLine
        Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
        int dy = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
        int baseLine = getHeight() / 2 + dy;
        /*public void drawText(@NonNull String text, int start, int end, float x, float y,
        @NonNull Paint paint)*/
        canvas.drawText(text, x, baseLine, paint);// 这么画其实还是只有一种颜色


        canvas.restore();  //释放画布
    }


    /**
     * 设置朝向
     * @param direction
     */
    public void setDirection(Direction direction){
        this.mDirection = direction ;
    }

    public void setCurrentProgress(float currentProgress){
        this.mCurrentProgress = currentProgress ;

        invalidate();
    }


    /**
     * 设置改变的颜色
     * @param color
     */
    public void setChangeColor(int color){
        this.mChangePaint.setColor(color);
    }

    public void setOriginColor(int color){
        this.mOriginPaint.setColor(color);
    }
}
