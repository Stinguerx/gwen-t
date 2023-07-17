package cl.uchile.dcc
package gwent.boardTest

import gwent.board._
import gwent.cards._

import cl.uchile.dcc.gwent.cardEffects.CardEffect
import cl.uchile.dcc.gwent.player.Player
import munit.FunSuite

import scala.collection.mutable.ArrayBuffer

class BoardTest extends FunSuite{

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

  var player1: Player = _
  var player2: Player = _
  var board: Board = _
  var section1: BoardSection = _
  var section2: BoardSection = _

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
      new RangedCard("Card 4", "desc4", 4, Some(vinculo)),
      new SiegeCard("Card 5", "desc5", 5),
      new SiegeCard("Card 6", "desc6", 6, Some(vinculo)),
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
    player1 = new Player("player1", testDeck)
    player2 = new Player("player2", testDeck2)
    board = new Board(player1, player2)
    section1 = board.SectionA
    section2 = board.SectionB
  }


  test("The board's battlefield is empty upon initialization") {
    assert(section1.MeleeZone.isEmpty)
    assert(section1.RangedZone.isEmpty)
    assert(section1.SiegeZone.isEmpty)
    assert(section2.MeleeZone.isEmpty)
    assert(section2.RangedZone.isEmpty)
    assert(section2.SiegeZone.isEmpty)
    assert(board.weather.isEmpty)
  }

  test("The board correctly assigned one section to each player") {
    assert(player1.assignedSection.isDefined)
    assertEquals(player1.assignedSection.get, section1)
    assert(player2.assignedSection.isDefined)
    assertEquals(player2.assignedSection.get, section2)
  }

  test("The players contain a reference to the board after it's initialized") {
    assert(player1.board.isDefined)
    assertEquals(player1.board.get, board)
    assert(player2.board.isDefined)
    assertEquals(player2.board.get, board)
  }

  test("The board places a WeatherCard in the correct zone when either player plays one") {
    val weatherCard1 = player1.hand(6).asInstanceOf[WeatherCard]
    val weatherCard2 = player2.deck(10).asInstanceOf[WeatherCard]
    player1.playCard(7)
    assert(board.weather.get.equals(weatherCard1))
    player2.drawCards(15)
    player2.playCard(21)
    assert(board.weather.get.equals(weatherCard2))

  }

  test("The board places a MeleeCard in the correct zone when either player plays one") {
    val meleeCard1 = player1.hand(0).asInstanceOf[MeleeCard]
    val meleeCard2 = player2.hand(1).asInstanceOf[MeleeCard]
    player1.playCard(1)
    player2.playCard(2)
    assert(meleeCard1.equals(board.SectionA.MeleeZone(0)))
    assert(meleeCard2.equals(board.SectionB.MeleeZone(0)))
  }

  test("The board places a RangedCard in the correct zone when either player plays one") {
    val rangedCard1 = player1.hand(2).asInstanceOf[RangedCard]
    val rangedCard2 = player2.hand(3).asInstanceOf[RangedCard]
    player1.playCard(3)
    player2.playCard(4)
    assert(rangedCard1.equals(board.SectionA.RangedZone(0)))
    assert(rangedCard2.equals(board.SectionB.RangedZone(0)))
  }

  test("The board places a SiegeCard in the correct zone when either player plays one") {
    val siegeCard1 = player1.hand(4).asInstanceOf[SiegeCard]
    val siegeCard2 = player2.hand(5).asInstanceOf[SiegeCard]
    player1.playCard(5)
    player2.playCard(6)
    assert(siegeCard1.equals(board.SectionA.SiegeZone(0)))
    assert(siegeCard2.equals(board.SectionB.SiegeZone(0)))
  }

  test("clearBoard clears every card that's been played in the board") {
    val range = 10 to 1
    range.foreach(player1.playCard)
    range.foreach(player2.playCard)
    board.clearBoard()
    assert(section1.MeleeZone.isEmpty)
    assert(section1.RangedZone.isEmpty)
    assert(section1.SiegeZone.isEmpty)
    assert(section2.MeleeZone.isEmpty)
    assert(section2.RangedZone.isEmpty)
    assert(section2.SiegeZone.isEmpty)
    assert(board.weather.isEmpty)
  }
}
