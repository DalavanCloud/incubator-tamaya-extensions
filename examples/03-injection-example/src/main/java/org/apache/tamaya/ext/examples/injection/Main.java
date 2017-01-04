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
package org.apache.tamaya.ext.examples.injection;

import org.apache.tamaya.inject.ConfigurationInjection;
import org.apache.tamaya.inject.ConfigurationInjector;

import java.util.logging.LogManager;
import java.util.logging.Logger;


/**
 * Example illustrating the functionality of injecting configuration and
 * defining configuration templates.
 */
public class Main {

    /*
     * Turns off all logging.
     */
    static {
        LogManager.getLogManager().reset();
        Logger globalLogger = Logger.getLogger(java.util.logging.Logger.GLOBAL_LOGGER_NAME);
        globalLogger.setLevel(java.util.logging.Level.OFF);
    }

    private Main() {
    }

    public static void main(String[] args) {
        ConfigurationInjector injector = ConfigurationInjection.getConfigurationInjector();
        ExampleTemplate template = injector.createTemplate(ExampleTemplate.class);

        System.out.println("****************************************************");
        System.out.println("Injection and TemplatesExample");
        System.out.println("****************************************************");
        System.out.println();

        Example example = new Example();
        injector.configure(example);

        System.out.println("Injected:");
        System.out.println("---------");
        System.out.println(example);
        System.out.println("Template:");
        System.out.println("---------");
        System.out.println(template);
        System.out.println();
    }

}
