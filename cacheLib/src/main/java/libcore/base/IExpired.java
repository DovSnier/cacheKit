package libcore.base;

/**
 * IExpired
 * Created by dovsnier on 2019-07-26.
 */
public interface IExpired {

    /**
     * whether the cache is invalid
     *
     * @return true means that the cache is invalid, otherwise no.
     */
    boolean isExpired();

    /**
     * the set expired that cache status
     *
     * @param expired true  is invalid, otherwise no.
     */
    void setExpired(boolean expired);
}
