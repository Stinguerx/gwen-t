package cl.uchile.dcc
package gwent

import munit.FunSuite
import gwent.cards._

import cl.uchile.dcc.gwent.board.Board
import cl.uchile.dcc.gwent.cardEffects.CardEffect
import cl.uchile.dcc.gwent.player.{InvalidCardException, NoBoardAssignedException, Player}

import scala.collection.mutable.ArrayBuffer

class PlayerTest extends FunSuite {

  val badClimateEffect: (ICard, ArrayBuffer[_ <: IUnitCard]) => Unit = (originCard, affectedCards) => {
    affectedCards.foreach(card => card.currentStrength = 1)
  }

  val goodClimateEffect: (ICard, ArrayBuffer[_ <: IUnitCard]) => Unit = (originCard, affectedCards) => {
    affectedCards.foreach(card => card.currentStrength = card.strength)
  }

  val niebla: CardEffect = new CardEffect("Efecto Niebla impenetrable",
    "Establece el valor de fuerza de todas las cartas de combate a distancia a 1.",
    badClimateEffect)

  val lluvia: CardEffect = new CardEffect("Efecto lluvia torrencial",
    "Establece el valor de todas las cartas de asedio a 1.",
    badClimateEffect)

  val escarcha: CardEffect = new CardEffect("Efecto escarcha mordiente",
    "Establece el valor de fuerza de todas las cartas de combate cuerpo a cuerpo en 1.",
    badClimateEffect)

  val despejado: CardEffect = new CardEffect("Efecto clima despejado",
    "Establece el valor de fuerza de todas las cartas de combate cuerpo a cuerpo en 1.",
    goodClimateEffect)

  val refuerzoEffect: (ICard, ArrayBuffer[_ <: IUnitCard]) => Unit = (originCard, affectedCards) => {
    affectedCards.foreach(card => if (originCard != card) {
      card.currentStrength += 1
    })
  }

  val vinculoEffect: (ICard, ArrayBuffer[_ <: IUnitCard]) => Unit = (originCard, affectedCards) => {
    var check: Int = 0
    affectedCards.foreach(card => if (originCard.name == card.name) check += 1)

    if (check >= 2) {
      affectedCards.foreach(card => if (originCard.name == card.name) card.currentStrength *= 2)
    }
  }

  val refuerzo: CardEffect = new CardEffect("Refuerzo moral",
    "Cuando la carta entra en el campo, añade +1 a la fuerza de todas las cartas en su fila, excepto a si misma.",
    refuerzoEffect)

  val vinculo: CardEffect = new CardEffect("Vinculo estrecho",
    ": Si ya existe una carta con el mismo nombre en la fila, duplica la fuerza de esa(s) carta(s), incluyendose a sí misma",
    vinculoEffect)

  var testDeck: ArrayBuffer[ICard] = _
  var testDeck2: ArrayBuffer[ICard] = _

  override def beforeEach(context: BeforeEach): Unit = {
    testDeck = ArrayBuffer(
      new MeleeCard("Card 1", "desc1", 1),
      new MeleeCard("Card 2", "desc2", 2),
      new RangedCard("Card 3", "desc3", 3),
      new RangedCard("Card 4", "desc4", 4),
      new SiegeCard("Card 5", "desc5", 5),
      new SiegeCard("Card 6", "desc6", 6),
      new WeatherCard("Escarcha mordiente", "desc7", escarcha, (true, false, false)),
      new MeleeCard("Card 8", "desc8", 1),
      new MeleeCard("Card 9", "desc9", 2),
      new RangedCard("Card 10", "desc10", 3),
      new RangedCard("Card 11", "desc11", 4),
      new SiegeCard("Card 12", "desc12", 5),
      new SiegeCard("Card 13", "desc13", 6),
      new WeatherCard("Niebla impenetrable", "desc14", niebla, (false, true, false)),
      new MeleeCard("Card 15", "desc15", 1),
      new MeleeCard("Card 16", "desc16", 2),
      new RangedCard("Card 17", "desc17", 3),
      new RangedCard("Card 18", "desc18", 4),
      new SiegeCard("Card 19", "desc19", 5),
      new SiegeCard("Card 20", "desc20", 6),
      new WeatherCard("Clima despejado", "desc21", despejado, (true, true, true)),
      new MeleeCard("Card 22", "desc22", 1),
      new MeleeCard("Card 23", "desc23", 2),
      new RangedCard("Card 24", "desc24", 3),
      new RangedCard("Card 25", "desc25", 4)
    )
    testDeck2 = ArrayBuffer(
      new MeleeCard("Card 1", "desc1", 1),
      new MeleeCard("Card 2", "desc2", 2),
      new RangedCard("Card 3", "desc3", 3),
      new RangedCard("Card 4", "desc4", 4),
      new SiegeCard("Card 5", "desc5", 5),
      new SiegeCard("Card 6", "desc6", 6),
      new WeatherCard("Escarcha mordiente", "desc7", escarcha, (true, false, false)),
      new MeleeCard("Card 8", "desc8", 1),
      new MeleeCard("Card 9", "desc9", 2),
      new RangedCard("Card 10", "desc10", 3),
      new RangedCard("Card 11", "desc11", 4),
      new SiegeCard("Card 12", "desc12", 5),
      new SiegeCard("Card 13", "desc13", 6),
      new WeatherCard("Niebla impenetrable", "desc14", niebla, (false, true, false)),
      new MeleeCard("Card 15", "desc15", 1),
      new MeleeCard("Card 16", "desc16", 2),
      new RangedCard("Card 17", "desc17", 3),
      new RangedCard("Card 18", "desc18", 4),
      new SiegeCard("Card 19", "desc19", 5),
      new SiegeCard("Card 20", "desc20", 6),
      new WeatherCard("Clima despejado", "desc21", despejado, (true, true, true)),
      new MeleeCard("Card 22", "desc22", 1),
      new MeleeCard("Card 23", "desc23", 2),
      new RangedCard("Card 24", "desc24", 3),
      new RangedCard("Card 25", "desc25", 4)
    )
  }

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

