import io.github.krieven.stacker.common.ICallback;
import io.github.krieven.stacker.common.IParser;
import io.github.krieven.stacker.common.JsonParser;
import io.github.krieven.stacker.common.SerializingException;
import io.github.krieven.stacker.common.dto.Command;
import org.junit.Assert;
import org.junit.Test;

import io.github.krieven.stacker.sample.flow.catalog.contract.CatalogFlowRq;
import io.github.krieven.stacker.sample.flow.catalog.flow.FlowData;
import io.github.krieven.stacker.sample.flow.catalog.states.category.CategoryStateModel;
import io.github.krieven.stacker.sample.flow.catalog.states.category.contract.CategoryA;
import io.github.krieven.stacker.sample.flow.catalog.states.category.contract.CategoryQ;

public class TestCategoryState {
    private final IParser parser = new JsonParser();


//    @Test
    public void testAnswerOkBadCategory() throws SerializingException {
        Command input = new Command();
        input.setType(Command.Type.ANSWER);
        input.setFlow(TestUtils.FLOW_NAME);
        input.setState("category");
        CatalogFlowRq rq = new CatalogFlowRq();
        rq.setCategoryId("COMP");
        FlowData flowData = FlowData.build(rq);
        input.setFlowData(parser.serialize(flowData));
        CategoryA categoryA = new CategoryA();
        categoryA.setCategoryId("badId");
        categoryA.setAction(CategoryA.Action.OK);
        input.setContentBody(parser.serialize(categoryA));

        TestUtils.flow.handleCommand(
                input,
                new ICallback<Command>() {
                    @Override
                    public void success(Command command) {
                        TestUtils.assertCommandIsOk(command,  TestUtils.FLOW_NAME,"category");

                        TestUtils.assertCategoryQIsOk(command, "Please select product category");

                        FlowData flowData = TestUtils.extractFlowData(command, FlowData.class);
                        Assert.assertNotNull(flowData);
                        Assert.assertEquals("COMP", flowData.getFlowRequest().getCategoryId());
                        Assert.assertNull(flowData.getCategoryStateModel());
                    }

                    @Override
                    public void reject(Exception e) {
                        Assert.assertNull(e);
                    }
                });
    }

