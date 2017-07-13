package com.dvsnier.demo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dvsnier.cache.CacheManager;

public class MainActivity extends Activity {

    private TextView test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        test = (TextView) findViewById(R.id.test);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key = String.valueOf(System.currentTimeMillis());
                CacheManager.getInstance().putString(key, view.toString()).put(this.toString(), "测试数据: " + System.currentTimeMillis()).commit();
                Toast.makeText(MainActivity.this, "测试完成，准备关闭...", Toast.LENGTH_SHORT).show();
                view.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 1000);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
//        CacheManager.getInstance().onFlush(); // the deprecated
    }
}
