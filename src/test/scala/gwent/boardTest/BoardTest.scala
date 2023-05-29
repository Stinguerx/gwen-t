package cl.uchile.dcc
package gwent.boardTest

import gwent.board._
import gwent.cards._
import gwent.Player
import munit.FunSuite
import scala.collection.mutable.ArrayBuffer

class BoardTest extends FunSuite{

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

  var player1: Player = _
  var player2: Player = _
  var board: Board = _
  var section1: BoardSection = _
  var section2: BoardSection = _

  override def beforeEach(context: BeforeEach): Unit = {
    player1 = new Player("player1", testDeck)
    player2 = new Player("player2", testDeck)
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
    val weatherCard1 = player1.hand(4).asInstanceOf[WeatherCard]
    val weatherCard2 = player2.deck(13).asInstanceOf[WeatherCard]
    player1.playCard(5)
    assert(board.weather.get.equals(weatherCard1))
    player2.drawCards(15)
    player2.playCard(12)
    assert(board.weather.get.equals(weatherCard2))

  }

  test("The board places a MeleeCard in the correct zone when either player plays one") {
    val meleeCard1 = player1.hand(3).asInstanceOf[MeleeCard]
    val meleeCard2 = player2.hand(2).asInstanceOf[MeleeCard]
    player1.playCard(4)
    player2.playCard(3)
    assert(meleeCard1.equals(board.SectionA.MeleeZone(0)))
    assert(meleeCard2.equals(board.SectionB.MeleeZone(0)))
  }

  test("The board places a RangedCard in the correct zone when either player plays one") {
    val rangedCard1 = player1.hand(0).asInstanceOf[RangedCard]
    val rangedCard2 = player2.hand(1).asInstanceOf[RangedCard]
    player1.playCard(1)
    player2.playCard(2)
    assert(rangedCard1.equals(board.SectionA.RangedZone(0)))
    assert(rangedCard2.equals(board.SectionB.RangedZone(0)))
  }

  test("The board places a SiegeCard in the correct zone when either player plays one") {
    val siegeCard1 = player1.hand(5).asInstanceOf[SiegeCard]
    val siegeCard2 = player2.hand(6).asInstanceOf[SiegeCard]
    player1.playCard(6)
    player2.playCard(7)
    assert(siegeCard1.equals(board.SectionA.SiegeZone(0)))
    assert(siegeCard2.equals(board.SectionB.SiegeZone(0)))
  }

  test("clearBoard clears every card that's been played in the board") {
    val range = 1 to 10
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
