package com.dvsnier.cache.transaction;

import android.support.annotation.NonNull;

import com.dvsnier.cache.annotation.Scheduled;
import com.dvsnier.cache.base.TimeUnit;
import com.dvsnier.cache.config.CacheAllocation;
import com.dvsnier.cache.config.Type;
import com.dvsnier.cache.infrastructure.Debug;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.nio.charset.Charset;

import libcore.io.DiskLruCache;
import libcore.io.LruCache;

/**
 * CacheTransaction
 * Created by dovsnier on 2018/6/12.
 */
public class CacheTransaction extends AbstractCacheTransaction {

    public CacheTransaction() {
        super();
    }

    public CacheTransaction(ICacheTransactionSession transactionSession) {
        super(transactionSession);
    }

    //<editor-fold desc="ICacheTransaction">

    @Override
    public CacheTransactionSession put(@NonNull String key, Object value) {
        if (validateKey(key)) {
            //noinspection ConstantConditions
            Debug.w(String.format("the current cache engine(%s), key(%s) is an illegal parameter.", getTransactionSession().getAlias(), key));
            return getCacheTransaction(Type.DEFAULT);
        }
        if (validateValue(value)) {
            //noinspection ConstantConditions
            Debug.w(String.format("the current cache engine(%s), that key(%s), then value(%s) is an illegal parameter.", getTransactionSession().getAlias(), key, value));
            return getCacheTransaction(Type.DEFAULT);
        }
        if (value instanceof Serializable) {
            putObject(key, value);
        } else {
            //noinspection ConstantConditions
            Debug.d(String.format("the current cache engine(%s), that key(%s), then value(%s) does not implement Serializable.", getTransactionSession().getAlias(), key, value));
        }
        return getCacheTransaction(Type.DEFAULT);
    }

    @Scheduled
    @Override
    public CacheTransactionSession put(@NonNull String key, Object value, long duration, TimeUnit timeUnit) {
//        throw new UnimplementedException();
        put(key, value);
        //noinspection ConstantConditions
        Debug.w(String.format("the current cache engine(%s), then key(%s) - value(%s), that calls do not conform to specifications. please configure CacheGenre#scheduled before using it. currently, the default configuration is used.", getTransactionSession().getAlias(), key, value));
        return getCacheTransaction(Type.DEFAULT);
    }

