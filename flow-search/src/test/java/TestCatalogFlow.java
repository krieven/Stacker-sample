import io.github.krieven.stacker.common.ICallback;
import io.github.krieven.stacker.common.dto.Command;
import io.github.krieven.stacker.flow.BaseFlow;
import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.Test;
import sample.flow.catalog.flow.CatalogFlow;

public class TestCatalogFlow {

    private BaseFlow flow = new CatalogFlow();

    @Test
    public void testOpenEmpty() {

        @NotNull Command input = new Command();
        input.setType(Command.Type.OPEN);
        input.setFlow("search");


        flow.handleCommand(
                input,
                new ICallback<Command>() {
                    @Override
                    public void success(Command command) {
                        Assert.assertNotNull(command);
                    }

                    @Override
                    public void reject(Exception e) {
                        Assert.assertNull(e);
                    }
                }
        );

    }

}
