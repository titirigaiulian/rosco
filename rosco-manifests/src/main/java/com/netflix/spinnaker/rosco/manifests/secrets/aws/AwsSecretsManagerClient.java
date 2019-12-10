/*
 * Copyright 2019 Netflix, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.netflix.spinnaker.rosco.manifests.secrets.aws;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest;
import com.amazonaws.services.secretsmanager.model.GetSecretValueResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Slf4j
@Component
public class AwsSecretsManagerClient {

  @Autowired AwsSecretsManagerAccount awsSecretsManagerAccount;

  private final ObjectMapper objectMapper = new ObjectMapper();

  private AWSSecretsManager getAwsSecretsManagerClient() {

    AWSSecretsManagerClientBuilder clientBuilder = AWSSecretsManagerClientBuilder.standard();
    if (!StringUtils.isEmpty(awsSecretsManagerAccount.getRegion())) {
      clientBuilder.withRegion(awsSecretsManagerAccount.getRegion());
    }

    if (!StringUtils.isEmpty(awsSecretsManagerAccount.getAwsAccessKeyId())
        && !StringUtils.isEmpty(awsSecretsManagerAccount.getAwsSecretAccessKey())) {
      BasicAWSCredentials awsStaticCreds =
          new BasicAWSCredentials(
              awsSecretsManagerAccount.getAwsAccessKeyId(),
              awsSecretsManagerAccount.getAwsSecretAccessKey());
      clientBuilder.withCredentials(new AWSStaticCredentialsProvider(awsStaticCreds));
    }

    if (!StringUtils.isEmpty(awsSecretsManagerAccount.getAwsProfile())) {
      clientBuilder.withCredentials(
          new ProfileCredentialsProvider(awsSecretsManagerAccount.getAwsProfile()));
    }

    return clientBuilder.build();
  }

  public String getAwsSecretsManagerSecret(String path, String key) {
    GetSecretValueRequest getSecretValueRequest = new GetSecretValueRequest().withSecretId(path);
    GetSecretValueResult getSecretValueResult =
        getAwsSecretsManagerClient().getSecretValue(getSecretValueRequest);
    String resolvedSecret = getSecretValueResult.getSecretString();
    try {
      return objectMapper.readValue(resolvedSecret, Map.class).get(key).toString();
    } catch (Exception e) {
      log.error("Secret not found");
      return null;
    }
  }
}
