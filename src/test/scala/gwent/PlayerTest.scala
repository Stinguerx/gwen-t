package cl.uchile.dcc
package gwent

import munit.FunSuite
import gwent.cards._
import scala.collection.mutable.ArrayBuffer

class PlayerTest extends FunSuite {

  var testDeck: ArrayBuffer[ICard] = ArrayBuffer(
    new MeleeCard("Card 1", "desc", 1),
    new MeleeCard("Card 2", "desc", 2),
    new RangedCard("Card 3", "desc", 3),
    new RangedCard("Card 4", "desc", 4),
    new SiegeCard("Card 5", "desc", 5),
    new SiegeCard("Card 6", "desc", 6),
    new WeatherCard("Card 7", "desc"),
    new MeleeCard("Card 8", "desc", 1),
    new MeleeCard("Card 9", "desc", 2),
    new RangedCard("Card 10", "desc", 3),
    new RangedCard("Card 11", "desc", 4),
    new SiegeCard("Card 12", "desc", 5),
    new SiegeCard("Card 13", "desc", 6),
    new WeatherCard("Card 14", "desc"),
    new MeleeCard("Card 15", "desc", 1),
    new MeleeCard("Card 16", "desc", 2),
    new RangedCard("Card 17", "desc", 3),
    new RangedCard("Card 18", "desc", 4),
    new SiegeCard("Card 19", "desc", 5),
    new SiegeCard("Card 20", "desc", 6),
    new WeatherCard("Card 21", "desc"),
    new MeleeCard("Card 22", "desc", 1),
    new MeleeCard("Card 23", "desc", 2),
    new RangedCard("Card 24", "desc", 3),
    new RangedCard("Card 25", "desc", 4)
  )

  test("Player initializes with the correct name and 2 gems") {
    val player = new Player("Player 1", testDeck)
    assertEquals(player.gems, 2)
    assertEquals(player.name, "Player 1")
  }

  test("Player initializes with a hand of 10 cards and a deck of 15 cards") {
    val player = new Player("Player 1", testDeck)
    assertEquals(player.hand.size, 10)
    assertEquals(player.deck.size, 15)
  }

  test("removeGem removes one gem from the player") {
    val player = new Player("Player 1", testDeck)
    player.removeGem()
    assertEquals(player.gems, 1)
  }

  test("removeGem doesn't set the player's gems below zero") {
    val player = new Player("Player 1", testDeck)
    player.removeGem()
    player.removeGem()
    player.removeGem()
    assertEquals(player.gems, 0)
  }

  test("shuffleDeck retains the same cards in the player's deck") {
    val player = new Player("Player 1", testDeck)
    val originalDeck: ArrayBuffer[ICard] = player.deck.clone()
    player.shuffleDeck()
    val shuffledDeck: ArrayBuffer[ICard] = player.deck
    assertEquals(originalDeck.sortBy(_.name), shuffledDeck.sortBy(_.name))
  }

  test("drawCards removes one card from the player's deck and puts it in it's hand") {
    val player = new Player("Player 1", testDeck)
    player.drawCards()
    assertEquals(player.deck.size, 14)
    assertEquals(player.hand.size, 11)
  }

  test("drawCards doesn't do anything if the player's deck is empty") {
    val player = new Player("Player 1", testDeck)
    player.drawCards(15)
    val deck1 = player.deck.size
    val hand1 = player.hand.size
    player.drawCards()
    val deck2 = player.deck.size
    val hand2 = player.hand.size
    assertEquals(deck1, deck2)
    assertEquals(hand1, hand2)
  }

  test("playCard throws an error when trying to play a card without a board assigned") {
    val player1 = new Player("Player", testDeck)
    val exception = interceptMessage[Error]("The player doesn't have a game board assigned.") {
      player1.playCard(0)
      throw new Error("The player doesn't have a game board assigned.")
    }
    assertEquals(exception.getMessage, "The player doesn't have a game board assigned.")
  }

  test("playCard throws an error when trying to play a card with position out of bounds") {
    val player1 = new Player("Player", testDeck)
    val exception = interceptMessage[Error]("The specified card does not exist.") {
      player1.playCard(100)
      throw new Error("The specified card does not exist.")
    }
    assertEquals(exception.getMessage, "The specified card does not exist.")
  }

  test("Equals returns true for players with the same name, gems, hand and deck") {
    val player1 = new Player("Player", testDeck)
    val player2 = new Player("Player", testDeck)
    assert(player1.equals(player2))
    assert(player2.equals(player1))
  }

  test("Equals returns false for players with different decks, hands, gems or name") {
    val player1 = new Player("Player 1", testDeck)
    val player2 = new Player("Player 2", testDeck)
    val player3 = new Player("Player 1", testDeck)
    player3.drawCards()
    val player4 = new Player("Player 1", testDeck)
    player4.removeGem()
    assert(!player1.equals(player2))
    assert(!player1.equals(player3))
    assert(!player1.equals(player4))
  }

  test("Equals return false when comparing a player with another class") {
    val player = new Player("Player 1", testDeck)
    val card = new MeleeCard("melee", "desc",3)
    assert(!player.equals(card))
  }

  test("HashCode returns the same value for players with the same name, gems, hand and deck") {
    val player1 = new Player("Player", testDeck)
    val player2 = new Player("Player", testDeck)
    assertEquals(player1.hashCode(), player2.hashCode())
  }
}