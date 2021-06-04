package com.github.fabriciolfj.reactor.operadores;

import com.github.fabriciolfj.reactor.util.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class Timeout {

    public static void main(String[] args) {
        delay().timeout(Duration.ofSeconds(2), fallback())
                .subscribe(Util.subscriber());

        Util.sleepSeconds(60);
    }

    private static Flux<Integer> delay() {
        return Flux.range(1,100)
                .delayElements(Duration.ofSeconds(5));
    }

    private static Flux<Integer> fallback() {
        return Flux.range(100, 10);
    }
}
