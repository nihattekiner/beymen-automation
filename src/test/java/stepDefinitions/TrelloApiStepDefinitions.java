package stepDefinitions;

import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import pojo.Board;
import pojo.Card;
import com.google.gson.Gson;
import utils.ConfigReader;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

import static io.restassured.RestAssured.given;

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
        board.setName("Test Board");
        board.setDesc("Created by Rest-Assured and POJO");

        Response boardResponse = given().contentType("application/json")
                .queryParam("key", apiKey)
                .queryParam("token", token)
                .log().all()
                .body("{\"name\":\"Test Board 1748811466749\",\"desc\":\"Created by Rest-Assured and POJO\"}").when()
                .post("https://api.trello.com/1/boards");

        boardResponse.then().statusCode(200).log().all();
        String boardId = boardResponse.jsonPath().getString("id");
        System.out.println("Created Board ID: " + boardId);
        assert boardId != null;

        Properties properties = new Properties();
        String filePath = "src/test/resources/Config.properties";
        try (FileInputStream inputStream = new FileInputStream(filePath)) {
            properties.load(inputStream);
        } catch (IOException e) {
            System.out.println("Config.properties not found or empty, starting fresh.");
        }

        properties.setProperty("board.id", boardId);

        Response listResponse = given()
                .queryParam("key", apiKey)
                .queryParam("token", token)
                .log().all()
                .get("/boards/" + boardId + "/lists");

        listResponse.then().statusCode(200).log().all();
        List<Map<String, String>> lists = listResponse.jsonPath().getList("$");

        for (int i = 0; i < lists.size(); i++) {
            Map<String, String> list = lists.get(i);
            properties.setProperty("list" + (i + 1) + ".id", list.get("id"));
            properties.setProperty("list" + (i + 1) + ".name", list.get("name"));
        }

        try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
            properties.store(outputStream, "Updated board and list IDs");
            System.out.println("Board and list IDs updated in Config.properties");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Then("Two cards are created successfully on the board")
    public void twoCardsAreCreatedSuccessfullyOnTheBoard() {
        String apiKey = ConfigReader.get("apiKey");
        String token = ConfigReader.get("token");
        String list1Id = ConfigReader.get("list1.id");
        String list2Id = ConfigReader.get("list2.id");
        String baseUrl = ConfigReader.get("baseUrl");

        RestAssured.baseURI = baseUrl;

        Properties properties = new Properties();
        String filePath = "src/test/resources/Config.properties";
        try (FileInputStream inputStream = new FileInputStream(filePath)) {
            properties.load(inputStream);
        } catch (IOException e) {
            System.out.println("Config.properties not found or empty, starting fresh.");
        }

        System.out.println(apiKey);
        System.out.println(token);
        System.out.println(list1Id);
        System.out.println(list2Id);

// First card creation
        Response firstCardResponse = given()
                .queryParam("idList", list1Id)  // Doğru parametre
                .queryParam("key", apiKey)
                .queryParam("token", token)
                .queryParam("name", "FirstCard")
                .queryParam("desc", "Card1")
                .log().all()
                .when()
                .post("/cards")  // Doğru endpoint
                .then()
                .statusCode(200)
                .log().all()
                .extract().response();

        String firstCardId = firstCardResponse.jsonPath().getString("id");
        properties.setProperty("card1.id", firstCardId);

// Second card creation
        Response secondCardResponse = given()
                .queryParam("idList", list2Id)  // Doğru parametre
                .queryParam("key", apiKey)
                .queryParam("token", token)
                .queryParam("name", "SecondCard")
                .queryParam("desc", "Card2")
                .log().all()
                .when()
                .post("/cards")  // Doğru endpoint
                .then()
                .statusCode(200)
                .log().all()
                .extract().response();

        String secondCardId = secondCardResponse.jsonPath().getString("id");
        properties.setProperty("card2.id", secondCardId);

        try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
            properties.store(outputStream, "Updated card IDs");
            System.out.println("Card IDs updated in Config.properties");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Then("A randomly selected card is updated successfully")
    public void aRandomlySelectedCardIsUpdatedSuccessfully() {
        // Rastgele kart ID'si seçimi için doğru ConfigReader key'lerini kullan
        List<String> cardIds = new ArrayList<>();
        cardIds.add(ConfigReader.get("card1.id"));  // Örnek key (kart ID'si)
        cardIds.add(ConfigReader.get("card2.id"));  // Örnek key (kart ID'si)

    // Rastgele kart ID'si seç
        String randomCardId = cardIds.get(new Random().nextInt(cardIds.size()));
        System.out.println("Updating card ID: " + randomCardId);

        RestAssured.baseURI = baseUrl;

    // JSON Body
        Map<String, String> body = new HashMap<>();
        body.put("name", "Updated Card " + System.currentTimeMillis());
        body.put("desc", "Card updated via API");

// PUT isteği
        Response updateResponse = given()
                .contentType("application/json")
                .queryParam("key", apiKey)
                .queryParam("token", token)
                .body(body)
                .log().all()
                .put("/cards/" + randomCardId);

// Yanıt kontrolü
        updateResponse.then().statusCode(200).log().all();
        System.out.println("Card updated successfully: " + randomCardId);

    }

    @Then("All created cards are deleted successfully")
    public void allCreatedCardsAreDeletedSuccessfully() {
        List<String> cardIds = new ArrayList<>();
        cardIds.add(ConfigReader.get("card1.id"));
        cardIds.add(ConfigReader.get("card2.id"));

        RestAssured.baseURI = baseUrl;

        for (String cardId : cardIds) {
            if (cardId != null && !cardId.isEmpty()) {
                Response deleteResponse = given()
                        .contentType("application/json")
                        .queryParam("key", apiKey)
                        .queryParam("token", token)
                        .log().all()
                        .delete("/cards/" + cardId);

                deleteResponse.then().statusCode(200).log().all();
                System.out.println("Deleted card ID: " + cardId);
            } else {
                System.out.println("Card ID boş veya null, atlanıyor.");
            }
        }

// İsteğe bağlı: Silinen kart ID'lerini Config.properties içinden temizleyelim
        Properties properties = new Properties();
        String filePath = "src/test/resources/Config.properties";
        try (FileInputStream inputStream = new FileInputStream(filePath)) {
            properties.load(inputStream);
            properties.setProperty("card1.id", "");
            properties.setProperty("card2.id", "");
            try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
                properties.store(outputStream, "Card IDs cleared after deletion");
                System.out.println("Card IDs temizlendi.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

        @Then("The created board is deleted successfully")
    public void theCreatedBoardIsDeletedSuccessfully() {
        String boardId = ConfigReader.get("board.id");

        RestAssured.baseURI = baseUrl;

        Response deleteResponse = given().contentType("application/json")
                .queryParam("key", apiKey)
                .queryParam("token", token)
                .log().all()
                .delete("/boards/" + boardId);

        deleteResponse.then().statusCode(200).log().all();
        System.out.println("Deleted board ID: " + boardId);
    }
}
