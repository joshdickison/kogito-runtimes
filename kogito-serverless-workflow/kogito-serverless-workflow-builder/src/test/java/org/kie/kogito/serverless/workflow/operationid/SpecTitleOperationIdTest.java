/*
 * Copyright 2022 Red Hat, Inc. and/or its affiliates.
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
package org.kie.kogito.serverless.workflow.operationid;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.serverlessworkflow.api.Workflow;
import io.serverlessworkflow.api.functions.FunctionDefinition;
import io.serverlessworkflow.api.functions.FunctionDefinition.Type;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;

class SpecTitleOperationIdTest {

    private Workflow workflow;
    private FunctionDefinition definition;

    @BeforeEach
    void setup() {
        workflow = mock(Workflow.class);
        definition = new FunctionDefinition("pepe");
    }

    @Test
    void testOperationId() {
        definition.setType(Type.REST);
        definition.setOperation("specs/external-service.yaml#sendRequest");
        WorkflowOperationId id = WorkflowOperationIdFactoryType.SPEC_TITLE.factory().from(workflow, definition, Optional.empty());
        assertEquals("sendRequest", id.getOperation());
        assertEquals("external-service", id.getFileName());
        assertEquals("externalservice", id.getPackageName());
        assertEquals("specs/external-service.yaml", id.getUri().toString());
        assertNull(id.getService());
    }
}
