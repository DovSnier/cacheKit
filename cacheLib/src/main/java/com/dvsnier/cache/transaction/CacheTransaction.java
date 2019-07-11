package com.dvsnier.cache.transaction;

import android.support.annotation.NonNull;

import com.dvsnier.cache.config.CacheAllocation;
import com.dvsnier.cache.config.Type;

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

import libcore.compat.ICompatDiskLruCache;
import libcore.compat.ICompatLruCache;
import libcore.io.DiskLruCache;

/**
 * CacheTransaction
 * Created by dovsnier on 2018/6/12.
 */
public class CacheTransaction extends AbstractCacheTransaction {

    public CacheTransaction() {
        super();
    }

    public CacheTransaction(@NonNull ICompatLruCache<String, Object> lruCache, @NonNull ICompatDiskLruCache diskLruCache) {
        super(lruCache, diskLruCache);
    }

    //<editor-fold desc="ICacheTransaction">

    @Override
    public CacheTransactionSimpleSession put(@NonNull String key, Object value) {
        //noinspection ConstantConditions
        if (null == key || "".equals(key)) return getCacheTransaction(Type.DEFAULT);
        if (null == value) return getCacheTransaction(Type.DEFAULT);
        if (value instanceof Serializable) {
            putObject(key, value);
        } else {
            // nothing to do
        }
        return getCacheTransaction(Type.DEFAULT);
    }

