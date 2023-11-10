package cl.uchile.dcc
package gwent.controller.states

import gwent.controller.GameController

/** State where player 2 (the computer) can either play a card or pass the turn. */
class Player2TurnState(context: GameController) extends GameState(context) {

  override def playCard(card: Int): Unit = {
    context.Player2.playCard(card)
    context.state = new Player1TurnState(context)
    context.promptPlayer()
  }

  override def passTurn(): Unit = {
    context.state = new Player1LastTurnState(context)
    context.promptPlayer()
  }
}
