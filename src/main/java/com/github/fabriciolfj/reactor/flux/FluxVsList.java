package com.github.fabriciolfj.reactor.flux;

import com.github.fabriciolfj.reactor.flux.helper.NameGenerator;
import com.github.fabriciolfj.reactor.util.Util;

public class FluxVsList {

    public static void main(String[] args) {
        var time = System.currentTimeMillis();
        //vai enviando quando ja estiver pronto
        NameGenerator.getNamesFlux(5)
                .doOnNext(e -> System.out.println((System.currentTimeMillis() - time) /1000))
                .subscribe(Util.onNext());

        //acumula tudo na lista primeiro, para enviar
        /*var time = System.currentTimeMillis();
        System.out.println(NameGenerator.getNames(5));
        System.out.println((System.currentTimeMillis() - time) /1000);*/
    }
}