    @Test
    public void testAnswerOkLastCategory() throws SerializingException {
        Command input = new Command();
        input.setType(Command.Type.ANSWER);
        input.setFlow(TestUtils.FLOW_NAME);
        input.setState("category");
        CatalogFlowRq rq = new CatalogFlowRq();
        rq.setCategoryId("COMP");
        FlowData flowData = FlowData.build(rq);
        input.setFlowData(parser.serialize(flowData));
        CategoryA categoryA = new CategoryA();
        categoryA.setCategoryId("LAPTOP");
        categoryA.setAction(CategoryA.Action.OK);
        input.setContentBody(parser.serialize(categoryA));

        TestUtils.flow.handleCommand(
                input,
                new ICallback<Command>() {
                    @Override
                    public void success(Command command) {
                        TestUtils.assertCommandIsOk(command, TestUtils.FLOW_NAME,"PRODUCT");


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
    public void testAnswerOkTopCategory() throws SerializingException {
        Command input = new Command();
        input.setType(Command.Type.ANSWER);
        input.setFlow(TestUtils.FLOW_NAME);
        input.setState("category");
        CatalogFlowRq rq = new CatalogFlowRq();
        rq.setCategoryId("COMP");
        FlowData flowData = FlowData.build(rq);
        input.setFlowData(parser.serialize(flowData));
        CategoryA categoryA = new CategoryA();
        categoryA.setCategoryId("COMP");
        categoryA.setAction(CategoryA.Action.OK);
        input.setContentBody(parser.serialize(categoryA));

        TestUtils.flow.handleCommand(
                input,
                new ICallback<Command>() {
                    @Override
                    public void success(Command command) {
                        TestUtils.assertCommandIsOk(command, TestUtils.FLOW_NAME,"category");

                        TestUtils.assertCategoryQIsOk(command, "Please select category of Computer");

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

//    @Test
    public void testAnswerUpTopCategory() throws SerializingException {
        Command input = new Command();
        input.setType(Command.Type.ANSWER);
        input.setFlow(TestUtils.FLOW_NAME);
        input.setState("category");
        CatalogFlowRq rq = new CatalogFlowRq();
        rq.setCategoryId("COMP");
        FlowData flowData = FlowData.build(rq);
        input.setFlowData(parser.serialize(flowData));
        CategoryA categoryA = new CategoryA();
        categoryA.setAction(CategoryA.Action.UP);
        input.setContentBody(parser.serialize(categoryA));

        TestUtils.flow.handleCommand(
                input,
                new ICallback<Command>() {
                    @Override
                    public void success(Command command) {
                        TestUtils.assertCommandIsOk(command, TestUtils.FLOW_NAME,"category");

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

//    @Test
    public void testAnswerUpDeepCategory() throws SerializingException {
        Command input = new Command();
        input.setType(Command.Type.ANSWER);
        input.setFlow(TestUtils.FLOW_NAME);
        input.setState("category");
        CatalogFlowRq rq = new CatalogFlowRq();
        rq.setCategoryId("DESKTOP");
        FlowData flowData = FlowData.build(rq);
        input.setFlowData(parser.serialize(flowData));
        CategoryA categoryA = new CategoryA();
        categoryA.setAction(CategoryA.Action.UP);
        input.setContentBody(parser.serialize(categoryA));

        TestUtils.flow.handleCommand(
                input,
                new ICallback<Command>() {
                    @Override
                    public void success(Command command) {
                        TestUtils.assertCommandIsOk(command, TestUtils.FLOW_NAME,"category");

                        TestUtils.assertCategoryQIsOk(command, "Please select category of Computer");

                        FlowData flowData = TestUtils.extractFlowData(command, FlowData.class);
                        Assert.assertNotNull(flowData);
                        Assert.assertEquals("DESKTOP", flowData.getFlowRequest().getCategoryId());
                    }

                    @Override
                    public void reject(Exception e) {
                        Assert.assertNull(e);
                    }
                });
    }

    @Test
    public void testAnswerUpCategory() throws SerializingException {
        Command input = new Command();
        input.setType(Command.Type.ANSWER);
        input.setFlow(TestUtils.FLOW_NAME);
        input.setState("category");
        CatalogFlowRq rq = new CatalogFlowRq();

        FlowData flowData = FlowData.build(rq);
        CategoryStateModel categoryStateModel = new CategoryStateModel();
        categoryStateModel.push(null);
        categoryStateModel.push("COMP");
        categoryStateModel.push("DESKTOP");
        flowData.setCategoryStateModel(categoryStateModel);

        input.setFlowData(parser.serialize(flowData));
        CategoryA categoryA = new CategoryA();
        categoryA.setAction(CategoryA.Action.UP);
        input.setContentBody(parser.serialize(categoryA));

        TestUtils.flow.handleCommand(
                input,
                new ICallback<Command>() {
                    @Override
                    public void success(Command command) {
                        TestUtils.assertCommandIsOk(command, TestUtils.FLOW_NAME,"category");

                        TestUtils.assertCategoryQIsOk(command, "Please select category of Computer");

                        FlowData flowData = TestUtils.extractFlowData(command, FlowData.class);
                        Assert.assertNotNull(flowData);
                        CategoryQ categoryQ = TestUtils.extractQuestion(command, CategoryQ.class);

                        Assert.assertNotNull(categoryQ);
                        Assert.assertEquals(2, categoryQ.getCategories().size());
                    }

                    @Override
                    public void reject(Exception e) {
                        Assert.assertNull(e);
                    }
                });
    }

    @Test
    public void testAnswerBackCategory() throws SerializingException {
        Command input = new Command();
        input.setType(Command.Type.ANSWER);
        input.setFlow(TestUtils.FLOW_NAME);
        input.setState("category");
        CatalogFlowRq rq = new CatalogFlowRq();
        rq.setCategoryId("COMP");
        FlowData flowData = FlowData.build(rq);
        CategoryStateModel categoryStateModel = new CategoryStateModel();
        categoryStateModel.push("DESKTOP");
        flowData.setCategoryStateModel(categoryStateModel);
        input.setFlowData(parser.serialize(flowData));
        CategoryA categoryA = new CategoryA();
        categoryA.setAction(CategoryA.Action.BACK);
        input.setContentBody(parser.serialize(categoryA));

        TestUtils.flow.handleCommand(
                input,
                new ICallback<Command>() {
                    @Override
                    public void success(Command command) {
                        TestUtils.assertCommandIsOk(command, null,null);
                        Assert.assertEquals(Command.Type.RETURN, command.getType());

                        FlowData flowData = TestUtils.extractFlowData(command, FlowData.class);
                        Assert.assertNull(flowData);

                    }

                    @Override
                    public void reject(Exception e) {
                        Assert.assertNull(e);
                    }
                });
    }
}
