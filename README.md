## cacheKit
一个简单易用的cache 库
 
### 特征
   - 简单
   - 实用
   - 方便
   
#### 使用Gradle构建时添加一下依赖即可:
```
compile 'com.dvsnier:cacheLib:0.0.5'
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
        CacheManager.getInstance().initialize(this);
        ...
    }
    
    @Override
    public void onTerminate() {
        super.onTerminate();
        CacheManager.getInstance().close();
    }
    
```
#### 扩展
##### 第一种
```
    // 在application的onCreate中初始化
    @Override
    public void onCreate() {
        super.onCreate();
//		默认磁盘配置512M 缓存空间
        CacheManager.getInstance().initialize(this);
        ...
    }
```
##### 第二种
```
    // 在application的onCreate中初始化
    @Override
    public void onCreate() {
        super.onCreate();
//		自定义磁盘1G 缓存空间
	    int cacheMaxSizeOfDisk = 1024 * 1024 * 1024; // 1G
        CacheManager.getInstance().initialize(new ICacheConfig.Builder(this).setCacheMaxSizeOfDisk(cacheMaxSizeOfDisk).create());
//      CacheManager.getInstance().initialize(new ICacheConfig.Builder(this).setAppVersion(getAppVersionCode(this)).setCacheMaxSizeOfDisk(cacheMaxSizeOfDisk).create());
        ...
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
