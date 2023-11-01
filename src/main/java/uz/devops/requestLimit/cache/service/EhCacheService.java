package uz.devops.requestLimit.cache.service;

import lombok.extern.slf4j.Slf4j;
import org.ehcache.Cache;
import uz.devops.requestLimit.cache.exception.TooManyRequestsException;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import static uz.devops.requestLimit.cache.config.Constants.EXCEPTION_MESSAGE;

@Slf4j
public abstract class EhCacheService {

    /**
     * The given requestKey is stored in the memory ehCache for the time specified in the 'timeToLive' parameter.
     * If the key does not exist in the ehCache.
     *
     * @param requestKey - the generated key from request parameters.
     * If you want to set a limit for your method without request parameters,
     * then you can generate one constant key and use this key for each request to set the limit.
     * @param timeToLive - the time in cache memory
     * @param timeUnit - the timeUnit of timeToLive
     */
    public abstract void setRequestLimit(String requestKey, Long timeToLive, TimeUnit timeUnit);

    /**
     * Checks requestKey from ehCache, if the given requestKey is exists in cache, throws an exception
     * with status TOO_MANY_REQUESTS
     *
     * @param requestKey - the generated key from request parameters.
     * If you want to set a limit for your method without request parameters,
     * then you can generate one constant key and use this key for each request to set the limit.
     * @param requestDateTimeCache - the cache in which request keys are stored
     */
    public void throwIfExistsWithRequestKey(String requestKey, Cache<String, LocalDateTime> requestDateTimeCache) {
        var localDateTime = requestDateTimeCache.get(requestKey);
        var currentTime = LocalDateTime.now();
        if (localDateTime.isAfter(currentTime)) {
            var exMessage = String.format(EXCEPTION_MESSAGE, localDateTime.minusSeconds(currentTime.getSecond()).getSecond());
            throw new TooManyRequestsException(exMessage);
        }
    }
}
