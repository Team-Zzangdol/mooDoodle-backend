package zzangdol.feign.config;

import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import zzangdol.InfrastructurePackageLocation;

@Configuration
@ImportAutoConfiguration({FeignAutoConfiguration.class})
@EnableFeignClients(basePackageClasses = InfrastructurePackageLocation.class)
public class FeignConfig {
}
