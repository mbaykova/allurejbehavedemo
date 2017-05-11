package ru.aplana.autotests.pages;


import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.aplana.autotests.steps.BaseSteps;


/**
 * Created by Maria on 06.05.2017.
 */
public class SendAppPage extends BasePageObject {


    @FindBy(xpath = "//h4[@class='modal-title']/b[text()]")
    public WebElement title;

    @FindBy(name = "LastName")
    WebElement lastName;

    @FindBy(name = "FirstName")
    WebElement firstName;

    @FindBy(name = "MiddleName")
    WebElement middleName;

    @FindBy(xpath = "//*[contains(text(),'Телефон')]/..//input")
    WebElement phone;

    @FindBy(name = "Email")
    WebElement email;

    @FindBy(name = "ContactDate")
    WebElement contactDate;

    @FindBy(name = "Comment")
    WebElement comment;

    @FindBy(name = "Region")
    WebElement region;

    @FindBy(xpath = "//button[contains(text(),'Отправить')]")
    WebElement sendButton;

    public SendAppPage() {
        PageFactory.initElements(BaseSteps.getDriver(), this);
        (new WebDriverWait(BaseSteps.getDriver(), 10))
                .until(ExpectedConditions.visibilityOf(title));
    }

    public void fillField(String fieldName, String value){
        switch (fieldName){
            case  "Фамилия":
                fillField(lastName, value);
                break;
            case  "Имя":
                fillField(firstName, value);
                break;
            case  "Отчество":
                fillField(middleName, value);
                break;
            case  "Телефон":
                fillField(phone, value);
                break;
            case  "Регион":
                region.click();
                region.findElement(By.xpath("//option[text()='" + value + "']")).click();
                break;
            case  "Эл. почта":
                fillField(email, value);
                break;
            case  "Дата контакта":
                fillField(contactDate, value);
                contactDate.sendKeys(Keys.TAB);
                break;
            case  "Комментарии":
                fillField(comment, value);
                break;
            default:  throw new AssertionError("Поле '"+fieldName+"' не объявлено на странице");
        }
    }

    public String getFillField(String fieldName){
        switch (fieldName){
            case  "Фамилия":
                return lastName.getAttribute("value");
            case  "Имя":
                return firstName.getAttribute("value");
            case  "Отчество":
                return middleName.getAttribute("value");
            case  "Телефон":
                return phone.getAttribute("value");
            case  "Регион":
                String value = region.getAttribute("value");
                return region.findElement(By.xpath(".//*[@value='"+value+"']")).getText();
            case  "Эл. почта":
                return email.getAttribute("value");
            case  "Комментарии":
                return comment.getAttribute("value");
            case  "Дата контакта":
                return contactDate.getAttribute("value");
        }
        throw new AssertionError("Поле не объявлено на странице");
    }


    public void checkFieldErrorMessage(String field, String errorMessage){
        String xpath = "//*[text()='"+field+"']/..//*[@class='validation-error']";
        String actualValue = BaseSteps.getDriver().findElement(By.xpath(xpath)).getText();
        Assert.assertTrue(String.format("Получено значение [%s]. Ожидалось [%s]", actualValue, errorMessage),
                actualValue.contains(errorMessage));
    }
}
