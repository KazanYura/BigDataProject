package com.database.stream;

import com.database.containers.Meeting;
import com.database.containers.Window2Container;
import com.database.containers.Window3Container;
import com.database.containers.WindowPerMinuteContainer;
import com.database.containers.pcontainers.GroupTopic;
import com.database.databasewriter.DatabaseWriter;
import com.fasterxml.jackson.databind.ObjectMapper;
import kafka.serializer.StringDecoder;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaPairInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka.KafkaUtils;
import org.json.JSONObject;
import scala.Tuple2;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class SparkStreamer {
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final DatabaseWriter databaseWriter = new DatabaseWriter();

    public static void task1(JavaPairInputDStream<String, String> directKafkaStream, final long[] time) {
        JavaDStream<String> citiesPerMinute = directKafkaStream.window(Durations.minutes(1)).map((rdd) -> {
            JSONObject jsonObject = new JSONObject(rdd._2);
            Meeting m = mapper.readValue(jsonObject.toString(), Meeting.class);
            return m.group.group_country;
        });
        JavaDStream<List<String>> j = citiesPerMinute.glom();
        j.foreachRDD(listJavaRDD -> {
            try {
                List<String> list = listJavaRDD.first();
                HashMap<String, Integer> hashMap = new HashMap<>();
                if (list.size() > 0) {
                    List<String> d_list = list.stream().distinct().collect(Collectors.toList());
                    for (String s : d_list) {
                        hashMap.put(s, Collections.frequency(list, s));
                    }
                    WindowPerMinuteContainer<Integer> window = new WindowPerMinuteContainer<>(time[0], hashMap);
                    databaseWriter.writeWindow(window);
                    time[0] += 60000;
                }
            } catch (IllegalArgumentException ignored) {
            }
        });
    }

    public static void task2(JavaPairInputDStream<String, String> directKafkaStream, final long[] time) {
        JavaPairDStream<String, String> statesToGroups = directKafkaStream.window(Durations.minutes(10)).filter(rdd ->
        {
            JSONObject jsonObject = new JSONObject(rdd._2);
            Meeting m = mapper.readValue(jsonObject.toString(), Meeting.class);
            return (m.group.group_country.equals("us"));
        }).mapToPair((rdd) -> {
            JSONObject jsonObject = new JSONObject(rdd._2);
            Meeting m = mapper.readValue(jsonObject.toString(), Meeting.class);
            return new Tuple2<>(m.group.group_state,m.group.group_name);
        });

        JavaDStream<List<Tuple2<String, String>>> j = statesToGroups.glom();
        j.foreachRDD(listJavaRDD -> {
            try {
                List<Tuple2<String, String>> list = listJavaRDD.first();
                if (list.size() > 0) {
                    list.forEach((h_map) -> {
                        Window2Container window = new Window2Container(time[1], h_map._1,h_map._2);
                        try {
                            databaseWriter.writeWindow2(window);
                        } catch (ClassNotFoundException | SQLException e) {
                            e.printStackTrace();
                        }
                    });

                    time[1] += 600000;
                }
            } catch (IllegalArgumentException ignored) {
            }
        });
}
    public static void task3(JavaPairInputDStream<String, String> directKafkaStream, final long[] time) {
        JavaPairDStream<String, List<String>> countryTopics = directKafkaStream.window(Durations.minutes(10)).mapToPair((rdd) -> {
            JSONObject jsonObject = new JSONObject(rdd._2);
            Meeting m = mapper.readValue(jsonObject.toString(), Meeting.class);
            List<String> topics = new ArrayList<>();
            for (GroupTopic t:
                 m.group.group_topics) {
                topics.add(t.topic_name);
            }
            return new Tuple2<>(m.group.group_country,topics);
        });

        JavaDStream<List<Tuple2<String, List<String>>>> j = countryTopics.glom();
        j.foreachRDD(listJavaRDD -> {
            try {
                List<Tuple2<String, List<String>>> list = listJavaRDD.first();
                if (list.size() > 0) {
                    list.forEach((h_map) -> {
                        for (String t:
                             h_map._2) {
                            Window3Container window = new Window3Container(time[2], h_map._1,t,Collections.frequency(h_map._2,t));
                            try {
                                databaseWriter.writeWindow3(window);
                            } catch (ClassNotFoundException | SQLException | InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    time[2] += 600000;
                }
            } catch (IllegalArgumentException ignored) {
            }
        });
    }
    public static void main(String[] args) throws InterruptedException {
        SparkSession sparkSession = SparkSession.builder().master("local[*]")
                .appName("Homework4").getOrCreate();
        JavaSparkContext sc = new JavaSparkContext(sparkSession.sparkContext());
        JavaStreamingContext ssc = new JavaStreamingContext(sc, Durations.minutes(1));
        final long[] time = new long[]{sparkSession.sparkContext().startTime(), sparkSession.sparkContext().startTime(), sparkSession.sparkContext().startTime()};
        Set<String> topics = Collections.singleton("storage");
        Map<String, String> kafkaParams = new HashMap<>();
        kafkaParams.put("metadata.broker.list", "localhost:9092");
        JavaPairInputDStream<String, String> directKafkaStream = KafkaUtils.createDirectStream(ssc,
                String.class, String.class, StringDecoder.class, StringDecoder.class, kafkaParams, topics);

        task1(directKafkaStream, time);
        task2(directKafkaStream,time);
        task3(directKafkaStream,time);
        ssc.start();
        ssc.awaitTermination();
    }
}