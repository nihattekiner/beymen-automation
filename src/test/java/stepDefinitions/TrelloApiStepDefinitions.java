package stepDefinitions;

import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import pojo.Board;
import pojo.Card;
import com.google.gson.Gson;
import com.fasterxml.jackson.databind.ObjectMapper;
import utils.ConfigReader;

import java.util.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class TrelloApiStepDefinitions {

    private String baseUrl = ConfigReader.get("baseUrl");
    private String apiKey = ConfigReader.get("apiKey");
    private String token = ConfigReader.get("token");

    private String boardId;
    private List<String> cardIds = new ArrayList<>();
    private Random random = new Random();
    private Gson gson = new Gson();

    @Given("The board creation request is prepared and sent successfully")
    public void theBoardCreationRequestIsPreparedAndSentSuccessfully() {
        RestAssured.baseURI = baseUrl;
        Board board = new Board();
        board.setName("Test Board " + System.currentTimeMillis());
        board.setDesc("Created by Rest-Assured and POJO");

        Response response = given()
                .queryParam("key", apiKey)
                .queryParam("token", token)
                .contentType("application/json")
                .body(gson.toJson(board))
                .post("/boards");

        response.then().statusCode(200);
        boardId = response.jsonPath().getString("id");
        System.out.println("Board ID: " + boardId);
        assert boardId != null;
    }


    @Given("Two cards are created successfully on the board")
    public void twoCardsAreCreatedSuccessfullyOnTheBoard() {
        RestAssured.baseURI = baseUrl;
        Card cardFirst = new Card();
        cardFirst.setName("First Card");
        cardFirst.setDesc("Created by Rest-Assured");

        Response responseFirstCard = given()
                .queryParam("key", apiKey)
                .queryParam("token", token)
                .queryParam("idList", getFirstListId(boardId))
                .contentType("application/json")
                .body(gson.toJson(cardFirst))
                .post("/cards");

        responseFirstCard.then().statusCode(200);
        String cardIdFirst = responseFirstCard.jsonPath().getString("id");
        cardIds.add(cardIdFirst);
        System.out.println("First Card ID: " + cardIdFirst);

        Card cardSecond = new Card();
        cardSecond.setName("Second Card");
        cardSecond.setDesc("Created by Rest-Assured");

        Response responseSecondCard = given()
                .queryParam("key", apiKey)
                .queryParam("token", token)
                .queryParam("idList", getFirstListId(boardId))
                .contentType("application/json")
                .body(gson.toJson(cardSecond))
                .post("/cards");

        responseSecondCard.then().statusCode(200);
        String cardIdSecond = responseSecondCard.jsonPath().getString("id");
        cardIds.add(cardIdSecond);
        System.out.println("Second Card ID: " + cardIdSecond);
    }

    @Given("A randomly selected card is updated successfully")
    public void aRandomlySelectedCardIsUpdatedSuccessfully() {
        if (cardIds.isEmpty()) {
            throw new RuntimeException("No cards to update");
        }
        String cardId = cardIds.get(random.nextInt(cardIds.size()));

        Card card = new Card();
        card.setName("Updated Card " + System.currentTimeMillis());
        card.setDesc("Updated description");

        Response response = given()
                .queryParam("key", apiKey)
                .queryParam("token", token)
                .contentType("application/json")
                .body(gson.toJson(card))
                .put("/cards/" + cardId);

        response.then().statusCode(200);
        System.out.println("Updated Card ID: " + cardId);
    }

    @Given("All created cards are deleted successfully")
    public void allCreatedCardsAreDeletedSuccessfully() {
        RestAssured.baseURI = baseUrl;
        for (String cardId : cardIds) {
            given()
                    .queryParam("key", apiKey)
                    .queryParam("token", token)
                    .delete("/cards/" + cardId)
                    .then()
                    .statusCode(200);
            System.out.println("Deleted Card ID: " + cardId);
        }
    }

    @Given("The created board is deleted successfully")
    public void theCreatedBoardIsDeletedSuccessfully() {
        RestAssured.baseURI = baseUrl;
        given()
                .queryParam("key", apiKey)
                .queryParam("token", token)
                .delete("/boards/" + boardId)
                .then()
                .statusCode(200);
        System.out.println("Deleted Board ID: " + boardId);
    }

    private String getFirstListId(String boardId) {
        Response response = given()
                .queryParam("key", apiKey)
                .queryParam("token", token)
                .get("/boards/" + boardId + "/lists");

        response.then().statusCode(200);
        return response.jsonPath().getString("id[0]");
    }



}
