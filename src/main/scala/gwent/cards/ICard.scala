package cl.uchile.dcc
package gwent.cards

import gwent.Player
import gwent.board.CardVisitor

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

  /** Calls the appropriate method on the CardPlacer class according to the type of the card, in order to handle
   * the placement of the card on the board.
   */
  def accept(visitor: CardVisitor, player: Player): Unit

}
