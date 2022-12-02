package com.github.fabriciolfj.reactor.v1.flux;

import com.github.fabriciolfj.reactor.v1.util.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class FluxFromMono {

    public static void main(String[] args) {
        Mono mono = Mono.just("teste");

        Flux<String> flux = Flux.from(mono);

        flux.subscribe(Util.onNext());

        //flux to mono, vai emitir apenas um evento no caso 1
        Flux.range(1, 3)
                .next()
                .subscribe(Util.onNext());

        //error interrompe o fluxo
        Flux.range(3, 5)
                .map(i -> i / (i - 4))
        .subscribe(System.out::println);
    }
}
