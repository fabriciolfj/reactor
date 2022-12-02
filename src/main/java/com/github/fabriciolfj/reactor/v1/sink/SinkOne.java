package com.github.fabriciolfj.reactor.v1.sink;

import com.github.fabriciolfj.reactor.v1.util.Util;
import reactor.core.publisher.Sinks;

public class SinkOne {

    public static void main(String[] args) {
        var sink = Sinks.one();
        var mono = sink.asMono();

        //sink.tryEmitValue("ola");
        /*sink.tryEmitError(new RuntimeException("teste"));
        sink.tryEmitEmpty();*/

        /*
        * uma forma de tratar as etapas de emissão
        * */
        sink.emitValue("ola", (signalType, emitResult) -> {
            System.out.println("sinal " + signalType.name());
            System.out.println("result " + emitResult.name());
            return false;
        });

        /*
        * vai dar error, pq o mono so emite 0 ou 1 evento, nesse caso ele já estará concluido (fail_terminated)
        * */
        sink.emitValue("ola", (signalType, emitResult) -> {
            System.out.println("sinal " + signalType.name());
            System.out.println("result " + emitResult.name());
            return false;
        });

        mono.doOnError(e -> System.out.println(e.getMessage()))
                .subscribe(Util.subscriber());

        mono.doOnError(e -> System.out.println(e.getMessage()))
                .subscribe(Util.subscriber());
    }
}
