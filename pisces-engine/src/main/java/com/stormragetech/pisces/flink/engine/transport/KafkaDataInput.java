package com.stormragetech.pisces.flink.engine.transport;

import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.Properties;

public class KafkaDataInput<T> implements DataInput<T> {

    @Override
    public void in(StreamExecutionEnvironment env, Properties prop, DataStreamSource<T> ds) {

    }

}
