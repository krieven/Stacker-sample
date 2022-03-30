import io.github.krieven.stacker.common.*;
import io.github.krieven.stacker.common.dto.Command;
import io.github.krieven.stacker.flow.FlowHandler;
import org.junit.Assert;
import org.junit.Test;
import sample.contract.ContractWrapper;
import sample.flow.catalog.flow.CatalogFlow;
import sample.flow.catalog.flow.CatalogFlowRq;
import sample.flow.catalog.flow.CatalogFlowRs;
import sample.flow.catalog.flow.FlowData;
import sample.flow.catalog.states.category.contract.CategoryA;
import sample.flow.catalog.states.category.contract.CategoryQ;
import sample.flow.catalog.states.product.contract.ProductQ;
import sample.services.CatalogCategoryServiceStub;

public class TestCatalogFlow {
    private final String FLOW_NAME = "search";
    private final IParser parser = new JsonParser();

    private final FlowHandler flow = FlowHandler.create(new CatalogFlow(new CatalogCategoryServiceStub()));

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
                        Assert.assertEquals(2, question.getCategories().size());


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
    public void testOpenBadId() throws SerializingException {

        Command input = new Command();
        input.setType(Command.Type.OPEN);
        input.setFlow(FLOW_NAME);
        CatalogFlowRq rq = new CatalogFlowRq();
        rq.setCategoryId("badId");

        input.setContentBody(parser.serialize(rq));

        flow.handleCommand(
                input,
                new ICallback<Command>() {
                    @Override
                    public void success(Command command) {
                        Assert.assertNotNull(command);

                        Assert.assertEquals(Command.Type.RETURN, command.getType());
                        Assert.assertNull(command.getFlow());
                        Assert.assertNull(command.getState());
                        Assert.assertEquals(parser.getContentType(), command.getBodyContentType());

                        CatalogFlowRs question = extractQuestion(command, CatalogFlowRs.class);
                        Assert.assertNull(question);


                        FlowData flowData = extractFlowData(command, FlowData.class);
                        Assert.assertNull(flowData);
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
        rq.setCategoryId("COMP");

        input.setContentBody(parser.serialize(rq));

        flow.handleCommand(
                input, new ICallback<Command>() {
                    @Override
                    public void success(Command command) {
                        Assert.assertNotNull(command);
                        Assert.assertEquals(FLOW_NAME, command.getFlow());
                        Assert.assertEquals("category", command.getState().toLowerCase());
                        Assert.assertEquals(parser.getContentType(), command.getBodyContentType());

                        assertCategoryQIsOk(command);

                        FlowData flowData = extractFlowData(command, FlowData.class);
                        Assert.assertNotNull(flowData);
                        Assert.assertEquals("COMP", flowData.getFlowRequest().getCategoryId());
                        Assert.assertNull(flowData.getParentCategoryId());
                    }

                    @Override
                    public void reject(Exception e) {
                        Assert.assertNull(e);
                    }
                });
    }

    @Test
    public void testOpenLastDeep() throws SerializingException {
        Command input = new Command();
        input.setType(Command.Type.OPEN);
        input.setFlow(FLOW_NAME);
        CatalogFlowRq rq = new CatalogFlowRq();
        rq.setCategoryId("DESKTOP");

        input.setContentBody(parser.serialize(rq));

        flow.handleCommand(
                input, new ICallback<Command>() {
                    @Override
                    public void success(Command command) {
                        assertCommandIsOk(command, "PRODUCT");

                        ProductQ question = extractQuestion(command, ProductQ.class);
                        Assert.assertNotNull(question);

                        FlowData flowData = extractFlowData(command, FlowData.class);
                        Assert.assertNotNull(flowData);
                        Assert.assertEquals("DESKTOP", flowData.getFlowRequest().getCategoryId());
                        Assert.assertNull(flowData.getParentCategoryId());
                    }

                    @Override
                    public void reject(Exception e) {
                        Assert.assertNull(e);
                    }
                });
    }

    @Test
    public void testAnswerEmpty() throws SerializingException {
        Command input = new Command();
        input.setType(Command.Type.ANSWER);
        input.setFlow(FLOW_NAME);
        input.setState("category");
        CatalogFlowRq rq = new CatalogFlowRq();
        rq.setCategoryId("COMP");
        FlowData flowData = FlowData.build(rq);
        input.setFlowData(parser.serialize(flowData));

        flow.handleCommand(
                input, new ICallback<Command>() {
                    @Override
                    public void success(Command command) {
                        assertCommandIsOk(command, "category");

                        assertCategoryQIsOk(command);

                        FlowData flowData = extractFlowData(command, FlowData.class);
                        Assert.assertNotNull(flowData);
                        Assert.assertEquals("COMP", flowData.getFlowRequest().getCategoryId());
                        Assert.assertNull(flowData.getParentCategoryId());
                        Assert.assertNull(flowData.getCategoryAnswer());
                    }

                    @Override
                    public void reject(Exception e) {
                        Assert.assertNull(e);
                    }
                });
    }

    @Test
    public void testAnswerOkBadCategory() throws SerializingException {
        Command input = new Command();
        input.setType(Command.Type.ANSWER);
        input.setFlow(FLOW_NAME);
        input.setState("category");
        CatalogFlowRq rq = new CatalogFlowRq();
        rq.setCategoryId("COMP");
        FlowData flowData = FlowData.build(rq);
        input.setFlowData(parser.serialize(flowData));
        CategoryA categoryA = new CategoryA();
        categoryA.setCategoryId("badId");
        categoryA.setAction(CategoryA.Action.OK);
        input.setContentBody(parser.serialize(categoryA));

        flow.handleCommand(
                input, new ICallback<Command>() {
                    @Override
                    public void success(Command command) {
                        assertCommandIsOk(command, "category");

                        assertCategoryQIsOk(command);

                        FlowData flowData = extractFlowData(command, FlowData.class);
                        Assert.assertNotNull(flowData);
                        Assert.assertEquals("COMP", flowData.getFlowRequest().getCategoryId());
                        Assert.assertNull(flowData.getParentCategoryId());
                        Assert.assertNull(flowData.getCategoryAnswer());
                    }

                    @Override
                    public void reject(Exception e) {
                        Assert.assertNull(e);
                    }
                });
    }

    @Test
    public void testAnswerOkOkCategory() throws SerializingException {
        Command input = new Command();
        input.setType(Command.Type.ANSWER);
        input.setFlow(FLOW_NAME);
        input.setState("category");
        CatalogFlowRq rq = new CatalogFlowRq();
        rq.setCategoryId("COMP");
        FlowData flowData = FlowData.build(rq);
        input.setFlowData(parser.serialize(flowData));
        CategoryA categoryA = new CategoryA();
        categoryA.setCategoryId("LAPTOP");
        categoryA.setAction(CategoryA.Action.OK);
        input.setContentBody(parser.serialize(categoryA));

        flow.handleCommand(
                input, new ICallback<Command>() {
                    @Override
                    public void success(Command command) {
                        assertCommandIsOk(command, "PRODUCT");


                        FlowData flowData = extractFlowData(command, FlowData.class);
                        Assert.assertNotNull(flowData);
                        Assert.assertEquals("COMP", flowData.getFlowRequest().getCategoryId());
                        Assert.assertNull(flowData.getParentCategoryId());
                        Assert.assertNotNull(flowData.getCategoryAnswer());
                    }

                    @Override
                    public void reject(Exception e) {
                        Assert.assertNull(e);
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

    private void assertCommandIsOk(Command command, String state){
        Assert.assertNotNull(command);
        Assert.assertEquals(FLOW_NAME, command.getFlow());
        Assert.assertEquals(state, command.getState());
        Assert.assertEquals(parser.getContentType(), command.getBodyContentType());
    }

    private void assertCategoryQIsOk(Command command){
        CategoryQ question = extractQuestion(command, CategoryQ.class);
        Assert.assertNotNull(question);
        Assert.assertEquals("Please select product category", question.getTitle());
        Assert.assertEquals(2, question.getCategories().size());
        Assert.assertEquals("DESKTOP", question.getCategories().get(0).getId());
        Assert.assertEquals("COMP", question.getCategories().get(0).getParent());
        Assert.assertEquals("Desktop", question.getCategories().get(0).getTitle());
    }
}
