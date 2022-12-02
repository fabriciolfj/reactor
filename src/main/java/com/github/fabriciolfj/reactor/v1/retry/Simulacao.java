package com.github.fabriciolfj.reactor.v1.retry;

import com.github.fabriciolfj.reactor.v1.util.Util;
import reactor.core.publisher.Flux;
import reactor.util.retry.Retry;

public class Simulacao {

    public static void main(String[] args) {
        random().retryWhen(
                //Retry.fixedDelay(2, Duration.ofSeconds(2))
                Retry.max(2)
        )
                .subscribe(Util.subscriber());
    }

    private static Flux<Integer> random() {
        return Flux.range(5, 10)
                .map(i -> {
                    var valor = Util.faker().random().nextInt(10);
                    System.out.println("Random: " + valor);
                    return i / valor;
                })
                .doOnSubscribe(s -> System.out.println("subscribe"))
                .doOnComplete(() -> System.out.println("--completed"))
                .doOnNext(value -> System.out.println("Next: " + value))
                .doOnError(value -> System.out.println("Error: " + value));
    }

}
