package com.canny.snowflakemigration.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import org.hibernate.cache.jcache.ConfigSettings;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.canny.snowflakemigration.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.canny.snowflakemigration.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.canny.snowflakemigration.domain.User.class.getName());
            createCache(cm, com.canny.snowflakemigration.domain.Authority.class.getName());
            createCache(cm, com.canny.snowflakemigration.domain.User.class.getName() + ".authorities");
            createCache(cm, com.canny.snowflakemigration.domain.SourceConnection.class.getName());
            createCache(cm, com.canny.snowflakemigration.domain.SnowflakeConnection.class.getName());
            createCache(cm, com.canny.snowflakemigration.domain.MigrationProcess.class.getName());
            createCache(cm, com.canny.snowflakemigration.domain.SnowDDL.class.getName());
            createCache(cm, com.canny.snowflakemigration.domain.SnowDDLProcessStatus.class.getName());
            createCache(cm, com.canny.snowflakemigration.domain.SnowDDLJobStatus.class.getName());
            createCache(cm, com.canny.snowflakemigration.domain.MigrationProcessStatus.class.getName());
            createCache(cm, com.canny.snowflakemigration.domain.MigrationProcessJobStatus.class.getName());
            createCache(cm, com.canny.snowflakemigration.domain.DeltaProcess.class.getName());
            createCache(cm, com.canny.snowflakemigration.domain.DeltaProcessStatus.class.getName());
            createCache(cm, com.canny.snowflakemigration.domain.DeltaProcessJobStatus.class.getName());
            createCache(cm, com.canny.snowflakemigration.domain.SnowHistory.class.getName());
            createCache(cm, com.canny.snowflakemigration.domain.SnowHistoryProcessStatus.class.getName());
            createCache(cm, com.canny.snowflakemigration.domain.SnowHistoryJobStatus.class.getName());
            createCache(cm, com.canny.snowflakemigration.domain.SnowParse.class.getName());
            createCache(cm, com.canny.snowflakemigration.domain.SnowParseProcessStatus.class.getName());
            createCache(cm, com.canny.snowflakemigration.domain.SnowParseJobStatus.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cm.destroyCache(cacheName);
        }
        cm.createCache(cacheName, jcacheConfiguration);
    }
}
