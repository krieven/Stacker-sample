package io.github.krieven.stacker.sample.flow.pack.states.editPack;

import io.github.krieven.stacker.sample.model.Pack;

public interface EditPackStateData {
    EditPackStateModel getStateModel(EditPackState state);
    interface EditPackStateModel {
        Pack getPack();

        void removeAll();

        void setAddProductCategory(String id);
    }
}
