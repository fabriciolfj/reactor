package com.github.fabriciolfj.reactor.v1.operadores;

import com.github.fabriciolfj.reactor.v1.util.Util;
import reactor.core.publisher.Flux;

public class SwitchifEmpty {

    public static void main(String[] args) {
        getOrderNumbers()
                .filter(i -> i > 12)
                .switchIfEmpty(fallback())
                .subscribe(Util.subscriber());
    }

    private static Flux<Integer> getOrderNumbers() {
        return Flux.range(1, 11);
    }

    private static Flux<Integer> fallback() {
        return Flux.range(20, 5);
    }
}
