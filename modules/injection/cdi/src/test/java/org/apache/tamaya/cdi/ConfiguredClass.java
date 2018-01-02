/*
 *
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

import org.apache.tamaya.inject.api.ConfigFallbackKeys;

import javax.config.inject.ConfigProperty;
import javax.inject.Singleton;
import java.math.BigDecimal;

/**
 * Class to be loaded from CDI to ensure fields are correctly configured using CDI injection mechanisms.
 */
@Singleton
public class ConfiguredClass{

    @ConfigProperty
    private String testProperty;

    @ConfigProperty(name = "a.b.c.key1", defaultValue = "The current \\${JAVA_HOME} env property is ${env:JAVA_HOME}.")
    @ConfigFallbackKeys({"a.b.c.key2","a.b.c.key3"})
    String value1;

    @ConfigProperty(name="foo")
    @ConfigFallbackKeys({"a.b.c.key2"})
    private String value2;

    @ConfigProperty(defaultValue = "N/A")
    private String runtimeVersion;

    @ConfigProperty(defaultValue = "${sys:java.version}")
    private String javaVersion2;

    @ConfigProperty(defaultValue = "5")
    private Integer int1;

    @ConfigProperty
    private int int2;

    @ConfigProperty
    private boolean booleanT;

    @ConfigProperty(name="BD")
    private BigDecimal bigNumber;

    @ConfigProperty(name="double1")
    private double doubleValue;

    public String getTestProperty() {
        return testProperty;
    }

    public String getValue1() {
        return value1;
    }

    public String getValue2() {
        return value2;
    }

    public String getRuntimeVersion() {
        return runtimeVersion;
    }

    public String getJavaVersion2() {
        return javaVersion2;
    }

    public Integer getInt1() {
        return int1;
    }

    public int getInt2() {
        return int2;
    }

    public boolean isBooleanT() {
        return booleanT;
    }

    public BigDecimal getBigNumber() {
        return bigNumber;
    }

    public double getDoubleValue() {
        return doubleValue;
    }

    @Override
	public String toString(){
        return super.toString() + ": testProperty="+testProperty+", value1="+value1+", value2="+value2
                +", int1="+int1+", int2="+int2+", booleanT="+booleanT+", bigNumber="+bigNumber
                +", runtimeVersion="+runtimeVersion+", javaVersion2="+javaVersion2;
    }

}
