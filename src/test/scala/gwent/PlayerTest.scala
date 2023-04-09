package cl.uchile.dcc
package gwent

import munit.FunSuite

class PlayerTest extends FunSuite {

  val card1 = new genericCard("unit1", "melee", 10)
  val card2 = new genericCard("unit2", "ranged", 5)
  val card3 = new genericCard("weather1", "weather", 0)
  val deck: List[genericCard] = List(card1, card2, card3)

  test("getName should return the player name") {
    val player = new GwentPlayer("Player1", deck)
    assertEquals(player.getName, "Player1")
  }

  test("getGems should return the number of gems, initially 2") {
    val player = new GwentPlayer("Player1", deck)
    assertEquals(player.getGems, 2)
  }

  test("removeGem should decrease the number of gems by 1") {
    val player = new GwentPlayer("Player1", deck)
    player.removeGem()
    assertEquals(player.getGems, 1)
  }

  test("getHand should return the list of cards in hand") {
    val player = new GwentPlayer("Player1", deck)
    assertEquals(player.getHand, List())
    player.drawCard(2)
    assertEquals(player.getHand, List(card1, card2))
  }

  test("getDeck should return the list of cards in the deck") {
    val player = new GwentPlayer("Player1", deck)
    assertEquals(player.getDeck, List(card1, card2, card3))
  }

  test("shuffleDeck should shuffle the deck") {
    val player = new GwentPlayer("Player 1", deck)
    player.shuffleDeck()

    assert(deck != player.getDeck)
  }

  test("playCard should remove the card from hand") {
    val player = new GwentPlayer("Player 1", deck)
    player.playCard(0)

    assert(player.getHand == List(card2, card3))
  }

  test("playCard should throw an exception if position is out of bounds") {
    val player = new GwentPlayer("Player 1", deck)
    intercept[IndexOutOfBoundsException] {
      player.playCard(5)
    }
  }

  test("playCard should throw an exception if hand is empty") {
    val player = new GwentPlayer("Player 1", deck)
    intercept[NoSuchElementException] {
      player.playCard(0)
    }
  }

  test("drawCard should add one card to the hand and remove one card from the deck") {
    assertEquals(player.getHand, List())
    player.drawCard()
    assertEquals(player.getHand.length, 1)
    assertEquals(player.getDeck.lenght, 2)
  }

  test("drawCard should add the specified number of cards to the hand") {
    assertEquals(player.getHand, List())
    player.drawCard(2)
    assertEquals(player.getHand.length, 2)
  }

  test("drawCard should throw an exception if deck is empty") {
    val player = new GwentPlayer("Player 1", List())
    intercept[NoSuchElementException] {
      player.drawCard()
    }
  }

  test("drawCard should throw an exception if drawing more cards than there are in the deck") {
    val player = new GwentPlayer("Player 1", List(card1))
    intercept[NoSuchElementException] {
      player.drawCard(2)
    }
  }
}
