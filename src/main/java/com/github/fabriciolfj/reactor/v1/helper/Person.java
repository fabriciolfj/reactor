package com.github.fabriciolfj.reactor.v1.helper;

import com.github.fabriciolfj.reactor.v1.util.Util;
import lombok.Data;

@Data
public class Person {

    private String name;
    private Integer age;

    public Person() {
        name = Util.faker().name().fullName();
        age = Util.faker().random().nextInt(1, 30);
    }
}
