package io.github.krieven.stacker.sample.flow.pack;

import io.github.krieven.stacker.flow.server.FlowServer;
import io.github.krieven.stacker.sample.flow.pack.flow.PackFlow;

public class Main {
    public static void main(String[] args) throws Exception {
        new FlowServer(new PackFlow(), 4002).start();
    }
}