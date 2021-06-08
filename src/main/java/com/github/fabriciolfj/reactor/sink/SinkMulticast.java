package com.github.fabriciolfj.reactor.sink;

import com.github.fabriciolfj.reactor.util.Util;
import reactor.core.publisher.Sinks;

public class SinkMulticast {

    public static void main(String[] args) {
        //buffer
        //var sink = Sinks.many().multicast().onBackpressureBuffer();
        //no history
        var sink = Sinks.many().multicast().directAllOrNothing();

        var flux = sink.asFlux();

        flux.subscribe(Util.subscriber("ONE"));
        flux.subscribe(Util.subscriber("TWO"));

        sink.tryEmitNext(1);
        sink.tryEmitNext(2);
        sink.tryEmitNext(3);

        //não vai receber, pois se inscreveu após a emissão
        flux.subscribe(Util.subscriber("THREE"));

    }
}
