package com.github.fabriciolfj.reactor.flux;

import com.github.fabriciolfj.reactor.util.Util;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.Stream;

public class FluxStream {

    public static void main(String[] args) {
        var list = List.of(1,2,3);
        var stream = list.stream();

        // para manter o stream aberto, preciso colocar list.stream como: () -> list.stream()
        Flux<Integer> fluxStream = Flux.fromStream(stream);

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
