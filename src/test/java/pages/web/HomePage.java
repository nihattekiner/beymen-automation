package pages.web;

import org.openqa.selenium.By;
import pages.commen.BaseElement;

public class HomePage extends WebMainPage {
    public static final String PAGE_NAME = "Home Page";

    public static final BaseElement COOKIE_ACCEPT_BUTTON = new BaseElement(By.xpath("//a[@id='wt-cli-accept-all-btn']"));
    public static final BaseElement COMPANY_BUTTON = new BaseElement(By.xpath("(//a[@id='navbarDropdownMenuLink'])[5] | //a[text()='Company']"));
    public static final BaseElement CAREERS_BUTTON = new BaseElement(By.xpath("//a[contains(@href,'careers')]"));

    @Override
    public String getPageName() {
        return PAGE_NAME;
    }
}
