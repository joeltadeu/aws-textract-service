package com.textract.image.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.textract.TextractClient;

@Configuration
@EnableConfigurationProperties(AwsProperties.class)
public class AwsConfiguration {

  private final AwsProperties awsProperties;

  public AwsConfiguration(AwsProperties awsProperties) {
    this.awsProperties = awsProperties;
  }

  @Bean
  public TextractClient textractClient() {
    String region = awsProperties.getRegion();
    String accessKey = awsProperties.getAccessKey();
    String secretKey = awsProperties.getSecretKey();
    AwsBasicCredentials awsCredentials = AwsBasicCredentials.create(accessKey, secretKey);

    return TextractClient.builder()
        .region(Region.of(region))
        .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
        .build();
  }
}
