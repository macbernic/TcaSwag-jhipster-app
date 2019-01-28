package io.github.tcaswag.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import io.github.jhipster.config.jcache.BeanClassLoaderAwareJCacheRegionFactory;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        BeanClassLoaderAwareJCacheRegionFactory.setBeanClassLoader(this.getClass().getClassLoader());
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(io.github.tcaswag.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(io.github.tcaswag.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(io.github.tcaswag.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.tcaswag.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.tcaswag.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(io.github.tcaswag.domain.Product.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.tcaswag.domain.Product.class.getName() + ".assets", jcacheConfiguration);
            cm.createCache(io.github.tcaswag.domain.Product.class.getName() + ".availableSkuses", jcacheConfiguration);
            cm.createCache(io.github.tcaswag.domain.ProductSku.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.tcaswag.domain.ProductAsset.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.tcaswag.domain.MemberOrder.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.tcaswag.domain.MemberOrder.class.getName() + ".items", jcacheConfiguration);
            cm.createCache(io.github.tcaswag.domain.MemberOrderItem.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.tcaswag.domain.Member.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.tcaswag.domain.Member.class.getName() + ".orders", jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
