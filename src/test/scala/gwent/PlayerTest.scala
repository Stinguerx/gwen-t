package cl.uchile.dcc
package gwent

import munit.FunSuite
import gwent.cards._
import scala.collection.mutable.ArrayBuffer

class PlayerTest extends FunSuite {

  var testDeck: ArrayBuffer[Card] = ArrayBuffer(
    new MeleeCard("Card 1", 1),
    new MeleeCard("Card 2", 2),
    new RangedCard("Card 3", 3),
    new RangedCard("Card 4", 4),
    new SiegeCard("Card 5", 5),
    new SiegeCard("Card 6", 6),
    new WeatherCard("Card 7"),
    new MeleeCard("Card 1", 1),
    new MeleeCard("Card 2", 2),
    new RangedCard("Card 3", 3),
    new RangedCard("Card 4", 4),
    new SiegeCard("Card 5", 5),
    new SiegeCard("Card 6", 6),
    new WeatherCard("Card 7"),
    new MeleeCard("Card 1", 1),
    new MeleeCard("Card 2", 2),
    new RangedCard("Card 3", 3),
    new RangedCard("Card 4", 4),
    new SiegeCard("Card 5", 5),
    new SiegeCard("Card 6", 6),
    new WeatherCard("Card 7"),
    new MeleeCard("Card 1", 1),
    new MeleeCard("Card 2", 2),
    new RangedCard("Card 3", 3),
    new RangedCard("Card 4", 4)
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
    val originalDeck: ArrayBuffer[Card] = player.deck.clone()
    player.shuffleDeck()
    val shuffledDeck: ArrayBuffer[Card] = player.deck
    assertEquals(originalDeck.sortBy(_.name), shuffledDeck.sortBy(_.name))
  }

  test("drawCards() removes one card from the player's deck and puts it in it's hand") {
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
    val card = new MeleeCard("melee",3)
    assert(!player.equals(card))
  }

  test("HashCode returns the same value for players with the same name, gems, hand and deck") {
    val player1 = new Player("Player", testDeck)
    val player2 = new Player("Player", testDeck)
    assertEquals(player1.hashCode(), player2.hashCode())
  }
}