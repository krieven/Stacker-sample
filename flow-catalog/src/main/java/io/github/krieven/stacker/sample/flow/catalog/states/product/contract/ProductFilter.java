package io.github.krieven.stacker.sample.flow.catalog.states.product.contract;

import io.github.krieven.stacker.sample.model.KeyVal;

import java.util.List;

public class ProductFilter {
    private String path;
    private String title;
    private List<KeyVal> presets;

    public ProductFilter(String path, String title, List<KeyVal> presets) {
        this.path = path;
        this.title = title;
        this.presets = presets;
    }

    public ProductFilter() {
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<KeyVal> getPresets() {
        return presets;
    }

    public void setPresets(List<KeyVal> presets) {
        this.presets = presets;
    }


}
