package libcore.bean;

import com.dvsnier.cache.base.TimeUnit;

/**
 * DiskLruValueBean
 * Created by dovsnier on 2019-07-29.
 */
public class DiskLruValueBean extends AbstractValueBean {

    public DiskLruValueBean() {
        super();
    }

    public DiskLruValueBean(long timeStamp) {
        super(timeStamp);
    }

    public DiskLruValueBean(long duration, TimeUnit unit) {
        super(duration, unit);
    }

    public DiskLruValueBean(long timeStamp, long duration, TimeUnit unit) {
        super(timeStamp, duration, unit);
    }
}
