package com.teacher.judge.demo.bean;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "configs")
@Data
public class Configs {
    private Integer timeOut;
    private Double proportion;
    private Integer msgPageSize;
}
