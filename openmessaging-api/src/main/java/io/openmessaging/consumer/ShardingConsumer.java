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

package io.openmessaging.consumer;

import java.util.List;

import io.openmessaging.KeyValue;
import io.openmessaging.Message;
import io.openmessaging.OMSBuiltinKeys;
import io.openmessaging.ServiceLifecycle;
import io.openmessaging.exception.OMSRuntimeException;

/**
 * A {@code ShardingConsumer} provides low level APIs to open multiple shards
 * from a specified queue and supports submit the consume result by acknowledgement.
 *
 * A {@code Queue} is consists of multiple shards, the {@code Shard} is an abstract concept and
 * can be associated with partition in most messaging systems.
 *
 * @version OMS 1.0.0
 * @since OMS 1.0.0
 */
public interface ShardingConsumer extends ServiceLifecycle {
    /**
     * Returns the attributes of this {@code ShardingConsumer} instance.
     * Changes to the return {@code KeyValue} are not reflected in physical {@code ShardingConsumer}.
     * <p>
     * There are some standard attributes defined by OMS for {@code ShardingConsumer}:
     * <ul>
     * <li> {@link OMSBuiltinKeys#CONSUMER_ID}, the unique consumer id for a consumer instance.
     * <li> {@link OMSBuiltinKeys#OPERATION_TIMEOUT}, the default timeout period for operations of {@code
     * ShardingConsumer}.
     * </ul>
     *
     * @return the attributes
     */
    KeyValue attributes();

    /**
     * Attaches the {@code PullConsumer} to a specified queue.
     *
     * @param queueName a specified queue
     * @return this {@code PullConsumer} instance
     */
    ShardingConsumer attachQueue(String queueName);

    /**
     * Attaches the {@code PullConsumer} to a specified queue with some specified attributes..
     *
     * @param queueName a specified queue
     * @param attributes some specified attributes
     * @return this {@code PullConsumer} instance
     */
    ShardingConsumer attachQueue(String queueName, KeyValue attributes);

    /**
     * Detaches the {@code PullConsumer} from a specified queue.
     * <p>
     * After the success call, this consumer won't receive new message
     * from the specified queue any more.
     *
     * @param queueName a specified queue
     * @return this {@code PullConsumer} instance
     */
    ShardingConsumer detachQueue(String queueName);

    /**
     * Receives the next message from the attached queues of this consumer.
     * <p>
     * This call blocks indefinitely until a message is arrives, the timeout expires,
     * or until this {@code PullConsumer} is shut down.
     *
     * @return the next message received from the attached queues, or null if the consumer is
     * concurrently shut down or the timeout expires
     * @throws OMSRuntimeException if the consumer fails to pull the next message due to some internal error.
     */
    Message receive(String shard);

    /**
     * Receives the next message from the attached queues of this consumer, using the specified attributes.
     * <p>
     * This call blocks indefinitely until a message is arrives, the timeout expires,
     * or until this {@code PullConsumer} is shut down.
     *
     * @param attributes the specified attributes
     * @return the next message received from the attached queues, or null if the consumer is
     * concurrently shut down or the timeout expires
     * @throws OMSRuntimeException if the consumer fails to pull the next message due to some internal error.
     */
    Message receive(String shard, KeyValue attributes);

    /**
     * Acknowledges the specified and consumed message with unique message id.
     * <p>
     * Messages that have been received but not acknowledged may be redelivered.
     *
     * @throws OMSRuntimeException if the consumer fails to acknowledge the messages due to some internal error.
     */
    void ack(String messageId);

    /**
     * Acknowledges the specified and consumed message with the specified attributes.
     * <p>
     * Messages that have been received but not acknowledged may be redelivered.
     *
     * @throws OMSRuntimeException if the consumer fails to acknowledge the messages due to some internal error.
     */
    void ack(String messageId, KeyValue attributes);

    /**
     * Creates a {@code StreamingIterator} from the fixed position of the specified stream.
     * <p>
     * Creates a {@code StreamingIterator} from the begin position if the given position
     * is earlier than the first message's store position in this stream.
     * <p>
     * Creates a {@code StreamingIterator} from the end position, if the given position
     * is later than the last message's store position in this stream.
     * <p>
     * The position is a {@code String} value, may represented by timestamp, offset, cursor,
     * even a casual key.
     *
     * @param shard the specified shard
     * @param position the specified position
     * @return a message iterator at the specified position
     */
    void seek(String shard, String position);

    /**
     * List all shards for specified queue.
     * @param queue the specified queue
     * @return the list of all shards for specified queue
     */
    List<String> shardsFor(String queue);

    /**
     * Get the position for specified shard.
     * @param shard the specified shard
     * @return the position for specified shard
     */
    String position(String shard);

    /**
     * Get the position by timestamp for specified shard.
     * @param shard
     * @param timestamp
     * @return the position by timestamp for specified shard
     */
    String getPositionByTimestamp(String shard, long timestamp);

}
