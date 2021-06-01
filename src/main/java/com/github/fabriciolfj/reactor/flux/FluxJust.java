package com.github.fabriciolfj.reactor.flux;

import com.github.fabriciolfj.reactor.util.Util;
import reactor.core.publisher.Flux;

public class FluxJust {

    public static void main(String[] args) {
        Flux.just(Util.faker().name().firstName(),
                Util.faker().name().firstName(),
                Util.faker().name().firstName(),
                Util.faker().name().firstName())
        .subscribe(
                Util.onNext(),
                Util.error(),
                Util.onComplete()
        );
    }
}
