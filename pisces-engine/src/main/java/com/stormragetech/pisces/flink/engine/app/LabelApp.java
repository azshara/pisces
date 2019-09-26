package com.stormragetech.pisces.flink.engine.app;

import com.ql.util.express.DefaultContext;
import com.ql.util.express.ExpressRunner;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LabelApp {

    public static void main(String[] args) {

//        List<Integer> label = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);
//
//        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
//        env.setParallelism(4);
//        env.enableCheckpointing(5000);
//        env.getCheckpointConfig().setCheckpointingMode(CheckpointingMode.EXACTLY_ONCE);
//        env.getCheckpointConfig().setCheckpointTimeout(60000);
//        env.getCheckpointConfig().setMinPauseBetweenCheckpoints(500);
//        env.getCheckpointConfig().setMaxConcurrentCheckpoints(1);
//        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
//
//        DataStreamSource<Integer> source = env.addSource(new DynamicDataSource());
//
//        final int[] i = {1};
//        label.forEach(s -> {
//            source.map((MapFunction<Integer, String>) integer -> {
//                final String[] result = {"null"};
//                if (s.equals(integer)) {
//                    result[0] = "a" + integer + " || " + s;
//                }
//                return result[0];
//            }).filter((FilterFunction<String>) s1 -> !s1.equals("null")).print();
//            System.out.println("[ foreach ] " + i[0]);
//            i[0]++;
//        });

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

//        try {
//            env.execute();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        Map<String, Object> map = new HashMap<>();
        map.put("fst", 1);
        map.put("scd", 2);
        map.put("thd", 5);

        String express = "fst+scd*thd>20";


        try {
            String r = Utils.culcultate(map, express);
            System.out.println("计算结果1为: " + r);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<String, Object> map1 = new HashMap<>();
        map1.put("fst", 1);
        map1.put("scd", 2);
        map1.put("thd", 5);
        map1.put("a", "大于20");
        map1.put("b", "小于20");

        String express1 = "fst+scd*thd>20?a:b";

        try {
            String r = Utils.culcultate(map1, express1);
            System.out.println("计算结果2为: " + r);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
