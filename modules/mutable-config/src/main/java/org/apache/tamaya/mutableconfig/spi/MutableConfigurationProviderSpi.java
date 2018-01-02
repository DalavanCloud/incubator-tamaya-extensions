/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.tamaya.mutableconfig.spi;

import org.apache.tamaya.mutableconfig.ChangePropagationPolicy;
import org.apache.tamaya.mutableconfig.MutableConfiguration;

import javax.config.Config;


/**
 * Provider SPI used by {@link org.apache.tamaya.mutableconfig.MutableConfigurationProvider}. Providers may override
 * other providers registering with a higher {@link javax.annotation.Priority} value annotated.
 */
public interface MutableConfigurationProviderSpi {

   /**
    * Creates a new {@link MutableConfiguration} with {@code autoCommit = false} as default.
    *
    * @param configuration the configuration, not null.
    * @param propagationPolicy policy that defines how changes are published to the property
    *                          sources.
    * @return a new mutable configuration instance.
    */
   MutableConfiguration createMutableConfiguration(Config configuration,
                                                   ChangePropagationPolicy propagationPolicy);
}
