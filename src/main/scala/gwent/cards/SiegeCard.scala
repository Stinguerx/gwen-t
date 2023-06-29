package cl.uchile.dcc
package gwent.cards

import gwent.board.CardVisitor
import gwent.Player

import gwent.cardEffects.CardEffect

/** Class used for instantiating siege type cards in Gwent.
 *
 *  @param name The name of the card.
 *  @param strength The strength of the card.
 *  */
class SiegeCard(name: String,
                description: String,
                strength: Int,
                cardEffect: Option[CardEffect] = None) extends UnitCard(name, description, strength, cardEffect) {

  def accept(visitor: CardVisitor, player: Player): Unit = {
    visitor.visit(player, this)
  }

  override def canEqual(that: Any): Boolean = that.isInstanceOf[SiegeCard]

  override def equals(that: Any): Boolean = {
    if (canEqual(that)) {
      val other = that.asInstanceOf[SiegeCard]
      (this eq other) || ((this.name == other.name) && (this.strength == other.strength) && (this.description == other.description))
    } else false
  }

  override def hashCode(): Int = {
    val prime = 31
    var result = 1
    result = prime * result + classOf[UnitCard].##
    result = prime * result + name.##
    result = prime * result + strength.##
    result = prime * result + description.##
    result
  }
}
