package com.github.fabriciolfj.reactor.v1.flux;

import com.github.fabriciolfj.reactor.v1.util.Util;
import reactor.core.publisher.Flux;

public class FluxTake {

    public static void main(String[] args) {
        Flux.range(1,10)
                .take(3) //pega os 3 primeiros eventos e cancela a inscrição
                .log()
                .subscribe(Util.subscriber());
    }
}
