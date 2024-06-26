package gr.grnet.eseal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableCaching
@EnableScheduling
public class EsealApplication {

  private static final Logger LOGGER = LoggerFactory.getLogger(EsealApplication.class);

  public static void main(String[] args) {
    SpringApplication.run(EsealApplication.class, args);
  }

  @Bean
  public CacheManager cacheManager() {
    return new ConcurrentMapCacheManager("remote_certificates");
  }

  @CacheEvict(value = "remote_certificates", allEntries = true)
  @Scheduled(fixedRateString = "${caching.spring.remoteCertificatesTTL}")
  public void emptyHotelsCache() {
    LOGGER.info("emptying remote_certificates cache");
  }

  //  @Bean
  //  @Primary
  //  public HostnameVerifier defaultHostnameVerifier() {
  //    return new DefaultHostnameVerifier();
  //  }
  //
  //  @Bean("trustAllVerifier")
  //  public HostnameVerifier allowAllHostnameVerifier() {
  //    return new NoopHostnameVerifier();
  //  }
}
