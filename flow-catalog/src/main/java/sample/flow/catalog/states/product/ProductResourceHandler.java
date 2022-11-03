package sample.flow.catalog.states.product;

import io.github.krieven.stacker.common.IParser;
import io.github.krieven.stacker.common.JsonParser;
import io.github.krieven.stacker.common.SerializingException;
import io.github.krieven.stacker.flow.FlowContext;
import io.github.krieven.stacker.flow.ResourceController;
import io.github.krieven.stacker.flow.StateCompletion;
import org.jetbrains.annotations.NotNull;
import sample.model.Product;
import sample.services.CatalogProductService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ProductResourceHandler extends ResourceController<ProductData> {
    private final ProductState productState;
    private final CatalogProductService catalogProductService;
    private final IParser parser = new JsonParser();

    public ProductResourceHandler(ProductState productState, CatalogProductService catalogProductService) {
        this.productState = productState;
        this.catalogProductService = catalogProductService;
    }

    private int toInt(String s, int byDefault) {
        try {
            return Integer.parseInt(Optional.ofNullable(s).orElse("" + byDefault), 10);
        } catch (NumberFormatException nfe) {
            return byDefault;
        }
    }

    @Override
    protected @NotNull StateCompletion handle(List<String> pathInfo, Map<String, String[]> params, FlowContext<? extends ProductData> flowContext) {
        pathInfo = Optional.ofNullable(pathInfo).orElse(new ArrayList<>());
        int pageSize = toInt(pathInfo.get(0), 20);
        int pageNumber = toInt(pathInfo.get(1), 0);

        ProductStateModel stateModel = flowContext.getFlowData().getStateModel(productState);
        List<Product> products = catalogProductService.getByCategory(stateModel.getCategoryId());

        try {
            return sendResponse(parser.serialize(products.subList(pageNumber * pageSize, (pageNumber + 1) * pageSize)), flowContext);
        } catch (SerializingException e) {
            return sendResponse("error creating response".getBytes(), flowContext);
        }
    }

    @Override
    protected String getContentType() {
        return parser.getContentType();
    }
}
