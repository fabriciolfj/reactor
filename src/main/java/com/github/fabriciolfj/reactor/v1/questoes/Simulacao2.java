package com.github.fabriciolfj.reactor.v1.questoes;

import reactor.core.publisher.Flux;

public class Simulacao2 {
    public static void main(String[] args) {
        var flux = Flux.range(1, 10);
        flux.map(i -> i *10);

        flux.subscribe(System.out::println);
    }
}
