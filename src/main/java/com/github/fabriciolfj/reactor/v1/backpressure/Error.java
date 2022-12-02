package com.github.fabriciolfj.reactor.v1.backpressure;

import com.github.fabriciolfj.reactor.v1.util.Util;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class Error {

    public static void main(String[] args) {
        System.setProperty("reactor.bufferSize.small", "16");
        var flux = Flux.create(fluxSink -> {
            System.out.println("created");
            for(int i = 0; i < 100; i++) {
                fluxSink.next(i);
            }
        });

        flux.onBackpressureError()
                .publishOn(Schedulers.boundedElastic())
                .subscribe(i -> System.out.println(i), e -> System.out.println(e.getMessage()));

        Util.sleepSeconds(60); // o inscrito recebe 0 - 15 e o útimo 99
    }
}