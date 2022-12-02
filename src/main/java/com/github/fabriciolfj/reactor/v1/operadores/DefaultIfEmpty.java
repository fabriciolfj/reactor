package com.github.fabriciolfj.reactor.v1.operadores;

import com.github.fabriciolfj.reactor.v1.util.Util;
import reactor.core.publisher.Flux;

public class DefaultIfEmpty {


    public static void main(String[] args) {
        Flux.range(1,100)
                .filter(i -> i > 101)
                .defaultIfEmpty(-100)
                .subscribe(Util.subscriber());
    }
}
