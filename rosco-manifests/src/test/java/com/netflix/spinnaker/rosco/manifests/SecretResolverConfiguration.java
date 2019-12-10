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

package com.netflix.spinnaker.rosco.manifests;

import com.netflix.spinnaker.rosco.manifests.secrets.AwsSecretsManagerSecretResolver;
import com.netflix.spinnaker.rosco.manifests.secrets.VaultSecretResolver;
import com.netflix.spinnaker.rosco.manifests.secrets.aws.AwsSecretsManagerAccount;
import com.netflix.spinnaker.rosco.manifests.secrets.aws.AwsSecretsManagerClient;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
public class SecretResolverConfiguration {

  @Bean
  AwsSecretsManagerAccount awsSecretsManagerAccount() {
    return new AwsSecretsManagerAccount();
  }

  @Bean
  AwsSecretsManagerClient awsSecretsManagerClient() {
    return new AwsSecretsManagerClient();
  }

  @Bean
  AwsSecretsManagerSecretResolver awsSecretsManagerSecretResolver() {
    return new AwsSecretsManagerSecretResolver();
  }

  @Bean
  VaultSecretResolver vaultSecretResolver() {
    return new VaultSecretResolver();
  }
}
