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
package org.kie.kogito.persistence.kafka.quarkus.deployment;

import org.kie.kogito.persistence.kafka.KafkaPersistenceUtils;
import org.kie.kogito.quarkus.addons.common.deployment.KogitoCapability;
import org.kie.kogito.quarkus.addons.common.deployment.RequireCapabilityKogitoAddOnProcessor;

import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.builditem.FeatureBuildItem;
import io.quarkus.deployment.builditem.RunTimeConfigurationDefaultBuildItem;

class KogitoAddOnPersistenceKafkaProcessor extends RequireCapabilityKogitoAddOnProcessor {

    private static final String FEATURE = "kogito-addon-persistence-kafka-extension";
    private static final String QUARKUS_KAFKA_STREAMS_TOPICS_PROP = "quarkus.kafka-streams.topics";

    KogitoAddOnPersistenceKafkaProcessor() {
        super(KogitoCapability.PROCESSES);
    }

    @BuildStep
    FeatureBuildItem feature() {
        return new FeatureBuildItem(FEATURE);
    }

    @BuildStep
    RunTimeConfigurationDefaultBuildItem runTimeConfiguration() {
        return new RunTimeConfigurationDefaultBuildItem(QUARKUS_KAFKA_STREAMS_TOPICS_PROP, KafkaPersistenceUtils.topicName());
    }
}
