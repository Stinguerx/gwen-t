package cl.uchile.dcc
package gwent.controller

import gwent.controller.states.{EndState, GameState, InitialState}
import gwent.view.UserInterface
import gwent.board.Board
import gwent.player.Player
import gwent.cards.WeatherCard

import scala.util.Random

/** Class in charge of managing an instance of the game Gwent
 * @param userInterface An instance of a user interface to use in the game
 * */
class GameController(val userInterface: UserInterface) {
  var state: GameState = new InitialState(this)
  private[controller] var Player1 :Player  = _
  private[controller] var Player2 :Player  = _
  private[controller] var board: Board = _
  private var _gameAlive: Boolean = true

  /** Creates a board and associates two players with the game controller,
   *  must be called to start the game.
   *  @param P1 The first player of the game
   *  @param P2 The second player of the game
   *  */
  def prepareMatch(P1: Player, P2: Player): Unit = {
    board = new Board(P1, P2)
    Player1 = P1
    Player2 = P2
    Player1.registerObserver(this)
    Player2.registerObserver(this)
  }

  /** @return If the game has finished or not */
  def gameAlive: Boolean = _gameAlive

  /** @return The list of players associated with the controller */
  def getPlayers: List[Player] = List(Player1, Player2)

  /** @return The board currently associated with the controller */
  def getBoard: Board = board

  /** Function called when the player wants to notify the controller of a change in its state
   * @param player the player that notifies the controller
   * */
  def update(player: Player): Unit = {
    _gameAlive = false
  }

  /** Method that needs to be called after prepareMatch() in order to start the game */
  def startGame(): Unit = {
    state.startMatch()
  }

  /** Method used for calling the userInterface to ask for user input */
  def promptPlayer(): Unit = {
    if (Player1.hand.isEmpty) {
      println("Passing turn because hand is empty")
      state.passTurn()
    }
    else {
      val input = userInterface.promptPlayer(Player1.hand.size)
      if (input == 0) {
        state.passTurn()
      }
      else {
        state.playCard(input)
      }
    }
  }

  /** Computer logic for deciding which card to play next */
  def promptComputer(): Unit = {
    if (Player2.hand.isEmpty) state.passTurn()
    else {
      // Computer is winning
      if (board.calculateScore() == -1) {
        state.playCard(Random.nextInt(Player2.hand.size) + 1)

      } else {  // Player is winning or there is a tie

        var chosenCard: Int  = -1
        for (idx <- Player2.hand.indices) {
          val element = Player2.hand(idx)
          element match {
            case _: WeatherCard => chosenCard = idx
            case _ =>
          }
        }

        if (chosenCard > -1) {
          state.playCard(chosenCard+1)
        } else {
          state.passTurn()
        }
      }
    }
  }

  /** Function that needs to be called to start a new round after both players finished
   *  a round and nobody lost all their gems */
  def startRound(): Unit = {
    state.startRound()
  }

  /** Function called when both players passed their turns and the score must be calculated */
  def endRound(): Unit = {
    state.calculateScores()
  }

  /** Function called when one or both players lost all their gems, thus the game has ended */
  def endGame(): Unit = {
    if (!_gameAlive) {
      state.endMatch()
    }
  }
}
