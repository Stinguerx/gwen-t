package cl.uchile.dcc
package gwent.controller

import gwent.controller.states.{GameState, InitialState}
import gwent.view.UserInterface

import gwent.Player

class GameController(val userInterface: UserInterface) {
  var state: GameState = new InitialState(this)
  private[controller] var Player1 :Player  = _
  private[controller] var Player2 :Player  = _

  /** Associates two players with the game controller, must be done to start the game
   *  @param P1 The first player of the game
   *  @param P2 The second player of the game
   *  */
  def addPlayers(P1: Player, P2: Player): Unit = {
    Player1 = P1
    Player2 = P2
  }

  /** @return The list of players associated with the controller */
  def getPlayers: List[Player] = List(Player1, Player2)

  /** Function called when the player wants to notify the controller of a change in its state
   * @param player the player that notifies the controller
   * */
  def update(player: Player): Unit = {
    if (player == Player1) {
      if (!player.alive) {
        println("Player 2 won")
      }
    }
    else if (player == Player2) {
      if (!player.alive) {
        println("Player 1 won")
      }
    }
  }

  def startGame(): Unit = {
    state.startMatch()
  }
  def promptPlayer(): Unit = {
    println("Select a card to play")
    val input = userInterface.promptPlayer()
    state.playCard(input)
  }

  /* Placeholder for computer logic */
  def promptComputer(): Unit = {
    val decision: Int = 1
    state.playCard(decision)
  }

  def endRound(): Unit = {
    state.calculateScores()
  }

}
