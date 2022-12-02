package com.github.fabriciolfj.reactor.v1.sink;

import com.github.fabriciolfj.reactor.v1.util.Util;
import reactor.core.publisher.Sinks;

public class SinkMulticastReplay {

    public static void main(String[] args) {
        var sink = Sinks.many().replay().all(2);

        var flux = sink.asFlux();

        flux.subscribe(Util.subscriber("ONE"));
        flux.subscribe(Util.subscriber("TWO"));

        sink.tryEmitNext(1);
        sink.tryEmitNext(2);
        sink.tryEmitNext(3);

        //vai receber os eventos, mesmo após a emissão
        flux.subscribe(Util.subscriber("THREE"));

    }
}
