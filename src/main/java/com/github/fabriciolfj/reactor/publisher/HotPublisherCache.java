package com.github.fabriciolfj.reactor.publisher;

import com.github.fabriciolfj.reactor.util.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class HotPublisherCache {

    public static void main(String[] args) {
        /*
        guarda na memória os eventos e os reemitem quando um novo inscrito se inscreve no fluxo
        * **/
        Flux<String> movieStream = getMovies()
                .delayElements(Duration.ofSeconds(1))
                .publish()
                //.autoConnect(2);
                .cache(2); //quantidade que quero guardar no cache

        Util.sleepSeconds(2);

        movieStream.subscribe(Util.subscriber("teste"));

        Util.sleepSeconds(10);

        System.out.println("inicio - teste2");
        movieStream.subscribe(Util.subscriber("teste2")); // vai conseguir consumir apena so 4 evento por causa do sleed de 3

        Util.sleepSeconds(60);
    }

    private static Flux<String> getMovies() {
        return Flux.just(
                "movie 1",
                "movie 2",
                "movie 3",
                "movie 4"
        );
    }
}
