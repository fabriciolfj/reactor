package com.github.fabriciolfj.reactor.publisher;

import com.github.fabriciolfj.reactor.util.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class HotPublisher {

    public static void main(String[] args) {
        Flux<String> movieStream = getMovies()
                .delayElements(Duration.ofSeconds(2))
                .share();

        movieStream.subscribe(Util.subscriber("teste"));

        Util.sleepSeconds(4);

        movieStream.subscribe(Util.subscriber("teste2"));

        Util.sleepSeconds(5);
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
