package io.openmessaging.samples.consumer;

import io.openmessaging.Message;
import io.openmessaging.MessagingAccessPoint;
import io.openmessaging.OMS;
import io.openmessaging.ResourceManager;
import io.openmessaging.consumer.ShardingConsumer;
import io.openmessaging.exception.OMSResourceNotExistException;
import java.util.List;

public class ShardingConsumerApp {
    public static void main(String[] args) throws OMSResourceNotExistException {
        final MessagingAccessPoint messagingAccessPoint =
            OMS.getMessagingAccessPoint("oms:rocketmq://alice@rocketmq.apache.org/us-east:default_space");

        messagingAccessPoint.startup();

        //Create a Queue resource
        ResourceManager resourceManager = messagingAccessPoint.resourceManager();
        String targetQueue = "HELLO_QUEUE";
        resourceManager.createQueue(targetQueue, OMS.newKeyValue());

        List<String> shards = resourceManager.listShards(targetQueue);
        final ShardingConsumer shardingConsumer = messagingAccessPoint.createShardingConsumer();
        shardingConsumer.startup();

        //StreamingIterator streamingIterator = shardingConsumer.seekToBeginning(shards.get(0));
        //
        //while (streamingIterator.hasNext()) {
        //    Message message = streamingIterator.next();
        //    System.out.println("Received one message: " + message);
        //}
        //
        ////All the messages in the stream has been consumed.
        //
        ////Now consume the messages in reverse order
        //while (streamingIterator.hasPrevious()) {
        //    Message message = streamingIterator.previous();
        //    System.out.println("Received one message again: " + message);
        //}

        //receive one message from queue shard.
        Message message = shardingConsumer.receive("shard01");

        //Acknowledges the consumed message
        shardingConsumer.ack(message.sysHeaders().getString(Message.BuiltinKeys.MessageId));

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                shardingConsumer.shutdown();
                messagingAccessPoint.shutdown();
            }
        }));

    }
}
