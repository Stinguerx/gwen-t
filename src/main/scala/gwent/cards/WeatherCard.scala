package cl.uchile.dcc
package gwent.cards

/** Class used for instantiating weather type cards in Gwent.
 *
 *  @param name The name of the card.
 *  */
class WeatherCard(name: String) extends Card(name) {
  override def canEqual(that: Any): Boolean = that.isInstanceOf[WeatherCard]

  override def equals(that: Any): Boolean = {
    if (canEqual(that)) {
      val other = that.asInstanceOf[WeatherCard]
      (this eq other) || (this.name == other.name)
    } else false
  }

  override def hashCode(): Int = {
    val prime = 31
    var result = 1
    result = prime * result + classOf[UnitCard].##
    result = prime * result + name.##
    result
  }
}
