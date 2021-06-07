package com.github.fabriciolfj.reactor.threads;

import com.github.fabriciolfj.reactor.util.Util;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class OnSubscribe {

    public static void main(String[] args) {
        var flux = Flux.create(fluxSink -> {
            printNameThread("inicio");
            fluxSink.next("1");
            fluxSink.complete();
        }).doOnNext(i -> printNameThread("next " + i));

        Runnable runnable = () -> flux.doFirst(() -> printNameThread("first2"))
                .subscribeOn(Schedulers.boundedElastic())
                .doFirst(() -> printNameThread("first1"))
                .subscribe(v ->
                    printNameThread("sub " + v),
                            e -> System.out.println("error " + e),
                            () -> System.out.println("complete"));

        for(int i =0; i < 1; i++) {
            new Thread(runnable).start();
        }

        Util.sleepSeconds(3);
    }

    private static void printNameThread(final String etapa) {
        System.out.println(etapa + " " + Thread.currentThread().getName());
    }
}
