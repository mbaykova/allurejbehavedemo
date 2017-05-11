package ru.aplana.autotests.pages;




import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import ru.aplana.autotests.steps.BaseSteps;


/**
 * Created by Maria on 29.04.2017.
 */
public class MainPage {


    @FindBy(xpath = "//ol[contains(@class,'rgs-menu pull-left')]")
    WebElement menuItems;

    @FindBy(xpath = "//div[contains(@class,'grid rgs-main-menu')]")
    WebElement menuInsurance;

    public MainPage(){
        PageFactory.initElements(BaseSteps.getDriver(), this);
    }

    public void selectMenuItem(String itemName){
        menuItems.findElement(By.xpath(".//li[contains(@class,'current')]/*[contains(text(),'"+itemName+"')]")).click();
    }

    public void selectInsuranceItem(String itemName){
        menuInsurance.findElement(By.xpath(".//li[contains(@class,'line3-link')]//a[contains(text(),'"+itemName+"')]")).click();
    }
}


