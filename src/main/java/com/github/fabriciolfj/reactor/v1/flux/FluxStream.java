package com.github.fabriciolfj.reactor.v1.flux;

import com.github.fabriciolfj.reactor.v1.util.Util;
import reactor.core.publisher.Flux;

import java.util.List;

public class FluxStream {

    public static void main(String[] args) {
        var list = List.of(1,2,3);
        var stream = list.stream();

        // para manter o stream aberto, preciso colocar list.stream como: () -> list.stream()
        Flux<Integer> fluxStream = Flux.fromStream(stream);
        Flux.fromIterable(List.of(1,1,2));

        fluxStream.subscribe(
                        Util.onNext(),
                        Util.error(),
                        Util.onComplete()
                );

        //ja encontra-se fechado o stream
        fluxStream.subscribe(
                Util.onNext(),
                Util.error(),
                Util.onComplete()
        );
    }
}
