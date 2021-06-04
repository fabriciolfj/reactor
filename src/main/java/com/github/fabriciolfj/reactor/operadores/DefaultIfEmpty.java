package com.github.fabriciolfj.reactor.operadores;

import com.github.fabriciolfj.reactor.util.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class DefaultIfEmpty {


    public static void main(String[] args) {
        Flux.range(1,100)
                .filter(i -> i > 101)
                .defaultIfEmpty(-100)
                .subscribe(Util.subscriber());
    }
}
