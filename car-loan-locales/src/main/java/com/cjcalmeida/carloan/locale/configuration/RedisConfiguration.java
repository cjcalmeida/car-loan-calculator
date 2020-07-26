package com.cjcalmeida.carloan.locale.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

@Configuration
@EnableConfigurationProperties(CacheProperties.class)
@Profile("cloud")
public class RedisConfiguration extends CachingConfigurerSupport {

    private final RedisConnectionFactory connectionFactory;
    private final CacheProperties properties;

    public RedisConfiguration(RedisConnectionFactory connectionFactory, CacheProperties properties) {
        this.connectionFactory = connectionFactory;
        this.properties = properties;
    }

    @Bean
    @Override
    public CacheManager cacheManager() {
        var serializer = new GenericJackson2JsonRedisSerializer();
        return RedisCacheManager.RedisCacheManagerBuilder
                .fromConnectionFactory(connectionFactory)
                .cacheDefaults(
                        RedisCacheConfiguration.defaultCacheConfig()
                                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(serializer))
                                .disableCachingNullValues()
                                .entryTtl(properties.getRedis().getTimeToLive())
                                .prefixCacheNameWith(properties.getRedis().getKeyPrefix() + "::")

                ).build();
    }

    @Override
    public CacheErrorHandler errorHandler() {
        return new CacheErrorHandler() {
            private Logger logger = LoggerFactory.getLogger(CacheErrorHandler.class);
            @Override
            public void handleCacheGetError(RuntimeException e, Cache cache, Object o) {
                logger.error("Error captured on accessing Cache", e);
            }

            @Override
            public void handleCachePutError(RuntimeException e, Cache cache, Object o, Object o1) {
                logger.error("Error captured on accessing Cache", e);
            }

            @Override
            public void handleCacheEvictError(RuntimeException e, Cache cache, Object o) {
                logger.error("Error captured on accessing Cache", e);
            }

            @Override
            public void handleCacheClearError(RuntimeException e, Cache cache) {
                logger.error("Error captured on accessing Cache", e);
            }
        };
    }

}