package com.stormragetech.pisces.flink.engine.app;

import org.apache.flink.api.common.functions.JoinFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.triggers.CountTrigger;

public class Launch {

    public static void main(String[] args) {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStreamSource<String> source1 = env.readTextFile("/Users/marshall/Desktop/a.txt");
        DataStreamSource<String> source2 = env.readTextFile("/Users/marshall/Desktop/b.txt");

        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

        DataStream<String> stream1 = source1
                .map((MapFunction<String, String>) s -> null == s || s.trim().length() <= 0 ? "null" : s.trim())
                .assignTimestampsAndWatermarks(Utils.assignerWithPeriodicWatermarks());
        stream1.print("stream [1] >>>");

        DataStream<String> stream2 = source1
                .map((MapFunction<String, String>) s -> null == s || s.trim().length() <= 0 ? "null" : s.trim())
                .forward()
                .assignTimestampsAndWatermarks(Utils.assignerWithPeriodicWatermarks());
        stream2.print("stream [2] >>>");

        stream1.join(stream2)
                .where(String::trim).equalTo(String::trim)
                .window(TumblingEventTimeWindows.of(Time.seconds(10)))
                .trigger(CountTrigger.of(1))
                .apply((JoinFunction<String, String, String>) (s, s2) -> s + " || " + s2).print("result>>>");


        //        DataStream<String> stream2 = source2.map((MapFunction<String, String>) s -> null == s ? "null" : s.trim());
//        stream2.print("stream [2] >>>");

//        DataStream<String> result = stream1.coGroup(stream2)
//                .where(String::trim).equalTo(t -> t.substring(0, 1))
//                .window(TumblingProcessingTimeWindows.of(Time.seconds(1)))
//                .apply(new CoGroupFunction<String, String, String>() {
//                    @Override
//                    public void coGroup(Iterable<String> iterable, Iterable<String> iterable1, Collector<String> collector) throws Exception {
//                        StringBuffer sb = new StringBuffer();
//                        iterable.forEach(sb::append);
//                        iterable1.forEach(sb::append);
//                        System.out.println(sb.toString());
//                        collector.collect(sb.toString());
//                    }
//                });


//        SingleOutputStreamOperator result = stream1.connect(stream2)
//                .flatMap(new CoFlatMapFunction<String, String, String>() {
//                    private static final long serialVersionUID = 6615509072158676666L;
//
//                    @Override
//                    public void flatMap1(String s, Collector<String> collector) throws Exception {
//                        collector.collect(s);
//                    }
//
//                    @Override
//                    public void flatMap2(String s, Collector<String> collector) throws Exception {
//                        collector.collect(s);
//                    }
//                });

//        result.print("result >>>");

        try {
            env.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
