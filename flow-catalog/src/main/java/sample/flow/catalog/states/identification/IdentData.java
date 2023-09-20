package sample.flow.catalog.states.identification;

import javax.validation.constraints.NotNull;
public interface IdentData {
    @NotNull
    IdentStateModel getStateModel(IdentState o);
}
