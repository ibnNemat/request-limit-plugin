package uz.devops.requestLimit.cache.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ehcache.Cache;
import org.springframework.stereotype.Service;
import uz.devops.requestLimit.cache.annotation.limit.RequestLimit;
import uz.devops.requestLimit.cache.exception.IncompleteProcessException;
import uz.devops.requestLimit.cache.service.EhCacheService;

import java.time.LocalDateTime;

import static uz.devops.requestLimit.cache.config.Constants.EXCEPTION_MESSAGE;

@Service
@Slf4j
@AllArgsConstructor
public class EhCacheServiceImpl implements EhCacheService {
    private final Cache<String, LocalDateTime> requestDateTimeCache;
    @Override
    public void setRequestLimit(String requestKey, RequestLimit requestLimit) {
        log.debug("Check request key: {} in ehcache", requestKey);
        if (!requestDateTimeCache.containsKey(requestKey)) {
            var localDateTime = LocalDateTime.now().plus(requestLimit.timeToLive(), requestLimit.timeUnit().toChronoUnit());
            requestDateTimeCache.put(requestKey, localDateTime);
            log.debug("Created new cache with dataTime: {}", localDateTime);
            return;
        }

        var localDateTime = requestDateTimeCache.get(requestKey);
        var currentTime = LocalDateTime.now();
        if (localDateTime.isAfter(currentTime)) {
            var exMessage = String.format(EXCEPTION_MESSAGE, localDateTime.minusSeconds(currentTime.getSecond()).getSecond());
            log.warn(exMessage);
            throw new IncompleteProcessException(exMessage);
        }

        requestDateTimeCache.remove(requestKey);
        log.debug("Deleted cache with key: {}", requestKey);
    }
}
