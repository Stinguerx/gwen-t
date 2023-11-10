package cl.uchile.dcc
package gwent.controller.states

import gwent.controller.GameController

/** State where player 2 (the computer) can either play a card or pass the turn,
 *  after player 1 has already passed its turn. */
class Player2LastTurnState(context: GameController) extends GameState(context) {

  override def playCard(card: Int): Unit = {
    context.Player2.playCard(card)
    context.promptComputer()
  }

  override def passTurn(): Unit = {
    context.state = new EndRoundState(context)
    context.endRound()
  }

}
