package com.github.fabriciolfj.reactor.context;

import com.github.fabriciolfj.reactor.util.Util;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

public class Simulacao {

    public static void main(String[] args) {
        getWelcome().contextWrite(Context.of("user", "fabricio"))
                .subscribe(Util.subscriber());
    }

    private static Mono<String> getWelcome() {
        return Mono.deferContextual(ctx -> {
            if(ctx.hasKey("user")) {
                return Mono.just("Welcome " + ctx.get("user"));
            }

            return Mono.error(new RuntimeException("unauthenticated"));
        });
    }
}
