package com.github.fabriciolfj.reactor.backpressure;

import com.github.fabriciolfj.reactor.util.Util;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class Buffer {

    public static void main(String[] args) {
        var flux = Flux.create(fluxSink -> {
            System.out.println("created");
            for(int i = 0; i < 100; i++) {
                fluxSink.next(i);
            }
        });

        flux.onBackpressureBuffer(5, i -> System.out.println("buffer " + i))
                .publishOn(Schedulers.boundedElastic())
                .subscribe(Util.subscriber());

        Util.sleepSeconds(60);
    }
}