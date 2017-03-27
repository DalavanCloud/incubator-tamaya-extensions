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

import org.apache.tamaya.ConfigurationProvider;
import org.apache.tamaya.spi.PropertySource;
import org.apache.tamaya.spi.PropertyValue;

import java.util.*;

import static java.lang.System.arraycopy;

/**
 * Accessor that provides useful functions along with configuration.
 */
public final class PropertySourceFunctions {
    /**
     * Implementation of an empty propertySource.
     */
    private static final PropertySource EMPTY_PROPERTYSOURCE = new PropertySource() {

        @Override
        public int getOrdinal() {
            return 0;
        }

        @Override
        public String getName() {
            return "<empty>";
        }

        @Override
        public PropertyValue get(String key) {
            return null;
        }

        @Override
        public Map<String, PropertyValue> getProperties() {
            return Collections.emptyMap();
        }

        @Override
        public boolean isScannable() {
            return true;
        }

        @Override
        public String toString() {
            return "PropertySource<empty>";
        }
    };

    /**
     * Private singleton constructor.
     */
    private PropertySourceFunctions() {
    }

    /**
     * Calculates the current section key and compares it to the given key.
     *
     * @param key        the fully qualified entry key, not null
     * @param sectionKey the section key, not null
     *
     * @return true, if the entry is exact in this section
     */
    public static boolean isKeyInSection(String key, String sectionKey) {
        Objects.requireNonNull(key, "Key must be given.");
        Objects.requireNonNull(sectionKey, "Section key must be given.");

        sectionKey = normalizeSectionKey(sectionKey);

        int lastIndex = key.lastIndexOf('.');
        String curAreaKey = lastIndex > 0 ? key.substring(0, lastIndex) : "";
        return curAreaKey.equals(sectionKey);
    }

    private static String normalizeKey(String key) {
        return key.startsWith(".") ? key.substring(1)
                                   : key;
    }

    static String normalizeSectionKey(String sectionKey) {
        // Ignore unneeded and trailing dot at the end of the section key

        String normalizedKey = sectionKey.endsWith(".")
                   ? sectionKey.substring(0, sectionKey.length() - 1)
                   : sectionKey;

        normalizedKey = sectionKey.startsWith(".") ? sectionKey.length() == 1 ? ""
                                                                              : normalizedKey.substring(1)
                                                   : normalizedKey;

        return normalizedKey;
    }

    /**
     * Calculates the current section key and compares it to the given section keys.
     *
     * @param key             the fully qualified entry key, not {@code null}
     * @param sectionKey      the section keys, not {@code null}
     * @param moreSectionKeys the more section keys, not {@code null}
     *
     * @return true, if the entry is in one of the given sections
     */
    public static boolean isKeyInSections(String key, String sectionKey, String... moreSectionKeys) {
        Objects.requireNonNull(key, "Key must be given.");
        Objects.requireNonNull(sectionKey, "At least one section key must be given.");
        Objects.requireNonNull(moreSectionKeys, "Additional section keys must not be null.");

        String[] sectionKeys = new String[moreSectionKeys.length + 1];
        sectionKeys[0] = sectionKey;

        if (moreSectionKeys.length > 0) {
            arraycopy(moreSectionKeys, 0, sectionKeys, 1, moreSectionKeys.length);
        }

        return isKeyInSections(key, sectionKeys);
    }

    /**
     * Calculates the current section key and compares it to the given section keys.
     *
     * @param key             the fully qualified entry key, not {@code null}
     * @param sectionKeys     the section keys, not {@code null}
     *
     *  @return true, if the entry is in one of the given sections
     */
    public static boolean isKeyInSections(String key, String[] sectionKeys) {
        Objects.requireNonNull(key, "Key must be given.");
        Objects.requireNonNull(sectionKeys, "Section keys must be given.");

        boolean result = false;

        for (String areaKey : sectionKeys) {
            if (areaKey == null) {
                continue;
            }

            if (isKeyInSection(key, areaKey)) {
                result = true;
                break;
            }
        }

        return result;
    }

