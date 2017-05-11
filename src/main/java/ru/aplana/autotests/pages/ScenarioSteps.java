package ru.aplana.autotests.pages;

import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.model.ExamplesTable;
import org.jbehave.core.steps.Steps;
import ru.aplana.autotests.steps.DMSSteps;
import ru.aplana.autotests.steps.MainPageSteps;
import ru.aplana.autotests.steps.SendAppSteps;
import ru.yandex.qatools.allure.annotations.Step;

import java.util.Map;

/**
 * Created by Maria on 01.05.2017.
 */
public class ScenarioSteps{

    MainPageSteps mainPageSteps = new MainPageSteps();

    DMSSteps dmsSteps = new DMSSteps();

    SendAppSteps sendAppSteps = new SendAppSteps();

    @When("выбран пункт меню \"$menuName\"")
    public void selectMenuItem(String menuName){
        mainPageSteps.selectMenuItem(menuName);
    }

    @When("выбран вид страхования \"$menuName\"")
    public void selectMenuInsurance(String menuName){
        mainPageSteps.selectMenuInsurance(menuName);
    }

    @Then("заголовок страницы - ДМС равен \"$title\"")
    public void checkTitleDMSPage(String title){
        dmsSteps.checkPageTitle(title);
    }

    @When("выполнено нажати на кнопку Отправить заявку")
    public void clickBtnSendApp(){
        dmsSteps.goToSendAppPage();
    }

    @Then("заголовок страницы - Заявка на ДМС равен \"$title\"")
    public void checkTitleSendAppPage(String title){
        sendAppSteps.checkPageTitle(title);
    }


    @When("заполняются поля: $fields")
    @Step("заполняются поля:")
    public void fillForm(ExamplesTable fields){
        for (Map<String, String> row : fields.getRows()) {
            String field = row.get("field");
            String value = row.get("value");
            sendAppSteps.fillField(field, value);
        }
    }


    @Then("значения полей равны: $fields")
    @Step("поля заполнены значениями:")
    public void checkFillForm(ExamplesTable fields){
        for (Map<String, String> row : fields.getRows()) {
            String field = row.get("field");
            String value = row.get("value");
            sendAppSteps.checkFillField(field, value);
        }
    }

    @Then("в поле $field присутствует сообщение об ошибке $errorMessage")
    public void checkErrorMessage(String field, String errorMessage){
        sendAppSteps.checkErrorMessageField(field, errorMessage);

    }


}
