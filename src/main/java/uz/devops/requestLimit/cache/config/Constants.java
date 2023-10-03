package uz.devops.requestLimit.cache.config;

public class Constants {
    public static final String EHCACHE_MANAGER_BEAN_NAME = "EHCACHE_MANAGER_BEAN_NAME";
    public static final String P2P_PAY_EHCACHE_BEAN = "P2P_PAY_EHCACHE_BEAN";
    public static final String EHCACHE_DISK_STORE_PATH = "cache/ehcache-disk-store";
    public static final String EXCEPTION_MESSAGE = "TOO MANY ATTEMPT REQUESTS: You can send the next request in %d seconds!";
}
