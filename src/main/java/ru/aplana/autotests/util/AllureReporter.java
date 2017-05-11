package ru.aplana.autotests.util;

import org.jbehave.core.model.*;
import org.jbehave.core.reporters.StoryReporter;
import ru.aplana.autotests.steps.BaseSteps;
import ru.yandex.qatools.allure.Allure;
import ru.yandex.qatools.allure.annotations.Step;
import ru.yandex.qatools.allure.config.AllureModelUtils;
import ru.yandex.qatools.allure.events.*;
import ru.yandex.qatools.allure.model.TestCaseResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static ru.yandex.qatools.allure.utils.AnnotationManager.withExecutorInfo;

/**
 * Created by 777 on 08.05.2017.
 */
public class AllureReporter implements StoryReporter {

    private Allure lifecycle = Allure.LIFECYCLE;
    private final Map<String, String> suites = new HashMap<>();
    private String uid;
    private boolean withExamples = false;
    private String exampleName;
    private boolean isTestLevel = false;


    @Override
    public void storyNotAllowed(Story story, String filter) {

    }

    @Override
    public void storyCancelled(Story story, StoryDuration storyDuration) {

    }

    @Override
    public void beforeStory(Story story, boolean givenStory) {
        if (!givenStory) {
            uid = generateSuiteUid(story.getName());
            TestSuiteStartedEvent event = new TestSuiteStartedEvent(uid, story.getPath());
            event.withLabels(AllureModelUtils.createTestFrameworkLabel("JBehave"));
            event.withTitle(story.getName());
            getLifecycle().fire(event);
        }
    }

    @Override
    public void afterStory(boolean givenStory) {
        if (!givenStory) {
            getLifecycle().fire(new TestSuiteFinishedEvent(uid));
        }
    }

    @Override
    public void narrative(Narrative narrative) {

    }

    @Override
    public void lifecyle(Lifecycle lifecycle) {

    }

    @Override
    public void scenarioNotAllowed(Scenario scenario, String filter) {

    }

    @Override
    public void beforeScenario(String scenarioTitle) {
        if (!isTestLevel) {
            TestCaseStartedEvent event = new TestCaseStartedEvent(uid, scenarioTitle);
            withExecutorInfo(event);
            getLifecycle().fire(event);
            getLifecycle().fire(new ClearStepStorageEvent());
            isTestLevel = true;
        } else {
            beforeStep(scenarioTitle);
            isTestLevel = false;
        }
    }

    @Override
    public void scenarioMeta(Meta meta) {

    }

    @Override
    public void afterScenario() {
        if (isTestLevel) {
            getLifecycle().fire(new TestCaseFinishedEvent());
            isTestLevel = false;
        } else {
            getLifecycle().fire(new StepFinishedEvent());
            isTestLevel = true;
        }

    }

    @Override
    public void givenStories(GivenStories givenStories) {

    }

    @Override
    public void givenStories(List<String> storyPaths) {

    }

    @Override
    public void beforeExamples(List<String> steps, ExamplesTable table) {
        withExamples = true;
    }

    @Override
    public void example(final Map<String, String> tableRow) {
        final String rowName = tableRow.toString();
        if (withExamples) {
            getLifecycle().fire(new TestCaseEvent() {
                @Override
                public void process(TestCaseResult context) {
                    exampleName = context.getName();
                    String name = exampleName + " " + rowName;
                    context.setName(name);
                    context.setTitle(name);
                }
            });
        } else {
            String name = exampleName + " " + rowName;
            getLifecycle().fire(new TestCaseFinishedEvent());
            TestCaseStartedEvent event = new TestCaseStartedEvent(uid, name).withTitle(name);
            withExecutorInfo(event);
            getLifecycle().fire(event);
        }
        withExamples = false;
    }

    @Override
    public void afterExamples() {
        getLifecycle().fire(new TestCaseFinishedEvent());
    }

    @Override
    public void beforeStep(String step) {
        getLifecycle().fire(new StepStartedEvent(step).withTitle(step));
    }

    @Override
    public void successful(final String step) {
        updateStep(step);
        getLifecycle().fire(new StepFinishedEvent());
    }

    private void updateStep(final String step) {
        getLifecycle().fire(new StepEvent() {
            @Override
            public void process(ru.yandex.qatools.allure.model.Step context)
            {
                context.setName(step);
                context.setTitle(step);
            }

        });
    }

    @Override
    public void ignorable(String step) {
        getLifecycle().fire(new StepCanceledEvent());
    }

    @Override
    public void comment(String s) {

    }

    @Override
    public void pending(String step) {
        getLifecycle().fire(new StepStartedEvent(step).withTitle(step));
        getLifecycle().fire(new StepCanceledEvent());
        getLifecycle().fire(new StepFinishedEvent());
        getLifecycle().fire(new TestCasePendingEvent().withMessage("PENDING: Step is not implemented yet!"));
    }

    @Override
    public void notPerformed(String step) {
        getLifecycle().fire(new StepStartedEvent(step).withTitle(step));
        getLifecycle().fire(new StepCanceledEvent());
        getLifecycle().fire(new StepFinishedEvent());
    }

    @Override
    public void failed(String step, Throwable cause) {
        updateStep(step);
        getLifecycle().fire(new StepFailureEvent().withThrowable(cause.getCause()));
        getLifecycle().fire(new StepFinishedEvent());
        getLifecycle().fire(new TestCaseFailureEvent().withThrowable(cause.getCause()));
        getLifecycle().fire(new MakeAttachmentEvent(BaseSteps.takeScreenshot(), "screenshot", "png"));
    }

    @Override
    public void failedOutcomes(String step, OutcomesTable table) {

    }

    @Override
    public void restarted(String step, Throwable cause) {

    }

    @Override
    public void restartedStory(Story story, Throwable throwable) {

    }

    @Override
    public void dryRun() {

    }

    @Override
    public void pendingMethods(List<String> methods) {

    }

    private String generateSuiteUid(String suiteName) {
        String uid = UUID.randomUUID().toString();
        synchronized (getSuites()) {
            getSuites().put(suiteName, uid);
        }
        return uid;
    }

    private Allure getLifecycle() {
        return lifecycle;
    }

    private Map<String, String> getSuites() {
        return suites;
    }
}
