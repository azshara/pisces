package com.stormragetech.pisces.flink.engine.app;

import com.alibaba.fastjson.JSON;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class DxApp {

    private final static String KAFKA_ADDR_KEY = "bootstrap.servers";
    private final static String KAFKA_ADDR_VAL = "10.47.85.158:9092";

    private final static String KAFKA_GROUP_KEY = "group.id";
    private final static String KAFKA_GROUP_VAL = "flink-group";

    private final static String KAFKA_TOPIC_VAL = "";

    public static void main(String[] args) {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(4);
        env.enableCheckpointing(5000);
        env.getCheckpointConfig().setCheckpointingMode(CheckpointingMode.EXACTLY_ONCE);
        env.getCheckpointConfig().setCheckpointTimeout(60000);
        env.getCheckpointConfig().setMinPauseBetweenCheckpoints(500);
        env.getCheckpointConfig().setMaxConcurrentCheckpoints(1);
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

        Properties prop = new Properties();
        prop.setProperty(KAFKA_ADDR_KEY, KAFKA_ADDR_VAL);
        prop.setProperty(KAFKA_GROUP_KEY, KAFKA_GROUP_VAL);

        FlinkKafkaConsumer<String> consumer = new FlinkKafkaConsumer<>(
                KAFKA_TOPIC_VAL,
                new SimpleStringSchema(),
                prop
        );

        DataStreamSource<String> dataStreamSource = env.addSource(consumer);

        DataStream<Map<String, Object>> dataStream = dataStreamSource.flatMap((FlatMapFunction<String, Map<String, Object>>) (s, collector) -> {
            collector.collect(JSON.parseObject(s));
        })
                .map((MapFunction<Map<String, Object>, Map<String, Object>>) s -> {
                    Map<String, Object> map = new HashMap<>(12);
                    
                    return map;
                });


    }

}