    /**
     * Return a query to evaluate the set with all fully qualified section names. This method should return the sections as accurate as possible,
     * but may not provide a complete set of sections that are finally accessible, especially when the underlying storage
     * does not support key iteration.
     *
     * @param properties properties to find sections in.
     * @return set with all sections, never {@code null}.
     */
    public static Set<String> sections(Map<String, String> properties) {
        final Set<String> areas = new HashSet<>();
        for (String key : properties.keySet()) {
            String normalizedKey = normalizeKey(key);

            int index = normalizedKey.lastIndexOf('.');
            if (index > 0) {
                areas.add(normalizedKey.substring(0, index));
            } else {
                areas.add("<root>");
            }
        }
        return areas;
    }

    /**
     * Return a query to evaluate the set with all fully qualified section names, containing the transitive closure also including all
     * subarea names, regardless if properties are accessible or not. This method should return the sections as accurate
     * as possible, but may not provide a complete set of sections that are finally accessible, especially when the
     * underlying storage does not support key iteration.
     * 
     * @param properties properties to find transitive sections in.
     * @return s set with all transitive sections, never {@code null}.
     */
    public static Set<String> transitiveSections(Map<String, String> properties) {
        final Set<String> transitiveAreas = new HashSet<>();
        for (String section : sections(properties)) {
            section = normalizeSectionKey(section);

            int index = section.lastIndexOf('.');
            if (index < 0 && section.isEmpty()) {
                transitiveAreas.add("<root>");
            } if (index < 0) {
                transitiveAreas.add(section);
            } else {
                while (index > 0) {
                    section = section.substring(0, index);
                    transitiveAreas.add(section);
                    index = section.lastIndexOf('.');
                }
            }
        }
        return transitiveAreas;
    }

    /**
     * Return a query to evaluate the set with all fully qualified section names, containing only the
     * sections that match the predicate and have properties attached. This method should return the sections as accurate as possible,
     * but may not provide a complete set of sections that are finally accessible, especially when the underlying storage
     * does not support key iteration.
     * 
     * @param properties properties to find sections in.
     * @param predicate A predicate to determine, which sections should be returned, not {@code null}.
     * @return s set with all sections, never {@code null}.
     */
    public static Set<String> sections(Map<String, String> properties, final Predicate<String> predicate) {
        Set<String> treeSet = new TreeSet<>();
        for (String area : sections(properties)) {
            if (predicate.test(area)) {
                treeSet.add(area);
            }
        }
        return treeSet;
    }

    /**
     * Return a query to evaluate the set with all fully qualified section names, containing the transitive closure also including all
     * subarea names, regardless if properties are accessible or not. This method should return the sections as accurate as possible,
     * but may not provide a complete set of sections that are finally accessible, especially when the underlying storage
     * does not support key iteration.
     *
     * @param properties properties to find transitive sections in.
     * @param predicate A predicate to determine, which sections should be returned, not {@code null}.
     * @return s set with all transitive sections, never {@code null}.
     */
    public static Set<String> transitiveSections(Map<String, String> properties, Predicate<String> predicate) {
        Set<String> treeSet = new TreeSet<>();
        for (String area : transitiveSections(properties)) {
            if (predicate.test(area)) {
                treeSet.add(area);
            }
        }
        return treeSet;
    }


    /**
     * Creates a ConfigOperator that creates a Configuration containing only keys
     * that are contained in the given section (recursive). Hereby
     * the section key is stripped away from the Map of the resulting keys.
     *
     * @param properties properties to find recursive sections in.
     * @param sectionKeys the section keys, not null
     * @return the section configuration, with the areaKey stripped away.
     */
    public static Map<String, String> sectionsRecursive(Map<String, String> properties, String... sectionKeys) {
        return sectionRecursive(properties, true, sectionKeys);
    }

