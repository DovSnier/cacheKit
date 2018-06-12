package com.dvsnier.cache.config;

import android.support.annotation.NonNull;

import com.dvsnier.cache.transaction.ICacheTransaction;

/**
 * Created by dovsnier on 2018/6/12.
 */
public interface IAlias {

    /**
     * to get icache instance
     *
     * @param type {@link Type}
     * @return {@link ICacheTransaction}
     */
    ICacheTransaction getCache(@NonNull Type type);
}
