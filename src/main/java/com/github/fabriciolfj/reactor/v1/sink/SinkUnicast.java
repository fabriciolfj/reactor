package com.github.fabriciolfj.reactor.v1.sink;

import com.github.fabriciolfj.reactor.v1.util.Util;
import reactor.core.publisher.Sinks;

public class SinkUnicast {

    public static void main(String[] args) {
        var sink = Sinks.many().unicast().onBackpressureBuffer();

        var flux = sink.asFlux();

        flux.subscribe(Util.subscriber());

        sink.tryEmitNext(1);
        sink.tryEmitNext(2);
        sink.tryEmitNext(3);

        //vai dar erro, pois permite apenas 1 inscrito
        flux.subscribe(Util.subscriber());

    }
}
