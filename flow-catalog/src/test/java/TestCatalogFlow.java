import io.github.krieven.stacker.common.*;
import io.github.krieven.stacker.common.dto.Command;
import org.junit.Assert;
import org.junit.Test;

import sample.flow.catalog.contract.CatalogFlowRq;
import sample.flow.catalog.flow.FlowData;
import sample.flow.catalog.states.category.contract.CategoryQ;
import sample.flow.catalog.states.product.contract.ProductQ;

public class TestCatalogFlow {
    private final IParser parser = new JsonParser();


    @Test
    public void testOpenEmpty() {

        Command input = new Command();
        input.setType(Command.Type.OPEN);
        input.setFlow(TestUtils.FLOW_NAME);

        TestUtils.flow.handleCommand(
                input,
                new ICallback<>() {
                    @Override
                    public void success(Command command) {
                        Assert.assertNotNull(command);

                        Assert.assertEquals(Command.Type.QUESTION, command.getType());
                        Assert.assertEquals(TestUtils.FLOW_NAME, command.getFlow());
                        Assert.assertEquals("CATEGORY", command.getState());
                        Assert.assertEquals(parser.getContentType(), command.getBodyContentType());

                        CategoryQ question = TestUtils.extractQuestion(command, CategoryQ.class);
                        Assert.assertNotNull(question);
                        Assert.assertEquals(2, question.getCategories().size());


                        FlowData flowData = TestUtils.extractFlowData(command, FlowData.class);
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
        input.setFlow(TestUtils.FLOW_NAME);
        CatalogFlowRq rq = new CatalogFlowRq();
        rq.setCategoryId("badId");

        input.setContentBody(parser.serialize(rq));

        TestUtils.flow.handleCommand(
                input,
                new ICallback<>() {
                    @Override
                    public void success(Command command) {
                        Assert.assertNotNull(command);

                        Assert.assertEquals(Command.Type.RETURN, command.getType());
                        Assert.assertNull(command.getFlow());
                        Assert.assertNull(command.getState());
                        Assert.assertEquals(parser.getContentType(), command.getBodyContentType());

                        FlowData flowData = TestUtils.extractFlowData(command, FlowData.class);
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
        input.setFlow(TestUtils.FLOW_NAME);
        CatalogFlowRq rq = new CatalogFlowRq();
        rq.setCategoryId("COMP");

        input.setContentBody(parser.serialize(rq));

        TestUtils.flow.handleCommand(
                input,
                new ICallback<>() {
                    @Override
                    public void success(Command command) {
                        Assert.assertNotNull(command);
                        Assert.assertEquals(TestUtils.FLOW_NAME, command.getFlow());
                        Assert.assertEquals("category", command.getState().toLowerCase());
                        Assert.assertEquals(parser.getContentType(), command.getBodyContentType());

                        TestUtils.assertCategoryQIsOk(command, "Please select product category");

                        FlowData flowData = TestUtils.extractFlowData(command, FlowData.class);
                        Assert.assertNotNull(flowData);
                        Assert.assertEquals("COMP", flowData.getFlowRequest().getCategoryId());

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
        input.setFlow(TestUtils.FLOW_NAME);
        CatalogFlowRq rq = new CatalogFlowRq();
        rq.setCategoryId("DESKTOP");

        input.setContentBody(parser.serialize(rq));

        TestUtils.flow.handleCommand(
                input,
                new ICallback<>() {
                    @Override
                    public void success(Command command) {
                        TestUtils.assertCommandIsOk(command, TestUtils.FLOW_NAME,"PRODUCT");

                        ProductQ question = TestUtils.extractQuestion(command, ProductQ.class);
                        Assert.assertNotNull(question);


                        FlowData flowData = TestUtils.extractFlowData(command, FlowData.class);
                        Assert.assertNotNull(flowData);
                        Assert.assertEquals("DESKTOP", flowData.getFlowRequest().getCategoryId());
                        Assert.assertEquals("DESKTOP", flowData.getProductStateModel().getCategoryId());


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
        input.setFlow(TestUtils.FLOW_NAME);
        input.setState("category");
        CatalogFlowRq rq = new CatalogFlowRq();
        rq.setCategoryId("COMP");
        FlowData flowData = FlowData.build(rq);
        input.setFlowData(parser.serialize(flowData));

        TestUtils.flow.handleCommand(
                input,
                new ICallback<>() {
                    @Override
                    public void success(Command command) {
                        TestUtils.assertCommandIsOk(command,  TestUtils.FLOW_NAME,"category");

                        TestUtils.assertCategoryQIsOk(command, "Please select product category");

                        FlowData flowData = TestUtils.extractFlowData(command, FlowData.class);
                        Assert.assertNotNull(flowData);
                        Assert.assertEquals("COMP", flowData.getFlowRequest().getCategoryId());

                        CategoryQ categoryQ = TestUtils.extractQuestion(command, CategoryQ.class);

                        Assert.assertEquals(2, categoryQ.getCategories().size());
                        Assert.assertEquals("Please select product category", categoryQ.getTitle());

                    }

                    @Override
                    public void reject(Exception e) {
                        Assert.assertNull(e);
                    }
                });
    }




}
