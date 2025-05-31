package stepDefinitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.commen.BaseElement;
import pages.web.CareersPage;
import steps.BaseSteps;
import steps.CareersSteps;
import utils.ClassList;

import java.time.Duration;


public class CareersStepDefinitions extends BaseSteps {


    private final BaseSteps baseSteps = ClassList.getInstance().get(BaseSteps.class);
    public final CareersSteps careersSteps = ClassList.getInstance().get(CareersSteps.class);
    protected Logger logger = LoggerFactory.getLogger(getClass());

    String productPriceBeforeAddToCart;
    String productTotalPriceInCart;
    WebElement pieceDropdown;
    Select selectPiece;
    String emptyCartText;
    String title;


    @Given("Home Page, Send Keys, Urun Ara: {string}")
    public void sendKeysUrunAra(String text) {

        baseSteps.waitByMilliSeconds(2000);
        baseSteps.clickElement(CareersPage.CEREZ_REJECT.getLocator());
        baseSteps.clickElement(CareersPage.CHOOSE_GENDER_CLOSE.getLocator());
        System.out.println("------------------------------------------------------------------------------------------------");
        System.out.println(driver.getTitle());
        title = "Beymen.com – Türkiye’nin Tek Dijital Lüks Platformu";
        //Assert.assertEquals(title,driver.getTitle());

        baseSteps.clickElement(CareersPage.SEARCH_BOX.getLocator());
        baseSteps.sendKeys(CareersPage.SEARCH_BOX.getLocator(), text); // sort
        baseSteps.clickElement(CareersPage.SEARCH_BUTTON.getLocator());
        baseSteps.clickElement(CareersPage.DELETE_BUTTON.getLocator());
        //WebElement searchBox = driver.findElement(By.xpath("//div[contains(@class,'o-header__search--wrapper')]//input"));
        //searchBox.sendKeys("şort");
        //searchBox.sendKeys(Keys.ENTER); // Enter tuşu da gönderebilirsin

    }
    @Then("Kullanıcı {string} arar ve rastgele bir ürünü seçer")
    public void selectUrun(String text) {
        baseSteps.waitByMilliSeconds(3000);
        baseSteps.sendKeys(CareersPage.SEARCH_BOX.getLocator(), text + Keys.ENTER); // gomlek
        careersSteps.clickRandomProductFromList(CareersPage.SELECT_PRODUCT.getLocator());
        careersSteps.clickRandomProductFromList(CareersPage.ADD_CART_BUTTON.getLocator());

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        BaseElement[] sizes = {
                CareersPage.CHOOSE_SIZE_SMALL,
                CareersPage.CHOOSE_SIZE_MEDIUM,
                CareersPage.CHOOSE_SIZE_LARGE,
                CareersPage.CHOOSE_SIZE_XLARGE
        };

        boolean sizeSelected = false;

        for (BaseElement size : sizes) {
            try {
                wait.until(ExpectedConditions.elementToBeClickable(size.getLocator()));
                driver.findElement(size.getLocator()).click();
                System.out.println("Chosen Size: " + size.getLocator().toString());
                sizeSelected = true;
                break;
            } catch (Exception e) {
                System.out.println("No clickable locator: " + size.getLocator().toString());
            }
        }

        if (!sizeSelected) {
            System.out.println("No size could be chosen!");
        }

        baseSteps.clickElement(CareersPage.ADD_CART.getLocator());
        productPriceBeforeAddToCart = CareersPage.PRODUCT_PRICE.getLocator().toString();

        baseSteps.clickElement(CareersPage.CART_BUTTON.getLocator());
        productTotalPriceInCart = CareersPage.CART_TOTAL_PRICE.getLocator().toString();

        Assert.assertEquals(productPriceBeforeAddToCart,productTotalPriceInCart);

        pieceDropdown = driver.findElement(CareersPage.PRODUCT_PIECE.getLocator());
        selectPiece = new Select(pieceDropdown);
        selectPiece.selectByIndex(2);

        String piece = "2 Adet";
        Assert.assertEquals(piece,productTotalPriceInCart);

        baseSteps.clickElement(CareersPage.DELETE_PRODUCTS.getLocator());
        emptyCartText = "Sepetinizde Ürün Bulunmamaktadır";
        Assert.assertEquals(emptyCartText,CareersPage.EMPTY_CART.toString());

    }




}
