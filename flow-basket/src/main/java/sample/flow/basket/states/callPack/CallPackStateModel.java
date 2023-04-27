package sample.flow.basket.states.callPack;

import sample.flow.pack.contract.FlowPackRq;

public class CallPackStateModel {
    public FlowPackRq createQuestion() {
        return new FlowPackRq();
    }
}
