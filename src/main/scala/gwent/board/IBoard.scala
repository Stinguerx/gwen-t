package cl.uchile.dcc
package gwent.board

import gwent.cards._
import cl.uchile.dcc.gwent.player.Player

/**
 * Trait that contains the common methods of a board in the game Gwent.
 * */
trait IBoard {

  /** Calculates the strength of the cards of each player and returns who won.
   *
   *  @return Who won the round in integer form. A value of 1 represents player 1 won the
   *          round, -1 represents player 2 won the round, 0 represents a tie.
   */
  def calculateScore(): Int

  /** Clears the board by removing every card that's been played */
  def clearBoard(): Unit
}
