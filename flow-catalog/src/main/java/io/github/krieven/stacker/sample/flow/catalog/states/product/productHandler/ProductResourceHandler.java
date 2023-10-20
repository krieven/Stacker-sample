package io.github.krieven.stacker.sample.flow.catalog.states.product.productHandler;

import io.github.krieven.stacker.common.IParser;
import io.github.krieven.stacker.common.JsonParser;
import io.github.krieven.stacker.common.SerializingException;
import io.github.krieven.stacker.flow.FlowContext;
import io.github.krieven.stacker.flow.ResourceController;
import io.github.krieven.stacker.flow.StateCompletion;
import io.github.krieven.stacker.sample.flow.catalog.states.product.ProductData;
import io.github.krieven.stacker.sample.flow.catalog.states.product.ProductState;
import io.github.krieven.stacker.util.Probe;
import javax.validation.constraints.NotNull;
import io.github.krieven.stacker.sample.model.Product;
import io.github.krieven.stacker.sample.services.CatalogProductService;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class ProductResourceHandler extends ResourceController<ProductData> {
    private final ProductState productState;
    private final CatalogProductService catalogProductService;
    private final IParser parser = new JsonParser();

    public ProductResourceHandler(ProductState productState, CatalogProductService catalogProductService) {
        this.productState = productState;
        this.catalogProductService = catalogProductService;
    }

    @Override
    protected @NotNull StateCompletion handle(List<String> pathInfo, Map<String, String[]> params, FlowContext<? extends ProductData> flowContext) {
        int pageSize = Probe.tryGet(() -> Integer.parseInt(pathInfo.get(0),10)).orElse(3);
        int pageNumber = Probe.tryGet(() -> Integer.parseInt(pathInfo.get(1), 10)).orElse(0);

        ProductData.StateModel stateModel = flowContext.getFlowData().getStateModel(productState);
        List<Product> products = catalogProductService.getByCategory(stateModel.getCategoryId())
                .stream().filter(Objects::nonNull).sorted(
                        Comparator.comparing(Product::getPrice).reversed()
                ).collect(Collectors.toList());

        Response response = Response.create(
                pageNumber,
                pageSize,
                products.size(),
                products.stream().skip((long) pageNumber * pageSize).limit(pageSize).collect(Collectors.toList())
        );
        try {
            return sendResponse(parser.serialize(response), flowContext);
        } catch (SerializingException e) {
            return sendResponse("error creating response".getBytes(), flowContext);
        }
    }

    @Override
    protected String getContentType() {
        return parser.getContentType();
    }
}
