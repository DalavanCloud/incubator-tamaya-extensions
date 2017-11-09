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
package org.apache.tamaya.inject;


import org.apache.tamaya.Configuration;

import java.util.function.Supplier;

/**
 * Accessor interface for injection of configuration and configuration templates.
 */
public interface ConfigurationInjector {

    /**
     * Configures the current instance and registers necessary listener to forward config change events as
     * defined by the current annotations in place.
     *
     * Unannotated types are ignored.
     *
     * @param <T> the type of the instance.
     * @param instance the instance to be configured
     * @return the configured instance (allows chaining of operations).
     */
    <T> T configure(T instance);

    /**
     * Configures the current instance and registers necessary listener to forward config change events as
     * defined by the current annotations in place.
     *
     * Unannotated types are ignored.
     *
     * @param <T> the type of the instance.
     * @param instance the instance to be configured
     * @param config the configuration to be used for injection.
     * @return the configured instance (allows chaining of operations).
     */
    <T> T configure(T instance, Configuration config);

    /**
     * Creates a template implementing the annotated methods based on current configuration data.
     * 
     * @param <T> the type of the template.
     * @param templateType the type of the template to be created.
     * @return the configured template.
     */
    <T> T createTemplate(Class<T> templateType);

    /**
     * Creates a template implementing the annotated methods based on current configuration data.
     * 
     * @param <T> the type of the template.
     * @param config the configuration to be used for backing the template.
     * @param templateType the type of the template to be created.
     * @return the configured template.
     */
    <T> T createTemplate(Class<T> templateType, Configuration config);


    /**
     * Creates a supplier for configured instances of the given type {@code T}.
     * 
     * @param supplier the supplier to create new instances.
     * @param <T> the target type.
     * @return a supplier creating configured instances of {@code T}.
     */
    <T> Supplier<T> getConfiguredSupplier(Supplier<T> supplier);

    /**
     * Creates a supplier for configured instances of the given type {@code T}.
     * 
     * @param supplier the supplier to create new instances.
     * @param config the configuration to be used for backing the supplier.
     * @param <T> the target type.
     * @return a supplier creating configured instances of {@code T}.
     */
    <T> Supplier<T> getConfiguredSupplier(Supplier<T> supplier, Configuration config);

}
