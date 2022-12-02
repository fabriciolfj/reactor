package com.github.fabriciolfj.reactor.v1.operadores;

import com.github.fabriciolfj.reactor.v1.util.Util;
import reactor.core.publisher.Flux;

public class LimitRater {

    public static void main(String[] args) {
        Flux.range(1,1000)
                .log()
                .limitRate(100, 50)
                .subscribe(Util.subscriber());
    }
}
