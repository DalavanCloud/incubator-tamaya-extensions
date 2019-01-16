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
package org.apache.tamaya.collections;

import org.apache.tamaya.Configuration;
import org.apache.tamaya.TypeLiteral;
import org.junit.Ignore;
import org.junit.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Basic tests for Tamaya collection support. Relevant configs for this tests:
 * <pre>base.items=1,2,3,4,5,6,7,8,9,0
 * base.map=1:a, 2:b, 3:c, [4: ]
 * </pre>
 */
//@Ignore("Readonly support has been deactivated as of now.")
public class CollectionsTypedReadOnlyTests {

    @Test(expected=UnsupportedOperationException.class)
    public void testArrayListList_1(){
        Configuration config = Configuration.current();
        List<String> items = config.get("typed.arraylist", new TypeLiteral<List<String>>(){});
        assertThat(items).isNotNull();
        assertThat(items.isEmpty()).isFalse();
        assertThat(items).hasSize(10);
        items.add("test");
    }

    @Test(expected=UnsupportedOperationException.class)
    public void testArrayListList_2(){
        Configuration config = Configuration.current();
        List<String> items = (List<String>) config.get("typed.arraylist", List.class);
        assertThat(items).isNotNull();
        assertThat(items.isEmpty()).isFalse();
        assertThat(items).hasSize(10);
        items.add("test");
    }

    @Test
    public void testLinkedListList_1(){
        Configuration config = Configuration.current();
        List<String> items = config.get("typed.linkedlist", new TypeLiteral<List<String>>(){});
        assertThat(items).isNotNull();
        assertThat(items.isEmpty()).isFalse();
        assertThat(items).hasSize(10);
        items.add("test");
    }

    @Test
    public void testLinkedListList_2(){
        Configuration config = Configuration.current();
        List<String> items = (List<String>) config.get("typed.linkedlist", List.class);
        assertThat(items).isNotNull();
        assertThat(items.isEmpty()).isFalse();
        assertThat(items).hasSize(10);
        items.add("test");
    }


    @Test
    public void testHashSet_1(){
        Configuration config = Configuration.current();
        Set<String> items = config.get("typed.hashset", new TypeLiteral<Set<String>>(){});
        assertThat(items).isNotNull();
        assertThat(items.isEmpty()).isFalse();
        assertThat(items).hasSize(10);
        items.add("test");
    }
    @Test
    public void testHashSet_2(){
        Configuration config = Configuration.current();
        Set<String> items = (Set<String>) config.get("typed.hashset", Set.class);
        assertThat(items).isNotNull();
        assertThat(items.isEmpty()).isFalse();
        assertThat(items).hasSize(10);
        items.add("test");
    }

    @Test
    public void testTreeSet_1(){
        Configuration config = Configuration.current();
        Set<String> items = config.get("typed.treeset", new TypeLiteral<Set<String>>(){});
        assertThat(items).isNotNull();
        assertThat(items.isEmpty()).isFalse();
        assertThat(items).hasSize(10);
        items.add("test");
    }
    @Test
    public void testTreeSet_2(){
        Configuration config = Configuration.current();
        Set<String> items = items = (Set<String>) config.get("typed.treeset", Set.class);
        assertThat(items).isNotNull();
        assertThat(items.isEmpty()).isFalse();
        assertThat(items).hasSize(10);
        items.add("test");
    }

    @Test(expected=UnsupportedOperationException.class)
    public void testHashMap_1(){
        Configuration config = Configuration.current();
        Map<String,String> items = config.get("typed.hashmap", new TypeLiteral<Map<String,String>>(){});
        assertThat(items).isNotNull();
        assertThat(items.isEmpty()).isFalse();
        assertThat(items).hasSize(4);
        assertThat("a").isEqualTo(items.get("1"));
        assertThat("b").isEqualTo(items.get("2"));
        assertThat("c").isEqualTo(items.get("3"));
        assertThat(" ").isEqualTo(items.get("4"));
        items.put("g","hjhhj");
    }
    @Test(expected=UnsupportedOperationException.class)
    public void testHashMap_2(){
        Configuration config = Configuration.current();
        Map<String,String> items = (Map<String,String>) config.get("typed.hashmap", Map.class);
        assertThat(items).isNotNull();
        assertThat(items.isEmpty()).isFalse();
        assertThat(items).hasSize(4);
        assertThat("a").isEqualTo(items.get("1"));
        assertThat("b").isEqualTo(items.get("2"));
        assertThat("c").isEqualTo(items.get("3"));
        assertThat(" ").isEqualTo(items.get("4"));
        items.put("g","hjhhj");
    }


    @Test
    public void testTreeMap_1(){
        Configuration config = Configuration.current();
        Map<String,String> items = config.get("typed.treemap", new TypeLiteral<Map<String,String>>(){});
        assertThat(items).isNotNull();
        assertThat(items.isEmpty()).isFalse();
        assertThat(items).hasSize(4);
        assertThat("a").isEqualTo(items.get("1"));
        assertThat("b").isEqualTo(items.get("2"));
        assertThat("c").isEqualTo(items.get("3"));
        assertThat(" ").isEqualTo(items.get("4"));
        items.put("g","hjhhj");
    }
    @Test
    public void testTreeMap_2(){
        Configuration config = Configuration.current();
        Map<String,String> items = (Map<String,String>) config.get("typed.treemap", Map.class);
        assertThat(items).isNotNull();
        assertThat(items.isEmpty()).isFalse();
        assertThat(items).hasSize(4);
        assertThat("a").isEqualTo(items.get("1"));
        assertThat("b").isEqualTo(items.get("2"));
        assertThat("c").isEqualTo(items.get("3"));
        assertThat(" ").isEqualTo(items.get("4"));
        items.put("g","hjhhj");
    }

}
