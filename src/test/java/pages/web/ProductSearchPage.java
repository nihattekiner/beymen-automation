package pages.web;

import org.openqa.selenium.By;
import pages.commen.BaseElement;

public class ProductSearchPage extends WebMainPage {
    public static final String PAGE_NAME = "Product Searh Page";

    public static final BaseElement SEARCH_BOX_AFTER_CLICK = new BaseElement(By.id("o-searchSuggestion__input"));
    public static final BaseElement SEARCH_BUTTON = new BaseElement(By.xpath("//button[contains(@class,'search--btn')]"));
    public static final BaseElement DELETE_BUTTON = new BaseElement(By.xpath("//button[contains(@class,'hasButton')]"));
    public static final BaseElement SELECT_PRODUCT = new BaseElement(By.xpath("(//div[@class='o-productList__item'])"));

    @Override
    public String getPageName() {
        return PAGE_NAME;
    }
}
