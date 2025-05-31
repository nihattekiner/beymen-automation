package stepDefinitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.web.HomePage;
import steps.BaseSteps;
import steps.HomePageSteps;
import utils.ClassList;

public class HomePageStepDefinitions extends BaseSteps {


    private final BaseSteps baseSteps = ClassList.getInstance().get(BaseSteps.class);
    public final HomePageSteps homePageSteps = ClassList.getInstance().get(HomePageSteps.class);
    protected Logger logger = LoggerFactory.getLogger(getClass());


    @Given("Accept Chrome cookie popup")
    public void acceptChromeCookiePopup() {
        logger.info("Entered");
        baseSteps.clickElement(HomePage.COOKIE_ACCEPT_BUTTON.getLocator());
    }
    @Given("Go to the Career page and verify the page")
    public void goToCareerPageAndVerify(DataTable table) {
        logger.info("Entered.Parameters; table: \n{}", table);
        homePageSteps.careersProcess(table);
    }
}
