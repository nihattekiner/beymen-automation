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
import java.util.List;


public class CareersStepDefinitions extends BaseSteps {


    private final BaseSteps baseSteps = ClassList.getInstance().get(BaseSteps.class);
    public final CareersSteps careersSteps = ClassList.getInstance().get(CareersSteps.class);
    protected Logger logger = LoggerFactory.getLogger(getClass());

    String title;
    String productPriceBeforeAddToCart;
    String productTotalPriceInCart;
    WebElement quantityDropdown;
    Select selectQuantity;
    String emptyCartText;
    String productName;
    String productPrice;
    String quantityString;
    By waitElement;


    @Given("the homepage should be displayed")
    public void theHomepageShouldBeDisplayed() {
        baseSteps.waitByMilliSeconds(2000);
        baseSteps.clickElement(HomePage.CEREZ_REJECT.getLocator());
        baseSteps.clickElement(HomePage.CHOOSE_GENDER_CLOSE.getLocator());
        System.out.println("Page Title: " + driver.getTitle());
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
        baseSteps.waitByMilliSeconds(2000);
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

        boolean productAdded  = false;

        for (BaseElement size : sizes) {
            try {
                // Bedeni tıklama
                WebElement sizeElement = driver.findElement(size.getLocator());
                wait.until(ExpectedConditions.elementToBeClickable(sizeElement));
                sizeElement.click();
                System.out.println("Tıklanan beden: " + size.getLocator());

                // Sepete ekleme
                baseSteps.clickElement(ProductPage.ADD_CART.getLocator());
                System.out.println("Sepete ekleme tıklandı.");

                // Sepete git (ör: sepete git butonunun locator'ı)
                baseSteps.clickElement(ProductPage.CART_BUTTON.getLocator());
                System.out.println("Sepete gidildi.");

                // Sepette boş yazısını kontrol et
                Thread.sleep(2000); // Sepet yüklenmesini beklemek için, wait kullanabilirsin
                List<WebElement> emptyCartElements = driver.findElements(By.xpath("//strong[contains(text(),'Sepetinizde Ürün Bulunmamaktadır')]"));
                if (emptyCartElements.isEmpty()) {
                    // Ürün var, döngüyü kır
                    System.out.println("Sepette ürün var.");
                    productAdded = true;
                    break;
                } else {
                    // Sepet boş, geri git (ör: tarayıcı geri)
                    driver.navigate().back();  // veya baseSteps.goBack(); varsa onu kullan
                    System.out.println("Sepet boş, geri dönüldü.");
                }
            } catch (Exception e) {
                System.out.println("Hata oluştu: " + e.getMessage());
                driver.navigate().back(); // Hata olursa geri dön
            }
        }

        if (!productAdded) {
            System.out.println("Hiçbir beden eklenemedi.");
        } else {
            System.out.println("Başarılı şekilde ürün sepete eklendi.");
            // Buradan sonra diğer adımlara geçebilirsin

        /*
        for (BaseElement size : sizes) {
            try {
                wait.until(ExpectedConditions.presenceOfElementLocated(size.getLocator()));
                WebElement sizeElement = driver.findElement(size.getLocator());
                if (sizeElement.isDisplayed() && sizeElement.isEnabled()) {
                    sizeElement.click();
                    //System.out.println("Seçilen beden: " + size.getLocator().toString());
                    sizeSelected = true;
                    break;
                } else {
                    System.out.println("Beden bulunuyor ama tıklanamaz: " + size.getLocator().toString());
                }
            } catch (Exception e) {
                System.out.println("Beden bulunamadı: " + size.getLocator().toString());
            }
        }

        if (sizeSelected) {
            baseSteps.waitByMilliSeconds(2000);
            baseSteps.clickElement(ProductPage.ADD_CART.getLocator());
        } else {
            System.out.println("Sepete eklenemedi, uygun beden bulunamadı.");
        }

         */

        }
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

        baseSteps.waitByMilliSeconds(1000);
        By priceLocator = By.xpath("(//span[contains(@class,'Summary__value')])[1]");
        baseSteps.waitForTheElement(priceLocator);
        WebElement elementProductPrice = driver.findElement(priceLocator);
        //    WebElement elementProductPrice = driver.findElement(By.xpath("(//span[contains(@class,'Summary__value')])[1]"));
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
    }

    @When("the product quantity is increased to {int}")
    public void theProductQuantityIsIncreasedTo(int quantity) {
        quantityDropdown = driver.findElement(CartPage.PRODUCT_QUANTITY.getLocator());
        selectQuantity = new Select(quantityDropdown);
        int availableOptions = selectQuantity.getOptions().size();
        System.out.println("Sepetteki mevcut adet seçenekleri: " + availableOptions);

        if (quantity <= availableOptions) {
            selectQuantity.selectByIndex(quantity - 1);
            System.out.println("Adet " + quantity + " olarak seçildi.");
        } else {
            System.out.println("İstenilen adet (" + quantity + ") mevcut değil. Mevcut adet: " + availableOptions +
                    ". Adet artırılamadı, işlem mevcut adet ile devam ediyor.");
        }
        //selectQuantity = new Select(quantityDropdown);
        //selectQuantity.selectByIndex(quantity-1);
    }

    @Then("the product quantity should be {int} in the cart")
    public void theProductQuantityShouldBeInTheCart(int quantity) {

        WebElement updatedQuantityDropdown = driver.findElement(CartPage.PRODUCT_QUANTITY.getLocator());
        Select updatedSelectQuantity = new Select(updatedQuantityDropdown);
        String quantityDropdownString = updatedSelectQuantity.getFirstSelectedOption().getText();
        String quantityString = quantity + " adet"; // Beklenen değeri dinamik yap
        Assert.assertEquals(quantityString, quantityDropdownString);

        //quantityDropdown = driver.findElement(CartPage.PRODUCT_QUANTITY.getLocator());
        //selectQuantity = new Select(quantityDropdown);
        //baseSteps.waitByMilliSeconds(2000);
        //String quantityDropdownString = selectQuantity.getFirstSelectedOption().getText();
        //quantityString = "2 adet";
        //Assert.assertEquals(quantityString,quantityDropdownString);
    }

    @When("the product is removed from the cart")
    public void theProductIsRemovedFromTheCart() {
        baseSteps.waitByMilliSeconds(3000);
        waitElement = CartPage.DELETE_PRODUCTS.getLocator();
        baseSteps.waitForTheElement(waitElement);
        baseSteps.clickElement(CartPage.DELETE_PRODUCTS.getLocator());
    }

    @Then("the cart should be empty")
    public void theCartShouldBeEmpty() {
        emptyCartText = "SEPETINIZDE ÜRÜN BULUNMAMAKTADIR";

        waitElement = By.xpath("//strong[contains(text(),'Sepetinizde Ürün Bulunmamaktadır')]");
        baseSteps.waitForTheElement(waitElement);
        //WebElement elementEmptyCartText = driver.findElement(waitElement);
        WebElement elementEmptyCartText = driver.findElement(By.xpath("//strong[contains(text(),'Sepetinizde Ürün Bulunmamaktadır')]"));
        String emptyCartTextInPage = elementEmptyCartText.getText();
        //String emptyCartTextInPage = baseSteps.getElementText(CartPage.EMPTY_CART.getLocator());
        System.out.println("Empty Cart Text: " + emptyCartTextInPage);
        Assert.assertEquals(emptyCartText, emptyCartTextInPage);
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



