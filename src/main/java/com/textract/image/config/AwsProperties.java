package com.textract.image.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "config.aws")
@Data
public class AwsProperties {
  private String region;
  private String accessKey;
  private String secretKey;
}
