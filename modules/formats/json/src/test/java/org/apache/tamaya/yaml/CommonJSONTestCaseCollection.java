/*
 * Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package org.apache.tamaya.yaml;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.URL;

import org.apache.tamaya.base.configsource.ConfigSourceComparator;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.Test;

import javax.config.spi.ConfigSource;

/**
 * Class with a collection of common test cases each JSON processing
 * class must be able to pass.
 */
public abstract class CommonJSONTestCaseCollection {

    abstract ConfigSource getPropertiesFrom(URL source) throws Exception;

    @Test
    public void canReadNonLatinCharacters() throws Exception {
        URL configURL = CommonJSONTestCaseCollection.class
             .getResource("/configs/valid/cyrillic.json");

        assertThat(configURL, Matchers.notNullValue());

        ConfigSource propertySource = getPropertiesFrom(configURL);

        assertThat(propertySource.getValue("name"), Matchers.notNullValue());
        assertThat(propertySource.getValue("name"), equalTo("\u041e\u043b\u0438\u0432\u0435\u0440"));
        assertThat(propertySource.getValue("\u0444\u0430\u043c\u0438\u043b\u0438\u044f"), Matchers.notNullValue());
        assertThat(propertySource.getValue("\u0444\u0430\u043c\u0438\u043b\u0438\u044f"), Matchers.equalTo("Fischer"));
    }

    @Test
    public void canReadUnicodeCharacters() throws Exception {
        URL configURL = CommonJSONTestCaseCollection.class
                .getResource("/configs/valid/kanji.json");

        assertThat(configURL, Matchers.notNullValue());

        ConfigSource propertySource = getPropertiesFrom(configURL);

        assertThat(propertySource.getValue("onamae"), Matchers.notNullValue());
        // 霊屋 = Tamaya
        assertThat(propertySource.getValue("onamae"), equalTo("\u970a\u5c4b"));
    }

    @Test
    public void canReadNestedStringOnlyJSONConfigFile2() throws Exception {
        URL configURL = CommonJSONTestCaseCollection.class
                .getResource("/configs/valid/simple-nested-string-only-config-1.json");

        assertThat(configURL, CoreMatchers.notNullValue());

        ConfigSource properties = getPropertiesFrom(configURL);

        assertTrue(properties.getProperties().keySet().size()>=5);

        String keyB = properties.getValue("b");
        String keyDO = properties.getValue("d.o");
        String keyDP = properties.getValue("d.p");

        assertThat(keyB, notNullValue());
        assertThat(keyB, equalTo("B"));
        assertThat(keyDO, notNullValue());
        assertThat(keyDO, equalTo("O"));
        assertThat(keyDP, Matchers.notNullValue());
        assertThat(keyDP, is("P"));
    }

    @Test
    public void canReadNestedStringOnlyJSONConfigFileWithObjectInTheMiddle()
            throws Exception {
        URL configURL = CommonJSONTestCaseCollection.class
                .getResource("/configs/valid/simple-nested-string-only-config-2.json");

        assertThat(configURL, CoreMatchers.notNullValue());

        ConfigSource properties = getPropertiesFrom(configURL);

        assertTrue(properties.getProperties().keySet().size()>=4);

        String keyA = properties.getValue("a");
        String keyDO = properties.getValue("b.o");
        String keyDP = properties.getValue("b.p");
        String keyC = properties.getValue("c");

        assertThat(keyA, notNullValue());
        assertThat(keyA, is("A"));
        assertThat(keyC, notNullValue());
        assertThat(keyC, equalTo("C"));
        assertThat(keyDO, notNullValue());
        assertThat(keyDO, equalTo("O"));
        assertThat(keyDP, notNullValue());
        assertThat(keyDP, is("P"));
    }

    @Test
    public void canHandleJSONFileWhichContainsAnArray() throws Exception {
        URL configURL = CommonJSONTestCaseCollection.class.getResource("/configs/valid/with-array.json");

        assertThat(configURL, CoreMatchers.notNullValue());

        getPropertiesFrom(configURL).getProperties();
    }

    @Test(expected = IOException.class)
    public void canHandleIllegalJSONFileConsistingOfOneOpeningBracket() throws Exception {
        URL configURL = CommonJSONTestCaseCollection.class.getResource("/configs/invalid/only-opening-bracket.json");

        assertThat(configURL, CoreMatchers.notNullValue());

        getPropertiesFrom(configURL).getProperties();
    }

    @Test(expected = IOException.class)
    public void canHandleIllegalJSONFileWhichIsEmpty() throws Exception {
        URL configURL = CommonJSONTestCaseCollection.class.getResource("/configs/invalid/empty-file.json");

        assertThat(configURL, CoreMatchers.notNullValue());

        getPropertiesFrom(configURL).getProperties();
    }

    @Test
    public void priorityInConfigFileOverwriteExplicitlyGivenPriority() throws Exception {
        URL configURL = CommonJSONTestCaseCollection.class.getResource("/configs/valid/with-explicit-priority.json");

        assertThat(configURL, CoreMatchers.notNullValue());

        ConfigSource properties = getPropertiesFrom(configURL);

        assertThat(ConfigSourceComparator.getOrdinal(properties), is(16784));
    }

    @Test
    public void canReadFlatStringOnlyJSONConfigFile() throws Exception {
        URL configURL = CommonJSONTestCaseCollection.class.getResource("/configs/valid/simple-flat-string-only-config.json");

        assertThat(configURL, CoreMatchers.notNullValue());

        ConfigSource properties = getPropertiesFrom(configURL);

        assertTrue(properties.getProperties().keySet().size()>=3);

        String keyA = properties.getValue("a");
        String keyB = properties.getValue("b");
        String keyC = properties.getValue("c");

        assertThat(keyA, notNullValue());
        assertThat(keyA, equalTo("A"));
        assertThat(keyB, notNullValue());
        assertThat(keyB, is("B"));
        assertThat(keyC, notNullValue());
        assertThat(keyC, is("C"));
    }

    @Test(expected = IOException.class)
    public void emptyJSONFileResultsInConfigException() throws Exception {
        URL configURL = CommonJSONTestCaseCollection.class.getResource("/configs/invalid/empty-file.json");

        assertThat(configURL, CoreMatchers.notNullValue());

        ConfigSource properties = getPropertiesFrom(configURL);

        properties.getProperties();
    }

    @Test
    public void canHandleEmptyJSONObject() throws Exception {
        URL configURL = CommonJSONTestCaseCollection.class.getResource("/configs/valid/empty-object-config.json");

        assertThat(configURL, CoreMatchers.notNullValue());

        ConfigSource properties = getPropertiesFrom(configURL);

        assertTrue(properties.getProperties().keySet().size()>=0);
    }
}
