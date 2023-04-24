package cl.uchile.dcc
package gwent.cards

/** Abstract class that defines the common attributes of all cards in Gwent. */
abstract class Card(protected val _name: String) extends Equals{
  /** @return The name of the card.*/
  def name: String = _name

}

