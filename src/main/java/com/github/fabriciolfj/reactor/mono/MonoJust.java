package com.github.fabriciolfj.reactor.mono;

import reactor.core.publisher.Mono;

public class MonoJust {

    public static void main(String[] args) {
        var monojust = Mono.just(1);

        System.out.println(monojust);

        monojust.subscribe(i -> System.out.println("Received " + i));
    }
}
