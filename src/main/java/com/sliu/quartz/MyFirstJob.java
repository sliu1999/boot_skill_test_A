package com.sliu.quartz;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class MyFirstJob {
    public void sayHello(){
        System.out.print("MyFirstJob:sayHello"+new Date());
    }
}
