package com.github.fabriciolfj.reactor.v1.mono;

import com.github.fabriciolfj.reactor.v1.util.Util;
import reactor.core.publisher.Mono;

public class MonoFromSupplier {

    public static void main(String[] args) {
        //quando nao quero que nada seja invocado, sem mesmo ter algum assinante.
        //Se utilizar um Fornecedor, obtém um resultado.
        Mono.fromSupplier(MonoFromSupplier::getName)
        .subscribe(Util.onNext());

        //Se utilizar um Callable, este calcula um resultado ou lança uma excepção se não o conseguir fazer.
        Mono.fromCallable(() -> getName())
                .subscribe(Util.onNext());
    }

    public static String getName() {
        System.out.println("Generating name...");
        return Util.faker().name().fullName();
    }
}
