import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.io.CodeLocations;
import org.jbehave.core.io.StoryFinder;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.junit.JUnitStory;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;
import ru.aplana.autotests.pages.ScenarioSteps;
import ru.aplana.autotests.steps.BaseSteps;
import ru.aplana.autotests.util.AllureReporter;

import java.util.List;

import static java.util.Arrays.asList;

/**
 * Created by 777 on 08.05.2017.
 */

public class BaseTest extends JUnitStories {

    @Override
    protected List<String> storyPaths() {
        return new StoryFinder().findPaths(CodeLocations.codeLocationFromClass(this.getClass()).getFile(), asList("**/*.story".split("\\s*,\\s*")), null);

    }
    @Override
    public Configuration configuration() {
        if (super.hasConfiguration()) {
            return super.configuration();
        }
        return new MostUsefulConfiguration()
                .useStoryReporterBuilder(new StoryReporterBuilder()
                        .withReporters(new AllureReporter()));
    }

    @Override
    public InjectableStepsFactory stepsFactory() {
        return new InstanceStepsFactory(configuration(), new BaseSteps(), new ScenarioSteps());
    }

    @Override
    public Embedder configuredEmbedder() {
        Embedder embedder = super.configuredEmbedder();
        embedder.embedderControls().doGenerateViewAfterStories(false);
        return embedder;
    }
}
