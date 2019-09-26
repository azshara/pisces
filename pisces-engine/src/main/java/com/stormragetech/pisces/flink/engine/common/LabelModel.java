package com.stormragetech.pisces.flink.engine.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LabelModel implements Serializable {

    private static final long serialVersionUID = -2386990288281306562L;

    private String labelName;
    private String expression;
    private Integer calculateype;

}
