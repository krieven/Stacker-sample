package io.github.krieven.stacker.sample.flow.catalog.states.category;


import javax.validation.constraints.NotNull;
public interface CategoryData {
    @NotNull
    CategoryStateModel getStateModel(CategoryState state);
}
