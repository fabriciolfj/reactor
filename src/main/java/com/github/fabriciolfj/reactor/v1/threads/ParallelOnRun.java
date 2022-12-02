package com.github.fabriciolfj.reactor.v1.threads;

import com.github.fabriciolfj.reactor.v1.util.Util;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class ParallelOnRun {

    public static void main(String[] args) {
        Flux.range(1, 10000)
                .parallel(10)
                .runOn(Schedulers.boundedElastic())
                .doOnNext(i -> printThreadName("next " + i))
                .sequential()
                .subscribe(sub -> printThreadName("sub " + sub));

        Util.sleepSeconds(10);
    }

    private static void printThreadName(final String name) {
        System.out.println("Thread: " + Thread.currentThread().getName() + " value: " + name);
    }
}
