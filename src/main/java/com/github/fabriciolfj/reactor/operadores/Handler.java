package com.github.fabriciolfj.reactor.operadores;

import com.github.fabriciolfj.reactor.util.Util;
import reactor.core.publisher.Flux;

public class Handler {

    public static void main(String[] args) {
        /*Flux.range(1,10)
                .handle((integer, sync) -> {
                    if(integer == 7) {
                        sync.complete();
                    } else {
                        sync.next(integer);
                    }
        }).subscribe(Util.subscriber());*/

        Flux.range(1, 10)
                .map(i -> i *10)
                .subscribe(Util.subscriber());

        Flux.range(1, 10)
                //.filter(i -> i > 5)
                //.take(2)
                .next() //retorna o evento inicial
                .subscribe(System.out::println);
    }
}
