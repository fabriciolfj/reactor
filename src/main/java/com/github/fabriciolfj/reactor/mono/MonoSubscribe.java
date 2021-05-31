package com.github.fabriciolfj.reactor.mono;

import com.github.fabriciolfj.reactor.util.Util;
import reactor.core.publisher.Mono;

public class MonoSubscribe {

    public static void main(String[] args) {
        var mono = Mono.just("Ball");

        /*mono.subscribe(
                i -> System.out.println(i),
                err -> System.out.println(err),
                () -> System.out.println("Complete")
        );*/

        mono.subscribe(
                Util.onNext(),
                Util.error(),
                Util.onComplete()
        );
    }
}
