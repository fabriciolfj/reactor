package com.github.fabriciolfj.reactor.v1.flux;

import com.github.fabriciolfj.reactor.v1.util.Util;
import reactor.core.publisher.Flux;

public class FluxCreate {

    //vc personaliza a forma de emitir eventos, erros, complete
    public static void main(String[] args) {
        Flux.create(fluxSink -> {
            int qtde = 0;
            String country;
            do {
                country = Util.faker().country().name();
                fluxSink.next(country);
                qtde++;
            } while(!country.equalsIgnoreCase("canada") && qtde < 10);

            fluxSink.complete();
        }).subscribe(Util.subscriber());
    }
}
