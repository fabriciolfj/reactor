package com.github.fabriciolfj.reactor.v1.flux;

import com.github.fabriciolfj.reactor.v1.util.Util;
import reactor.core.publisher.Flux;

public class FluxGenerateCount {

    public static void main(String[] args) {
        Flux.generate(
                () -> 1,
                (count, sink) -> {
                    var country = Util.faker().country().name();
                    sink.next(country);

                    if (count >= 10 || country.equalsIgnoreCase("canada")) {
                        sink.complete();
                    }

                    return count + 1;
                }
        ).subscribe(Util.subscriber());
    }
}
