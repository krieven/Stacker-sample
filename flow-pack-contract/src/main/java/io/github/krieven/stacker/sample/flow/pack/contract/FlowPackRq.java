package io.github.krieven.stacker.sample.flow.pack.contract;

import io.github.krieven.stacker.sample.model.Pack;

public class FlowPackRq {
    private Integer id;
    private Pack pack;

    public Pack getPack() {
        return pack;
    }

    public void setPack(Pack pack) {
        this.pack = pack;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
