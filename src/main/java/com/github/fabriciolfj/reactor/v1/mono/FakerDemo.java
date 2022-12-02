package com.github.fabriciolfj.reactor.v1.mono;

import com.github.fabriciolfj.reactor.v1.util.Util;

public class FakerDemo {

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            System.out.println(Util.faker().book().author());
        }
    }
}
