import io.github.krieven.stacker.common.*;
import io.github.krieven.stacker.common.dto.Command;
import io.github.krieven.stacker.flow.BaseFlow;
import org.junit.Assert;
import org.junit.Test;
import sample.contract.ContractWrapper;
import sample.flow.catalog.flow.CatalogFlow;
import sample.flow.catalog.flow.CatalogFlowRq;
import sample.flow.catalog.flow.FlowData;
import sample.flow.catalog.states.category.contract.CategoryQ;

public class TestCatalogFlow {
    private final String FLOW_NAME = "search";
    private final IParser parser = new JsonParser();

    private BaseFlow flow = new CatalogFlow();

    @Test
    public void testOpenEmpty() {

        Command input = new Command();
        input.setType(Command.Type.OPEN);
        input.setFlow(FLOW_NAME);


        flow.handleCommand(
                input,
                new ICallback<Command>() {
                    @Override
                    public void success(Command command) {
                        Assert.assertNotNull(command);

                        Assert.assertEquals(Command.Type.QUESTION, command.getType());
                        Assert.assertEquals(FLOW_NAME, command.getFlow());
                        Assert.assertEquals("category", command.getState());
                        Assert.assertEquals(parser.getContentType(), command.getBodyContentType());

                        CategoryQ question = extractQuestion(command, CategoryQ.class);
                        Assert.assertNotNull(question);
                        Assert.assertEquals( 2, question.getCategories().size());


                        FlowData flowData = extractFlowData(command, FlowData.class);
                        Assert.assertNotNull(flowData);
                    }

                    @Override
                    public void reject(Exception e) {
                        Assert.assertNull(e);
                    }
                }
        );

    }

    @Test
    public void testOpenFistDeep() throws SerializingException {
        Command input = new Command();
        input.setType(Command.Type.OPEN);
        input.setFlow(FLOW_NAME);
        CatalogFlowRq rq = new CatalogFlowRq();

        input.setContentBody(parser.serialize(rq));

        flow.handleCommand(
                input, new ICallback<Command>() {
                    @Override
                    public void success(Command command) {
                        Assert.assertNotNull(command);
                    }

                    @Override
                    public void reject(Exception e) {

                    }
                });
    }

    private <T> T extractQuestion(Command command, Class<T> dataClass) {
        try {
            ContractWrapper wrapper = parser.parse(command.getContentBody(), ContractWrapper.class);
            return parser.parse(parser.serialize(wrapper.getData()), dataClass);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }

    }

    private <T> T extractFlowData(Command command, Class<T> dataClass) {
        try {
            return parser.parse(command.getFlowData(), dataClass);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }

    }
}
