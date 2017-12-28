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
package org.apache.tamaya.events;

import org.apache.tamaya.base.configsource.ConfigSourceComparator;

import javax.config.spi.ConfigSource;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * PropertySource implementation that stores all current values of a given (possibly dynamic, contextual and non server
 * capable instance) and is fully serializable. Note that hereby only the scannable key/value pairs are considered.
 */
public final class FrozenConfigSource implements ConfigSource, Serializable {
    private static final long serialVersionUID = -6373137316556444171L;
    /**
     * The ordinal.
     */
    private final int ordinal;
    /**
     * The properties read.
     */
    private Map<String, String> properties = new HashMap<>();
    /**
     * The PropertySource's name.
     */
    private final String name;

    private long frozenAt = System.currentTimeMillis();

    /**
     * Constructor.
     *
     * @param configSource The base ConfigSource.
     */
    private FrozenConfigSource(ConfigSource configSource) {
        this.properties.putAll(configSource.getProperties());
        this.properties = Collections.unmodifiableMap(this.properties);
        this.ordinal = ConfigSourceComparator.getOrdinal(configSource);
        this.name = configSource.getName();
    }

    /**
     * Creates a new FrozenPropertySource instance based on a PropertySource given.
     *
     * @param configSource the config source to be frozen, not null.
     * @return the frozen property source.
     */
    public static FrozenConfigSource of(ConfigSource configSource) {
        if (configSource instanceof FrozenConfigSource) {
            return (FrozenConfigSource) configSource;
        }
        return new FrozenConfigSource(configSource);
    }

    @Override
    public String getName() {
        return this.name;
    }

    public int getOrdinal() {
        return this.ordinal;
    }

    /**
     * Get the creation timestamp of this instance.
     * @return the creation timestamp
     */
    public long getFrozenAt(){
        return frozenAt;
    }

    @Override
    public String getValue(String key) {
        return this.properties.get(key);
    }

    @Override
    public Map<String,String> getProperties() {
        return Collections.unmodifiableMap(properties);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FrozenConfigSource)) {
            return false;
        }
        FrozenConfigSource that = (FrozenConfigSource) o;
        return ordinal == that.ordinal && properties.equals(that.properties);
    }

    @Override
    public int hashCode() {
        int result = ordinal;
        result = 31 * result + properties.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "FrozenPropertySource{" +
                "name=" + name +
                ", ordinal=" + ordinal +
                ", properties=" + properties +
                '}';
    }
}
