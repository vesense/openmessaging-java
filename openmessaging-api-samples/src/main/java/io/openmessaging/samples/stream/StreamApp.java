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

package io.openmessaging.samples.stream;

import java.util.List;

import io.openmessaging.MessagingAccessPoint;
import io.openmessaging.OMS;
import io.openmessaging.ResourceManager;
import io.openmessaging.exception.OMSResourceNotExistException;
import io.openmessaging.stream.Stream;

public class StreamApp {
    public static void main(String[] args) throws OMSResourceNotExistException {
        final MessagingAccessPoint messagingAccessPoint =
            OMS.getMessagingAccessPoint("oms:rocketmq://localhost:10911/us-east:namespace");

        messagingAccessPoint.startup();

        ResourceManager resourceManager = messagingAccessPoint.resourceManager();
        final Stream stream = messagingAccessPoint.createStream();

        //Consume messages from a simple queue.
        String simpleQueue = "HELLO_QUEUE";
        resourceManager.createQueue( simpleQueue, OMS.newKeyValue());

        List<Stream> streams = stream.of(simpleQueue);
        for (Stream ss : streams) {
            ss.filter(s -> s.equals("k")).map(s -> s + "_suffix").print();
        }

        stream.startup();

        System.out.println("Stream startup OK");

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                stream.shutdown();
                messagingAccessPoint.shutdown();
            }
        }));
    }
}