    @Override
    public CacheTransactionSimpleSession putString(@NonNull String key, String value) {
        //noinspection ConstantConditions
        if (null == key || "".equals(key)) return getCacheTransaction(Type.DEFAULT);
        if (null == value) return getCacheTransaction(Type.DEFAULT);
        if (null != getCache()) {
            //noinspection unchecked
            getCache().put(key, value);
        }
        if (null != getDiskCache()) {
            DiskLruCache.Editor edit = null;
            OutputStream outputStream = null;
            BufferedWriter bufferedWriter = null;
            try {
                edit = getDiskCache().edit(key);
                outputStream = edit.newOutputStream(DEFAULT_INDEX);
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, Charset.forName("UTF-8")));
                bufferedWriter.write(value);
                bufferedWriter.flush();
                edit.commit();
            } catch (IOException e) {
                e.printStackTrace();
                try {
                    if (null != edit)
                        edit.abort();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            } finally {
                try {
                    if (null != bufferedWriter)
                        bufferedWriter.close();
                    if (null != outputStream)
                        outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return getCacheTransaction(Type.DEFAULT);
    }

    @Override
    public CacheTransactionSimpleSession putInputStream(@NonNull String key, InputStream inputStream) {
        //noinspection ConstantConditions
        if (null == key || "".equals(key)) return getCacheTransaction(Type.DEFAULT);
        if (null == inputStream) return getCacheTransaction(Type.DEFAULT);
        if (null != getCache() && CacheAllocation.INSTANCE().ApiOfInner()) {
            //noinspection unchecked
            getCache().put(key, inputStream);
        }
        if (null != getDiskCache()) {
            DiskLruCache.Editor edit = null;
            OutputStream outputStream = null;
            BufferedInputStream bufferedInputStream = null;
            BufferedOutputStream bufferedOutputStream = null;
            byte[] bytes = new byte[DEFAULT_BUFFER_SIZE];
            try {
                edit = getDiskCache().edit(key);
                outputStream = edit.newOutputStream(DEFAULT_INDEX);
                bufferedOutputStream = new BufferedOutputStream(outputStream);
                bufferedInputStream = new BufferedInputStream(inputStream);
                int read = 0;
                while ((read = bufferedInputStream.read(bytes)) != DEFAULT) {
                    bufferedOutputStream.write(bytes);
                }
                bufferedOutputStream.flush();
                edit.commit();
            } catch (IOException e) {
                e.printStackTrace();
                try {
                    if (null != edit)
                        edit.abort();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            } finally {
                try {
                    if (null != bufferedInputStream)
                        bufferedInputStream.close();
                    if (null != bufferedOutputStream)
                        bufferedOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return getCacheTransaction(Type.DEFAULT);
    }

    @Override
    public CacheTransactionSimpleSession putObject(@NonNull String key, Object value) {
        //noinspection ConstantConditions
        if (null == key || "".equals(key)) return getCacheTransaction(Type.DEFAULT);
        if (null == value) return getCacheTransaction(Type.DEFAULT);
        if (null != getCache()) {
            //noinspection unchecked
            getCache().put(key, value);
        }
        if (null != getDiskCache()) {
            if (value instanceof Serializable) {
                DiskLruCache.Editor edit = null;
                ObjectOutputStream objectOutputStream = null;
                OutputStream outputStream = null;
                try {
                    edit = getDiskCache().edit(key);
                    outputStream = edit.newOutputStream(DEFAULT_INDEX);
                    objectOutputStream = new ObjectOutputStream(new BufferedOutputStream(outputStream));
                    objectOutputStream.writeObject(value);
                    objectOutputStream.flush();
                    edit.commit();
                } catch (IOException e) {
                    e.printStackTrace();
                    try {
                        if (null != edit)
                            edit.abort();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                } finally {
                    try {
                        if (null != objectOutputStream)
                            objectOutputStream.close();
                        if (null != outputStream)
                            outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return getCacheTransaction(Type.DEFAULT);
    }

    @Override
    public Object get(@NonNull String key) {
        //noinspection ConstantConditions
        if (null == key || "".equals(key)) return null;
        Object value = null;
        if (null != getCache()) {
            //noinspection unchecked
            value = getCache().get(key);
        }
        if (null == value) {
            value = getObject(key);
        }
        return value;
    }

    @Override
    public String getString(@NonNull String key) {
        //noinspection ConstantConditions
        if (null == key || "".equals(key)) return null;
        String value = null;
        if (null != getCache()) {
            //noinspection unchecked
            Object o = getCache().get(key);
            if (null != o && o instanceof String) {
                value = (String) o;
            }
        }
        if (null == value) {
            if (null != getDiskCache()) {
                try {
                    DiskLruCache.Snapshot snapshot = getDiskCache().get(key);
                    if (null != snapshot) {
                        value = snapshot.getString(DEFAULT_INDEX);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return value;
    }

    @Override
    public InputStream getInputStream(@NonNull String key) {
        //noinspection ConstantConditions
        if (null == key || "".equals(key)) return null;
        InputStream value = null;
        if (null != getCache() && CacheAllocation.INSTANCE().ApiOfInner()) {
            //noinspection unchecked
            Object o = getCache().get(key);
            if (null != o && o instanceof InputStream) {
                value = (InputStream) o;
            }
        }
        if (null == value) {
            if (null != getDiskCache()) {
                try {
                    DiskLruCache.Snapshot snapshot = getDiskCache().get(key);
                    if (null != snapshot) {
                        value = snapshot.getInputStream(DEFAULT_INDEX);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return value;
    }

    @Override
    public Object getObject(@NonNull String key) {
        //noinspection ConstantConditions
        if (null == key || "".equals(key)) return null;
        Object value = null;
        if (null != getCache()) {
            //noinspection unchecked
            value = getCache().get(key);
        }
        if (null == value) {
            if (null != getDiskCache()) {
                InputStream inputStream = null;
                ObjectInputStream objectInputStream = null;
                try {
                    DiskLruCache.Snapshot snapshot = getDiskCache().get(key);
                    if (null != snapshot) {
                        inputStream = snapshot.getInputStream(DEFAULT_INDEX);
                        objectInputStream = new ObjectInputStream(new BufferedInputStream(inputStream));
                        value = objectInputStream.readObject();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (null != inputStream)
                            inputStream.close();
                        if (null != objectInputStream)
                            objectInputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return value;
    }

    @Override
    public CacheTransactionSimpleSession remove(@NonNull String key) {
        //noinspection ConstantConditions
        if (null == key || "".equals(key)) return getCacheTransaction(Type.DEFAULT);
        if (null != getCache())
            //noinspection unchecked
            getCache().remove(key);
        if (null != getDiskCache()) {
            try {
                getDiskCache().remove(key);
            } catch (IOException e) {
                e.printStackTrace();
                return getCacheTransaction(Type.DEFAULT);
            }
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
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    //</editor-fold>
}
