package io.github.krieven.stacker.sample.flow.basket.states.basket;

import io.github.krieven.stacker.sample.model.Pack;
import io.github.krieven.stacker.util.Probe;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class BasketStateModel {
    private Integer index;
    private List<Pack> packs = new ArrayList<>();

    public List<Pack> getPacks() {
        return packs;
    }

    public void setPacks(@NotNull List<Pack> packs) {
        this.packs = packs;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Pack readSelectedPack() {
        return Probe.tryGet(() -> packs.get(index)).orElse(null);
    }

    public void putPack(Integer id, Pack pack) {
        if(pack != null && !pack.isEmpty()) {
            if (id == null) {
                packs.add(pack);
                return;
            }
            packs.set(id, pack);
            return;
        }
        if(id != null) {
            packs.remove(id.intValue());
        }
    }
}
