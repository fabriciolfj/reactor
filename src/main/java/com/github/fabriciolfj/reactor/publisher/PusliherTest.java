package com.github.fabriciolfj.reactor.publisher;

import com.github.fabriciolfj.reactor.util.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class PusliherTest {

    public static void main(String[] args) {
        var flux = Flux.just(1,2,3,4,5,6,7,8,9,10)
                .delayElements(Duration.ofSeconds(1))
                .publish()
                .cache(3);

        Util.sleepSeconds(2);
        flux.subscribe(i -> System.out.println("Primeiro: " + i));
        Util.sleepSeconds(3);
        flux.subscribe(i -> System.out.println("Segundo: " + i));

        Util.sleepSeconds(60);
    }


}
