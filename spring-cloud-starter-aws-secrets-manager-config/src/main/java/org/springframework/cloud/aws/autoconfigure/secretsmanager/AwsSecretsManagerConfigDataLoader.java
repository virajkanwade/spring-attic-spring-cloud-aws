/*
 * Copyright 2013-2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.cloud.aws.autoconfigure.secretsmanager;

import java.util.Collections;

import com.amazonaws.services.secretsmanager.AWSSecretsManager;

import org.springframework.boot.context.config.ConfigData;
import org.springframework.boot.context.config.ConfigDataLoader;
import org.springframework.boot.context.config.ConfigDataLoaderContext;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.cloud.aws.secretsmanager.AwsSecretsManagerPropertySource;

/**
 * @author Eddú Meléndez
 * @since 2.3.0
 */
public class AwsSecretsManagerConfigDataLoader implements ConfigDataLoader<AwsSecretsManagerConfigDataResource> {

	@Override
	public ConfigData load(ConfigDataLoaderContext context, AwsSecretsManagerConfigDataResource resource) {
		try {
			AWSSecretsManager ssm = context.getBootstrapContext().get(AWSSecretsManager.class);
			AwsSecretsManagerPropertySource propertySource = new AwsSecretsManagerPropertySource(resource.getContext(),
					ssm);
			propertySource.init();
			return new ConfigData(Collections.singletonList(propertySource));
		}
		catch (Exception e) {
			throw new ConfigDataResourceNotFoundException(resource, e);
		}
	}

}