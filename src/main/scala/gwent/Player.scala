package cl.uchile.dcc
package gwent

/** Trait that defines all attributes and behaviours of a player in Gwent*/
trait Player {
  /** @return the name of the player. */
  def getName: String

  /** @return the number of gems the player has. */
  def getGems: Int

  /** Removes one gem from the player */
  def removeGem(): Unit

  /** @return the list of cards in the player's hand. */
  def getHand: List[Card]

  /** @return the list of cards in the player's deck. */
  def getDeck: List[Card]

  /** Shuffles the player's deck. */
  def shuffleDeck(): Unit

  /** Plays a from the player's hand.
   *
   *  @param position the position of the card to play.
   */
  def playCard(position: Int): Unit

  /** Draws cards from the player's deck and adds them to their hand.
   *  @param number the number of cards to draw (default: 1).
   */
  def drawCard(number: int = 1): Unit
}

/** Class that implements the Player trait.
 *  The Player is initialized with a name, 2 gems and a deck of cards. The deck gets shuffled, then 10 cards are drawn
 *  from it and get put in the Player's hand.
 *
 *  @param name the name of the player.
 *  @param deck the initial deck of cards for the player.
 */
class GwentPlayer(name: String, deck: List[Card]) extends Player {
  private var gems: Int = 2
  shuffleDeck()
  drawCard(10)

  def getName: String = name

  def getGems: Int = gems

  def removeGem(): Unit = {
    gems -= 1
  }

  def getHand: List[Card] = hand

  def getDeck: List[Card] = deck

  def shuffleDeck(): Unit = {

  }

  def playCard(position: Int): Unit = {

  }

  def drawCard(number: int = 1): Unit = {

  }

}



