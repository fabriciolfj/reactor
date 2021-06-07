package com.github.fabriciolfj.reactor.threads;

import com.github.fabriciolfj.reactor.util.Util;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class OnSubscribe2 {

    public static void main(String[] args) {
        var flux = Flux.create(fluxSink -> {
            printNameThread("inicio");
            fluxSink.next("1");
            fluxSink.complete();
        });

        flux.subscribeOn(Schedulers.boundedElastic())
                .subscribe(v ->
                    printNameThread("sub " + v),
                            e -> System.out.println("error " + e),
                            () -> System.out.println("complete"));

        flux.subscribeOn(Schedulers.boundedElastic())
                .subscribe(v ->
                                printNameThread("sub " + v),
                        e -> System.out.println("error " + e),
                        () -> System.out.println("complete"));


        Util.sleepSeconds(3);
    }

    private static void printNameThread(final String etapa) {
        System.out.println(etapa + " " + Thread.currentThread().getName());
    }
}
