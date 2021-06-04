package com.github.fabriciolfj.reactor.flux;

import com.github.fabriciolfj.reactor.util.Util;
import reactor.core.publisher.Flux;

public class FluxGenerate {

    /*
    * usado para gerar eventos e dá o controle de quanto/parada na sua construção
    * */
    public static void main(String[] args) {
        Flux.generate(synchronousSink -> {
            System.out.println("Emitting");
            synchronousSink.next(Util.faker().country().name()); //emite no maximo 1 item
            //synchronousSink.complete();
            //synchronousSink.error(new RuntimeException("Teste"));
            //synchronousSink.next(Util.faker().country().name()); error
        }).take(2)
                .subscribe(Util.subscriber());
    }
}
