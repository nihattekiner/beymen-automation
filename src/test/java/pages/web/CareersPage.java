package pages.web;

import org.openqa.selenium.By;
import pages.commen.BaseElement;

public class CareersPage extends WebMainPage {
    public static final String PAGE_NAME = "Careers Page";
    public static final BaseElement CEREZ_REJECT = new BaseElement(By.xpath("//button[@id='onetrust-reject-all-handler']"));
    public static final BaseElement CHOOSE_GENDER_CLOSE = new BaseElement(By.xpath("//button[contains(@class,'closeButton')]"));
    public static final BaseElement SEARCH_BOX = new BaseElement(By.xpath("//div[contains(@class,'search--wrapper')]"));
    public static final BaseElement SEARCH_BUTTON = new BaseElement(By.xpath("//button[contains(@class,'search--btn')]"));
    public static final BaseElement DELETE_BUTTON = new BaseElement(By.xpath("//button[contains(@class,'hasButton')]"));
    public static final BaseElement SELECT_PRODUCT = new BaseElement(By.xpath("(//div[@class='o-productList__item'])"));
    public static final BaseElement PRODUCT_PRICE = new BaseElement(By.xpath("(//span[@class='m-productCard__newPrice'])"));
    public static final BaseElement ADD_CART_BUTTON = new BaseElement(By.xpath("//div[@class='m-productCard__stock -hasMultiStock']"));
    public static final BaseElement CHOOSE_SIZE_SMALL = new BaseElement(By.xpath("(//span[@class='m-variation__item'])[1]"));
    public static final BaseElement CHOOSE_SIZE_MEDIUM = new BaseElement(By.xpath("(//span[@class='m-variation__item'])[2]"));
    public static final BaseElement CHOOSE_SIZE_LARGE = new BaseElement(By.xpath("(//span[@class='m-variation__item'])[3]"));
    public static final BaseElement CHOOSE_SIZE_XLARGE = new BaseElement(By.xpath("(//span[@class='m-variation__item'])[4]"));
    public static final BaseElement ADD_CART = new BaseElement(By.id("addBasket"));
    public static final BaseElement CART_BUTTON = new BaseElement(By.xpath("(//span[contains(@class,'userInfo--text')])[3]"));
    public static final BaseElement CART_TOTAL_PRICE = new BaseElement(By.xpath("(//span[contains(@class,'Summary__value')])[3]"));
    public static final BaseElement PRODUCT_PIECE = new BaseElement(By.id("quantitySelect0-key-0"));
    public static final BaseElement DELETE_PRODUCTS = new BaseElement(By.id("removeCartItemBtn0-key-0"));
    public static final BaseElement EMPTY_CART = new BaseElement(By.xpath("//strong[contains(text(),'Sepetinizde')]"));







    @Override
    public String getPageName() {
        return PAGE_NAME;
    }
}
