package cl.uchile.dcc
package gwent.cards

/** Abstract class that defines the common attributes and behaviours of all unit cards in Gwent. */
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
    _currentStrength = newStrength.max(0)
  }

}