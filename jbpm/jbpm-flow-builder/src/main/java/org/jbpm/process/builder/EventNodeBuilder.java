/*
 * Copyright 2014 Red Hat, Inc. and/or its affiliates.
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
package org.jbpm.process.builder;

import java.util.HashMap;
import java.util.Map;

import org.drools.drl.ast.descr.ProcessDescr;
import org.jbpm.process.core.impl.DataTransformerRegistry;
import org.jbpm.workflow.core.WorkflowProcess;
import org.jbpm.workflow.core.node.Transformation;
import org.kie.api.definition.process.Node;
import org.kie.api.definition.process.Process;
import org.kie.api.runtime.process.DataTransformer;

public class EventNodeBuilder implements ProcessNodeBuilder {

    @Override
    public void build(Process process, ProcessDescr processDescr, ProcessBuildContext context, Node node) {
        Transformation transformation = (Transformation) node.getMetaData().get("Transformation");
        if (transformation != null) {
            WorkflowProcess wfProcess = (WorkflowProcess) process;
            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("imports", wfProcess.getImports());
            parameters.put("classloader", context.getConfiguration().getClassLoader());

            DataTransformer transformer = DataTransformerRegistry.get().find(transformation.getLanguage());
            transformation.setCompiledExpression(transformer.compile(transformation.getExpression(), parameters));
        }

    }

}
