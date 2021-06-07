package com.github.fabriciolfj.reactor.operadores;

import com.github.fabriciolfj.reactor.util.Util;
import reactor.core.publisher.Flux;

public class Callbacks {

    public static void main(String[] args) {
        Flux.create(fluxSink -> {
            System.out.println("created");
            for(int i =0; i < 5; i++) {
                fluxSink.next(i);
            }
            fluxSink.complete();
            System.out.println("complete");
        }).doOnComplete(() -> System.out.println("doOnComplete")) //chama no fim do fluxo, seja concluido pelo consumo ou cancelada a inscrição
                .doFirst(() -> System.out.println("doFirst 1")) //primeiro evento
                .doOnNext(o -> System.out.println("doOnNext: " + o)) //retorna a cada evento, ou a cada evento dentro do create, dependi a onde foi declarado
                .doOnSubscribe(s -> System.out.println("doOnSubscribe " + s)) //momento da inscrição no publisher
                .doOnError(err -> System.out.println("doOnError: " + err.getMessage()))
                .doOnTerminate(() -> System.out.println("doOnTerminate")) // chama apos o fim do complete
                .doOnCancel(() -> System.out.println("doOnCancel")) //retorna no momento do cancelamento da inscrição no publisher
                .doFinally(signalType -> System.out.println("doFinally: " + signalType)) // chama apos o terminate
                .doOnDiscard(Object.class, o -> System.out.println("doOndiscard: " + o)) // são eventos emtidos após o cancelamento da inscrição no publisher
                .doFirst(() -> System.out.println("doFirst 2")) //primeiro evento, nao importa o local
                .subscribe(Util.subscriber())   ;
    }
}
