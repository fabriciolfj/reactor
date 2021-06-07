package com.github.fabriciolfj.reactor.combinando;

import com.github.fabriciolfj.reactor.util.Util;
import reactor.core.publisher.Flux;

public class With {

    public static void main(String[] args) {
        var flux1 = Flux.just(1,2,3);
        var flux2 = Flux.just(4,5,6);

        flux1.concatWith(flux2)
                .subscribe(Util.subscriber());
    }
}
