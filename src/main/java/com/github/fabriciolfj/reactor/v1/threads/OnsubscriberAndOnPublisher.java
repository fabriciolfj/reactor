package com.github.fabriciolfj.reactor.v1.threads;

import com.github.fabriciolfj.reactor.v1.util.Util;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class OnsubscriberAndOnPublisher {

    public static void main(String[] args) {
        var flux = Flux.create(fluxSink -> {
            printNameThread("inicio");
            fluxSink.next("1");
            fluxSink.next("2");
            fluxSink.complete();
        }).doOnNext(i -> printNameThread("next create " + i));

        flux.publishOn(Schedulers.parallel())
                .doOnNext(i -> printNameThread("next " + i))
                .subscribeOn(Schedulers.boundedElastic())
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
