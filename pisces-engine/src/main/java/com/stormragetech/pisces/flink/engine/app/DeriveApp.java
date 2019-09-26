package com.stormragetech.pisces.flink.engine.app;

import com.alibaba.fastjson.JSON;
import com.stormragetech.pisces.flink.engine.calculate.Calculate;
import com.stormragetech.pisces.flink.engine.calculate.DeriveCalculate;
import com.stormragetech.pisces.flink.engine.common.LabelModel;
import com.stormragetech.pisces.flink.engine.common.ReturnInfo;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeriveApp {

    public static void main(String[] args) {

        List<LabelModel> list = new ArrayList<>();
        list.add(new LabelModel("cal1", "rptTime>20181231&&rptTime<20190202&&(eventTypeId==1||eventTypeId==2||eventTypeId==3)", 2));
        list.add(new LabelModel("sta1", "rptTime>20181231&&eventTypeId==1", 3));

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(4);
        env.enableCheckpointing(5000);
        env.getCheckpointConfig().setCheckpointingMode(CheckpointingMode.EXACTLY_ONCE);
        env.getCheckpointConfig().setCheckpointTimeout(60000);
        env.getCheckpointConfig().setMinPauseBetweenCheckpoints(500);
        env.getCheckpointConfig().setMaxConcurrentCheckpoints(1);
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

        Calculate calculate = new DeriveCalculate();

        DataStreamSource<String> source = env.addSource(new DynamicDataSource());

        source.map((MapFunction<String, String>) s -> {

            Map<String, Object> base = new HashMap<>(1);
            Map<String, Object> derive = new HashMap<>(1);
            Map<String, Object> statistic = new HashMap<>(1);

            for (LabelModel label : list) {
                ReturnInfo result = new ReturnInfo();
                Map<String, Object> map = JSON.parseObject(s);
                calculate.calculate(map, result, label.getExpression());
                if (result.getCode().equals("0000")) {
                    switch (label.getCalculateype()) {
                        case 1:
                            if (null != result.getData() && !result.getData().equals("false")) {
                                base.put(label.getLabelName(), result.getData());
                            }
                            break;
                        case 2:
                            if (null != result.getData() && !result.getData().equals("false")) {
                                derive.put(label.getLabelName(), result.getData());
                            }
                            break;
                        case 3:
                            if (null != result.getData() && !result.getData().equals("false")) {
                                statistic.put(label.getLabelName(), result.getData());
                            }
                            break;
                        default:
                            break;
                    }
                }
            }
            System.out.println("-----------------------------");
            System.out.println("基础标签: " + base);
            System.out.println("衍生标签: " + derive);
            System.out.println("统计标签: " + statistic);
            System.out.println("-----------------------------");
            return JSON.toJSONString("成功");
        }).print();

//        final int[] i = {1};
//        label.forEach(s -> {
//            source.map((MapFunction<String, String>) integer -> {
//                final String[] result = {"null"};
//                if (s.equals(integer)) {
//                    result[0] = "a" + integer + " || " + s;
//                }
//                return result[0];
//            }).filter((FilterFunction<String>) s1 -> !s1.equals("null")).print();
//            System.out.println("[ foreach ] " + i[0]);
//            i[0]++;
//        });
//
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
