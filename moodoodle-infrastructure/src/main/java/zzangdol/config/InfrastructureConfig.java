package zzangdol.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import zzangdol.InfrastructurePackageLocation;

@Configuration
@ComponentScan(basePackageClasses = InfrastructurePackageLocation.class)
public class InfrastructureConfig {
}
