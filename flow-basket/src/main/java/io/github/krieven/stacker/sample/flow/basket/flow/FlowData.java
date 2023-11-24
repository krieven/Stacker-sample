package io.github.krieven.stacker.sample.flow.basket.flow;

import io.github.krieven.stacker.sample.flow.basket.contract.BasketFlowRq;
import io.github.krieven.stacker.sample.flow.basket.states.basket.BasketStateModel;
import io.github.krieven.stacker.sample.flow.basket.states.basket.BasketState;
import io.github.krieven.stacker.sample.flow.basket.states.basket.BasketStateData;
import io.github.krieven.stacker.sample.flow.basket.states.callPack.CallPackStateData;
import io.github.krieven.stacker.sample.flow.pack.contract.FlowPackRq;
import io.github.krieven.stacker.sample.flow.pack.contract.FlowPackRs;
import io.github.krieven.stacker.sample.model.Pack;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Class for storing packs during the flow-basket process
 */
public class FlowData implements CallPackStateData, BasketStateData {
    /**
     * Serializable (session scope) field - the List of created Pack's
     */
    private List<Pack> packs;
    /**
     * Temporary (request scope) field - selected Pack index
     */
    private Integer index;

    public List<Pack> getPacks() {
        return packs;
    }

    public void setPacks(List<Pack> packs) {
        this.packs = packs;
    }

    /**
     * Initializer
     * @param basketFlowRq BasketFlowRq - basket-flow question
     */
    public void setBasketFlowRq(BasketFlowRq basketFlowRq) {
        packs = Optional.ofNullable(basketFlowRq)
                .map(BasketFlowRq::getPacks)
                .orElse(new ArrayList<>()).stream().filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    /**
     * Realization of BasketStateData.getStateModel
     * @param state caller BasketState
     * @return BasketStateModel - proxy object
     */
    @Override
    public BasketStateModel getStateModel(BasketState state) {
        return new BasketStateModel() {
            @Override
            public void setIndex(Integer i) {
                index = i;
            }

            @Override
            public List<Pack> getPacks() {
                return packs;
            }
        };
    }

    /**
     * Realization of CallPackStateData.createFlowPackRq
     * @return FlowPackRq - the question to flow-pack
     */
    @Override
    public FlowPackRq createFlowPackRq() {
        FlowPackRq request = new FlowPackRq();
        if(packs!=null && index != null && index >= 0 && packs.size() > index) {
            request.setId(index);
            request.setPack(packs.get(index));
        }
        return request;
    }

    /**
     * Realization of CallPackStateData.setFlowPackRs
     * if response.id is not properly index in the Pack's list
     * and response.pack is not empty
     * then push response.pack to the Pack's list
     * if response.id is properly index in the Pack's list
     * and response.pack is empty then remove Pack
     * with index response.id from the list,
     * otherwise replace Pack in the list
     *
     * @param response FlowPackRs - the answer from flow-pack
     */
    @Override
    public void setFlowPackRs(FlowPackRs response) {
        if (response == null) {
            return;
        }
        if(response.getId() == null || response.getId() < 0 || response.getId() >= packs.size()){
            if(response.getPack() == null || response.getPack().isEmpty()) {
                return;
            }
            packs.add(response.getPack());
            return;
        }
        if(response.getPack() == null || response.getPack().isEmpty()) {
            packs.remove(response.getId().intValue());
            return;
        }
        packs.set(response.getId(), response.getPack());
    }
}
