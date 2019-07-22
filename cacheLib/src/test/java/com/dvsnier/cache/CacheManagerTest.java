package com.dvsnier.cache;

import com.dvsnier.cache.infrastructure.Debug;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * CacheManagerTest
 * Created by dovsnier on 2019-07-18.
 */
public class CacheManagerTest {

    @Before
    public void setUp() throws Exception {
        Debug.i("开始进行单元测试...");
    }

    @After
    public void tearDown() throws Exception {
        Debug.i("单元测试执行完成.");
    }

    @Test
    public void getInstance() {
        Assert.assertNotNull(CacheManager.getInstance());
    }

    @Test
    public void close() {
        CacheManager.getInstance().close();
    }
}