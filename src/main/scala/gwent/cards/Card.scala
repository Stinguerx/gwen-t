package cl.uchile.dcc
package gwent.cards


/** Abstract class that implements the ICard trait and defines the common attributes of all cards in Gwent. */
abstract class Card(protected val _name: String, protected val _description: String) extends ICard with Equals{

}

