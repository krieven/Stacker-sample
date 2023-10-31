package io.github.krieven.stacker.sample.flow.pack.states.editPack.contract;

public class EditPackStateA {
    private Action action;
    private String id;

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public enum Action {
        OK, BACK, ADD, REM
    }
}
