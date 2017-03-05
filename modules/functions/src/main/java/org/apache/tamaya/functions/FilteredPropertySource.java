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

import org.apache.tamaya.spi.PropertySource;
import org.apache.tamaya.spi.PropertyValue;
import org.apache.tamaya.spisupport.PropertySourceComparator;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * PropertySource that on the fly filters out part of the key/values of the underlying PropertySource.
 */
class FilteredPropertySource implements PropertySource {

    private final PropertySource baseSource;
    private final Predicate<String> filter;

    /**
     * Creates a new instance
     * @param baseSource the underlying PropertySource
     * @param filter the filter to be applied.
     */
    public FilteredPropertySource(PropertySource baseSource, Predicate<String> filter){
        this.baseSource = Objects.requireNonNull(baseSource);
        this.filter = Objects.requireNonNull(filter);
    }

    @Override
    public int getOrdinal(){
        return PropertySourceComparator.getOrdinal(baseSource);
    }

    @Override
    public String getName() {
        return baseSource.getName();
    }

    @Override
    public PropertyValue get(String key) {
        PropertyValue val = this.baseSource.get(key);
        if(val!=null && filter.test(val.getKey())) {
            return val;
        }
        return null;
    }

    @Override
    public Map<String, PropertyValue> getProperties(){
        final Map<String,PropertyValue> result = new HashMap<>();
        for(PropertyValue val: this.baseSource.getProperties().values()) {
            if (filter.test(val.getKey())) {
                result.put(val.getKey(), val);
            }
        }
        return result;
    }

    @Override
    public boolean isScannable() {
        return baseSource.isScannable();
    }

    @Override
    public String toString() {
        return "FilteredPropertySource{" +
                "baseSource=" + baseSource +
                ", filter=" + filter +
                '}';
    }
}
