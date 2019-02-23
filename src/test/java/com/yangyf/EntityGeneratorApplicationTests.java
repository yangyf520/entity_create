package com.yangyf;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
//@SpringBootTest
public class EntityGeneratorApplicationTests {

    @Test
    public void contextLoads() {
        String path = "C:\\Users\\CCC\\.m2\\repository\\mysql\\mysql-connector-java\\8.0.15\\mybatis-generator-core-1.3.2.jar";
        Runtime runtime = Runtime.getRuntime();
        try {
            runtime.exec("java -jar " + path + " -configfile");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
