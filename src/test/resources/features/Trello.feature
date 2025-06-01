Feature: Trello API Tests

  Scenario: Managing a board and cards on Trello
    Given The user sets up the Trello API key and token
    When The user creates a new board
    And The user creates two new cards on the board
    And The user randomly updates one of the cards
    And The user deletes all created cards
    Then The user deletes the created board
