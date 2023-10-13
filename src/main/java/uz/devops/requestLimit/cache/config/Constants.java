package uz.devops.requestLimit.cache.config;

    public class Constants {
    public static final String EHCACHE_MANAGER_BEAN = "EHCACHE_MANAGER_BEAN";
    public static final String EHCACHE_BEAN_WITH_EXPIRE_TIME = "EHCACHE_BEAN_WITH_EXPIRE_TIME";
    public static final String EHCACHE_BEAN_WITHOUT_EXPIRE_TIME = "EHCACHE_BEAN_WITHOUT_EXPIRE_TIME";
    public static final String EHCACHE_DISK_STORE_PATH = "cache/ehcache-disk-store";
    public static final String EXCEPTION_MESSAGE = "TOO MANY ATTEMPT REQUESTS: You can send the next request in %d seconds!";
}
