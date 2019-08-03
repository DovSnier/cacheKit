package com.dvsnier.demo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dvsnier.cache.CacheCleanManager;
import com.dvsnier.cache.CacheManager;
import com.dvsnier.cache.base.TimeUnit;
import com.dvsnier.cache.config.IType;

import java.util.Locale;

public class MainActivity extends Activity {

    private final String key0 = "key_0";
    private final String key1 = "key_1";
    private final String key2 = "key_2";
    private TextView test;
    private TextView clean;
    private TextView content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        test = (TextView) findViewById(R.id.test);
        clean = (TextView) findViewById(R.id.clean);
        content = (TextView) findViewById(R.id.content);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CacheManager.getInstance().put(key0, "测试数据: " + System.currentTimeMillis())
                        .put(getKey(), getValue(), 2, TimeUnit.MINUTES)
                        .putObject(key2, new Bean("cache object " + System.currentTimeMillis(), BuildConfig.VERSION_NAME))
                        .putObject(getKey(), getValue())
                        .putString(key1, "测试字符串: " + view.toString())
                        .putString(getKey(), view.toString())
                        .commit();

                CacheManager.getInstance().put(IType.TYPE_DOWNLOADS, getKey(), getValue())
                        .put(IType.TYPE_DOWNLOADS, key1, getValue(), 30, TimeUnit.SECONDS)
                        .put(IType.TYPE_DOWNLOADS, key2, getValue(), 1, TimeUnit.MINUTES)
                        .put(IType.TYPE_DOWNLOADS, String.valueOf(System.currentTimeMillis()), getValue(), 3, TimeUnit.SECONDS)
                        .commit(IType.TYPE_DOWNLOADS);
                obtainContent();
                close();
            }
        });
        clean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CacheCleanManager.clearAllCache();
//                onTips("清理完成");
                close();
            }
        });
    }

    @NonNull
    protected String getValue() {
        return String.valueOf(System.currentTimeMillis());
    }

    @NonNull
    protected String getKey() {
        return String.valueOf(Math.random() * 100);
    }

    protected void close() {
        onTips("测试完成，3s 后准备关闭...");
        getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 3 * 1000);
    }

    protected void onTips(String msg) {
        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
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

                Object O3 = CacheManager.getInstance().get(IType.TYPE_DOWNLOADS, key1);
                Object O4 = CacheManager.getInstance().get(IType.TYPE_DOWNLOADS, key2);

                content.setText(String.format(Locale.getDefault(), "1. value：\n\t\t\t\t%1$s\n\n2. 字符串：\n\t\t\t\t%2$s\n\n3. 对象：\n\t\t\t\t%3$s\n\n", o0, o1, o2));
            }
        }, 1000);
    }
}
