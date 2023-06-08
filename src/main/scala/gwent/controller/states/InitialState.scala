package cl.uchile.dcc
package gwent.controller.states

import gwent.controller.GameController

class InitialState(context: GameController) extends GameState(context) {

  override def startMatch(): Unit = {
    context.state = new Player1TurnState(context)
    context.promptPlayer()
  }
}
