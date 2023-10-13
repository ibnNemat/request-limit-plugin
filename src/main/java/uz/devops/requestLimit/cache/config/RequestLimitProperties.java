package uz.devops.requestLimit.cache.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import uz.devops.requestLimit.cache.enumeration.RequestTimeUnit;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "request-limit")
@Data
public class RequestLimitProperties {
    private List<String> usernameSkipList;
    private final Cache cache = new Cache();
    @Data
    public static class Cache {
        private Long maxHeapSize;
        private Integer memoryOffHeap;
        private Long timeToLive;
        private RequestTimeUnit timeUnit;
    }
}
