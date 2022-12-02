package com.github.fabriciolfj.reactor.v1.flux;

import com.github.fabriciolfj.reactor.v1.flux.helper.NameProducer;
import com.github.fabriciolfj.reactor.v1.util.Util;
import reactor.core.publisher.Flux;

public class FluxCreateConsumer {

    public static void main(String[] args) {
        var nameProducer = new NameProducer();
        var flux = Flux.create(nameProducer);
        flux.subscribe(Util.onNext());

        //nameProducer.produce(); //somente vai disparar o envento nesse ponto
        Runnable run = nameProducer::produce;

        for (int i = 0; i < 10; i++) {
            new Thread(run).start();
        }

        Util.sleepSeconds(5);
    }
}
