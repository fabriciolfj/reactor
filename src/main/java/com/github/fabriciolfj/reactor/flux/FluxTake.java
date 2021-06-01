package com.github.fabriciolfj.reactor.flux;

import com.github.fabriciolfj.reactor.util.Util;
import reactor.core.publisher.Flux;

public class FluxTake {

    public static void main(String[] args) {
        Flux.range(1,10)
                .take(3) //pega os 3 primeiros eventos e cancela a inscrição
                .log()
                .subscribe(Util.subscriber());
    }
}
