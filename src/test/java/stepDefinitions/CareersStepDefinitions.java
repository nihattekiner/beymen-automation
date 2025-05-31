package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.commen.BaseElement;
import pages.web.CartPage;
import pages.web.HomePage;
import pages.web.ProductPage;
import pages.web.ProductSearchPage;
import steps.BaseSteps;
import steps.CareersSteps;
import utils.ClassList;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;


public class CareersStepDefinitions extends BaseSteps {


    private final BaseSteps baseSteps = ClassList.getInstance().get(BaseSteps.class);
    public final CareersSteps careersSteps = ClassList.getInstance().get(CareersSteps.class);
    protected Logger logger = LoggerFactory.getLogger(getClass());

    String title;

    String productPriceBeforeAddToCart;
    String productTotalPriceInCart;
    WebElement pieceDropdown;
    Select selectPiece;
    String emptyCartText;
    String productName;
    String productPrice;



    @Given("the homepage should be displayed")
    public void theHomepageShouldBeDisplayed() {
        baseSteps.waitByMilliSeconds(2000);
        baseSteps.clickElement(HomePage.CEREZ_REJECT.getLocator());
        baseSteps.clickElement(HomePage.CHOOSE_GENDER_CLOSE.getLocator());
        System.out.println(driver.getTitle());
        title = "Beymen.com – Türkiye’nin Tek Dijital Lüks Platformu";
        Assert.assertEquals(title,driver.getTitle());
    }

    @When("the user searchs {string} into the search box and clears the search box")
    public void theUserSearchsIntoTheSearchBoxAndClearsTheSearchBox(String text) {
        //text = utilities.ExcelUtils.getCellData(0, 0); // 1. satır 1. sütun
        baseSteps.clickElement(HomePage.SEARCH_BOX.getLocator());
        baseSteps.sendKeys(ProductSearchPage.SEARCH_BOX_AFTER_CLICK.getLocator(), text); // sort
        baseSteps.clickElement(ProductSearchPage.SEARCH_BUTTON.getLocator());
        baseSteps.clickElement(HomePage.SEARCH_BOX.getLocator());
        baseSteps.clickElement(ProductSearchPage.DELETE_BUTTON.getLocator());
    }

    @And("the user searchs {string} into the search box and presses the Enter Key")
    public void theUserSearchsIntoTheSearchBox(String text ) {
        baseSteps.waitByMilliSeconds(3000);
        //text = utilities.ExcelUtils.getCellData(0, 1); // 1. satır 2. sütun
        baseSteps.sendKeys(ProductSearchPage.SEARCH_BOX_AFTER_CLICK.getLocator(), text + Keys.ENTER); // gomlek
    }


    @And("a random product from the search results is selected")
    public void aRandomProductFromTheSearchResultsIsSelected() {
        careersSteps.clickRandomProductFromList(ProductSearchPage.SELECT_PRODUCT.getLocator());

        WebElement elementProductName = driver.findElement(By.xpath("//span[@class='o-productDetail__description']"));
        productName = elementProductName.getText();
        System.out.println("Product Name: " + productName);

        WebElement elementProductPrice = driver.findElement(By.id("priceNew"));
        productPrice = elementProductPrice.getText();
        System.out.println("Product Price: " + productPrice);

        try {
            FileWriter writer = new FileWriter("product_info.txt");
            writer.write("Product Name: " + productName + "\n");
            writer.write("Product Price: " + productPrice + "\n");
            writer.close();
            System.out.println("Product information has been written to the file.");
        } catch (IOException e) {
            System.out.println("File writing error: " + e.getMessage());
        }
    }

    @And("the selected product is added to the cart")
    public void theSelectedProductIsAddedToTheCart() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        BaseElement[] sizes = {
                ProductPage.CHOOSE_SIZE_SMALL,
                ProductPage.CHOOSE_SIZE_MEDIUM,
                ProductPage.CHOOSE_SIZE_LARGE,
                ProductPage.CHOOSE_SIZE_XLARGE
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
        baseSteps.waitByMilliSeconds(2000);
        baseSteps.clickElement(ProductPage.ADD_CART.getLocator());
    }

