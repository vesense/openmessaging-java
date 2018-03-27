/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package io.openmessaging;

import io.openmessaging.consumer.MessageListener;
import io.openmessaging.consumer.PullConsumer;
import io.openmessaging.consumer.PushConsumer;
import io.openmessaging.consumer.ShardingConsumer;
import io.openmessaging.exception.OMSRuntimeException;
import io.openmessaging.producer.Producer;
import io.openmessaging.stream.Stream;

/**
 * The {@code MessagingAccessPoint} obtained from {@link OMS} is capable of creating {@code
 * Producer}, {@code Consumer}, {@code ResourceManager}, and so on.
 * <p>
 * For example:
 * <pre>
 * MessagingAccessPoint messagingAccessPoint = MessagingAccessPointFactory.getMessagingAccessPoint("oms:rocketmq://alice@rocketmq.apache.org/us-east:default_space");
 * Producer producer = messagingAccessPoint.createProducer();
 * producer.send(producer.createBytesMessage("HELLO_QUEUE", "HELLO_BODY".getBytes(Charset.forName("UTF-8"))));
 * </pre>
 *
 * @version OMS 1.0.0
 * @since OMS 1.0.0
 */
public interface MessagingAccessPoint extends ServiceLifecycle {
    /**
     * Returns the target OMS specification version of the specified vendor implementation.
     *
     * @return the OMS version of implementation
     * @see OMS#specVersion
     */
    String implVersion();

    /**
     * Returns the attributes of this {@code MessagingAccessPoint} instance.
     * <p>
     * There are some standard attributes defined by OMS for {@code MessagingAccessPoint}:
     * <ul>
     * <li> {@link OMSBuiltinKeys#ACCESS_POINTS}, the specified access points.
     * <li> {@link OMSBuiltinKeys#DRIVER_IMPL}, the fully qualified class name of the specified MessagingAccessPoint's
     * implementation, the default value is {@literal io.openmessaging.<driver_type>.MessagingAccessPointImpl}.
     * <li> {@link OMSBuiltinKeys#NAMESPACE}, the namespace the OMS resource resides in.
     * <li> {@link OMSBuiltinKeys#REGION}, the region the namespace resides in.
     * <li> {@link OMSBuiltinKeys#ACCOUNT_ID}, the ID of the specific account system that owns the resource.
     * </ul>
     *
     * @return the attributes
     */
    KeyValue attributes();

    /**
     * Creates a new {@code Producer} for the specified {@code MessagingAccessPoint}.
     *
     * @return the created {@code Producer}
     * @throws OMSRuntimeException if the {@code MessagingAccessPoint} fails to handle this request
     * due to some internal error
     */
    Producer createProducer();

    /**
     * Creates a new {@code Producer} for the specified {@code MessagingAccessPoint}
     * with some preset attributes.
     *
     * @param attributes the preset attributes
     * @return the created {@code Producer}
     * @throws OMSRuntimeException if the {@code MessagingAccessPoint} fails to handle this request
     * due to some internal error
     */
    Producer createProducer(KeyValue attributes);

    /**
     * Creates a new {@code PushConsumer} for the specified {@code MessagingAccessPoint}.
     * The returned {@code PushConsumer} isn't attached to any queue,
     * uses {@link PushConsumer#attachQueue(String, MessageListener)} to attach queues.
     *
     * @return the created {@code PushConsumer}
     * @throws OMSRuntimeException if the {@code MessagingAccessPoint} fails to handle this request
     * due to some internal error
     */
    PushConsumer createPushConsumer();

    /**
     * Creates a new {@code PushConsumer} for the specified {@code MessagingAccessPoint} with some preset attributes.
     *
     * @param attributes the preset attributes
     * @return the created {@code PushConsumer}
     * @throws OMSRuntimeException if the {@code MessagingAccessPoint} fails to handle this request
     * due to some internal error
     */
    PushConsumer createPushConsumer(KeyValue attributes);

    /**
     * Creates a new {@code PullConsumer} for the specified {@code MessagingAccessPoint}.
     *
     * @return the created {@code PullConsumer}
     * @throws OMSRuntimeException if the {@code MessagingAccessPoint} fails to handle this request
     * due to some internal error
     */
    PullConsumer createPullConsumer();

    /**
     * Creates a new {@code PullConsumer} for the specified {@code MessagingAccessPoint} with some preset attributes.
     *
     * @param attributes the preset attributes
     * @return the created {@code PullConsumer}
     * @throws OMSRuntimeException if the {@code MessagingAccessPoint} fails to handle this request
     * due to some internal error
     */
    PullConsumer createPullConsumer(KeyValue attributes);

    /**
     * Creates a new {@code ShardingConsumer} for the specified {@code MessagingAccessPoint}.
     *
     * @return the created {@code ShardingConsumer}
     * @throws OMSRuntimeException if the {@code MessagingAccessPoint} fails to handle this request
     * due to some internal error
     */
    ShardingConsumer createShardingConsumer();

    /**
     * Creates a new {@code ShardingConsumer} for the specified {@code MessagingAccessPoint} with some preset
     * attributes.
     *
     * @param attributes the preset attributes
     * @return the created {@code ShardingConsumer}
     * @throws OMSRuntimeException if the {@code MessagingAccessPoint} fails to handle this request
     * due to some internal error
     */
    ShardingConsumer createShardingConsumer(KeyValue attributes);

    /**
     * Creates a new {@code Stream} for the specified {@code MessagingAccessPoint}.
     *
     * @return the created {@code Stream}
     * @throws OMSRuntimeException if the {@code MessagingAccessPoint} fails to handle this request
     * due to some internal error
     */
    Stream createStream();

    /**
     * Creates a new {@code Stream} for the specified {@code MessagingAccessPoint} with some preset
     * attributes.
     *
     * @param attributes the preset attributes
     * @return the created {@code Stream}
     * @throws OMSRuntimeException if the {@code MessagingAccessPoint} fails to handle this request
     * due to some internal error
     */
    Stream createStream(KeyValue attributes);

    /**
     * Gets a lightweight {@code ResourceManager} instance from the specified {@code MessagingAccessPoint}.
     *
     * @return the resource manger
     * @throws OMSRuntimeException if the {@code MessagingAccessPoint} fails to handle this request
     * due to some internal error
     */
    ResourceManager resourceManager();
}
