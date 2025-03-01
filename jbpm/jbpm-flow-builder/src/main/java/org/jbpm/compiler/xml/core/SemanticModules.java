/*
 * Copyright 2010 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jbpm.compiler.xml.core;

import java.util.HashMap;
import java.util.Map;

import org.jbpm.compiler.xml.SemanticModule;

public class SemanticModules {
    public Map<String, SemanticModule> modules;

    public SemanticModules() {
        this.modules = new HashMap<String, SemanticModule>();
    }

    public void addSemanticModule(SemanticModule module) {
        this.modules.put(module.getUri(),
                module);
    }

    public SemanticModule getSemanticModule(String uri) {
        return this.modules.get(uri);
    }

    public String toString() {
        return this.modules.toString();
    }
}
