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

import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(JUnitPlatform.class)
@SpringBootTest(
    classes = SecretResolverConfiguration.class,
    webEnvironment = SpringBootTest.WebEnvironment.NONE,
    properties = {
      "secrets.awssecretsmanager.enabled=false",
      "secrets.awssecretsmanager.region=us-east-1",
      "secrets.awssecretsmanager.awsProfile=ee-dev"
    })
public class SecretResolverIntegrationTest {

  /*
  {{secret(secretsmanager).path(/path/to/secret).key("key_of_secret")}}
   */

  //  @Autowired
  //  AwsSecretsManagerClient awsSecretsManagerClient;
  //  @Autowired
  //  AwsSecretsManagerSecretResolver awsSecretsManagerSecretResolver;
  //  @Autowired VaultSecretResolver vaultSecretResolver;
  //
  //  @Test
  //  public void resolveAwsSecretsManagerSecretTest() {
  //            String awsSecretsManagerSecretPath = "mytestsecret";
  //            String awsSecretsManagerSecretValue = "mytestsecretvalue";
  //            String vaultSecretPath = "mytestsecretvault";
  //            String vaultSecretValue = "mytestsecretvaluevault";
  ////            when(ssmSecretResolver.resolveSecret(ssmSecretPath)).thenReturn(ssmSecretValue);
  ////
  // when(vaultSecretResolver.resolveSecret(vaultSecretPath)).thenReturn(vaultSecretValue);
  //
  //            List<SecretResolver> secretResolvers = new ArrayList<>();
  //            secretResolvers.add(awsSecretsManagerSecretResolver);
  //            secretResolvers.add(vaultSecretResolver);
  //
  //            SecretsInjector secretsInjector = new SecretsInjector(secretResolvers);
  //      System.out.println("*****************");
  //
  // System.out.println(secretsInjector.resolve("{{awssecretsmanager.path(mytestsecret).key(test)}}"));
  //
  // System.out.println(secretsInjector.resolve("{{vault.path(/path/to/secret).key(key_of_secret)}}"));
  //      System.out.println("*****************");
  //  }
}
