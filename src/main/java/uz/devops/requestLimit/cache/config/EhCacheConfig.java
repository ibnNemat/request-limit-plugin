package uz.devops.requestLimit.cache.config;

import lombok.AllArgsConstructor;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.MemoryUnit;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import static uz.devops.requestLimit.cache.config.Constants.*;

@EnableCaching
@Configuration
@AllArgsConstructor
public class EhCacheConfig {

    private final RequestLimitProperties requestLimitProperties;

    @Bean(name = EHCACHE_MANAGER_BEAN, destroyMethod = "")
    public CacheManager cacheManager() {
        return CacheManagerBuilder.newCacheManagerBuilder()
                .build(true);
    }
 
    @Bean(EHCACHE_BEAN_WITH_EXPIRE_TIME)
    public Cache<String, LocalDateTime> ehcacheWithExpiredTime() {
        var cache = requestLimitProperties.getCache();
        var timeUnit = TimeUnit.valueOf(cache.getTimeUnit().name());

        return cacheManager().createCache(
                EHCACHE_BEAN_WITH_EXPIRE_TIME,
                CacheConfigurationBuilder.newCacheConfigurationBuilder(
                        String.class,
                        LocalDateTime.class,
                        ResourcePoolsBuilder
                                .heap(cache.getMaxHeapSize())
                                .offheap(cache.getMemoryOffHeap(), MemoryUnit.MB)
                )
                    .withExpiry(
                        ExpiryPolicyBuilder
                            .expiry()
                            .create(Duration.of(cache.getTimeToLive(), timeUnit.toChronoUnit()))
                            .build()
                    )
        );
    }

    @Bean(EHCACHE_BEAN_WITHOUT_EXPIRE_TIME)
    public Cache<String, LocalDateTime> ehcacheWithoutExpiredTime() {
        var cache = requestLimitProperties.getCache();

        return cacheManager().createCache(
                EHCACHE_BEAN_WITHOUT_EXPIRE_TIME,
                CacheConfigurationBuilder.newCacheConfigurationBuilder(
                                String.class,
                                LocalDateTime.class,
                                ResourcePoolsBuilder
                                        .heap(cache.getMaxHeapSize())
                                        .offheap(cache.getMemoryOffHeap(), MemoryUnit.MB)
                        )
        );
    }
}
