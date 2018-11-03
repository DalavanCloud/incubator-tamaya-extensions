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

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests for {@link ConfigEventManager}.
 */
public class ConfigEventManagerTest {

    private Object testAddListenerValue;

    @Test
    public void testAddRemoveListener() throws Exception {
        ConfigEventListener testListener = new ConfigEventListener() {
            @Override
            public void onConfigEvent(ConfigEvent<?> event) {
                testAddListenerValue = event.getResource();
            }
        };
        ConfigEventManager.getInstance().addListener(testListener);
        ConfigEventManager.getInstance().fireEvent(new SimpleEvent("Event1"));
        assertEquals(testAddListenerValue, "Event1");
        ConfigEventManager.getInstance().removeListener(testListener);
        ConfigEventManager.getInstance().fireEvent(new SimpleEvent("Event2"));
        assertEquals(testAddListenerValue, "Event1");
    }

    @Test
    public void testFireEvent() throws Exception {
        ConfigEventListener testListener = new ConfigEventListener() {
            @Override
            public void onConfigEvent(ConfigEvent<?> event) {
                testAddListenerValue = event.getResource();
            }
        };
        ConfigEventManager.getInstance().addListener(testListener);
        ConfigEventManager.getInstance().fireEvent(new SimpleEvent("Event1"));
        assertEquals(testAddListenerValue, "Event1");
        ConfigEventManager.getInstance().removeListener(testListener);
        ConfigEventManager.getInstance().fireEvent(new SimpleEvent("Event2"));
        assertEquals(testAddListenerValue, "Event1");
    }

}