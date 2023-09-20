package io.github.krieven.stacker.sample.flow.basket;

import io.github.krieven.stacker.flow.server.FlowServer;
import io.github.krieven.stacker.sample.flow.basket.flow.BasketFlow;

public class Main {
    public static void main(String[] args) throws Exception {
        new FlowServer(new BasketFlow(), 4000).start();
    }
}
