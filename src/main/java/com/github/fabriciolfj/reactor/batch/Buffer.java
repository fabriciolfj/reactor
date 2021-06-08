package com.github.fabriciolfj.reactor.batch;

import com.github.fabriciolfj.reactor.util.Util;
import reactor.core.publisher.Flux;

public class Buffer {

    public static void main(String[] args) {
        Flux.range(1, 10)
                .buffer(2)
                .subscribe(Util.subscriber());
    }
}
