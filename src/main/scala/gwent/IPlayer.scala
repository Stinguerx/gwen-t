package cl.uchile.dcc
package gwent


/** Trait that defines all the common behaviours of a player in the game Gwent */
trait IPlayer {

  /**
   * Draws the specified number of cards from the player's deck and puts them in the player's hand.
   * If the number specified is greater than the number of cards available in the player's deck, it draws the maximum
   * number of cards it can before the deck becomes empty.
   *
   * @param cards The number of cards to be drawn (type: Int, default = 1).
   * */
  def drawCards(cards: Int) : Unit

  /**
   * Plays a card from the player's hand into the game board, according to the card type.
   * Throws an error if the player doesn't have a board assigned to it, or the card in the position specified does not
   * exist.
   *
   * @param position The position of the card to be played in the player's hand.
   * */
  def playCard(position: Int) : Unit

  /** Shuffles the player's deck. */
  def shuffleDeck(): Unit
}
