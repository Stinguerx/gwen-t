package cl.uchile.dcc
package gwent.board

import gwent.cards._
import gwent.Player

/**
 * Trait that contains the common methods of a board in the game Gwent.
 * */
trait IBoard {

  /**
   * Receives the card the player wants to play and puts it in the correct zone according to the section of the
   * board assigned to the player.
   *
   * @param player the player that wants to place a card.
   * @param card the card the player wants to place in the board.
   * */
  def placeCard(player: Player, card: ICard): Unit

  /** Clears the board by removing every card that's been played */
  def clearBoard(): Unit
}
