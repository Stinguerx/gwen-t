package cl.uchile.dcc
package gwent

import gwent.cards._
import gwent.controller._
import gwent.view._

import java.io.{ByteArrayOutputStream, PrintStream}
import gwent.controller.states._
import gwent.cardEffects.CardEffect
import gwent.player.Player

import gwent.board.Board
import munit.FunSuite

import scala.collection.mutable.ArrayBuffer
class GameControllerTest extends FunSuite {
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
  var game: GameController = _
  val userInterface = new ConsoleInterface()

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
    player1 = new Player("player1", testDeck)
    player2 = new Player("player2", testDeck2)
    game = new GameController(userInterface)
  }

  test("Game Controller is well initialized") {
    assertEquals(game.userInterface, userInterface)
    game.prepareMatch(player1, player2)
    assert(game.getBoard.isInstanceOf[Board])
  }

  test("PrepareMatch works correctly") {
    game.prepareMatch(player1, player2)
    assertEquals(game.getPlayers, List(player1, player2))
  }

  test("The player notifies the controller when their gems reach zero") {
    game.prepareMatch(player1, player2)
    assert(game.gameAlive)
    player1.registerObserver(game)
    player1.removeGem()
    player1.removeGem()
    assert(!game.gameAlive)
  }

  test("Wrong transitions in the game controller trigger an exception") {
    game.prepareMatch(player1, player2)
    var exception = interceptMessage[InvalidActionException]("Pass Turn") {
      game.state.passTurn()
    }
    assertEquals(exception.getMessage, "Pass Turn")

    game.state = new Player1TurnState(game)
    exception = interceptMessage[InvalidActionException]("End Match") {
      game.state.endMatch()
    }
    assertEquals(exception.getMessage, "End Match")

    game.state = new Player2TurnState(game)
    exception = interceptMessage[InvalidActionException]("Start Match") {
      game.state.startMatch()
    }
    assertEquals(exception.getMessage, "Start Match")

    game.state = new Player1LastTurnState(game)
    exception = interceptMessage[InvalidActionException]("Calculate Scores") {
      game.state.calculateScores()
    }
    assertEquals(exception.getMessage, "Calculate Scores")

    game.state = new Player2LastTurnState(game)
    exception = interceptMessage[InvalidActionException]("Start Round") {
      game.state.startRound()
    }
    assertEquals(exception.getMessage, "Start Round")

    game.state = new Player2LastTurnState(game)
    exception = interceptMessage[InvalidActionException]("End Match") {
      game.state.endMatch()
    }
    assertEquals(exception.getMessage, "End Match")
  }

  test("The game starts and finishes works correctly") {
    // Since there is no user input implemented yet, the "player" plays a card or passes the turn at random.
    // This is why we test the game multiple times to see if it behaves correctly
    val testDeck3: ArrayBuffer[ICard] = ArrayBuffer(
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
    val testDeck4: ArrayBuffer[ICard] = ArrayBuffer(
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
    game.prepareMatch(player1, player2)
    game.startGame()
    assert(game.state.isInstanceOf[EndState])

    game.state = new InitialState(game)
    game.prepareMatch(new Player("player3", testDeck3), new Player("player4", testDeck4))
    game.startGame()
    assert(game.state.isInstanceOf[EndState])

  }

  test("EndRoundState works correctly") {
    game.prepareMatch(player1, player2)
    game.state = new EndRoundState(game)
    player1.removeGem()
    player2.removeGem()
    val expectedString = "End of game, its a tie."
    val outputStream = new ByteArrayOutputStream()
    val printStream = new PrintStream(outputStream)
    Console.withOut(printStream) {
      game.endRound()
    }
    val capturedOutput = outputStream.toString()
    assert(capturedOutput.contains(expectedString))
  }

  test("EndState works correctly") {
    game.prepareMatch(player1, player2)
    player1.removeGem()
    player1.removeGem()
    game.state = new EndState(game)
    val expectedString = "End of game, the computer won."
    val outputStream = new ByteArrayOutputStream()
    val printStream = new PrintStream(outputStream)
    Console.withOut(printStream) {
      game.endGame()
    }
    val capturedOutput = outputStream.toString()
    assert(capturedOutput.contains(expectedString))
  }
}
