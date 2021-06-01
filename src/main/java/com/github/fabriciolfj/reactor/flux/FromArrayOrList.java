package com.github.fabriciolfj.reactor.flux;

import com.github.fabriciolfj.reactor.util.Util;
import reactor.core.publisher.Flux;

import java.util.Arrays;

public class FromArrayOrList {

    public static void main(String[] args) {
        /*var strings = Arrays.asList("a", "b", "c");
        Flux.fromIterable(strings)
                .subscribe(Util.onNext());*/

        String[] strings = {"a", "b", "c"};
        Flux.fromArray(strings)
                .subscribe(Util.onNext());
    }
}
