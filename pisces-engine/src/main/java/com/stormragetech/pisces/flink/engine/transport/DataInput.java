package com.stormragetech.pisces.flink.engine.transport;

import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.Map;
import java.util.Properties;

public interface DataInput<T> {

    void in(StreamExecutionEnvironment env, Properties prop, DataStreamSource<T> ds);

}
