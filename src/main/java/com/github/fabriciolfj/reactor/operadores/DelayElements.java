package com.github.fabriciolfj.reactor.operadores;

import com.github.fabriciolfj.reactor.util.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class DelayElements {

    public static void main(String[] args) {
        Flux.range(1,100)
                .log()
                .delayElements(Duration.ofSeconds(1))
                .subscribe(Util.subscriber());

        Util.sleepSeconds(60);
    }
}
