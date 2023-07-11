package cl.uchile.dcc
package gwent

import gwent.cards._
import gwent.controller._
import gwent.view._
import java.io.{ByteArrayOutputStream, PrintStream}

import gwent.controller.states._
import munit.FunSuite

import scala.collection.mutable.ArrayBuffer
class GameControllerTest extends FunSuite {

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
  var game: GameController = _
  val userInterface = new ConsoleInterface()

  override def beforeEach(context: BeforeEach): Unit = {
    player1 = new Player("player1", testDeck)
    player2 = new Player("player2", testDeck)
    game = new GameController(userInterface)
  }

  test("Game Controller is well initialized") {
    assertEquals(game.userInterface, userInterface)
  }

  test("addPlayers works correctly") {
    game.addPlayers(player1, player2)
    assertEquals(game.getPlayers, List(player1, player2))
  }

  test("The player notifies the controller when their gems reach zero") {


    val outputStream = new ByteArrayOutputStream()
    Console.withOut(new PrintStream(outputStream)) {
      game.addPlayers(player1, player2)
      player1.registerObserver(game)
      player1.removeGem()
      player1.removeGem()
    }
    val printedOutput = outputStream.toString.trim
    val expectedOutput = "Player 2 won"
    assert(printedOutput.contains(expectedOutput))
  }

  test("startGame works correctly") {

  }

}
