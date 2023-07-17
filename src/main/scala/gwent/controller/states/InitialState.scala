package cl.uchile.dcc
package gwent.controller.states

import gwent.controller.GameController

/** Initial state of the game, just after its created. */
class InitialState(context: GameController) extends GameState(context) {

  override def startMatch(): Unit = {
    context.state = new Player1TurnState(context)
    context.promptPlayer()
  }
}
