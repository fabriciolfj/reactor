package com.github.fabriciolfj.reactor.v1.flux;

import com.github.fabriciolfj.reactor.v1.util.Util;
import reactor.core.publisher.Flux;

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