  test("playCard throws an exception when trying to play a card without a board assigned") {
    val player1 = new Player("Player", testDeck)
    val exception = interceptMessage[NoBoardAssignedException]("No board has been assigned to the player") {
      player1.playCard(1)
    }
    assertEquals(exception.getMessage, "No board has been assigned to the player")
  }

  test("playCard throws an exception when trying to play a card with position out of bounds") {
    val player1 = new Player("Player", testDeck)
    val player2 = new Player("Player", testDeck2)
    val b: Board = new Board(player1, player2)
    val exception = interceptMessage[InvalidCardException]("The specified card does not exist.") {
      player1.playCard(100)
    }
    assertEquals(exception.getMessage, "The specified card does not exist.")
  }

  test("Equals returns true for players with the same attributes") {
    val player1 = new Player("Player", testDeck)
    val player2 = new Player("Player", testDeck2)
    assert(player1.equals(player2))
    assert(player2.equals(player1))
  }

  test("Equals returns false for players with different attributes") {
    val player1 = new Player("Player 1", testDeck)
    val player2 = new Player("Player 2", testDeck2)
    val player3 = new Player("Player 1", ArrayBuffer(
      new MeleeCard("Card 1", "desc1", 1),
      new MeleeCard("Card 2", "desc2", 2),
      new RangedCard("Card 3", "desc3", 3),
      new RangedCard("Card 4", "desc4", 4),
      new SiegeCard("Card 5", "desc5", 5),
      new SiegeCard("Card 6", "desc6", 6),
      new WeatherCard("Escarcha mordiente", "desc7", escarcha, (true, false, false)),
      new MeleeCard("Card 8", "desc8", 1),
      new MeleeCard("Card 9", "desc9", 2),
      new RangedCard("Card 10", "desc10", 3),
      new RangedCard("Card 11", "desc11", 4),
      new SiegeCard("Card 12", "desc12", 5),
      new SiegeCard("Card 13", "desc13", 6),
      new WeatherCard("Niebla impenetrable", "desc14", niebla, (false, true, false)),
      new MeleeCard("Card 15", "desc15", 1),
      new MeleeCard("Card 16", "desc16", 2),
      new RangedCard("Card 17", "desc17", 3),
      new RangedCard("Card 18", "desc18", 4),
      new SiegeCard("Card 19", "desc19", 5),
      new SiegeCard("Card 20", "desc20", 6),
      new WeatherCard("Clima despejado", "desc21", despejado, (true, true, true)),
      new MeleeCard("Card 22", "desc22", 1),
      new MeleeCard("Card 23", "desc23", 2),
      new RangedCard("Card 24", "desc24", 3),
      new RangedCard("Card 25", "desc25", 4)
    ))
    player3.drawCards()
    val player4 = new Player("Player 1", ArrayBuffer(
      new MeleeCard("Card 1", "desc1", 1),
      new MeleeCard("Card 2", "desc2", 2),
      new RangedCard("Card 3", "desc3", 3),
      new RangedCard("Card 4", "desc4", 4),
      new SiegeCard("Card 5", "desc5", 5),
      new SiegeCard("Card 6", "desc6", 6),
      new WeatherCard("Escarcha mordiente", "desc7", escarcha, (true, false, false)),
      new MeleeCard("Card 8", "desc8", 1),
      new MeleeCard("Card 9", "desc9", 2),
      new RangedCard("Card 10", "desc10", 3),
      new RangedCard("Card 11", "desc11", 4),
      new SiegeCard("Card 12", "desc12", 5),
      new SiegeCard("Card 13", "desc13", 6),
      new WeatherCard("Niebla impenetrable", "desc14", niebla, (false, true, false)),
      new MeleeCard("Card 15", "desc15", 1),
      new MeleeCard("Card 16", "desc16", 2),
      new RangedCard("Card 17", "desc17", 3),
      new RangedCard("Card 18", "desc18", 4),
      new SiegeCard("Card 19", "desc19", 5),
      new SiegeCard("Card 20", "desc20", 6),
      new WeatherCard("Clima despejado", "desc21", despejado, (true, true, true)),
      new MeleeCard("Card 22", "desc22", 1),
      new MeleeCard("Card 23", "desc23", 2),
      new RangedCard("Card 24", "desc24", 3),
      new RangedCard("Card 25", "desc25", 4)
    ))
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

  test("HashCode returns the same value for players with the same attributes") {
    val player1 = new Player("Player", testDeck)
    val player2 = new Player("Player", testDeck2)
    assertEquals(player1.hashCode(), player2.hashCode())
  }
}