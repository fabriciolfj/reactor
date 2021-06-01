package com.github.fabriciolfj.reactor.util;

import com.github.javafaker.Faker;
import org.reactivestreams.Subscriber;

import java.util.function.Consumer;

public class Util {

    private static final Faker FAKER = Faker.instance();

    public static Consumer<Object> onNext() {
        return e -> System.out.println("Received: " + e);
    }

    public static Consumer<Throwable> error() {
        return e -> System.out.println("Error: " + e.getMessage());
    }

    public static Runnable onComplete() {
        return () -> System.out.println("Complete");
    }

    public static void sleepSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ;
    }

    public static Faker faker() {
        return FAKER;
    }

    public static Subscriber<Object> subscriber() {
        return new DefaultSubscriber();
    }

    public static Subscriber<Object> subscriber(final String name) {
        return new DefaultSubscriber(name);
    }
}
