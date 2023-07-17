package cl.uchile.dcc
package gwent.cards

import gwent.board.{Board, BoardSection}

/** Trait representing a card in Gwent. It shows the basic properties of every card in the game.
 * */
trait ICard {

  /** The name of the card.
   * This is an immutable property.
   */
  protected val _name: String

  /** A description of the card's properties or effects.
   * This is an immutable property.
   */
  protected val _description: String

  /** @return The name of the card. */
  def name: String = _name

  /** @return The description of the card */
  def description: String = _description

  /** Handles the placement of the card by calling the appropriate method on the BoardSection
   *  or Board class according to the type of the card and the player that played it.
   */
  def accept(section: BoardSection, board: Board): Unit

}
