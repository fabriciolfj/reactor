package com.github.fabriciolfj.reactor.flux;

import com.github.fabriciolfj.reactor.util.Util;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;

import java.util.concurrent.atomic.AtomicReference;

public class FluxSubscribeCustom {

    public static void main(String[] args) {
        // preciso criar um mecanismo para emitir os valores, no caso de subscribe custom, por isso o atomic
        AtomicReference<Subscription> atomicReference = new AtomicReference();
        /*Flux.range(1, 20)
                .log()
                .subscribeWith(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription subscription) {
                        System.out.println("inscricao");
                        atomicReference.set(subscription);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        System.out.println("onNext: " + integer);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        System.out.println("Error, msg: " + throwable.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("Process complete");
                    }
                });

        atomicReference.get().request(3);
        atomicReference.get().request(3);
        atomicReference.get().cancel();
        atomicReference.get().request(4);*/

        Flux.range(1,5)
                .subscribeWith(Util.subscriber(Util.faker().name().fullName()));
    }
}
