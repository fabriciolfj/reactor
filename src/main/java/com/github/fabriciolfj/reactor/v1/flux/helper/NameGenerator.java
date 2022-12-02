package com.github.fabriciolfj.reactor.v1.flux.helper;

import com.github.fabriciolfj.reactor.v1.util.Util;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

public class NameGenerator {

    public static List<String> getNames(int qtde) {
        List<String> names = new ArrayList<>();
        for(int i = 0; i< qtde; i++) {
            names.add(getName());
        }

        return names;
    }

    public static Flux<String> getNamesFlux(int qtde) {
        return Flux.range(0, qtde)
                .map(i -> getName());
    }

    private static String getName() {
        Util.sleepSeconds(1);
        return Util.faker().name().firstName();
    }
}
