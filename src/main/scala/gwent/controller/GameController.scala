package cl.uchile.dcc
package gwent.controller

import gwent.controller.states.{GameState, InitialState}
import gwent.view.UserInterface

import gwent.Player

class GameController(val userInterface: UserInterface) {
  var state: GameState = new InitialState(this)
  private[controller] var Player1 :Player  = _
  private[controller] var Player2 :Player  = _

  def addPlayers(P1: Player, P2: Player): Unit = {
    Player1 = P1
    Player2 = P2
  }

  def getPlayers: List[Player] = List(Player1, Player2)

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
