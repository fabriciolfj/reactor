package com.github.fabriciolfj.reactor.v1.then;

import com.github.fabriciolfj.reactor.v1.util.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class Simulacao1 {

    public static void main(String[] args) {
        Flux.just(1, 10)
                .doOnNext(i -> System.out.println(i))
                .then(Mono.just("teste"))
                .subscribe(Util.subscriber());
    }
}
