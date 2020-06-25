package com.tensquare.test;

import com.tensquare.rabbit.RebbitApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RebbitApplication.class)
public class ProduceTest {


    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void sendMsg1(){
        rabbitTemplate.convertAndSend("mark","直接模式测试");
    }
    @Test
    public void sendMsg2(){
        rabbitTemplate.convertAndSend("mq-test","","分列模式测试");
    }

    /**
     * 主题模式
     */
    @Test
    public void sendMsg3(){

        rabbitTemplate.convertAndSend("topic84","good.abc","主题模式测试");
    }
}
