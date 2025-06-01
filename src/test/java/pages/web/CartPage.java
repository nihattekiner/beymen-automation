package pages.web;

import org.openqa.selenium.By;
import pages.commen.BaseElement;

public class CartPage extends WebMainPage {
    public static final String PAGE_NAME = "Cart Page";

    public static final BaseElement CART_TOTAL_PRICE = new BaseElement(By.xpath("(//span[contains(@class,'Summary__value')])[3]"));
    public static final BaseElement PRODUCT_QUANTITY = new BaseElement(By.id("quantitySelect0-key-0"));
    public static final BaseElement DELETE_PRODUCTS = new BaseElement(By.id("removeCartItemBtn0-key-0"));
    public static final BaseElement EMPTY_CART = new BaseElement(By.xpath("//strong[contains(text(),'Sepetinizde')]"));

    public String getPageName() {return PAGE_NAME;
    }
}
