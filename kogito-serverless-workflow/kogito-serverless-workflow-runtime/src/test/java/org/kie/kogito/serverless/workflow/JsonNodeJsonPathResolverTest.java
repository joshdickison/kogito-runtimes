/*
 * Copyright 2021 Red Hat, Inc. and/or its affiliates.
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
package org.kie.kogito.serverless.workflow;

import java.util.Collections;

import org.jbpm.process.instance.ProcessInstance;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kie.api.definition.process.Process;
import org.kie.api.runtime.process.WorkflowProcessInstance;
import org.kie.kogito.internal.process.runtime.KogitoNodeInstance;
import org.kie.kogito.internal.process.runtime.KogitoWorkItem;
import org.kie.kogito.jackson.utils.ObjectMapperFactory;
import org.kie.kogito.serverless.workflow.workitemparams.JsonNodeResolver;
import org.kie.kogito.serverless.workflow.workitemparams.ObjectResolver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.withSettings;

class JsonNodeJsonPathResolverTest {

    final ObjectMapper mapper = new ObjectMapper();
    private KogitoWorkItem workItem;

    @BeforeEach
    void setup() {
        workItem = mock(KogitoWorkItem.class);
        KogitoNodeInstance ni = mock(KogitoNodeInstance.class);
        when(workItem.getNodeInstance()).thenReturn(ni);
        WorkflowProcessInstance pi = mock(WorkflowProcessInstance.class, withSettings().extraInterfaces(ProcessInstance.class));
        when(ni.getProcessInstance()).thenReturn(pi);
        Process process = mock(Process.class);
        when(pi.getProcess()).thenReturn(process);
        when(process.getMetaData()).thenReturn(Collections.emptyMap());
    }

    @Test
    void verifyArrayNode() throws JsonProcessingException {
        final JsonNode inputModel = mapper.readTree("{ \"fahrenheit\": \"32\", \"subtractValue\": \"3\" }");
        when(workItem.getParameter("pepe")).thenReturn(inputModel);
        final JsonNodeResolver resolver =
                new JsonNodeResolver("jsonpath", ObjectMapperFactory.get().createArrayNode().add(ObjectMapperFactory.get().createObjectNode().put("leftElement", "$.fahrenheit"))
                        .add(ObjectMapperFactory.get().createObjectNode().put("rightElement", "$.subtractValue")), "pepe");
        final JsonNode processedNode = (JsonNode) resolver.apply(workItem);
        assertTrue(processedNode.isArray());
        assertThat(processedNode.findValue("leftElement").asInt(), equalTo(32));
        assertThat(processedNode.findValue("rightElement").asInt(), equalTo(3));
    }

    @Test
    void verifyValueNode() throws JsonProcessingException {
        final JsonNode inputModel = mapper.readTree("{ \"fahrenheit\": \"32\", \"subtractValue\": \"3\" }");
        when(workItem.getParameter("pepe")).thenReturn(inputModel);
        final JsonNodeResolver resolver = new JsonNodeResolver("jsonpath", "$.fahrenheit", "pepe");
        final JsonNode processedNode = (JsonNode) resolver.apply(workItem);
        assertTrue(processedNode.isValueNode());
        assertThat(processedNode.asInt(), equalTo(32));
    }

    @Test
    void verifyQuotedValueNode() throws JsonProcessingException {
        final JsonNode inputModel = mapper.readTree("{ \"fahrenheit\": \"32\", \"subtractValue\": \"3\" }");
        when(workItem.getParameter("pepe")).thenReturn(inputModel);
        final JsonNodeResolver resolver = new JsonNodeResolver("jsonpath", "\"$.fahrenheit\"", "pepe");
        final JsonNode processedNode = (JsonNode) resolver.apply(workItem);
        assertTrue(processedNode.isValueNode());
        assertThat(processedNode.asText(), equalTo("\"$.fahrenheit\""));
    }

    @Test
    void verifyStringNode() throws JsonMappingException, JsonProcessingException {
        final JsonNode inputModel = mapper.readTree("{ \"fahrenheit\": \"32\", \"subtractValue\": \"3\" }");
        when(workItem.getParameter("pepe")).thenReturn(inputModel);
        final ObjectResolver resolver = new ObjectResolver("jsonpath", "pepa", "pepe");
        assertThat(resolver.apply(workItem).toString(), equalTo("pepa"));
    }
}
