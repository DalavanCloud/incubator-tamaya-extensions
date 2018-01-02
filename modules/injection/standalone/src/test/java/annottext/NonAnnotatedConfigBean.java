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
package annottext;

/**
 * An example showing some basic annotations, using an interface to be proxied by the
 * configuration system, nevertheless extending the overall Configuration interface.
 * Created by Anatole on 15.02.14.
 */
public class NonAnnotatedConfigBean {

    public String simple_value = "Should be overridden!";

    public String fieldKey;

    public String classFieldKey = "Foo";

    public String fullKey;

    public String test2 = "This is not set.";

}
