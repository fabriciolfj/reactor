package com.github.fabriciolfj.reactor.v1.batch;

import com.github.fabriciolfj.reactor.v1.util.Util;
import reactor.core.publisher.Flux;

public class Buffer {

    public static void main(String[] args) {
        Flux.range(1, 10)
                .buffer(2)
                .subscribe(Util.subscriber());
    }
}
