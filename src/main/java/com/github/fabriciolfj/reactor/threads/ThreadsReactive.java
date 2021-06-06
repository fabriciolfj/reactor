package com.github.fabriciolfj.reactor.threads;

import com.github.fabriciolfj.reactor.util.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

public class ThreadsReactive {

    public static void main(String[] args) {
        var flux = Flux.create(fluxSink -> {
            printNameThread("inicio");
            fluxSink.next("1");
        }).doOnNext(i -> printNameThread("next " + i));

        Runnable run = () -> flux.subscribe(v -> printNameThread("sub " + v));

        for(int i = 0; i < 5; i++) {
            new Thread(run).start();
        }
    }

    private static void printNameThread(final String etapa) {

        System.out.println(etapa + " " + Thread.currentThread().getName());
    }
}
