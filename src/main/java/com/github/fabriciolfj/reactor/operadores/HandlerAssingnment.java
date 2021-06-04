package com.github.fabriciolfj.reactor.operadores;

import com.github.fabriciolfj.reactor.util.Util;
import reactor.core.publisher.Flux;

public class HandlerAssingnment {

    public static void main(String[] args) {
        Flux.generate(synchronousSink -> synchronousSink.next(Util.faker().country().name()))
                .map(Object::toString)
                .handle((name, sync) -> {
                    sync.next(name);
                    if(name.equalsIgnoreCase("canada")) {
                        sync.complete();
                    }
                })
                .subscribe(Util.subscriber());
    }
}
