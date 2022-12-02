package com.github.fabriciolfj.reactor.v1.publisher;

import com.github.fabriciolfj.reactor.v1.util.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class HotPublisherRef {

    public static void main(String[] args) {
        Flux<String> movieStream = getMovies()
                .delayElements(Duration.ofSeconds(1))
                .publish()
                //.autoConnect();
                .refCount(1); //exige ao menos 1 inscrito, para começar a publicar eventos

        movieStream.subscribe(Util.subscriber("teste"));

        Util.sleepSeconds(3);

        movieStream.subscribe(Util.subscriber("teste2")); // vai conseguir consumir apena so 4 evento por causa do sleed de 3

        Util.sleepSeconds(15);
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
