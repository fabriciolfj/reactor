package com.github.fabriciolfj.reactor.v1.flux;

import com.github.fabriciolfj.reactor.v1.util.Util;
import reactor.core.publisher.Flux;

public class FluxCreateStreamTake {

    public static void main(String[] args) {
        Flux.create(fluxSink -> {
            String country;
            do {
                country = Util.faker().country().name();
                System.out.println("Emitting: " + country);
                fluxSink.next(country);
            } while (!country.equalsIgnoreCase("canada") && !fluxSink.isCancelled());

            fluxSink.complete();
        }).take(3).subscribe(Util.subscriber());
    }
}
