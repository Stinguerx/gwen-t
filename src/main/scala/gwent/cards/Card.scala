package cl.uchile.dcc
package gwent.cards

import gwent.board.CardVisitor

import gwent.Player

/** Abstract class that defines the common attributes of all cards in Gwent. */
abstract class Card(protected val _name: String, protected val _description: String) extends Equals{
  /** @return The name of the card.*/
  def name: String = _name

  /** @return The description of the card */
  def description: String = _description

  /** Calls the appropriate method on the CardPlacer class according to the type of the card, in order to handle
   *  the placement of the card on the board.
   * */
  def accept(visitor: CardVisitor, player: Player): Unit

}

