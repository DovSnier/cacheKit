## cacheKit
一个简单易用的cache 库
 
### 特征
   - 简单
   - 实用
   - 方便
   
#### 使用Gradle构建时添加一下依赖即可:
```
compile 'com.dvsnier:cacheLib:0.0.3'
```

#### 使用前配置
##### 需要的权限
```
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
```

##### 初始化
```
    // 在application的onCreate中初始化
    @Override
    public void onCreate() {
        super.onCreate();
        initializedCache();
        ...
    }
    
    @Override
    public void onTerminate() {
        super.onTerminate();
        CacheManager.getInstance().close();
    }

    private void initializedCache() {
        File cache = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            cache = getExternalCacheDir();
        } else {
            cache = getCacheDir();
        }
        int cacheMaxSizeOfDisk = 1024 * 1024 * 1024;
        CacheManager.getInstance().initialize(new ICacheConfig.Builder(this).setAppVersion(getAppVersionCode(this)).setCacheDirectory(cache).setCacheMaxSizeOfDisk(cacheMaxSizeOfDisk).create());
    }
    
    public static int getAppVersionCode(Context context) {
            int versionCode = 1;
            PackageManager pm = context.getPackageManager();
            try {
                PackageInfo packInfo = pm.getPackageInfo(context.getPackageName(), 0);
                versionCode = packInfo.versionCode;
            } catch (NameNotFoundException e) {
                e.printStackTrace();
            }
            return versionCode;
        }
```
#### FAQ
1.默认缓存文件保存目录为： `/mnt/sdcard/Android/data/package_your_name/cache/`

----
### 关于作者
* Email： <3086722095@qq.com>
* 有任何建议或者使用中遇到问题都可以给我发邮件，欢迎技术交流QQ:578562841
