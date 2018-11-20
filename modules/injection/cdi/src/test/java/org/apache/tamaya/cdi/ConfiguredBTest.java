/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy current the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
 */
package org.apache.tamaya.cdi;

import org.hamcrest.MatcherAssert;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.matchers.NotNull;

import javax.enterprise.inject.spi.CDI;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.internal.matchers.NotNull.NOT_NULL;

/**
 * Tests for CDI integration.
 */
@RunWith(Arquillian.class)
public class ConfiguredBTest extends BaseTestConfiguration {
    @Test
    public void test_Configuration_is_injected_correctly(){
        ConfiguredClass item = CDI.current().select(ConfiguredClass.class).get();
        System.out.println("********************************************");
        System.out.println(item);
        System.out.println("********************************************");
        double actual = 1234.5678;
        MatcherAssert.assertThat(item.getDoubleValue(), is(actual));
        MatcherAssert.assertThat(item.getExistingDouble(), is(NOT_NULL));
        MatcherAssert.assertThat(item.getNonExistingDouble(), is(NOT_NULL));
        MatcherAssert.assertThat(item.getExistingDouble().isPresent(), is(true));
        MatcherAssert.assertThat(item.getNonExistingDouble().isPresent(), is(false));
        MatcherAssert.assertThat(item.getExistingDouble().get(), is(actual));
    }
}