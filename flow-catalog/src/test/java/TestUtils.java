import io.github.krieven.stacker.common.IParser;
import io.github.krieven.stacker.common.JsonParser;
import io.github.krieven.stacker.common.dto.Command;
import io.github.krieven.stacker.flow.FlowHandler;
import org.junit.Assert;
import sample.contract.ContractWrapper;
import sample.flow.catalog.contract.CatalogFlowRs;
import sample.flow.catalog.flow.CatalogFlow;
import sample.flow.catalog.states.category.contract.CategoryQ;
import sample.services.CatalogCategoryServiceStub;
import sample.services.CatalogProductServiceStub;

public class TestUtils {
    public static final String FLOW_NAME = "search";

    public static final IParser parser = new JsonParser();
    public static final FlowHandler flow = FlowHandler.create(new CatalogFlow(new CatalogCategoryServiceStub(), new CatalogProductServiceStub()));


    public static  <T> T extractQuestion(Command command, Class<T> dataClass) {
        try {
            ContractWrapper wrapper = parser.parse(command.getContentBody(), ContractWrapper.class);
            return parser.parse(parser.serialize(wrapper.getData()), dataClass);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }

    }

    public static  <T> T extractFlowData(Command command, Class<T> dataClass) {
        try {
            return parser.parse(command.getFlowData(), dataClass);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }

    }

    public static void assertCommandIsOk(Command command, String flowName, String state){
        Assert.assertNotNull(command);
        Assert.assertEquals(flowName, command.getFlow());
        Assert.assertEquals(state, command.getState());
        Assert.assertEquals(parser.getContentType(), command.getBodyContentType());
    }

    public static void assertCategoryQIsOk(Command command, String title){
        CategoryQ question = extractQuestion(command, CategoryQ.class);
        Assert.assertNotNull(question);
        Assert.assertEquals(title, question.getTitle());
        Assert.assertEquals(2, question.getCategories().size());
        Assert.assertEquals("DESKTOP", question.getCategories().get(0).getId());
        Assert.assertEquals("COMP", question.getCategories().get(0).getParent());
        Assert.assertEquals("Desktop", question.getCategories().get(0).getTitle());
    }

    public static CatalogFlowRs extractReturn(Command command, Class<CatalogFlowRs> dataClass) {
        try {
            return parser.parse(command.getContentBody(), dataClass);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
}
