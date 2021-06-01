package com.github.fabriciolfj.reactor.flux;

import com.github.fabriciolfj.reactor.util.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class FluxInterval {

    //ele nao bloqueia a thread, para ver o resulvado preciso bloquear
    public static void main(String[] args) {
        Flux.interval(Duration.ofSeconds(1))
                .log()
                .subscribe(Util.onNext());

        //Util.sleepSeconds(4);
    }
}
