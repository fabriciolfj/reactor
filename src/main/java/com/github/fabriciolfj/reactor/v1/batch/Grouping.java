package com.github.fabriciolfj.reactor.v1.batch;

import reactor.core.publisher.Flux;
import reactor.core.publisher.GroupedFlux;

public class Grouping {

    public static void main(String[] args) {
        Flux.range(1, 10)
                .groupBy(p -> p % 2)
                .subscribe(value -> print(value, value.key()));
    }

    private static void print(GroupedFlux<Integer, Integer> value, Integer key) {
        value.subscribe(p -> System.out.println(p + " key: " + key));
    }
}
