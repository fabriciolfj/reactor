package com.github.fabriciolfj.reactor.operadores;

import reactor.core.publisher.Flux;

public class OnNextTest {

    public static void main(String[] args) {
        Flux.create(fluxSink -> {
            fluxSink.next(1);
        })
                .doOnNext(i -> System.out.println("Next1 " + i))
                .doOnNext(i -> System.out.println("Next2 " + i))
                .subscribe(i -> System.out.println("Sub " + i));
    }
}
