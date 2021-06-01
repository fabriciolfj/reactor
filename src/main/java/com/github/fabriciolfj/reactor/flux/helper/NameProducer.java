package com.github.fabriciolfj.reactor.flux.helper;

import com.github.fabriciolfj.reactor.util.Util;
import reactor.core.publisher.FluxSink;

import java.util.function.Consumer;

public class NameProducer implements Consumer<FluxSink<String>> {

    private FluxSink<String> fluxSink;

    @Override
    public void accept(FluxSink<String> stringFluxSink) {
        this.fluxSink = stringFluxSink;
    }

    public void produce() {
        final String name = Util.faker().name().fullName();
        final String thread = Thread.currentThread().getName();
        this.fluxSink.next(thread + " " + name);
    }
}
