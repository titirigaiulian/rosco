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

package com.netflix.spinnaker.rosco.manifests.secrets;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SecretsInjector {

  // {{secretsmanager.path(/path/to/secret).key("key_of_secret")}}

  List<SecretResolver> secretResolvers;

  @Autowired
  public SecretsInjector(List<SecretResolver> secretResolvers) {
    this.secretResolvers = secretResolvers;
  }

  public String resolve(String value) {
    if (isSecret(value)) {
      return resolve(
          getSecretTypeFromValue(value), getSecretPathFromValue(value), getSecretKeyValue(value));
    }
    return value;
  }

  private String resolve(String type, String path, String key) {

    return secretResolvers.stream()
        .filter(sr -> sr.supports(type))
        .findFirst()
        .orElseThrow(
            () ->
                new IllegalArgumentException(
                    "Could not resolve secret type " + type + " with path " + path))
        .resolveSecret(path, key);
  }

  private boolean isSecret(String value) {
    return value.trim().startsWith("{{") && value.endsWith("}}");
  }

  private String getSecretTypeFromValue(String value) {
    if (!isSecret(value)) {
      throw new IllegalArgumentException("Cannot get secret type. Required format: secret(type)");
    }
    /* {{awssecretsmanager.path(/path/to/secret).key(key_of_secret)}} will result in
    [awssecretsmanager, path(/path/to/secret), key(key_of_secret)]
     */
    String[] parts = value.substring(2, value.length() - 2).split("\\.\\s*(?![^()]*\\))");
    System.out.println("Secret type: " + parts[0].replace("path(", "").replace(")", ""));
    return parts[0].replace("path(", "").replace(")", "");
  }

  private String getSecretPathFromValue(String value) {
    if (!isSecret(value)) {
      throw new IllegalArgumentException("Cannot get secret type. Required format: secret(type)");
    }
    String[] parts = value.substring(2, value.length() - 2).split("\\.\\s*(?![^()]*\\))");
    System.out.println("Secret path: " + parts[1].replace("path(", "").replace(")", ""));
    return parts[1].replace("path(", "").replace(")", "");
  }

  private String getSecretKeyValue(String value) {
    if (!isSecret(value)) {
      throw new IllegalArgumentException("Cannot get secret type. Required format: secret(type)");
    }
    String[] parts = value.substring(2, value.length() - 2).split("\\.\\s*(?![^()]*\\))");
    System.out.println("Secret key: " + parts[2].replace("key(", "").replace(")", ""));
    return parts[2].replace("key(", "").replace(")", "");
  }
}
