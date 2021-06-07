package com.github.fabriciolfj.reactor.backpressure;

import com.github.fabriciolfj.reactor.util.Util;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class Drop {

    public static void main(String[] args) {
        System.setProperty("reactor.bufferSize.small", "16");
        var flux = Flux.create(fluxSink -> {
            System.out.println("created");
            for(int i = 0; i < 100; i++) {
                fluxSink.next(i);
            }
        });

        flux.onBackpressureDrop(i -> System.out.println("Drop: "  + i))
                .publishOn(Schedulers.boundedElastic())
                .subscribe(Util.subscriber());

        Util.sleepSeconds(60);
    }
}