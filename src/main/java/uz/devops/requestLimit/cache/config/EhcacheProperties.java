package uz.devops.requestLimit.cache.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "ehcache")
@Data
public class EhcacheProperties {
    private Long timeToLive;
    private final Store store = new Store();
    @Data
    public static class Store {
        private Long maxHeapSize;
        private Integer memoryOffHeap;
    }
}
