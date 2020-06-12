package com.database;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.database.containers.Meeting;
import com.database.databasewriter.DatabaseWriter;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.json.JSONObject;

import java.io.IOException;
import java.util.*;

public class ConsumerMain {
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final DatabaseWriter d = new DatabaseWriter();
    private final static String TOPIC = "storage";
    private final static String BOOTSTRAP_SERVERS =
            "localhost:9092";
    private static Consumer<Long, String> createConsumer() {
        final Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                BOOTSTRAP_SERVERS);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, UUID.randomUUID().toString());
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                LongDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class.getName());
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");
        final Consumer<Long, String> consumer =
                new KafkaConsumer<>(props);

        consumer.seekToBeginning(consumer.assignment());
        consumer.subscribe(Collections.singletonList(TOPIC));
        return consumer;
    }
    void runConsumer() {
        final Consumer<Long, String> consumer = createConsumer();

        final int giveUp = 25;   int noRecordsCount = 0;

        while (true) {
            final ConsumerRecords<Long, String> consumerRecords =
                    consumer.poll(1000);

            if (consumerRecords.count()==0) {
                noRecordsCount++;
                if (noRecordsCount > giveUp) break;
                else continue;
            }

            consumerRecords.forEach(record -> {
                JSONObject jsonObject = new JSONObject(record.value());
                try {
                    Meeting m = mapper.readValue(jsonObject.toString(), Meeting.class);
                    d.write(m);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            consumer.commitAsync();
        }
    }



    public static void main(String[] args) {
        ConsumerMain consumerMain = new ConsumerMain();
        consumerMain.runConsumer();
    }
}
