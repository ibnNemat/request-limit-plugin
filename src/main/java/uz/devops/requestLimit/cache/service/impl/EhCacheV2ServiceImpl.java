package uz.devops.requestLimit.cache.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.ehcache.Cache;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import uz.devops.requestLimit.cache.service.EhCacheService;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import static uz.devops.requestLimit.cache.config.Constants.EHCACHE_BEAN_WITH_EXPIRE_TIME;

@Service("ehCacheV2ServiceImpl")
@Slf4j
public class EhCacheV2ServiceImpl extends EhCacheService {
    private final Cache<String, LocalDateTime> requestDateTimeCache;

    public EhCacheV2ServiceImpl(@Qualifier(EHCACHE_BEAN_WITH_EXPIRE_TIME) Cache<String, LocalDateTime> requestDateTimeCache) {
        this.requestDateTimeCache = requestDateTimeCache;
    }

    @Override
    public void setRequestLimit(String requestKey, Long timeToLive, TimeUnit timeUnit) {
        throwIfExistsWithRequestKey(requestKey, timeToLive, timeUnit,  requestDateTimeCache);
    }
}
