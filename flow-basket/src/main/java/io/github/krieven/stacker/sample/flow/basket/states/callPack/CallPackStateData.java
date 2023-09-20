package io.github.krieven.stacker.sample.flow.basket.states.callPack;

import io.github.krieven.stacker.sample.flow.pack.contract.FlowPackRq;
import io.github.krieven.stacker.sample.flow.pack.contract.FlowPackRs;

public interface CallPackStateData {
    FlowPackRq createFlowPackRq();
    void setFlowPackRs(FlowPackRs flowPackRs);
}
