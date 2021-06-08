package com.github.fabriciolfj.reactor.repeat;

import com.github.fabriciolfj.reactor.util.Util;
import reactor.core.publisher.Flux;
import reactor.util.retry.Retry;

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
