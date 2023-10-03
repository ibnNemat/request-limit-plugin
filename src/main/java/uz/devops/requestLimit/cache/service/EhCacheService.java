package uz.devops.requestLimit.cache.service;

import uz.devops.requestLimit.cache.annotation.limit.RequestLimit;

public interface EhCacheService {

    /**
     * Set Request Limit
     *
     * @param requestKey The generated key from request parameters.
     * If you want to set a limit for your method without request parameters,
     * then you can generate one constant key and use this key for each request to set the limit.
     * @param requestLimit The annotation class to configure request limit values.
     */
    void setRequestLimit(String requestKey, RequestLimit requestLimit);
}
