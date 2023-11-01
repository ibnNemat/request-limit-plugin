package uz.devops.requestLimit.cache.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.ehcache.Cache;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import uz.devops.requestLimit.cache.service.EhCacheService;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import static uz.devops.requestLimit.cache.config.Constants.EHCACHE_BEAN_WITHOUT_EXPIRE_TIME;

@Service("ehCacheV1ServiceImpl")
@Slf4j
public class EhCacheV1ServiceImpl extends EhCacheService {
    private final Cache<String, LocalDateTime> requestDateTimeCache;

    public EhCacheV1ServiceImpl(@Qualifier(EHCACHE_BEAN_WITHOUT_EXPIRE_TIME) Cache<String, LocalDateTime> requestDateTimeCache) {
        this.requestDateTimeCache = requestDateTimeCache;
    }

    @Override
    public void setRequestLimit(String requestKey, Long timeToLive, TimeUnit timeUnit) {
        log.debug("Check request key in ehcache. RequestKey: {}", requestKey);

        if (!requestDateTimeCache.containsKey(requestKey)) {
            var localDateTime = LocalDateTime.now().plus(timeToLive, timeUnit.toChronoUnit());
            requestDateTimeCache.put(requestKey, localDateTime);
            log.debug("Created new cache with dataTime: {}", localDateTime);
        } else {
            throwIfExistsWithRequestKey(requestKey, requestDateTimeCache);
            requestDateTimeCache.remove(requestKey);
            log.debug("Deleted cache with key: {}", requestKey);
        }
    }
}
