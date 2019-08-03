package libcore.bean;

import com.dvsnier.cache.base.TimeUnit;

/**
 * LruValueBean
 * Created by dovsnier on 2019-07-26.
 */
public class LruValueBean<V> extends AbstractValueBean {

    protected int index;
    protected V value;
    protected int size;

    public LruValueBean() {
    }

    public LruValueBean(V value) {
        super();
        this.value = value;
    }

    public LruValueBean(V value, long timeStamp) {
        super(timeStamp);
        this.value = value;
    }

    public LruValueBean(V value, long duration, TimeUnit unit) {
        super(duration, unit);
        this.value = value;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "LruValueBean{" +
                "index=" + index +
                ", value=" + value +
                ", size=" + size +
                ", timeStamp=" + timeStamp +
                ", duration=" + duration +
                ", unit=" + unit +
                '}';
    }
}
