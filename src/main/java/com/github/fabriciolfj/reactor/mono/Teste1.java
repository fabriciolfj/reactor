package com.github.fabriciolfj.reactor.mono;

import java.util.stream.Stream;

public class Teste1 {

    public static void main(String[] args) {
        var stream = Stream.of(1)
                .map(v -> {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return v * 2;
                });
        stream.forEach(System.out::println);
    }
}
