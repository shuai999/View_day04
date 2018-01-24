package com.jack.view_day04;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Email 2185134304@qq.com
 * Created by JackChen on 2018/1/22.
 * Version 1.0
 * Description:
 */
public class ViewPagerActivity extends AppCompatActivity {


    private String[] items = {"直播", "推荐", "视频", "图片", "段子", "精华"};
    private LinearLayout mIndicatorContainer;// 变成通用的
    private List<ColorTrackTextView> mIndicators;
    private ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);

        mIndicators = new ArrayList<>() ;
        mIndicatorContainer = (LinearLayout) findViewById(R.id.indicator_view);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);

        //初始化指示器
        initIndicator() ;
        //初始化ViewPager
        initViewPager() ;


    }


    /**
     * 初始化ViewPager
     */
    private void initViewPager() {
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return ItemFragment.newInstance(items[position]);
            }

            @Override
            public int getCount() {
                return items.length;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {

            }
        });


        //ViewPager添加滑动监听器
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                //position:代表当前页码位置
                        //第0个item的position为0 ，第1个item的position为1，第2个item的position为2 等等

                //positionOffset:代表滚动的 0-1的百分比
                        //0.0-0.009259259-0.031481482-0.05462963-0.08055556-等等 -0.0

                Log.e("TAG" , "position -> "+position+" positionOffset ->"+positionOffset) ;

                //1.左边 位置 position 
                ColorTrackTextView left = mIndicators.get(position);
                left.setDirection(ColorTrackTextView.Direction.RIGHT_TO_LEFT);
                left.setCurrentProgress(1-positionOffset);


                try {
                    //2.右边   为防止最后一个精华item角标越界，所以添加try catch
                    ColorTrackTextView right = mIndicators.get(position + 1);
                    right.setDirection(ColorTrackTextView.Direction.LEFT_TO_RIGHT);
                    right.setCurrentProgress(positionOffset);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    /**
     * 初始化可变色的指示器
     */
    private void initIndicator() {
        for (int i = 0; i < items.length; i++) {
            // 动态添加颜色跟踪的TextView
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            params.weight = 1;
            ColorTrackTextView colorTrackTextView = new ColorTrackTextView(this);
            // 设置颜色
            colorTrackTextView.setTextSize(20);
            colorTrackTextView.setChangeColor(Color.RED);
            colorTrackTextView.setText(items[i]);
            colorTrackTextView.setLayoutParams(params);
            // 把新的加入LinearLayout容器
            mIndicatorContainer.addView(colorTrackTextView);
            // 加入集合
            mIndicators.add(colorTrackTextView);
        }
    }
}
