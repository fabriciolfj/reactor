package com.github.fabriciolfj.reactor.combinando;

import com.github.fabriciolfj.reactor.util.Util;
import reactor.core.publisher.Flux;

import java.util.function.Function;

public class Combine {

    public static void main(String[] args) {
        var flux1 = Flux.just(1,2,3);

        flux1.concatMap(transform())
                .subscribe(Util.subscriber());
    }

    private static Function<Integer, Flux<String>> transform() {
        return i -> Flux.just(i + "||" + i*2);
    }
}
