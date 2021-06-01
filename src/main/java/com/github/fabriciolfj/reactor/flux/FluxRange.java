package com.github.fabriciolfj.reactor.flux;

import com.github.fabriciolfj.reactor.util.Util;
import reactor.core.publisher.Flux;

public class FluxRange {

    public static void main(String[] args) {
        var integerFlux = Flux.range(1,40)
                .log()
                .map(i -> Util.faker().name().firstName())
                .log();

        integerFlux.subscribe(Util.onNext());
    }
}
