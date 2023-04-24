package cl.uchile.dcc
package gwent

/** Abstract class that defines the common attributes of all cards in Gwent. */
abstract class Card(protected val _name: String) {
  /** @return The name of the card.*/
  def name: String = _name
}


/** Abstract class that defines the common attributes of all unit cards in Gwent. */
abstract class UnitCard(name: String, protected val _strength: Int) extends Card(name) {
  private var _currentStrength: Int = _strength

  /** @return The default strength of the card. */
  def strength: Int = _strength

  /** @return The current strength of the card. */
  def currentStrength: Int = _currentStrength

  /** Sets the current strength of the card.
   *  @param newStrength The new strength of the card.
   * */
  def currentStrength_=(newStrength: Int): Unit = {
    _currentStrength = newStrength
  }
}


/** Class used for instantiating melee type cards in Gwent.
 *
 *  @param name The name of the card.
 *  @param strength The strength of the card.
 * */
class MeleeCard(name: String, strength: Int) extends UnitCard(name, strength) {
}


/** Class used for instantiating ranged type cards in Gwent.
 *
 *  @param name The name of the card.
 *  @param strength The strength of the card.
 *  */
class RangedCard(name: String, strength: Int) extends UnitCard(name, strength) {
}


/** Class used for instantiating siege type cards in Gwent.
 *
 *  @param name The name of the card.
 *  @param strength The strength of the card.
 *  */
class SiegeCard(name: String, strength: Int) extends UnitCard(name, strength) {
}


/** Class used for instantiating weather type cards in Gwent.
 *
 *  @param name The name of the card.
 *  */
class WeatherCard(name: String) extends Card(name) {
}
