package com.example.lilyde;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * 我想实现的是:
     */

    /**
     * 内存优化
     * @Override
     * public void onScrollStateChanged(AbsListView view, int scrollState) {
     *     switch (scrollState){
     *         case SCROLL_STATE_FLING:
     *             Log.i("ListView","用户在手指离开屏幕之前，由于滑了一下，视图仍然依靠惯性继续滑动");
     *             Glide.with(getApplicationContext()).pauseRequests();
     *             //刷新
     *             break;
     *         case SCROLL_STATE_IDLE:
     *             Log.i("ListView", "视图已经停止滑动");
     *             Glide.with(getApplicationContext()).resumeRequests();
     *             break;
     *         case SCROLL_STATE_TOUCH_SCROLL:
     *             Log.i("ListView","手指没有离开屏幕，视图正在滑动");
     *             Glide.with(getApplicationContext()).resumeRequests();
     *             break;
     *     }
     *
     * }

     */

}
