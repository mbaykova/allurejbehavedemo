package ru.aplana.autotests.pages;



import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import ru.aplana.autotests.steps.BaseSteps;

/**
 * Created by Maria on 06.05.2017.
 */
public class DMSPage extends BasePageObject{


    @FindBy(xpath = "//*[@class='page-header']/*")
    public WebElement title;

    @FindBy(xpath = "//*[contains(text(),'Отправить заявку')][contains(@class,'btn')]")
    public WebElement sendAppBtn;

    public DMSPage(){
        PageFactory.initElements(BaseSteps.getDriver(), this);
    }
}
