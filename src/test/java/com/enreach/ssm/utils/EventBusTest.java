package com.enreach.ssm.utils;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import org.junit.Before;
import org.junit.Test;

public class EventBusTest {

    EventBus eventBus = new EventBus();

    @Before
    public void before() {
        eventBus.register(new EventListener());
    }

    @Test
    public void event() {
        eventBus.post(new MyEvent("abc"));
        eventBus.post(new MyEvent("efg"));
    }
    /**
     * 事件,消息类
     */
    public class MyEvent {

        private String message;

        public String getMessage() {
            return message;
        }

        public MyEvent(String message) {
            this.message = message;
            System.out.println("event msg:" + message);
        }
    }

    /**
     * 消息接受类：
     */
    public class EventListener {

        @Subscribe
        public void listen(MyEvent event) {
            System.out.println("listen msg：" + event.getMessage());
        }
    }
}
