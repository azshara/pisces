package com.stormragetech.pisces.flink.engine.app;

import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.Arrays;
import java.util.List;

public class LabelApp {

    public static void main(String[] args) {

        List<Integer> label = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(4);
        env.enableCheckpointing(5000);
        env.getCheckpointConfig().setCheckpointingMode(CheckpointingMode.EXACTLY_ONCE);
        env.getCheckpointConfig().setCheckpointTimeout(60000);
        env.getCheckpointConfig().setMinPauseBetweenCheckpoints(500);
        env.getCheckpointConfig().setMaxConcurrentCheckpoints(1);
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

        DataStreamSource<Integer> source = env.addSource(new DynamicDataSource());

        label.forEach(s -> source.map((MapFunction<Integer, String>) integer -> {
            final String[] result = {"null"};
            if (s.equals(integer)) {
                result[0] = "a" + integer + " || " + s;
            }
            return result[0];
        }).filter((FilterFunction<String>) s1 -> {
            if (s1.equals("null")) {
                return false;
            } else {
                return true;
            }
        }).print());

//        source.map((MapFunction<Integer, String>) integer -> {
//            final String[] result = {"null"};
//            System.out.println("start >>> " + label + " ^^ " + result[0]);
//            label.parallelStream().map(s -> {
//                System.out.println(s + " ^^ " + integer);
//                if (s.equals(integer)) {
//                    result[0] = "a" + integer + " || " + s;
//                }
//                return result;
//            });
//            return result[0];
//        }).print();

        try {
            env.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
