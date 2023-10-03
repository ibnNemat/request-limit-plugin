package uz.devops.requestLimit.cache.config;

import lombok.AllArgsConstructor;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.MemoryUnit;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.Random;

import static uz.devops.requestLimit.cache.config.Constants.EHCACHE_MANAGER_BEAN_NAME;
import static uz.devops.requestLimit.cache.config.Constants.P2P_PAY_EHCACHE_BEAN;

@EnableCaching
@Configuration
@AllArgsConstructor
public class EhCacheConfig {

    private final EhcacheProperties ehcacheProperties;
    private final Random RANDOM = new Random();
    @Bean(name = EHCACHE_MANAGER_BEAN_NAME, destroyMethod = "")
    public CacheManager cacheManager() {
        return CacheManagerBuilder.newCacheManagerBuilder()
                .build(true);
    }
 
    @Bean
    public Cache<String, LocalDateTime> ehcache() {
        return cacheManager().createCache(
                P2P_PAY_EHCACHE_BEAN + "_" + RANDOM.nextInt(),
                CacheConfigurationBuilder.newCacheConfigurationBuilder(
                        String.class,
                        LocalDateTime.class,
                        ResourcePoolsBuilder
                                .heap(ehcacheProperties.getStore().getMaxHeapSize())
                                .offheap(ehcacheProperties.getStore().getMemoryOffHeap(), MemoryUnit.MB)
                )
//                        .withExpiry(
//                            ExpiryPolicyBuilder
//                                .expiry()
//                                .create(Duration.ofHours(ehcacheProperties.getTimeToLive()))
//                                .build()
//                        )
        );
    }
}
