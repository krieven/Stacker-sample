package io.github.krieven.stacker.sample.flow.pack.flow;

import io.github.krieven.stacker.sample.flow.pack.states.callCatalog.CallCatalogState;
import io.github.krieven.stacker.sample.flow.pack.states.callCatalog.CallCatalogStateData;
import io.github.krieven.stacker.sample.flow.pack.states.editPack.EditPackState;
import io.github.krieven.stacker.sample.flow.pack.states.editPack.EditPackStateData;
import io.github.krieven.stacker.sample.flow.pack.contract.FlowPackRq;
import io.github.krieven.stacker.sample.model.Pack;
import io.github.krieven.stacker.sample.model.Product;
import io.github.krieven.stacker.util.Probe;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Implementation of FlowData for the flow-pack
 */
public class FlowPackData implements CallCatalogStateData, EditPackStateData {
    /**
     * The argument of flow
     */
    private FlowPackRq flowPackRq;
    private Pack thePack;
    private String addProductCategoryId;

    public void setFlowPackRq(FlowPackRq flowPackRq) {
        this.flowPackRq = flowPackRq;
    }

    public FlowPackRq getFlowPackRq() {
        return flowPackRq;
    }

    public Pack getThePack() {
        return thePack;
    }

    public void setThePack(Pack thePack) {
        this.thePack = thePack;
    }

    @Override
    public CallCatalogStateModel getStateModel(CallCatalogState o) {
        return new CallCatalogStateModel() {
            @Override
            public String getCategoryId() {
                return addProductCategoryId;
            }

            @Override
            public void addProduct(@NotNull Product product) {
                if(thePack == null) {
                    thePack = new Pack();
                    thePack.setProducts(Probe.tryGet(()->flowPackRq.getPack().getProducts().stream().map(
                            p -> Product.build(p.getId(),p.getCategory(),p.getName(),p.getPrice(),p.getFields())
                    ).collect(Collectors.toList())).orElse(new ArrayList<>()));
                }
                thePack.getProducts().add(product);
            }
        };
    }

    @Override
    public EditPackStateModel getStateModel(EditPackState state) {
        return new EditPackStateModel() {
            @Override
            public Pack getPack() {
                return thePack;
            }

            @Override
            public void removeAll() {
                thePack = null;
            }

            @Override
            public void setAddProductCategory(String id) {
                addProductCategoryId = id;
            }

        };
    }
}
