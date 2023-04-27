package sample.contract;

import io.github.krieven.stacker.flow.FlowContext;

import java.util.function.BiFunction;

public class WrapQuestion<Q> implements BiFunction<Q, FlowContext<?>, ContractWrapper> {
    @Override
    public ContractWrapper apply(Q o, FlowContext<?> flowContext) {
        ContractWrapper result = new ContractWrapper();
        result.setData(o);
        result.setFlow(flowContext.getFlowName());
        result.setState(flowContext.getStateName());
        return result;
    }
}
