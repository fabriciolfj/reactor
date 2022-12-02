package com.github.fabriciolfj.reactor.v1.mono;

import com.github.fabriciolfj.reactor.v1.util.Util;
import reactor.core.publisher.Mono;

public class MonoEmptyOrError {

    public static void main(String[] args) {
        getUser(3)
                .subscribe(
                        Util.onNext(),
                        Util.error(),
                        Util.onComplete()
                );
    }

    private static Mono<String> getUser(final int id) {

        if (id == 1) {
            return Mono.just(Util.faker().name().firstName());
        }

        if (id ==2) {
           return Mono.empty();
        }

        return Mono.error(new RuntimeException("User not found"));
    }
}
