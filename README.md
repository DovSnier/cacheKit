## cacheKit
一个简单易用的cache 库,此版本的cache 支持多实例模式.
 
### 特征
   - 简单
   - 实用
   - 方便
   - 多实例
   
#### 使用Gradle构建时添加一下依赖即可:
```
implementation 'com.dvsnier:cache:0.1.0'
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
//  在application的onCreate中初始化
    @Override
    public void onCreate() {
        super.onCreate();
//      默认磁盘配置512M 缓存空间
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
//      自定义磁盘1G 缓存空间
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

#### 第三种
```
    // 在application的onCreate中初始化
    @Override
    public void onCreate() {
        super.onCreate();
//      自定义磁盘1G 缓存空间
        int cacheMaxSizeOfDisk = Double.valueOf(CacheStorage.INSTANCE().getFormatted(1, AbstractStorage.SCU.G)).intValue(); // 1G < Integer.MAX_VALUE ~ 2G
//      the configure cache alias, here in the downloads directory    
        CacheManager.getInstance().initialize(IType.TYPE_DOWNLOADS, new ICacheConfig.Builder(this)
                .setContext(this)
                .setAppVersion(getAppVersionCode(this))
                .setCacheMaxSizeOfDisk(cacheMaxSizeOfDisk)
//                .setUniqueName(IType.TYPE_DEFAULT)
                .setUniqueName(IType.TYPE_DOWNLOADS)
                .setDebug(true)     // debug mode
                .create());
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

#### 使用
#### 1. 默认方式
```
    CacheManager.getInstance().put(key0, "测试数据: " + System.currentTimeMillis())
            .putString(key1, "测试字符串: " + view.toString())
            .putObject(key2, new Bean("cache object " + System.currentTimeMillis(), BuildConfig.VERSION_NAME))
            .putString(getKey(), view.toString())
            .commit();
```
#### 2. 多实例方式
```
    CacheManager.getInstance().put(IType.TYPE_DOWNLOADS, getKey(), getValue())
            .commit(IType.TYPE_DOWNLOADS);
```

#### FAQ
1. 默认缓存文件保存目录为： `/mnt/sdcard/Android/data/package_your_name/cache/`
2. 配置缓存(IType.TYPE_DOWNLOADS)文件保存目录为： `/mnt/sdcard/Android/data/package_your_name/cache/downloads`,
  如果配置不符合规范，则提供的默认配置为： `/mnt/sdcard/Android/data/package_your_name/cache/local`
  
> ```0.0.6``` 版本的Cache SDK 和```0.1.0``` 版本的Cache SDK 不兼容，如需简单使用缓存请使用```0.0.6```；
----
### 关于作者
* Email： <dovsnier@qq.com>
* 有任何建议或者使用中遇到问题都可以给我发邮件，欢迎技术交流QQ:578562841
