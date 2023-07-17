package cl.uchile.dcc
package gwent.player

import gwent.board.{Board, BoardSection}
import gwent.cards.ICard
import gwent.controller.GameController

import scala.collection.mutable.ArrayBuffer
import scala.util.Random

/**
 * Class that implements the IPlayer trait. The player starts with 2 gems, its initial deck is shuffled, and then 10
 * cards are drawn from it to the player's hand. It also holds a reference to a game board and the section assigned to
 * the player that start as None. The player will hold a reference copy to the deck given as a parameter.
 *
 *  @param name The name of the player (type: String).
 *  @param deck The initial deck of cards for the player, a deck of 25 cards must be given (type: List[Card]).
 */
class Player(private val _name: String, private val _deck: ArrayBuffer[ICard]) extends IPlayer with Equals {

  /** Player initialization */
  require(_deck.size == 25, "The deck given must have 25 cards")
  private var _gems: Int = 2
  private val _hand: ArrayBuffer[ICard] = ArrayBuffer.empty[ICard]
  private var _assignedSection: Option[BoardSection] = None
  private var _board: Option[Board] = None
  private var observer: GameController = _
  private var _alive: Boolean = true
  shuffleDeck()
  drawCards(10)

  /** @return The name of the player. */
  def name: String = _name

  /** @return The assigned section of the board to the player. */
  def assignedSection: Option[BoardSection] = _assignedSection

  /** Assigns a board section to the player. */
  def assignedSection_=(section: BoardSection): Unit = {
    _assignedSection = Some(section)
  }

  /** @return The board the player is associated with. */
  def board: Option[Board] = _board

  /** Assigns a board to the player  */
  def board_=(board: Board): Unit = {
    _board = Some(board)
  }

  /** @return The number of gems the player has. */
  def gems: Int = _gems

  def alive: Boolean  = _alive

  /** Removes one gem from the player. Used after losing a round. Does nothing if the player has 0 gems. */
  def removeGem(): Unit = {
    if (_gems > 0) {
      _gems -= 1
    }
    if (gems == 0) {
      notifyObserver()
    }
  }

  /** @return The list of cards in the player's hand. */
  def hand: ArrayBuffer[ICard] = _hand

  /** @return The list of cards in the player's deck. */
  def deck: ArrayBuffer[ICard] = _deck

  def shuffleDeck(): Unit = {
    Random.shuffle(_deck)
  }

  def drawCards(cards: Int = 1): Unit = {
    val numCards = math.min(cards, 25 - _hand.size)
    for (_ <- 0 until numCards) {
      if (_deck.nonEmpty) {
        val drawnCard: ICard = _deck.head
        _deck.remove(0)
        _hand += drawnCard
      }
    }
  }

  def playCard(position: Int): Unit = {
    if (_board.isEmpty) {
      throw NoBoardAssignedException("No board has been assigned to the player")
    }
    else if (position > _hand.size || position < 0) {
      throw InvalidCardException("The specified card does not exist.")
    }
    else {
      val card: ICard = _hand(position - 1)
      _hand -= card
      card.accept(_assignedSection.get, _board.get)
    }
  }

  /** Registers a game controller as an observer of the player
   * @param newObserver The game controller
   *  */
  def registerObserver(newObserver: GameController): Unit = {
    observer = newObserver
  }

  /** Notifies the game controller when the player has lost all its gems. */
  private def notifyObserver(): Unit = {
    if (observer != null) {
      observer.update(this)
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


