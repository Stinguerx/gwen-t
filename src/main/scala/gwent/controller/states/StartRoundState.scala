package cl.uchile.dcc
package gwent.controller.states

import gwent.controller.GameController

class StartRoundState(context: GameController) extends GameState(context) {

  override def startRound(): Unit = {
    context.Player1.drawCards(3)
    context.Player2.drawCards(3)
    context.state = new Player1TurnState(context)
  }

}
