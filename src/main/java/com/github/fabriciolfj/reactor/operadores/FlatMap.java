package com.github.fabriciolfj.reactor.operadores;

import com.github.fabriciolfj.reactor.helper.OrderService;
import com.github.fabriciolfj.reactor.helper.UserService;
import com.github.fabriciolfj.reactor.util.Util;

public class FlatMap {

    public static void main(String[] args) {
        UserService.getUsers()
                //.flatMap(u -> OrderService.getPurchaseOrder(u.getUserId()))
                .concatMap(u -> OrderService.getPurchaseOrder(u.getUserId()))
                .subscribe(Util.subscriber());
    }
}
