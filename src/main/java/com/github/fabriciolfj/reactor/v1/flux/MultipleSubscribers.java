package com.github.fabriciolfj.reactor.v1.flux;

import reactor.core.publisher.Flux;

public class MultipleSubscribers {

    public static void main(String[] args) {
        var intergerFlux = Flux.just(1,2,3,4);
        var evenFlux = intergerFlux.filter(p -> p % 2 == 0);

        evenFlux.subscribe(i -> System.out.println("Sub 2 " + i));
        intergerFlux.subscribe(i -> System.out.println("Sub 1 " + i));
    }
}
