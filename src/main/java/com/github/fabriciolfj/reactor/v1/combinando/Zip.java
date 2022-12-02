package com.github.fabriciolfj.reactor.v1.combinando;

import com.github.fabriciolfj.reactor.v1.util.Util;
import reactor.core.publisher.Flux;

public class Zip {

    public static void main(String[] args) {
        var flux1 = Flux.just(1,2,3);
        var flux2 = Flux.just(4,5,6);

        flux1.zipWith(flux2, (t1, t2) -> t1 + "||" + t2)
                .subscribe(Util.subscriber());
    }
}
