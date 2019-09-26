package com.stormragetech.pisces.flink.engine.app;

import org.apache.flink.api.common.state.BroadcastState;
import org.apache.flink.api.common.state.MapStateDescriptor;
import org.apache.flink.api.common.typeinfo.BasicTypeInfo;
import org.apache.flink.runtime.state.HeapBroadcastState;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.BroadcastStream;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.co.BroadcastProcessFunction;
import org.apache.flink.util.Collector;

import java.util.Iterator;
import java.util.Map;

public class DemoLaunch {

    public static void main(String[] args) {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(4);
        env.enableCheckpointing(5000);
        env.getCheckpointConfig().setCheckpointingMode(CheckpointingMode.EXACTLY_ONCE);
        env.getCheckpointConfig().setCheckpointTimeout(60000);
        env.getCheckpointConfig().setMinPauseBetweenCheckpoints(1);
        env.getCheckpointConfig().setMaxConcurrentCheckpoints(1);
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

//        final MapStateDescriptor<String, String> CONFIG_DESCRIPTOR = new MapStateDescriptor<>(
//                "label",
//                BasicTypeInfo.STRING_TYPE_INFO,
//                BasicTypeInfo.STRING_TYPE_INFO
//        );
//
////        DataStreamSource<String> source1 = env.readTextFile("/Users/marshall/Desktop/a.txt");
//        BroadcastStream<String> broadcastStream = env
//                .readTextFile("/Users/marshall/Desktop/b.txt")
//                .setParallelism(1)
//                .broadcast(CONFIG_DESCRIPTOR);
//
////        try {
////            Thread.sleep(1000L);
////        } catch (InterruptedException e) {
////            e.printStackTrace();
////        }
//
//        DataStreamSource<Integer> source2 = env.addSource(new DynamicDataSource());
////        DataStreamSource<String> source2 = env.readTextFile("/Users/marshall/Desktop/b.txt");
////        source2.print("source2 >>> ");
//
//        DataStream<String> result = source2.connect(broadcastStream)
//                .process(new BroadcastProcessFunction<Integer, String, String>() {
//
////                    @Override
////                    public void open(Configuration parameters) throws Exception {
////                        super.open(parameters);
////                        label = "java";
////                        System.out.println("初始化模拟连接数据库读取拦截关键字：java");
////                    }
//
//                    @Override
//                    public void processElement(Integer s, ReadOnlyContext readOnlyContext, Collector<String> collector) throws Exception {
//
////                        System.out.println("process : " + s + " == " + label);
//
////                        if (null != s && null != label && s.startsWith(label)) {
////                            collector.collect(s + " || " + label);
////                        }
//
////                        System.out.println("no broadcast 1 >>> [ " + s + " ]");
//                        HeapBroadcastState<String, String> config = (HeapBroadcastState<String, String>) readOnlyContext.getBroadcastState(CONFIG_DESCRIPTOR);
////                        System.out.println(config);
//                        Iterator<Map.Entry<String, String>> iterator = config.iterator();
//                        while (iterator.hasNext()) {
//                            String result = "null";
//                            Map.Entry<String, String> entry = iterator.next();
//                            System.out.println(s + " ^^ " + entry.getValue() + " ^^ " + entry.getKey().startsWith(String.valueOf(s)));
//                            if (null != entry.getValue() && entry.getKey().startsWith(String.valueOf(s))) {
//                                result = s + " || " + entry.getValue();
//                            }
//                            if (!result.equals("null")) {
//                                collector.collect(result);
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void processBroadcastElement(String s, Context context, Collector<String> collector) throws Exception {
//                        BroadcastState<String, String> state = context.getBroadcastState(CONFIG_DESCRIPTOR);
//                        if (null == state.get(s)) {
//                            state.put(s, s);
//                        }
////                        System.out.println(state);
//                    }
//                });
//
//
////        DataStream<String> stream1 = source1
////                .map((MapFunction<String, String>) s -> null == s || s.trim().length() <= 0 ? "null" : s.trim())
////                .assignTimestampsAndWatermarks(Utils.assignerWithPeriodicWatermarks());
////        stream1.print("stream [1] >>>");
////
////        DataStream<String> stream2 = source1
////                .map((MapFunction<String, String>) s -> null == s || s.trim().length() <= 0 ? "null" : s.trim())
////                .forward()
////                .assignTimestampsAndWatermarks(Utils.assignerWithPeriodicWatermarks());
////        stream2.print("stream [2] >>>");
////
////        stream1.join(stream2)
////                .where(String::trim).equalTo(String::trim)
////                .window(TumblingEventTimeWindows.of(Time.seconds(10)))
////                .trigger(CountTrigger.of(1))
////                .apply((JoinFunction<String, String, String>) (s, s2) -> s + " || " + s2).print("result >>>");
//
//
//        //        DataStream<String> stream2 = source2.map((MapFunction<String, String>) s -> null == s ? "null" : s.trim());
////        stream2.print("stream [2] >>>");
//
////        DataStream<String> result = stream1.coGroup(stream2)
////                .where(String::trim).equalTo(t -> t.substring(0, 1))
////                .window(TumblingProcessingTimeWindows.of(Time.seconds(1)))
////                .apply(new CoGroupFunction<String, String, String>() {
////                    @Override
////                    public void coGroup(Iterable<String> iterable, Iterable<String> iterable1, Collector<String> collector) throws Exception {
////                        StringBuffer sb = new StringBuffer();
////                        iterable.forEach(sb::append);
////                        iterable1.forEach(sb::append);
////                        System.out.println(sb.toString());
////                        collector.collect(sb.toString());
////                    }
////                });
//
//
////        SingleOutputStreamOperator result = stream1.connect(stream2)
////                .flatMap(new CoFlatMapFunction<String, String, String>() {
////                    private static final long serialVersionUID = 6615509072158676666L;
////
////                    @Override
////                    public void flatMap1(String s, Collector<String> collector) throws Exception {
////                        collector.collect(s);
////                    }
////
////                    @Override
////                    public void flatMap2(String s, Collector<String> collector) throws Exception {
////                        collector.collect(s);
////                    }
////                });
//
//        result.print("result >>>");
//
//        try {
//            env.execute();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }

}
