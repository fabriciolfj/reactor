package com.github.fabriciolfj.reactor.helper;

import reactor.core.publisher.Flux;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderService {

    private static Map<Integer, List<PurchaseOrder>> db = new HashMap<>();

    static {
        var list1 = List.of(new PurchaseOrder(1), new PurchaseOrder(1));
        var list2 = List.of(new PurchaseOrder(2), new PurchaseOrder(2));
        db.put(1, list1);
        db.put(2, list2);
    }

    public static Flux<PurchaseOrder> getPurchaseOrder(int userId) {
        return Flux.create(fluxSink -> {
            db.get(userId).forEach(fluxSink::next);
            fluxSink.complete();
        });
    }
}
