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
    private ObjectMapper objectMapper = new ObjectMapper();

    @Given("Trello API için board oluşturma isteği hazırlanır")
    public void prepareBoardCreationRequest() {
        RestAssured.baseURI = baseUrl;
    }

    @When("Board oluşturma isteği gönderilir")
    public void sendBoardCreationRequest() {
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
    }

    @Then("Board başarıyla oluşturulur ve board ID kaydedilir")
    public void verifyBoardCreated() {
        assert boardId != null;
    }

    @Given("Trello board ID ile kart oluşturma isteği hazırlanır")
    public void prepareCardCreationRequest() {
        RestAssured.baseURI = baseUrl;
    }

    @When("Birinci kart için POST isteği gönderilir")
    public void sendFirstCardCreationRequest() {
        Card card = new Card();
        card.setName("First Card");
        card.setDesc("Created by Rest-Assured");

        Response response = given()
                .queryParam("key", apiKey)
                .queryParam("token", token)
                .queryParam("idList", getFirstListId(boardId))
                .contentType("application/json")
                .body(gson.toJson(card))
                .post("/cards");

        response.then().statusCode(200);
        String cardId = response.jsonPath().getString("id");
        cardIds.add(cardId);
        System.out.println("First Card ID: " + cardId);
    }

    @When("İkinci kart için POST isteği gönderilir")
    public void sendSecondCardCreationRequest() {
        Card card = new Card();
        card.setName("Second Card");
        card.setDesc("Created by Rest-Assured");

        Response response = given()
                .queryParam("key", apiKey)
                .queryParam("token", token)
                .queryParam("idList", getFirstListId(boardId))
                .contentType("application/json")
                .body(gson.toJson(card))
                .post("/cards");

        response.then().statusCode(200);
        String cardId = response.jsonPath().getString("id");
        cardIds.add(cardId);
        System.out.println("Second Card ID: " + cardId);
    }

    @Given("Rastgele seçilen bir kart için güncelleme isteği hazırlanır")
    public void prepareCardUpdateRequest() {
        RestAssured.baseURI = baseUrl;
    }

    @When("Kart güncelleme isteği gönderilir")
    public void sendCardUpdateRequest() {
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

    @Given("Silinecek kartlar için DELETE isteği hazırlanır")
    public void prepareCardDeleteRequest() {
        RestAssured.baseURI = baseUrl;
    }

    @When("Kartlar için silme isteği gönderilir")
    public void sendCardDeleteRequests() {
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

    @Given("Silinecek board için DELETE isteği hazırlanır")
    public void prepareBoardDeleteRequest() {
        RestAssured.baseURI = baseUrl;
    }

    @When("Board silme isteği gönderilir")
    public void sendBoardDeleteRequest() {
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
