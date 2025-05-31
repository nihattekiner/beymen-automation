package steps;

import io.cucumber.datatable.DataTable;
import pages.web.Page;

import java.util.HashMap;
import java.util.Map;

public class HomePageSteps extends BaseSteps {


    private final HashMap<String, Runnable> hashMapLogOut = new HashMap<>();
    private HashMap<String, Runnable> hashMap = new HashMap<>();


    public void careersProcess(DataTable table) {
        logger.info("Entered. Parameters; table: {}", table);
        for (Map<String, String> data : table.asMaps(String.class, String.class)) {
            if (data.containsKey("Company") && data.containsKey("Careers")) {
                String company = data.get("Company");
                clickCompanyButton();
                String careers = data.get("Careers");
                clickCareersButton();
            }
        }
    }

    public void clickCompanyButton() {
        logger.info("Entered.");
        clickElement(Page.COMPANY_BUTTON.getLocator());
        logger.info("The button was clicked successfully.");
    }


    public void clickCareersButton() {
        logger.info("Entered.");
        clickElement(Page.CAREERS_BUTTON.getLocator());
        logger.info("The button was clicked successfully.");
    }
}
