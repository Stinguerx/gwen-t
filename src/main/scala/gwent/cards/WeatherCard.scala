package cl.uchile.dcc
package gwent.cards

import gwent.board.CardVisitor

import gwent.Player

/** Class used for instantiating weather type cards in Gwent.
 *
 *  @param name The name of the card.
 *  */
class WeatherCard(name: String, description: String) extends Card(name, description) {

  def accept(visitor: CardVisitor, player: Player): Unit = {
    visitor.visit(player, this)
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
