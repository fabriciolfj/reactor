package com.github.fabriciolfj.reactor.v1.repeat;

import com.github.fabriciolfj.reactor.v1.util.Util;
import reactor.core.publisher.Flux;

public class Simulacao {

    public static void main(String[] args) {
        random()
                .repeat(2).subscribe(Util.subscriber());
    }

    private static Flux<Integer> random() {
        return Flux.range(5, 5)
                .doOnComplete(() -> System.out.println("Completed"));
    }

}
