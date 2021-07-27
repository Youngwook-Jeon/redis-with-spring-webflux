package com.young.redisson.test;

import org.junit.jupiter.api.Test;
import org.redisson.api.RTopicReactive;
import org.redisson.client.codec.StringCodec;

public class Lec12PubSubTest extends BaseTest {

    @Test
    public void subscriber1() {
        RTopicReactive topic = this.client.getTopic("slack-room", StringCodec.INSTANCE);
        topic.getMessages(String.class)
                .doOnError(System.out::println)
                .doOnNext(System.out::println)
                .subscribe();
        sleep(600_000);
    }

    @Test
    public void subscriber2() {
        RTopicReactive topic = this.client.getTopic("slack-room", StringCodec.INSTANCE);
        topic.getMessages(String.class)
                .doOnError(System.out::println)
                .doOnNext(System.out::println)
                .subscribe();
        sleep(600_000);
    }
}
