package com.github.fabriciolfj.reactor.operadores;

import com.github.fabriciolfj.reactor.helper.Person;
import com.github.fabriciolfj.reactor.util.Util;
import reactor.core.publisher.Flux;

import java.util.function.Function;

public class Transform {

    public static void main(String[] args) {
        getPerson()
                .switchOnFirst((signal, person) ->
                    signal.isOnNext() && signal.get().getAge() > 0 ? modify().apply(person) : person)
                //.transform(modify())
                .subscribe(Util.subscriber());
    }

    private static Function<Flux<Person>, Flux<Person>> modify() {
        return flux -> flux.filter(p -> p.getAge() > 10)
                    .doOnNext(p -> p.getName().toUpperCase())
                    .doOnDiscard(Person.class, d -> System.out.println("discarted : " + d));
    }

    private static Flux<Person> getPerson() {
        return Flux.range(1, 30)
                .map(p -> new Person());
    }
}
