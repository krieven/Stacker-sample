package io.github.krieven.stacker.sample.flow.catalog.states.product.contract;

import io.github.krieven.stacker.sample.model.KeyVal;

import java.util.List;

public class ProductFilter {
    private String key;
    private String title;
    private List<KeyVal> presets;

    public ProductFilter(String key, String title, List<KeyVal> presets) {
        this.key = key;
        this.title = title;
        this.presets = presets;
    }

    public ProductFilter() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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
