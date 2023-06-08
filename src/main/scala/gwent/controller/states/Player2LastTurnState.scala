package cl.uchile.dcc
package gwent.controller.states

import gwent.controller.GameController

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
