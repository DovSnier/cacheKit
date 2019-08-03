package com.dvsnier.cache.base;

import android.support.annotation.NonNull;

import com.dvsnier.cache.annotation.Internal;
import com.dvsnier.cache.config.ICacheConfig;
import com.dvsnier.cache.config.Type;

/**
 * OnEngineInstrumentStatusListener
 * Created by dovsnier on 2019-07-24.
 */
@Internal
public interface OnEngineInstrumentStatusListener extends IBaseOnChangeListener {

    /**
     * the monitoring initializes the caching system
     *
     * @param cacheConfig {@link ICacheConfig}
     */
    void onInitialize(@NonNull ICacheConfig cacheConfig);

    /**
     * the monitoring evict of type caching system
     *
     * @param type {@link Type}
     */
    void onEvict(@NonNull Type type);

    /**
     * the monitoring terminal caching system
     */
    void onClose();
}
