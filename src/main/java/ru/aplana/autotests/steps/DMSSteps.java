package ru.aplana.autotests.steps;




import ru.aplana.autotests.pages.DMSPage;
import ru.yandex.qatools.allure.annotations.Step;

import static org.junit.Assert.assertTrue;

/**
 * Created by Maria on 06.05.2017.
 */
public class DMSSteps {

    @Step("выполнено нажатие на Отправить заявку")
    public void goToSendAppPage(){
        new DMSPage().sendAppBtn.click();
    }


    @Step("заголовок страницы - ДМС равен {0}")
    public void checkPageTitle(String expectedTitle){
        String actualTitle = new DMSPage().title.getText();
        assertTrue(String.format("Заголовок равен [%s]. Ожидалось - [%s]",
                actualTitle, expectedTitle), actualTitle.contains(expectedTitle));
    }

}
