package com.github.fabriciolfj.reactor.v1.publisher;

import com.github.fabriciolfj.reactor.v1.util.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class ColdPublisher {

    public static void main(String[] args) {
        Flux<String> movieStream = getMovies()
                .delayElements(Duration.ofSeconds(2));

        movieStream.subscribe(Util.subscriber("teste"));

        Util.sleepSeconds(15);

        movieStream.subscribe(Util.subscriber("teste2"));

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
