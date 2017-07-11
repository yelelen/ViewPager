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

import java.util.ArrayList;

public class MainActivity extends Activity {
    private ViewPager mPager;
    private int[] mViewIds = {R.layout.pager1, R.layout.pager2, R.layout.pager3, R.layout.pager4};
    private View[] mViews = new View[mViewIds.length];
    ArrayList<ImageView> mIndicators = new ArrayList<>();
    private int lastClicked = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        mPager = (ViewPager)findViewById(R.id.pager);
        LinearLayout ll = (LinearLayout)findViewById(R.id.indicator);

        MyClickListener listener = new MyClickListener();

        for (int i = 0; i < mViewIds.length; i++){
            mViews[i] = getLayoutInflater().inflate(mViewIds[i],null);
            ImageView iv = new ImageView(this);
            if (i == 0) {
                iv.setImageResource(R.drawable.shape_selected);
            } else {
                iv.setImageResource(R.drawable.shape_normal);
            }
            iv.setPadding(DensityUtils.dp2px(this, 20), 0, 0, 0);
            iv.setOnClickListener(listener);
            iv.setId(i);
            ll.addView(iv);
            mIndicators.add(iv);
        }

        mPager.setAdapter(new MyPagerAdapter());
        mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mIndicators.get(lastClicked).setImageResource(R.drawable.shape_normal);
                mIndicators.get(position).setImageResource(R.drawable.shape_selected);
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
                ((ImageView)v).setImageResource(R.drawable.shape_selected);
                mIndicators.get(lastClicked).setImageResource(R.drawable.shape_normal);
                lastClicked = v.getId();
                mPager.setCurrentItem(v.getId());
            }

        }
    }

}
