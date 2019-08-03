package libcore.bean;

import com.dvsnier.cache.base.TimeUnit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * LruValueBeanTest
 * Created by dovsnier on 2019-07-26.
 */
public class LruValueBeanTest {

    LruValueBean<String> bean = new LruValueBean<>("LruValueBean");

    @Before
    public void setUp() throws Exception {
        bean.setTimeStamp(System.currentTimeMillis());
        bean.setDuration(10L);
        bean.setUnit(TimeUnit.SECONDS);
    }

    @Test
    public void isExpired() {
        bean.setDuration(1L);
        Assert.assertFalse(bean.isExpired());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Assert.assertTrue(bean.isExpired());
    }

    @Test
    public void setExpired() {
        Assert.assertFalse(bean.isExpired());
        bean.setExpired(true);
        Assert.assertTrue(bean.isExpired());
        bean.setExpired(false);
        Assert.assertFalse(bean.isExpired());
    }

    @After
    public void tearDown() throws Exception {
        bean = null;
    }
}