    /**
     * Creates a ConfigOperator that creates a Configuration containing only keys
     * that are contained in the given section (recursive).
     *
     * @param properties properties to find sections in.
     * @param sectionKeys the section keys, not null
     * @param stripKeys   if set to true, the section key is stripped away fromMap the resulting key.
     * @return the section configuration, with the areaKey stripped away.
     */
    public static Map<String, String> sectionRecursive(Map<String, String> properties, boolean stripKeys, String... sectionKeys) {
        Map<String, String> result = new HashMap<>(properties.size());
        if (stripKeys) {
            for (Map.Entry<String, String> en : properties.entrySet()) {
                if (isKeyInSections(en.getKey(), sectionKeys)) {
                    result.put(en.getKey(), en.getValue());
                }
            }
        } else {
            for (Map.Entry<String, String> en : properties.entrySet()) {
                if (isKeyInSections(en.getKey(), sectionKeys)) {
                    result.put(stripSectionKeys(en.getKey(), sectionKeys), en.getValue());
                }
            }
        }
        return result;
    }

    /**
     * Strips the section key of the given absolute key, if it is one of the areaKeys passed.
     *
     * @param key      the current key, not null.
     * @param areaKeys the areaKeys, not null.
     * @return the stripped key, or the original key (if no section was matching).
     */
    static String stripSectionKeys(String key, String... areaKeys) {
        for (String areaKey : areaKeys) {
            if (key.startsWith(areaKey + '.')) {
                return key.substring(areaKey.length() + 1);
            }
        }
        return key;
    }

    /**
     * Creates a ConfigOperator that adds the given items.
     *
     * @param propertySource source property source that is changed.
     * @param items    the items to be added/replaced.
     * @param override if true, all items existing are overridden by the new ones passed.
     * @return the ConfigOperator, never null.
     */
    public static PropertySource addItems(PropertySource propertySource, final Map<String, String> items, final boolean override) {
        return new EnrichedPropertySource(propertySource, items, override);
    }

    /**
     * Creates an operator that adds items to the instance.
     *
     * @param propertySource source property source that is changed.
     * @param items the items, not null.
     * @return the operator, never null.
     */
    public static PropertySource addItems(PropertySource propertySource, Map<String, String> items) {
        return addItems(propertySource, items, false);
    }

    /**
     * Creates an operator that replaces the given items.
     *
     * @param propertySource source property source that is changed.
     * @param items the items.
     * @return the operator for replacing the items.
     */
    public static PropertySource replaceItems(PropertySource propertySource, Map<String, String> items) {
        return addItems(propertySource, items, true);
    }

    /**
     * Accesses an empty PropertySource.
     *
     * @return an empty PropertySource, never null.
     */
    public static PropertySource emptyPropertySource() {
        return EMPTY_PROPERTYSOURCE;
    }

    /**
     * Find all {@link PropertySource} instances managed by the current
     * {@link org.apache.tamaya.spi.ConfigurationContext} that are assignable to the given type.
     *
     * @param expression the regular expression to match the source's name.
     * @return the list of all {@link PropertySource} instances matching, never null.
     */
    public static Collection<? extends PropertySource> findPropertySourcesByName(String expression) {
        List result = new ArrayList<>();
        for (PropertySource src : ConfigurationProvider.getConfigurationContext().getPropertySources()) {
            if (src.getName().matches(expression)) {
                result.add(src);
            }
        }
        return result;
    }

    /**
     * Get a list of all {@link PropertySource} instances managed by the current
     * {@link org.apache.tamaya.spi.ConfigurationContext} that are assignable to the given type.
     *
     * @param <T> the type of the property source instances requested 
     * @param type target type to filter for property sources. 
     * @return the list of all {@link PropertySource} instances matching, never null.
     */
    public static <T> Collection<T> getPropertySources(Class<T> type) {
        List<T> result = new ArrayList<>();
        for (PropertySource src : ConfigurationProvider.getConfigurationContext().getPropertySources()) {
            if (type.isAssignableFrom(src.getClass())) {
                result.add((T) src);
            }
        }
        return result;
    }

    /**
     * Get a list of all {@link PropertySource} instances managed by the current
     * {@link org.apache.tamaya.spi.ConfigurationContext} that are assignable to the given type.
     *
     * @param <T> the type of the property source instances requested
     * @param type target type to filter for property sources. 
     * @return the list of all {@link PropertySource} instances matching, never null.
     */
    public static <T> T getPropertySource(Class<T> type) {
        for (PropertySource src : ConfigurationProvider.getConfigurationContext().getPropertySources()) {
            if (type.isAssignableFrom(src.getClass())) {
                return (T) src;
            }
        }
        return null;
    }

}
