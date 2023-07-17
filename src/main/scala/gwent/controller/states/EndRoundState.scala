package cl.uchile.dcc
package gwent.controller.states

import gwent.controller.GameController

/** State where its decided which player (if any) won the round. */
class EndRoundState(context: GameController) extends GameState(context) {
  override def calculateScores(): Unit = {
    val result: Int = context.board.calculateScore()
    if (result == 1) {              // Player won
      context.Player2.removeGem()
    } else if (result == -1) {      // Computer won
      context.Player1.removeGem()
    } else {                        // Tie
      context.Player1.removeGem()
      context.Player2.removeGem()
    }

    if (context.Player1.gems > 0 && context.Player2.gems > 0) {
      context.state = new StartRoundState(context)
      context.startRound()
    } else {
      context.state = new EndState(context)
      context.endGame()
    }
  }
}
