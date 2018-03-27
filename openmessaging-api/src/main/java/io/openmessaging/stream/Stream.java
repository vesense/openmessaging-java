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

package io.openmessaging.stream;

import java.util.Comparator;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import io.openmessaging.ServiceLifecycle;

// java8!
public interface Stream<T> extends ServiceLifecycle {

    // source operators ----------------

    /**
     * Returns a Stream list by specified queue.
     * @param queue
     * @return
     */
    List<Stream> of(String queue);

    /**
     * Returns a sequential Stream by specified queue shard.
     * @param queue
     * @param shard
     * @return
     */
    Stream of(String queue, String shard);

    // operators ----------------

    /**
     * Return a new Stream by selecting only the records of the source Stream on which func returns true.
     * @param predicate
     * @return
     */
    Stream filter(Predicate<? super T> predicate);

    /**
     * Return a new Stream by passing each element of the source Stream through a function func.
     * @param func
     * @return
     */
    Stream map(Function func);

    /**
     * Similar to map, but each input item can be mapped to 0 or more output items.
     * @param mapper
     * @return
     */
    Stream flatMap(Function func);

    /**
     * Performs an action for each element of this stream.
     * @param action
     * @return
     */
    Stream foreach(Consumer<? super T> action);

    /**
     * Returns the maximum element of this stream according to the provided Comparator.
     * @param comparator
     * @return
     */
    Number max(Comparator<? super T> comparator);

    /**
     * Returns the minimum element of this stream according to the provided Comparator.
     * @param comparator
     * @return
     */
    Number min(Comparator<? super T> comparator);

    //Number sum();

    /**
     * Returns the count of elements in this stream.
     * @return
     */
    long count();

    /**
     * Performs a reduction on the elements of this stream, using an associative accumulation function.
     * @param accumulator
     * @return
     */
    Stream reduce(BinaryOperator<T> accumulator);

    // sink operators ----------------

    /**
     * Writes a Stream to the standard output stream (stdout).
     */
    void print();

    /**
     * Materialize this stream to a queue.
     * @param queue
     */
    void to(String queue);

    // join, union, window ----------------

    /**
     * Group the records of this Stream on a specified key.
     * @param key
     * @return
     */
    Stream groupBy(String key);

    /**
     * Creates a join operation.
     * @param stream
     * @return
     */
    Stream join(Stream stream);

    /**
     * Creates a new Stream by merging Stream outputs of the same type with each other.
     * @param stream
     * @return
     */
    Stream union(Stream stream);

    /**
     * Windows this Stream into tumbling/sliding time windows.
     * @return
     */
    Stream window();
}
