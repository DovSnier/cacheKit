package com.dvsnier.cache.base;

import android.content.Context;

import com.dvsnier.cache.annotation.Regulation;

import libcore.base.IDiskLruGenre;
import libcore.base.ILruGenre;

/**
 * CacheSession
 * Created by dovsnier on 2018/6/12.
 */
public class CacheSession extends AbstractCacheSession {

    protected ILruGenre lruCache;
    protected IDiskLruGenre diskLruCache;

    public CacheSession() {
        super();
    }

    public CacheSession(Context context) {
        super(context);
    }

    //<editor-fold desc="ICacheGenre">

    @Override
    public ILruGenre<String, Object> getCache() {
        return lruCache;
    }

    @Override
    public void setCache(ILruGenre<String, Object> lruCache) {
        this.lruCache = lruCache;
    }

    @Override
    public IDiskLruGenre getDiskCache() {
        return diskLruCache;
    }

    @Override
    public void setDiskCache(IDiskLruGenre diskLruCache) {
        this.diskLruCache = diskLruCache;
    }

    //</editor-fold>

    @Regulation
    public void associationCachingEngine(IEngineInstrument engineInstrument) {
        if (null == engineInstrument) {
            throw new IllegalArgumentException("the engine instrument object instance cannot be null");
        }

        if (engineInstrument instanceof CacheEngineInstrument) {
            //noinspection ConstantConditions
            associationCacheGenre(((CacheEngineInstrument) engineInstrument).getCacheGenre());
            //noinspection unchecked
            setCache(((CacheEngineInstrument) engineInstrument).getLruCache());
            setDiskCache(((CacheEngineInstrument) engineInstrument).getDiskLruCache());
        }

        setOrScheduledCacheTransaction();
    }
}
