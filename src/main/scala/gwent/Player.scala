package cl.uchile.dcc
package gwent

import cards.Card
import scala.collection.mutable.ArrayBuffer
import scala.util.Random

/** Class that defines all attributes and behaviours of a player and is used to instantiate a player in Gwent.
 *  The player starts with 2 gems, its initial deck is shuffled, and then 10 cards are drawn from it to the player's hand
 *
 *  @param name The name of the player (type: String).
 *  @param deck The initial deck of cards for the player, a deck of 25 cards must be given (type: List[Card]).
 */
class Player(private val _name: String, private val initialDeck: ArrayBuffer[Card]) extends Equals {
  /** Player initialization */
  require(initialDeck.size == 25, "The deck given must have 25 cards")
  private var _deck: ArrayBuffer[Card] = initialDeck.clone()
  private var _gems: Int = 2
  private var _hand: ArrayBuffer[Card] = ArrayBuffer.empty[Card]
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
  def hand: ArrayBuffer[Card] = _hand

  /** @return the list of cards in the player's deck. */
  def deck: ArrayBuffer[Card] = _deck

  /** Shuffles the player's deck. */
  def shuffleDeck(): Unit = {
    Random.shuffle(_deck)
  }

  /** Draws the specified number of cards from the player's deck and puts them in the player's hand.
   *  If the number specified is greater than the number of cards available in the player's deck, it draws the maximum
   *  number of cards it can before the deck becomes empty.
   *
   *  @param cards The number of cards to be drawn (type: Int, default = 1).
   * */
  def drawCards(cards: Int = 1): Unit = {
    val numCards = math.min(cards, 25 - _hand.size)
    for (_ <- 0 until numCards) {
      if (_deck.nonEmpty) {
        val drawnCard: Card = _deck.last
        _deck.dropRightInPlace(1)
        _hand += drawnCard
      }
    }
  }

  override def canEqual(that: Any): Boolean = that.isInstanceOf[Player]

  override def equals(that: Any): Boolean = {
    if (canEqual(that)) {
      val other = that.asInstanceOf[Player]
      (this eq other) || (
        this.name == other.name &&
          this.gems == other.gems &&
          this._deck == other._deck &&
          this.hand.toSet == other.hand.toSet
        )
    } else false

  }

  override def hashCode(): Int = {
    val prime = 31
    var result = 1
    result = prime * result + classOf[Player].##
    result = prime * result + name.##
    result = prime * result + _gems.##
    result = prime * result + _deck.##
    result = prime * result + _hand.toSet.##
    result
  }
}


