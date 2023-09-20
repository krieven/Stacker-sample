package sample.flow.catalog.contract;

import io.github.krieven.stacker.common.JsonParser;
import io.github.krieven.stacker.flow.Contract;

public class CatalogFlowContract extends Contract<CatalogFlowRq, CatalogFlowRs> {
    public CatalogFlowContract() {
        super(CatalogFlowRq.class, CatalogFlowRs.class, new JsonParser());
    }
}
