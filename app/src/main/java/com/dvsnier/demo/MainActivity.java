package com.dvsnier.demo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dvsnier.cache.CacheManager;

public class MainActivity extends Activity {

    private final String key0 = "key_0";
    private final String key1 = "key_1";
    private final String key2 = "key_2";
    private TextView test;
    private TextView content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        test = (TextView) findViewById(R.id.test);
        content = (TextView) findViewById(R.id.content);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CacheManager.getInstance().put(key0, "测试数据: " + System.currentTimeMillis())
                        .putString(key1, "测试字符串: " + view.toString())
                        .putObject(key2, new Bean("cache object " + System.currentTimeMillis(), BuildConfig.VERSION_NAME))
                        .commit();
                obtainContent();
                Toast.makeText(MainActivity.this, "测试完成，6s 后准备关闭...", Toast.LENGTH_SHORT).show();
                view.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 6 * 1000);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        obtainContent();
    }

    protected void obtainContent() {
        content.postDelayed(new Runnable() {
            @Override
            public void run() {
                Object o0 = CacheManager.getInstance().get(key0);
                String o1 = CacheManager.getInstance().getString(key1);
                Object o2 = CacheManager.getInstance().getObject(key2);
                content.setText(String.format("1. value：\n\t\t\t\t%1$s\n\n2. 字符串：\n\t\t\t\t%2$s\n\n3. 对象：\n\t\t\t\t%3$s\n\n", o0, o1, o2));
            }
        }, 1000);
    }
}