    @Then("the product price on the product page should match the price in the cart")
    public void theProductPriceOnTheProductPageShouldMatchThePriceInTheCart() {
        baseSteps.waitByMilliSeconds(2000);
        //WebElement elementTotalPriceInCart = driver.findElement(By.id("priceNew"));
        //productTotalPriceInCart = elementTotalPriceInCart.getText();
        //System.out.println("Product Name: " + productName);
        // Dosyadan Product Price bilgisini oku
        String priceFromFile = "";
        try {
            BufferedReader reader = new BufferedReader(new FileReader("product_info.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Product Price: ")) {
                    priceFromFile = line.replace("Product Price: ", "").trim();
                    break;
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("File reading error: " + e.getMessage());
        }

        System.out.println("Price from file: " + priceFromFile);

        baseSteps.clickElement(ProductPage.CART_BUTTON.getLocator());

        baseSteps.waitByMilliSeconds(2000);
        WebElement elementProductPrice = driver.findElement(By.xpath("(//span[contains(@class,'Summary__value')])[3]"));
        productTotalPriceInCart = elementProductPrice.getText();
        System.out.println("Product Price: " + productTotalPriceInCart);

        priceFromFile = priceFromFile.replaceAll("[\\s\\u00A0]", "")  // Boşlukları sil
                .replace("TL", "")
                .replace(",", ".");              // Opsiyonel, eğer nokta olmalıysa

        productTotalPriceInCart = productTotalPriceInCart.replaceAll("[\\s\\u00A0]", "")
                .replace("TL", "")
                .replace(",", ".");                // Opsiyonel

        priceFromFile = priceFromFile.replaceAll("\\.00$", "");
        productTotalPriceInCart = productTotalPriceInCart.replaceAll("\\.00$", "");

        Assert.assertEquals(priceFromFile, productTotalPriceInCart);


        //productPriceBeforeAddToCart = ProductPage.PRODUCT_PRICE.toString();
        //System.out.println("===============================================");
        //System.out.println(productPriceBeforeAddToCart);

        //baseSteps.clickElement(ProductPage.CART_BUTTON.getLocator());
        //productTotalPriceInCart = CartPage.CART_TOTAL_PRICE.toString();
        //System.out.println("===============================================");
        //System.out.println(productTotalPriceInCart);

    }

    @When("the product quantity is increased to {int}")
    public void theProductQuantityIsIncreasedTo(int arg0) {
        pieceDropdown = driver.findElement(CartPage.PRODUCT_PIECE.getLocator());
        selectPiece = new Select(pieceDropdown);
        selectPiece.selectByIndex(1);


    }

    @Then("the product quantity should be {int} in the cart")
    public void theProductQuantityShouldBeInTheCart(int arg0) {
        String piece = "2 Adet";
        Assert.assertEquals(piece,productTotalPriceInCart);
    }

    @When("the product is removed from the cart")
    public void theProductIsRemovedFromTheCart() {
        baseSteps.clickElement(CartPage.DELETE_PRODUCTS.getLocator());
    }

    @Then("the cart should be empty")
    public void theCartShouldBeEmpty() {
        emptyCartText = "Sepetinizde Ürün Bulunmamaktadır";
        Assert.assertEquals(emptyCartText, CartPage.EMPTY_CART.toString());
    }
}

/*
    @When("rastgele bir ürüne tıklar: {int}")
    public void rastgeleBirUrunTiklarIndexIle(int index) {
        List<WebElement> products = baseSteps.findElements(CareersPage.SELECT_PRODUCT.getLocator());

        if (products == null || products.isEmpty()) {
            throw new RuntimeException("Ürün listesi boş!");
        }
        if (index < 0 || index >= products.size()) {
            throw new IndexOutOfBoundsException("Geçersiz ürün indexi: " + index);
        }
        WebElement selectedProduct = products.get(index);
        System.out.println("Tıklanacak ürün indexi: " + index + " | Ürün içeriği: " + selectedProduct.getText());
        try {
            selectedProduct.click();
            waitForTheElement(CareersPage.PRODUCT_PRICE.getLocator());
            String price = String.valueOf(findElement(CareersPage.PRODUCT_PRICE.getLocator()));
            ScenarioContext.set("URUN_FIYAT", price);
            System.out.println("ürünün fiyat: " + price);
        } catch (Exception e) {
            throw new RuntimeException("Ürüne tıklanamadı: " + e.getMessage());
        }

    } */



