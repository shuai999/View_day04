package com.jack.view_day04;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

/**
 * Email 2185134304@qq.com
 * Created by JackChen on 2018/1/22.
 * Version 1.0
 * Description:
 */
public class MainActivity extends AppCompatActivity {

    private ColorTrackTextView color_track_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        color_track_tv = (ColorTrackTextView) findViewById(R.id.color_track_tv);
    }


    public void leftToRight(View view){
        //从左到右
        color_track_tv.setDirection(ColorTrackTextView.Direction.LEFT_TO_RIGHT);
        //属性动画,从0变到1
        ValueAnimator valueAnimator = ObjectAnimator.ofFloat(0, 1);
        //时长 2秒钟从0变到1
        valueAnimator.setDuration(2000) ;
        //添加属性动画的监听
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //获取当前进度
                float currentProgress = (float) animation.getAnimatedValue();
                color_track_tv.setCurrentProgress(currentProgress);
            }
        });
        valueAnimator.start();
    }
    
    
    public void rightToLeft(View view){
        //从右到左
        color_track_tv.setDirection(ColorTrackTextView.Direction.RIGHT_TO_LEFT);
        //属性动画
        ValueAnimator valueAnimator = ObjectAnimator.ofFloat(0, 1);
        //时长
        valueAnimator.setDuration(2000) ;
        //添加属性动画的监听
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float currentProgress = (float) animation.getAnimatedValue();
                color_track_tv.setCurrentProgress(currentProgress);
            }
        });
        valueAnimator.start();
        }
}
