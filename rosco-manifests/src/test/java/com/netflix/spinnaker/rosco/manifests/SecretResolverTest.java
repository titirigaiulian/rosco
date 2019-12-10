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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.netflix.spinnaker.rosco.manifests.secrets.SecretResolver;
import com.netflix.spinnaker.rosco.manifests.secrets.SecretsInjector;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
final class SecretResolverTest {

  @Test
  public void resolveAwsSecretsManagerSecretTest() {

    String secretPath = "mytestsecret";
    String secretKey = "test";
    String secretValue = "test";

    SecretResolver sr1 = mock(SecretResolver.class);
    SecretResolver sr2 = mock(SecretResolver.class);

    when(sr1.supports("awsSecretsManager")).thenReturn(true);
    when(sr1.resolveSecret(secretPath, secretKey)).thenReturn(secretValue);
    when(sr2.supports("bla")).thenReturn(false);

    List<SecretResolver> secretResolvers = new ArrayList<>();
    secretResolvers.add(sr1);
    secretResolvers.add(sr2);

    SecretsInjector secretsInjector = new SecretsInjector(secretResolvers);

    String resolvedSecret = secretsInjector.resolve("secret(awsSecretsManager):mytestsecret");

    verify(sr1, times(1)).resolveSecret("mytestsecret", "test");
    verify(sr2, times(0)).resolveSecret("mytestsecret", "test");
    assert (resolvedSecret.equals(secretValue));
  }
}
