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
package org.apache.tamaya.functions;

import org.apache.tamaya.base.configsource.ConfigSourceComparator;

import javax.config.spi.ConfigSource;
import java.util.*;

/**
 * PropertySource, that has values added or overridden.
 */
class EnrichedConfigSource implements ConfigSource {

    private final ConfigSource basePropertySource;

    private final Map<String, String> addedProperties = new HashMap<>();

    private final boolean overriding;

    /**
     * Constructor.
     *
     * @param propertySource the base property source, not null.
     * @param properties the properties to be added.
     * @param overriding flag if existing properties are overridden.
     */
    EnrichedConfigSource(ConfigSource propertySource, Map<String, String> properties, boolean overriding) {
        this.basePropertySource = Objects.requireNonNull(propertySource);
        for(Map.Entry<String,String> en:properties.entrySet()){
            this.addedProperties.putAll(properties);
        }
        this.overriding = overriding;
    }


    @Override
    public int getOrdinal() {
        return ConfigSourceComparator.getOrdinal(basePropertySource);
    }

    @Override
    public String getName() {
        return basePropertySource.getName();
    }

    @Override
    public String getValue(String key) {
        if (overriding) {
            String val = addedProperties.get(key);
            if (val != null) {
                return val;
            }
            return basePropertySource.getValue(key);
        }
        String val = basePropertySource.getValue(key);
        if (val != null) {
            return val;
        }
        return addedProperties.get(key);

    }

    @Override
    public Map<String, String> getProperties() {
        Map<String, String> allProps = new HashMap<>();
        if (overriding) {
            allProps.putAll(basePropertySource.getProperties());
            allProps.putAll(addedProperties);
        } else {
            allProps.putAll(addedProperties);
            allProps.putAll(basePropertySource.getProperties());
        }
        return allProps;
    }

}
