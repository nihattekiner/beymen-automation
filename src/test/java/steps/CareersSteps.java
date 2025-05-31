package steps;

import base.BaseTest;
import io.cucumber.datatable.DataTable;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class CareersSteps extends BaseSteps {



    public void filterProcess(DataTable option, String selectLocationText, By selectLocation, String selectDepartmentText, By selectDepartment) {
        logger.info("Entered.");
        for (Map<String, String> data : option.asMaps(String.class, String.class)) {

            if (data.containsKey("Select Location")) {
                selectDropDown(selectLocationText, selectLocation);
            }
            if (data.containsKey("Select Department")) {
                selectDropDown(selectDepartmentText, selectDepartment);
            }
        }
    }



    public void selectDropDown(String text, By by) {
        logger.info("Entered.");
        text = BaseTest.FilterParam.getString(text + "_Selection").trim();
        selectTextFromDropDown(text, by);
    }

    public void clickRandomProductFromList(By productLocator) {
        List<WebElement> productList = driver.findElements(productLocator);

        if (productList.isEmpty()) {
            throw new RuntimeException("Ürün listesi boş geldi!");
        }

        Random rand = new Random();
        int randomIndex = rand.nextInt(productList.size());

        WebElement selectedProduct = productList.get(randomIndex);
        selectedProduct.click();
    }



}
