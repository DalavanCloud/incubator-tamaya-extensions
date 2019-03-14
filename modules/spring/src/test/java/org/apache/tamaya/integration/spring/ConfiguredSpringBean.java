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
package org.apache.tamaya.integration.spring;

import org.apache.tamaya.inject.api.Config;
import org.apache.tamaya.inject.api.ConfigSection;
import org.apache.tamaya.inject.spi.AbsoluteKeyResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;

/**
 * Created by Anatole on 25.09.2015.
 */
@ConfigSection(keyResolver = AbsoluteKeyResolver.class)
public class ConfiguredSpringBean {

    private String message;

    @Autowired
    private Environment env;

    @Value("${myConfiguredValue}")
    private String springInjected;

    @Value("${spring.application:foo}")
    private String springApplication;

    @Config(key="java.version")
    private String javaVersion;

    @Config(defaultValue = "23")
    private int testNumber;

    public String getJavaVersion(){
        return javaVersion;
    }

    public String getSpringApplication(){
        return springApplication;
    }

    public int getTestNumber(){
        return testNumber;
    }

    public Environment getEnv(){
        return env;
    }

    public String getSpringInjected() {
        return springInjected;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
