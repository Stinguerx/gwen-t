package cl.uchile.dcc
package gwent

import scala.util.Random

/** Class that defines all attributes and behaviours of a player and is used to instantiate a player in Gwent.
 *  The player starts with 2 gems, its initial deck is shuffled, and then 10 cards are drawn from it to the player's hand
 *
 *  @param name The name of the player (type: String).
 *  @param deck The initial deck of cards for the player, a deck of 25 cards must be given (type: List[Card]).
 */
class Player(private val _name: String, private var _deck: List[Card]) {
  /** Player initialization */
  require(_deck.size == 25, "The deck given must have 25 cards")
  private var _gems: Int = 2
  private var _hand: List[Card] = List()
  shuffleDeck()
  drawCards(10)
  
  /** @return The name of the player. */
  def name: String = _name

  /** @return The number of gems the player has. */
  def gems: Int = _gems

  /** Removes one gem from the player. Used after losing a round. Does nothing if the player has 0 gems. */
  def removeGem(): Unit = {
    if (_gems > 0) {
      _gems -= 1
    }
  }

  /** @return The list of cards in the player's hand. */
  def hand: List[Card] = _hand

  /** @return the list of cards in the player's deck. */
  def deck: List[Card] = _deck

  /** Shuffles the player's deck. */
  def shuffleDeck(): Unit = {
    _deck = Random.shuffle(_deck)
  }

  /** Draws the specified number of cards from the player's deck and puts them in the player's hand.
   *  If the number specified is greater than the number of cards available in the player's deck, it draws the maximum
   *  number of cards it can before the deck becomes empty.
   *
   *  @param cards The number of cards to be drawn (type: Int, default = 1).
   * */
  def drawCards(cards: Int = 1): Unit = {
    for (_ <- 1 to cards) {
      if (_deck.nonEmpty) {
        val drawnCard: Card = _deck.last
        _deck = _deck.dropRight(1)
        _hand = _hand :+ drawnCard
      }
      else break
    }
  }
}


