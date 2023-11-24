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
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of FlowData for the flow-pack
 */
public class FlowPackData implements CallCatalogStateData, EditPackStateData {


    private Integer id;
    private Pack thePack;
    private String addProductCategoryId;

    public void setFlowPackRq(FlowPackRq flowPackRq) {
        if(flowPackRq == null ){
            return;
        }
        if(flowPackRq.getPack() != null && !flowPackRq.getPack().isEmpty()){
            thePack = flowPackRq.getPack();
        }
        id = flowPackRq.getId();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
            public void clean() {
                id = null;
                thePack = null;
            }

            @Override
            public void setAddProductCategory(String id) {
                addProductCategoryId = id;
            }

        };
    }
}
