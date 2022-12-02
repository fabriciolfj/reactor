package com.github.fabriciolfj.reactor.v1.questoes;

import reactor.core.publisher.Flux;

public class Simulacao3 {

    public static void main(String[] args) {
        Flux<Object> flux = Flux.create(fluxSink -> {
            System.out.println("created");
            for (int i = 0; i < 5; i++) {
                fluxSink.next(i);
            }
            fluxSink.complete();
        })
                .cache(1);
        System.out.println("primeiro");
        flux.subscribe(System.out::println);
        System.out.println("segundo");
        flux.subscribe(System.out::println);
        System.out.println("terceiro");
        flux.subscribe(System.out::println);
    }
}
