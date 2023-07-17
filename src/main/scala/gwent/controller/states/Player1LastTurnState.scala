package cl.uchile.dcc
package gwent.controller.states

import gwent.controller.GameController

/** State where player 1 can either play a card or pass the turn,
 *  after player 2 (the computer) has already passed its turn. */
class Player1LastTurnState(context: GameController) extends GameState(context) {

  override def playCard(card: Int): Unit = {
    context.Player1.playCard(card)
    context.promptPlayer()
  }

  override def passTurn(): Unit = {
    context.state = new EndRoundState(context)
    context.endRound()
  }
}
