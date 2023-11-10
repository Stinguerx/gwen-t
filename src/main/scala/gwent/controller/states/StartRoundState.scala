package cl.uchile.dcc
package gwent.controller.states

import gwent.controller.GameController

/** State for starting the second and following rounds. */
class StartRoundState(context: GameController) extends GameState(context) {

  override def startRound(): Unit = {
    context.board.clearBoard()
    context.Player1.shuffleDeck()
    context.Player2.shuffleDeck()
    context.Player1.drawCards(3)
    context.Player2.drawCards(3)
    context.state = new Player1TurnState(context)
    context.promptPlayer()
  }
}
