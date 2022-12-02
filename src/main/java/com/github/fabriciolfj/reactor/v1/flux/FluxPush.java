package com.github.fabriciolfj.reactor.v1.flux;

import com.github.fabriciolfj.reactor.v1.flux.helper.NameProducer;
import com.github.fabriciolfj.reactor.v1.util.Util;
import reactor.core.publisher.Flux;

public class FluxPush {

    /*
    * não e thread safe
    * */
    public static void main(String[] args) {
        NameProducer producer = new NameProducer();

        Flux.push(producer)
                .subscribe(Util.subscriber());

        Runnable runnable = producer::produce;

        for (int i = 0; i < 10; i ++) {
            new Thread(runnable).start();
        }

        producer.produce();
    }
}
