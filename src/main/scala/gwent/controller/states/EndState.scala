package cl.uchile.dcc
package gwent.controller.states

import gwent.controller.GameController

/** End State where the game ends.  */
class EndState(context: GameController) extends GameState(context) {

  override def endMatch(): Unit = {
    if (context.Player1.gems == 0 && context.Player2.gems == 0) {
      println("End of game, its a tie.")
    } else if (context.Player1.gems == 0) {
      println("End of game, the computer won.")
    } else {
      println("End of game, the player won.")
    }
  }
}
