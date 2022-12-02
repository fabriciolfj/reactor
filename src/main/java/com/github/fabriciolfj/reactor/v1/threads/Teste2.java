package com.github.fabriciolfj.reactor.v1.threads;

import com.github.fabriciolfj.reactor.v1.util.Util;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class Teste2 {

    public static void main(String[] args) {
        Flux<Object> flux = Flux.create(fluxSink -> {
            printThreadName("create");
            for (int i = 0; i < 5; i++) {
                fluxSink.next(i);
            }
            fluxSink.complete();
        })
                .subscribeOn(Schedulers.boundedElastic());

        flux.subscribeOn(Schedulers.parallel())
                .map(i -> i + "a")
                .subscribe(i -> printThreadName("Sub ->" + i));

        Util.sleepSeconds(10);
    }


        private static void printThreadName(final String name) {
            System.out.println("Thread: " + Thread.currentThread().getName() + " value: " + name);
        }
}