    @Override
    public CacheTransactionSession putString(@NonNull String key, String value) {
        if (validateKey(key)) {
            //noinspection ConstantConditions
            Debug.w(String.format("the current cache engine(%s), key(%s) is an illegal parameter.", getTransactionSession().getAlias(), key));
            return getCacheTransaction(Type.DEFAULT);
        }
        if (validateValue(value)) {
            //noinspection ConstantConditions
            Debug.w(String.format("the current cache engine(%s), that key(%s), then value(%s) is an illegal parameter.", getTransactionSession().getAlias(), key, value));
            return getCacheTransaction(Type.DEFAULT);
        }
        if (null != getCache()) {
            //noinspection
            getCache().put(key, value);
        } else {
            //noinspection ConstantConditions
            Debug.e(String.format("the current cache engine(%s), key(%s) - value(%s), then memory cache is illegal(null) that maybe is evicted.", getTransactionSession().getAlias(), key, value));
        }
        if (null != getDiskCache()) {
            DiskLruCache.Editor edit = null;
            OutputStream outputStream = null;
            BufferedWriter bufferedWriter = null;
            try {
                edit = getDiskCache().edit(key);
                if (null != edit) {
                    outputStream = edit.newOutputStream(DEFAULT_INDEX);
                    //noinspection ConstantConditions
                    if (null != outputStream) {
                        bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, Charset.forName("UTF-8")));
                        bufferedWriter.write(value);
                        bufferedWriter.flush();
                        edit.commit();
                    }
                }
            } catch (IOException e) {
                //noinspection ConstantConditions
                Debug.e(String.format("the current cache engine(%s), key(%s) - value(%s), then disk cache persistent data failure.", getTransactionSession().getAlias(), key, value));
                e.printStackTrace();
                try {
                    if (null != edit)
                        edit.abort();
                } catch (IOException e1) {
                    //noinspection
                    Debug.e(String.format("the current cache engine(%s), key(%s) - value(%s), then disk cache persistent data failure.", getTransactionSession().getAlias(), key, value));
                    e1.printStackTrace();
                }
            } finally {
                try {
                    if (null != bufferedWriter)
                        bufferedWriter.close();
                    if (null != outputStream)
                        outputStream.close();
                } catch (IOException e) {
                    //noinspection ConstantConditions
                    Debug.e(String.format("the current cache engine(%s), key(%s) - value(%s), then disk cache persistent data failure.", getTransactionSession().getAlias(), key, value));
                    e.printStackTrace();
                }
            }
        } else {
            //noinspection ConstantConditions
            Debug.e(String.format("the current cache engine(%s), key(%s) - value(%s), then disk cache is illegal(null) that maybe is evicted.", getTransactionSession().getAlias(), key, value));
        }
        if (validateOnTransactionSessionChangeListener()) {
            //noinspection ConstantConditions
            getOnTransactionSessionChangeListener().onTransactionSessionChange(getTransactionSession().getAlias(), key, value);

        }
        return getCacheTransaction(Type.DEFAULT);
    }

    @Override
    public CacheTransactionSession putString(@NonNull String key, String value, long duration, TimeUnit timeUnit) {
//        throw new UnimplementedException();
        putString(key, value);
        //noinspection ConstantConditions
        Debug.w(String.format("the current cache engine(%s), then key(%s) - value(%s), that calls do not conform to specifications. please configure CacheGenre#scheduled before using it. currently, the default configuration is used.", getTransactionSession().getAlias(), key, value));
        return getCacheTransaction(Type.DEFAULT);
    }

    @Override
    public CacheTransactionSession putInputStream(@NonNull String key, InputStream inputStream) {
        if (validateKey(key)) {
            //noinspection ConstantConditions
            Debug.w(String.format("the current cache engine(%s), key(%s) is an illegal parameter.", getTransactionSession().getAlias(), key));
            return getCacheTransaction(Type.DEFAULT);
        }
        if (validateValue(inputStream)) {
            //noinspection ConstantConditions
            Debug.w(String.format("the current cache engine(%s), that key(%s), then value(%s) is an illegal parameter.", getTransactionSession().getAlias(), key, inputStream));
            return getCacheTransaction(Type.DEFAULT);
        }
        if (null != getCache() && CacheAllocation.INSTANCE().ApiOfInner()) {
            //noinspection
            getCache().put(key, inputStream);
        } else {
            //noinspection ConstantConditions
            Debug.w(String.format("the current cache engine(%s), key(%s) - value(%s), then memory cache is illegal(that cache allocation uses default values).", getTransactionSession().getAlias(), key, inputStream));
        }
        if (null != getDiskCache()) {
            DiskLruCache.Editor edit = null;
            OutputStream outputStream = null;
            BufferedInputStream bufferedInputStream = null;
            BufferedOutputStream bufferedOutputStream = null;
            byte[] bytes = new byte[DEFAULT_BUFFER_SIZE];
            try {
                edit = getDiskCache().edit(key);
                if (null != edit) {
                    outputStream = edit.newOutputStream(DEFAULT_INDEX);
                    //noinspection ConstantConditions
                    if (null != outputStream) {
                        bufferedOutputStream = new BufferedOutputStream(outputStream);
                        bufferedInputStream = new BufferedInputStream(inputStream);
                        int read = 0;
                        while ((read = bufferedInputStream.read(bytes)) != DEFAULT) {
                            bufferedOutputStream.write(bytes);
                        }
                        bufferedOutputStream.flush();
                        edit.commit();
                    }
                }
            } catch (IOException e) {
                //noinspection ConstantConditions
                Debug.e(String.format("the current cache engine(%s), key(%s) - value(%s), then disk cache persistent data failure.", getTransactionSession().getAlias(), key, inputStream));
                e.printStackTrace();
                try {
                    if (null != edit)
                        edit.abort();
                } catch (IOException e1) {
                    //noinspection
                    Debug.e(String.format("the current cache engine(%s), key(%s) - value(%s), then disk cache persistent data failure.", getTransactionSession().getAlias(), key, inputStream));
                    e1.printStackTrace();
                }
            } finally {
                try {
                    if (null != bufferedInputStream)
                        bufferedInputStream.close();
                    if (null != bufferedOutputStream)
                        bufferedOutputStream.close();
                } catch (IOException e) {
                    //noinspection ConstantConditions
                    Debug.e(String.format("the current cache engine(%s), key(%s) - value(%s), then disk cache persistent data failure.", getTransactionSession().getAlias(), key, inputStream));
                    e.printStackTrace();
                }
            }
        }
        if (validateOnTransactionSessionChangeListener()) {
            //noinspection ConstantConditions
            getOnTransactionSessionChangeListener().onTransactionSessionChange(getTransactionSession().getAlias(), key, inputStream);

        }
        return getCacheTransaction(Type.DEFAULT);
    }

    @Override
    public CacheTransactionSession putInputStream(@NonNull String key, InputStream inputStream, long duration, TimeUnit timeUnit) {
//        throw new UnimplementedException();
        putInputStream(key, inputStream);
        //noinspection ConstantConditions
        Debug.w(String.format("the current cache engine(%s), then key(%s) - value(%s), that calls do not conform to specifications. please configure CacheGenre#scheduled before using it. currently, the default configuration is used.", getTransactionSession().getAlias(), key, inputStream));
        return getCacheTransaction(Type.DEFAULT);
    }

    @Override
    public CacheTransactionSession putObject(@NonNull String key, Object value) {
        if (validateKey(key)) {
            //noinspection ConstantConditions
            Debug.w(String.format("the current cache engine(%s), key(%s) is an illegal parameter.", getTransactionSession().getAlias(), key));
            return getCacheTransaction(Type.DEFAULT);
        }
        if (validateValue(value)) {
            //noinspection ConstantConditions
            Debug.w(String.format("the current cache engine(%s), that key(%s), then value(%s) is an illegal parameter.", getTransactionSession().getAlias(), key, value));
            return getCacheTransaction(Type.DEFAULT);
        }
        if (null != getCache()) {
            //noinspection
            getCache().put(key, value);
        } else {
            //noinspection ConstantConditions
            Debug.e(String.format("the current cache engine(%s), key(%s) - value(%s), then memory cache is illegal(null) that maybe is evicted.", getTransactionSession().getAlias(), key, value));
        }
        if (null != getDiskCache()) {
            if (value instanceof Serializable) {
                DiskLruCache.Editor edit = null;
                ObjectOutputStream objectOutputStream = null;
                OutputStream outputStream = null;
                try {
                    edit = getDiskCache().edit(key);
                    if (null != edit) {
                        outputStream = edit.newOutputStream(DEFAULT_INDEX);
                        //noinspection ConstantConditions
                        if (null != outputStream) {
                            objectOutputStream = new ObjectOutputStream(new BufferedOutputStream(outputStream));
                            objectOutputStream.writeObject(value);
                            objectOutputStream.flush();
                            edit.commit();
                        }
                    }
                } catch (IOException e) {
                    //noinspection ConstantConditions
                    Debug.e(String.format("the current cache engine(%s), key(%s) - value(%s), then disk cache persistent data failure.", getTransactionSession().getAlias(), key, value));
                    e.printStackTrace();
                    try {
                        if (null != edit)
                            edit.abort();
                    } catch (IOException e1) {
                        //noinspection
                        Debug.e(String.format("the current cache engine(%s), key(%s) - value(%s), then disk cache persistent data failure.", getTransactionSession().getAlias(), key, value));
                        e1.printStackTrace();
                    }
                } finally {
                    try {
                        if (null != objectOutputStream)
                            objectOutputStream.close();
                        if (null != outputStream)
                            outputStream.close();
                    } catch (IOException e) {
                        //noinspection ConstantConditions
                        Debug.e(String.format("the current cache engine(%s), key(%s) - value(%s), then disk cache persistent data failure.", getTransactionSession().getAlias(), key, value));
                        e.printStackTrace();
                    }
                }
            } else {
                //noinspection ConstantConditions
                Debug.d(String.format("the current cache engine(%s), that key(%s), then value(%s) does not implement Serializable.", getTransactionSession().getAlias(), key, value));
            }
        } else {
            //noinspection ConstantConditions
            Debug.e(String.format("the current cache engine(%s), key(%s) - value(%s), then disk cache is illegal(null) that maybe is evicted.", getTransactionSession().getAlias(), key, value));
        }
        if (validateOnTransactionSessionChangeListener()) {
            //noinspection ConstantConditions
            getOnTransactionSessionChangeListener().onTransactionSessionChange(getTransactionSession().getAlias(), key, value);

        }
        return getCacheTransaction(Type.DEFAULT);
    }

    @Override
    public CacheTransactionSession putObject(@NonNull String key, Object value, long duration, TimeUnit timeUnit) {
//        throw new UnimplementedException();
        putObject(key, value);
        //noinspection ConstantConditions
        Debug.w(String.format("the current cache engine(%s), then key(%s) - value(%s), that calls do not conform to specifications. please configure CacheGenre#scheduled before using it. currently, the default configuration is used.", getTransactionSession().getAlias(), key, value));
        return getCacheTransaction(Type.DEFAULT);
    }

    @Override
    public Object get(@NonNull String key) {
        if (validateKey(key)) {
            //noinspection ConstantConditions
            Debug.w(String.format("the current cache engine(%s), key(%s) is an illegal parameter.", getTransactionSession().getAlias(), key));
            return null;
        }
        Object value = null;
        if (null != getCache()) {
            //noinspection
            value = getCache().get(key);
        } else {
            //noinspection ConstantConditions
            Debug.e(String.format("the current cache engine(%s), key(%s), then memory cache is illegal(null) that maybe is evicted.", getTransactionSession().getAlias(), key));
        }
        if (validateValue(value)) {
            value = getObject(key);
//            if (!validateValue(value)) {
//                //noinspection ConstantConditions
//                Debug.d(String.format("the current cache engine(%s), key(%s) - value(%s) that is from disk cache.", getTransactionSession().getAlias(), key, value));
//            }
        } else {
            //noinspection ConstantConditions
            Debug.d(String.format("the current cache engine(%s), key(%s) - value(%s) that is from memory cache.", getTransactionSession().getAlias(), key, value));
        }
        return value;
    }

    @Override
    public String getString(@NonNull String key) {
        if (validateKey(key)) {
            //noinspection ConstantConditions
            Debug.w(String.format("the current cache engine(%s), key(%s) is an illegal parameter.", getTransactionSession().getAlias(), key));
            return null;
        }
        String value = null;
        if (null != getCache()) {
            //noinspection
            Object o = getCache().get(key);
            //noinspection ConditionCoveredByFurtherCondition
            if (null != o && o instanceof String) {
                value = (String) o;
            }
        } else {
            //noinspection ConstantConditions
            Debug.e(String.format("the current cache engine(%s), key(%s), then memory cache is illegal(null) that maybe is evicted.", getTransactionSession().getAlias(), key));
        }
        if (validateValue(value)) {
            if (null != getDiskCache()) {
                try {
                    DiskLruCache.Snapshot snapshot = getDiskCache().get(key);
                    if (null != snapshot) {
                        value = snapshot.getString(DEFAULT_INDEX);
                    }
                } catch (IOException e) {
                    //noinspection ConstantConditions
                    Debug.e(String.format("the current cache engine(%s), key(%s) - value(%s), then failure of disk cache to obtain persistent data.", getTransactionSession().getAlias(), key, value));
                    e.printStackTrace();
                }
            } else {
                //noinspection ConstantConditions
                Debug.e(String.format("the current cache engine(%s), key(%s), then disk cache is illegal(null) that maybe is evicted.", getTransactionSession().getAlias(), key));
            }
            if (!validateValue(value)) {
                //noinspection ConstantConditions
                Debug.d(String.format("the current cache engine(%s), key(%s) - value(%s) that is from disk cache.", getTransactionSession().getAlias(), key, value));
            }
        } else {
            //noinspection ConstantConditions
            Debug.d(String.format("the current cache engine(%s), key(%s) - value(%s) that is from memory cache.", getTransactionSession().getAlias(), key, value));
        }
        return value;
    }

    @Override
    public InputStream getInputStream(@NonNull String key) {
        if (validateKey(key)) {
            //noinspection ConstantConditions
            Debug.w(String.format("the current cache engine(%s), key(%s) is an illegal parameter.", getTransactionSession().getAlias(), key));
            return null;
        }
        InputStream value = null;
        if (null != getCache() && CacheAllocation.INSTANCE().ApiOfInner()) {
            //noinspection
            Object o = getCache().get(key);
            //noinspection ConditionCoveredByFurtherCondition
            if (null != o && o instanceof InputStream) {
                value = (InputStream) o;
            }
        } else {
            //noinspection ConstantConditions
            Debug.w(String.format("the current cache engine(%s), key(%s), then memory cache is illegal(that cache allocation uses default values).", getTransactionSession().getAlias(), key));
        }
        if (validateValue(value)) {
            if (null != getDiskCache()) {
                try {
                    DiskLruCache.Snapshot snapshot = getDiskCache().get(key);
                    if (null != snapshot) {
                        value = snapshot.getInputStream(DEFAULT_INDEX);
                    }
                } catch (IOException e) {
                    //noinspection ConstantConditions
                    Debug.e(String.format("the current cache engine(%s), key(%s) - value(%s), then failure of disk cache to obtain persistent data.", getTransactionSession().getAlias(), key, value));
                    e.printStackTrace();
                }
            } else {
                //noinspection ConstantConditions
                Debug.e(String.format("the current cache engine(%s), key(%s), then disk cache is illegal(null) that maybe is evicted.", getTransactionSession().getAlias(), key));
            }
            if (!validateValue(value)) {
                //noinspection ConstantConditions
                Debug.d(String.format("the current cache engine(%s), key(%s) - value(%s) that is from disk cache.", getTransactionSession().getAlias(), key, value));
            }
        } else {
            //noinspection ConstantConditions
            Debug.d(String.format("the current cache engine(%s), key(%s) - value(%s) that is from memory cache.", getTransactionSession().getAlias(), key, value));
        }
        return value;
    }

    @Override
    public Object getObject(@NonNull String key) {
        if (validateKey(key)) {
            //noinspection ConstantConditions
            Debug.w(String.format("the current cache engine(%s), key(%s) is an illegal parameter.", getTransactionSession().getAlias(), key));
            return null;
        }
        Object value = null;
        if (null != getCache()) {
            //noinspection
            value = getCache().get(key);
        } else {
            //noinspection ConstantConditions
            Debug.e(String.format("the current cache engine(%s), key(%s), then memory cache is illegal(null) that maybe is evicted.", getTransactionSession().getAlias(), key));
        }
        if (validateValue(value)) {
            if (null != getDiskCache()) {
                InputStream inputStream = null;
                ObjectInputStream objectInputStream = null;
                try {
                    DiskLruCache.Snapshot snapshot = getDiskCache().get(key);
                    if (null != snapshot) {
                        inputStream = snapshot.getInputStream(DEFAULT_INDEX);
                        if (null != inputStream) {
                            objectInputStream = new ObjectInputStream(new BufferedInputStream(inputStream));
                            value = objectInputStream.readObject();
                        }
                    }
                } catch (IOException e) {
                    //noinspection ConstantConditions
                    Debug.e(String.format("the current cache engine(%s), key(%s) - value(%s), then failure of disk cache to obtain persistent data.", getTransactionSession().getAlias(), key, value));
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    //noinspection ConstantConditions
                    Debug.e(String.format("the current cache engine(%s), key(%s) - value(%s), then failure of disk cache to obtain persistent data.", getTransactionSession().getAlias(), key, value));
                    e.printStackTrace();
                } finally {
                    try {
                        if (null != inputStream)
                            inputStream.close();
                        if (null != objectInputStream)
                            objectInputStream.close();
                    } catch (IOException e) {
                        //noinspection ConstantConditions
                        Debug.e(String.format("the current cache engine(%s), key(%s) - value(%s), then failure of disk cache to obtain persistent data.", getTransactionSession().getAlias(), key, value));
                        e.printStackTrace();
                    }
                }
            } else {
                //noinspection ConstantConditions
                Debug.e(String.format("the current cache engine(%s), key(%s), then disk cache is illegal(null) that maybe is evicted.", getTransactionSession().getAlias(), key));
            }
            if (!validateValue(value)) {
                //noinspection ConstantConditions
                Debug.d(String.format("the current cache engine(%s), key(%s) - value(%s) that is from disk cache.", getTransactionSession().getAlias(), key, value));
            }
        } else {
            //noinspection ConstantConditions
            Debug.d(String.format("the current cache engine(%s), key(%s) - value(%s) that is from memory cache.", getTransactionSession().getAlias(), key, value));
        }
        return value;
    }

    @Override
    public CacheTransactionSession remove(@NonNull String key) {
        if (validateKey(key)) {
            //noinspection ConstantConditions
            Debug.w(String.format("the current cache engine(%s), key(%s) is an illegal parameter.", getTransactionSession().getAlias(), key));
            return getCacheTransaction(Type.DEFAULT);
        }
        if (null != getCache()) {
            //noinspection
            getCache().remove(key);
        } else {
            //noinspection ConstantConditions
            Debug.e(String.format("the current cache engine(%s), key(%s), then memory cache is illegal(null) that maybe is evicted.", getTransactionSession().getAlias(), key));
        }
        if (null != getDiskCache()) {
            try {
                getDiskCache().remove(key);
            } catch (IOException e) {
                //noinspection ConstantConditions
                Debug.e(String.format("the current cache engine(%s), key(%s), then failure of disk cache remove obtain persistent data.", getTransactionSession().getAlias(), key));
                e.printStackTrace();
                return getCacheTransaction(Type.DEFAULT);
            }
        } else {
            //noinspection ConstantConditions
            Debug.e(String.format("the current cache engine(%s), key(%s), then disk cache is illegal(null) that maybe is evicted.", getTransactionSession().getAlias(), key));
        }
        return getCacheTransaction(Type.DEFAULT);
    }

    @Override
    public boolean commit() {
        if (null != getDiskCache()) {
            try {
                getDiskCache().flush();
                return true;
            } catch (IOException e) {
                //noinspection ConstantConditions
                Debug.e(String.format("the current cache engine(%s), then failure of disk cache flush obtain persistent data.", getTransactionSession().getAlias()));
                e.printStackTrace();
                return false;
            }
        } else {
            //noinspection ConstantConditions
            Debug.e(String.format("the current cache engine(%s), then disk cache is illegal(null) that is flush operation.", getTransactionSession().getAlias()));
        }
        return false;
    }

    //</editor-fold>

    @Override
    public LruCache<String, Object> getCache() {
        if (null != cache) {
            if (cache instanceof LruCache) {
                //noinspection unchecked
                return (LruCache<String, Object>) cache;
            } else {
                throw new ClassCastException();
            }
        }
        return null;
    }

    @Override
    public DiskLruCache getDiskCache() {
        if (null != diskCache) {
            if (diskCache instanceof DiskLruCache) {
                return (DiskLruCache) diskCache;
            } else {
                throw new ClassCastException();
            }
        }
        return null;
    }
}
