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
package org.apache.tamaya.filter;

import org.apache.tamaya.spi.PropertyFilter;
import org.apache.tamaya.spi.PropertyValue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * A setCurrent of property filter and accessor methods. This class is built for
 * usage within a single threaded context, so it is NOT thread-safe.
 */
public final class ThreadFilterContext implements PropertyFilter{
    /** The filters. */
    private List<PropertyFilter> filters = new ArrayList<>();

    /**
     * Add a filter.
     * @param filter the filter.
     */
    public void addFilter(PropertyFilter filter){
        filters.add(filter);
    }

    /**
     * Adds a filter at given position.
     * @param pos the position.
     * @param filter the filter.
     */
    public void addFilter(int pos, PropertyFilter filter){
        filters.add(pos, filter);
    }

    /**
     * Removes a filter at a given position.
     * @param pos the position.
     * @return the filter removed, or null.
     */
    public PropertyFilter removeFilter(int pos){
        return filters.remove(pos);
    }

    /**
     * Removes a filter.
     * @param filter the filter to be removed, not null.
     */
    public void removeFilter(PropertyFilter filter) {
        filters.remove(filter);
    }

    /**
     * Clears all filters.
     */
    public void clearFilters(){
        filters.clear();
    }

    /**
     * Set the filters.
     * @param filters the filters to be applied.
     */
    public void setFilters(PropertyFilter... filters){
        setFilters(Arrays.asList(filters));
    }

    /**
     * Set the filters.
     * @param filters the filters to be applied.
     */
    public void setFilters(Collection<PropertyFilter> filters) {
        this.filters.clear();
        this.filters.addAll(filters);
    }

    /**
     * Get all filters.
     * @return all filters.
     */
    public List<PropertyFilter> getFilters(){
        return Collections.unmodifiableList(filters);
    }

    @Override
    public PropertyValue filterProperty(PropertyValue valueToBeFiltered) {
        for(PropertyFilter filter:filters){
            valueToBeFiltered = filter.filterProperty(valueToBeFiltered);
        }
        return valueToBeFiltered;
    }

    @Override
    public String toString() {
        return "ProgrammableFilter{" +
                "filters=" + filters +
                '}';
    }

}
