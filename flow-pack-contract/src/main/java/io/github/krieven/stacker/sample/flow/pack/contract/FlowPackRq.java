package io.github.krieven.stacker.sample.flow.pack.contract;

import io.github.krieven.stacker.sample.model.Pack;

public class FlowPackRq {
    private int id;
    private Pack pack;

    public Pack getPack() {
        return pack;
    }

    public void setPack(Pack pack) {
        this.pack = pack;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
