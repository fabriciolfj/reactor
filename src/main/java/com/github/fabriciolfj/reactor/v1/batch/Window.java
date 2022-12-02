package com.github.fabriciolfj.reactor.v1.batch;

import com.github.fabriciolfj.reactor.v1.util.Util;
import reactor.core.publisher.Flux;

public class Window {

    public static void main(String[] args) {
        Flux.range(1, 10)
                .window(2)
                .flatMap(e -> save(e))
                .subscribe(Util.subscriber());
    }

    private static Flux<Integer> save(Flux<Integer> flux) {
        return flux.doOnNext(e-> System.out.println("saving " + e))
                .doOnComplete(() -> {
                    System.out.println("saved this batch");
                });
    }
}
