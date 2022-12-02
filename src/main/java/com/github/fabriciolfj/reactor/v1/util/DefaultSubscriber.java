package com.github.fabriciolfj.reactor.v1.util;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class DefaultSubscriber implements Subscriber<Object> {

    private String name = "";

    public DefaultSubscriber(final String name) {
        this.name = name;
    }

    public DefaultSubscriber() {

    }

    @Override
    public void onSubscribe(Subscription subscription) {
        subscription.request(Long.MAX_VALUE);
    }

    @Override
    public void onNext(Object o) {
        System.out.println(name + " Received: " + o);
    }

    @Override
    public void onError(Throwable throwable) {
        System.out.println("Error, msg: " + throwable.getMessage());
    }

    @Override
    public void onComplete() {
        System.out.println("Complete process.");
    }
}
