package sample.flow.catalog.states.category;


import org.jetbrains.annotations.NotNull;

public interface CategoryData {
    @NotNull
    CategoryStateModel getStateModel(CategoryState state);
}
