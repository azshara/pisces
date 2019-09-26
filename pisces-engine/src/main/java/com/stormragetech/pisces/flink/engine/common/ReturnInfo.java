package com.stormragetech.pisces.flink.engine.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReturnInfo implements Serializable {

    private static final long serialVersionUID = 3205862231802218243L;

    private String code;
    private String message;
    private String data;

}
