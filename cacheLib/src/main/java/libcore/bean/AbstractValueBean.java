package libcore.bean;

import com.dvsnier.cache.annotation.Scheduled;
import com.dvsnier.cache.base.TimeUnit;
import com.dvsnier.cache.infrastructure.Debug;

import libcore.base.IAbstractCache;
import libcore.base.IExpired;

/**
 * AbstractValueBean
 * Created by dovsnier on 2019-07-26.
 */
public class AbstractValueBean implements IExpired {

    protected long timeStamp; // the current time stamp
    @Scheduled
    protected long duration; // the presupposed duration
    @Scheduled
    protected TimeUnit unit; // the presupposed time unit

    public AbstractValueBean() {
        this.timeStamp = System.currentTimeMillis();
    }

    public AbstractValueBean(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public AbstractValueBean(long duration, TimeUnit unit) {
        this();
        this.duration = duration;
        this.unit = unit;
    }

    public AbstractValueBean(long timeStamp, long duration, TimeUnit unit) {
        this.timeStamp = timeStamp;
        this.duration = duration;
        this.unit = unit;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Scheduled
    public long getDuration() {
        return duration;
    }

    @Scheduled
    public void setDuration(long duration) {
        this.duration = duration;
    }

    @Scheduled
    public TimeUnit getUnit() {
        return unit;
    }

    @Scheduled
    public void setUnit(TimeUnit unit) {
        this.unit = unit;
    }

    @Override
    public boolean isExpired() {
        if (timeStamp < IAbstractCache.DEFAULT) {
            Debug.e("the current time stamp is less than zero, invalid parameter.");
            timeStamp = IAbstractCache.VALID_MASK;
        }
        if (duration < IAbstractCache.DEFAULT) {
            Debug.e("the current duration is less than zero, invalid parameter.");
            duration = IAbstractCache.DEFAULT;
        }
        if (timeStamp == IAbstractCache.VALID_MASK && duration == IAbstractCache.DEFAULT) {
            return true;
        } else if (timeStamp > IAbstractCache.VALID_MASK && duration > IAbstractCache.DEFAULT) {
            if (null != unit && (System.currentTimeMillis() - timeStamp) >= unit.toMillis(duration)) {
                return true;
            }
        } else {
            // nothing to do
        }
        return false;
    }

    @Override
    public void setExpired(boolean expired) {
        if (expired) {
            setTimeStamp(IAbstractCache.VALID_MASK);
            setDuration(IAbstractCache.DEFAULT);
        } else {
//            Debug.d(String.format("the current lapse of time loss is %s ms.", System.currentTimeMillis() - getTimeStamp()));
            setTimeStamp(System.currentTimeMillis());
        }
    }

    @Override
    public String toString() {
        return "AbstractValueBean{" +
                ", timeStamp=" + timeStamp +
                ", duration=" + duration +
                ", unit=" + unit +
                '}';
    }
}
