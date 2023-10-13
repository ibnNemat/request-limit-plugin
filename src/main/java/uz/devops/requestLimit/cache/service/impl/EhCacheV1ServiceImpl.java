package uz.devops.requestLimit.cache.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ehcache.Cache;
import org.springframework.stereotype.Service;
import uz.devops.requestLimit.cache.service.EhCacheService;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Service("EhCacheV1ServiceImpl")
@Slf4j
@RequiredArgsConstructor
public class EhCacheV1ServiceImpl extends EhCacheService {
    private final Cache<String, LocalDateTime> requestDateTimeCache;

    @Override
    public void setRequestLimit(String requestKey, Long timeToLive, TimeUnit timeUnit) {
        throwIfExistsWithRequestKey(requestKey, timeToLive, timeUnit, requestDateTimeCache);

        requestDateTimeCache.remove(requestKey);
        log.debug("Deleted cache with key: {}", requestKey);
    }
}
