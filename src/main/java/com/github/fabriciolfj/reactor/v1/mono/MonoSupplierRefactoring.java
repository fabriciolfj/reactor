package com.github.fabriciolfj.reactor.v1.mono;

import com.github.fabriciolfj.reactor.v1.util.Util;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class MonoSupplierRefactoring {

    public static void main(String[] args) {
        getName();
        getName().subscribeOn(Schedulers.boundedElastic())
                .subscribe(Util.onNext());
                //.block();
        //System.out.println(name);
        getName();

        Util.sleepSeconds(4);
    }

    public static Mono<String> getName() {
        System.out.println("Entered getName method");
        return Mono.fromSupplier(() -> {
            System.out.println("Generating name..");
            Util.sleepSeconds(3);
            return Util.faker().name().firstName();
        }).map(String::toUpperCase);
    }
}
