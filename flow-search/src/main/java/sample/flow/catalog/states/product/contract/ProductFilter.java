package sample.flow.catalog.states.product.contract;

import sample.model.Pair;

import java.util.List;

public class ProductFilter {
    private String key;
    private String title;
    private List<Pair> presets;

    public ProductFilter(String key, String title, List<Pair> presets) {
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

    public List<Pair> getPresets() {
        return presets;
    }

    public void setPresets(List<Pair> presets) {
        this.presets = presets;
    }


}
