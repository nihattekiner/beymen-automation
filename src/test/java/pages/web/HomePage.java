package pages.web;

import org.openqa.selenium.By;
import pages.commen.BaseElement;

public class HomePage extends WebMainPage {
    public static final String PAGE_NAME = "Home Page";

    public static final BaseElement CEREZ_REJECT = new BaseElement(By.xpath("//button[@id='onetrust-reject-all-handler']"));

    public static final BaseElement CHOOSE_GENDER_CLOSE = new BaseElement(By.xpath("//button[contains(@class,'closeButton')]"));

    public static final BaseElement SEARCH_BOX = new BaseElement(By.xpath("//input[@placeholder='Ürün, Marka Arayın']"));











    @Override
    public String getPageName() {
        return PAGE_NAME;
    }
}
