package cl.uchile.dcc
package gwent.cards

import gwent.board.{Board, BoardSection}
import gwent.cardEffects.CardEffect

import cl.uchile.dcc.gwent.player.Player

/** Class used for instantiating weather type cards in Gwent.
 *
 *  @param name The name of the card.
 *  @param description The description of the card
 *  @param cardEffect The effect of the card
 *  @param affectedCard The boolean 3-tuple that determines which cards are affected by the card effect,
 *                      ie: (Melee, Ranged, Siege)
 *  */
class WeatherCard(name: String,
                  description: String,
                  protected val _cardEffect: CardEffect,
                  private val _affectedCards: (Boolean, Boolean, Boolean)
                  ) extends Card(name, description) {

  /** @return Which cards does this weather card affect (Melee, Ranged, Siege) */
  def affectedCards: (Boolean, Boolean, Boolean) = _affectedCards

  /** @return The effect of the card. */
  def cardEffect: CardEffect = _cardEffect

  def accept(section: BoardSection, board: Board): Unit = {
    board.weather = this
  }

  override def canEqual(that: Any): Boolean = that.isInstanceOf[WeatherCard]

  override def equals(that: Any): Boolean = {
    if (canEqual(that)) {
      val other = that.asInstanceOf[WeatherCard]
      (this eq other) || ((this.name == other.name) && (this.description == other.description))
    } else false
  }

  override def hashCode(): Int = {
    val prime = 31
    var result = 1
    result = prime * result + classOf[UnitCard].##
    result = prime * result + name.##
    result = prime * result + description.##
    result
  }
}
