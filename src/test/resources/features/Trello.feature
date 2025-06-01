Feature: Trello API Tests

  @Deneme
  Scenario: Create a Trello board
    Given The board creation request is prepared and sent successfully
    Then Two cards are created successfully on the board
    Then A randomly selected card is updated successfully
    Then All created cards are deleted successfully
    Then The created board is deleted successfully
