package com.github.fabriciolfj.reactor.v1.flux;

import com.github.fabriciolfj.reactor.v1.util.Util;
import reactor.core.publisher.Flux;

import java.util.concurrent.atomic.AtomicInteger;

public class FluxGenerateExample2 {

    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(0);
        Flux.generate(synchronousSink -> {
            var country = Util.faker().country().name();
            System.out.println("emiting :" + country);
            synchronousSink.next(country);
            atomicInteger.incrementAndGet();
            if (country.equalsIgnoreCase("canada") || atomicInteger.get() == 10) {
                synchronousSink.complete();
            }
        }).subscribe(Util.subscriber());
    }
}
