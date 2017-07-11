package com.yll.viewpager;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;

public class MainActivity extends Activity {
    private ViewPager mPager;
    private int[] mViewIds = {R.layout.pager1, R.layout.pager2, R.layout.pager3, R.layout.pager4};
    private View[] mViews = new View[mViewIds.length];
    ArrayList<ImageView> mIndicators = new ArrayList<>();
    private int lastClicked = 0;
    private int pWidth = 0; // 两个点之间的距离
    private View mSelectPoint;
    private LinearLayout ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        mPager = (ViewPager)findViewById(R.id.pager);
        ll = (LinearLayout)findViewById(R.id.indicator);
        mSelectPoint = findViewById(R.id.select_point);

        MyClickListener listener = new MyClickListener();

        for (int i = 0; i < mViewIds.length; i++){
            mViews[i] = getLayoutInflater().inflate(mViewIds[i],null);
            ImageView iv = new ImageView(this);
            if (i != 0) {
                iv.setPadding(DensityUtils.dp2px(this, 30), 0, 0, 0);
            }
            iv.setImageResource(R.drawable.shape_normal);
            iv.setOnClickListener(listener);
            iv.setId(i);
            ll.addView(iv);
            mIndicators.add(iv);
        }

        mPager.setAdapter(new MyPagerAdapter());
        mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            /**
             * 当页面正在滚动时 position 当前选中的是哪个页面 positionOffset 比例 positionOffsetPixels 偏移像素
             */
            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
                //获取两个点间的距离,获取一次即可
                if(pWidth==0) {
                    pWidth = ll.getChildAt(1).getLeft() - ll.getChildAt(0).getLeft() ;
                }


                // 获取点要移动的距离
                int leftMargin = (int) (pWidth * (position + positionOffset));
                // 给红点设置参数
                RelativeLayout.LayoutParams params = (android.widget.RelativeLayout.LayoutParams) mSelectPoint
                        .getLayoutParams();
                params.leftMargin = leftMargin << 1;
                mSelectPoint.setLayoutParams(params);
            }

            @Override
            public void onPageSelected(int position) {
                lastClicked = position;

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

//        mPager.setPageTransformer(true, new ZoomOutPageTransformer());
        mPager.setPageTransformer(true, new DepthPagerTransformer());
    }

    private class MyPagerAdapter extends PagerAdapter{
        @Override
        public int getCount() {
            return mViewIds.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mViews[position]);

            return mViews[position];
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

            container.removeView(mViews[position]);
        }
    }

    private class MyClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            if (v.getId() != lastClicked){
                lastClicked = v.getId();
                mPager.setCurrentItem(v.getId());
            }

        }
    }

}
