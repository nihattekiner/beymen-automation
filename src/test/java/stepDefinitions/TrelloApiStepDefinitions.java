package stepDefinitions;

import io.cucumber.java.en.*;

public class TrelloApiStepDefinitions {


    @Given("The user sets up the Trello API key and token")
    public void setupApiCredentials() {
    }

    @When("The user creates a new board")
    public void createBoard() {
    }

    @And("The user creates two new cards on the board")
    public void createTwoCards() {
    }

    private String createCard(String cardName) {
        return cardName;
    }

    @And("The user randomly updates one of the cards")
    public void updateRandomCard() {
    }

    @And("The user deletes all created cards")
    public void deleteCards() {
    }

    private void deleteCard(String cardId) {
    }

    @Then("The user deletes the created board")
    public void deleteBoard() {
    }
}
