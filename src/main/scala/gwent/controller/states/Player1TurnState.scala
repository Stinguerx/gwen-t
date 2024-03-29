package cl.uchile.dcc
package gwent.controller.states

import gwent.controller.GameController

/** State where player 1 can either play a card or pass the turn. */
class Player1TurnState(context: GameController) extends GameState(context) {

  override def playCard(card: Int): Unit = {
    context.Player1.playCard(card)
    context.state = new Player2TurnState(context)
    context.promptComputer()
  }

  override def passTurn(): Unit = {
    context.state = new Player2LastTurnState(context)
    context.promptComputer()
  }
}
