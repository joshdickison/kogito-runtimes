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
package org.jbpm.process.builder;

import java.util.Map;

import org.drools.drl.ast.descr.ProcessDescr;
import org.jbpm.process.core.timer.Timer;
import org.jbpm.workflow.core.DroolsAction;
import org.jbpm.workflow.core.impl.NodeImpl;
import org.jbpm.workflow.core.node.StateBasedNode;
import org.kie.api.definition.process.Node;
import org.kie.api.definition.process.Process;

public class EventBasedNodeBuilder extends ExtendedNodeBuilder {

    public void build(Process process,
            ProcessDescr processDescr,
            ProcessBuildContext context,
            Node node) {
        super.build(process, processDescr, context, node);
        Map<Timer, DroolsAction> timers = ((StateBasedNode) node).getTimers();
        if (timers != null) {
            for (DroolsAction action : timers.values()) {
                buildAction(action, context, (NodeImpl) node);
            }
        }
    }

}
