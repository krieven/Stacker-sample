package io.github.krieven.stacker.sample.flow.basket.states.basket;

import io.github.krieven.stacker.sample.flow.pack.contract.Pack;
import io.github.krieven.stacker.util.Probe;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class BasketStateModel {
    private int index = -1;
    private List<Pack> packs = new ArrayList<>();

    public List<Pack> getPacks() {
        return packs;
    }

    public void setPacks(@NotNull List<Pack> packs) {
        this.packs = packs;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Pack getSelectedPack() {
        return Probe.tryGet(() -> packs.get(index)).orElse(null);
    }

    public void putPack(Pack pack) {
        int currentIndex = index;
        index = -1;

        if (currentIndex < 0) {
            if(pack == null || pack.isEmpty()) {
                return;
            }
            packs.add(pack);
            return;
        }
        if(pack == null || pack.isEmpty()) {
            packs.remove(currentIndex);
            return;
        }
        packs.set(currentIndex, pack);
    }
}
