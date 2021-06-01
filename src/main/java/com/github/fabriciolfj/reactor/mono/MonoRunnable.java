package com.github.fabriciolfj.reactor.mono;

import com.github.fabriciolfj.reactor.util.Util;
import reactor.core.publisher.Mono;

public class MonoRunnable {

    // o fromRunnable, apenas emite o onComplete
    public static void main(String[] args) {
        Mono.fromRunnable(timeConsuming()).subscribe(
                Util.onNext(),
                Util.error(),
                () -> System.out.println("Operation complete")
        );
    }

    private static Runnable timeConsuming() {
        return () -> {
            Util.sleepSeconds(3);
            System.out.println("Send to email");
        };
    }
}
