package com.github.fabriciolfj.reactor.v1.operadores;

import com.github.fabriciolfj.reactor.v1.helper.OrderService;
import com.github.fabriciolfj.reactor.v1.helper.UserService;
import com.github.fabriciolfj.reactor.v1.util.Util;

public class FlatMap {

    public static void main(String[] args) {
        UserService.getUsers()
                //.flatMap(u -> OrderService.getPurchaseOrder(u.getUserId()))
                .concatMap(u -> OrderService.getPurchaseOrder(u.getUserId()))
                .subscribe(Util.subscriber());
    }
}
