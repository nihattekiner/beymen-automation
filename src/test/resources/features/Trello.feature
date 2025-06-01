Feature: Trello API Tests

  @Deneme
  Scenario: Create a Trello board
    Given The board creation request is prepared and sent successfully

  @Deneme
  Scenario: Add two cards to the board
    Given Two cards are created successfully on the board

  @Deneme
  Scenario: Update one randomly selected card
    Given A randomly selected card is updated successfully

  @Deneme
  Scenario: Delete created cards
    Given All created cards are deleted successfully

  @Deneme
  Scenario: Delete the created board
    Given The created board is deleted successfully
