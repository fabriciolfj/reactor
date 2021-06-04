package com.github.fabriciolfj.reactor.operadores;

import com.github.fabriciolfj.reactor.util.Util;
import reactor.core.publisher.Flux;

public class Handler {

    public static void main(String[] args) {
        Flux.range(1,10)
                .handle((integer, sync) -> {
                    if(integer == 7) {
                        sync.complete();
                    } else {
                        sync.next(integer);
                    }
        }).subscribe(Util.subscriber());
    }
}
