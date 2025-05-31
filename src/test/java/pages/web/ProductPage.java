package pages.web;

import org.openqa.selenium.By;
import pages.commen.BaseElement;

public class ProductPage extends WebMainPage {
    public static final String PAGE_NAME = "Product Page";

    public static final BaseElement PRODUCT_NAME = new BaseElement(By.xpath("//span[@class='o-productDetail__description']"));
    public static final BaseElement PRODUCT_PRICE = new BaseElement(By.id("priceNew"));
    public static final BaseElement ADD_CART_BUTTON = new BaseElement(By.xpath("//div[@class='m-productCard__stock -hasMultiStock']"));
    public static final BaseElement CHOOSE_SIZE_SMALL = new BaseElement(By.xpath("(//span[@class='m-variation__item'])[1]"));
    public static final BaseElement CHOOSE_SIZE_MEDIUM = new BaseElement(By.xpath("(//span[@class='m-variation__item'])[2]"));
    public static final BaseElement CHOOSE_SIZE_LARGE = new BaseElement(By.xpath("(//span[@class='m-variation__item'])[3]"));
    public static final BaseElement CHOOSE_SIZE_XLARGE = new BaseElement(By.xpath("(//span[@class='m-variation__item'])[4]"));
    public static final BaseElement ADD_CART = new BaseElement(By.xpath("//button[@id='addBasket']"));
    public static final BaseElement CART_BUTTON = new BaseElement(By.xpath("(//span[contains(@class,'userInfo--text')])[3]"));

    @Override
    public String getPageName() {
        return PAGE_NAME;
    }
}