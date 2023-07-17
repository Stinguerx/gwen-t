package cl.uchile.dcc
package gwent.cards

import gwent.cardEffects.CardEffect


/** Abstract class that defines the common attributes and behaviours of all unit cards in Gwent. */
abstract class UnitCard(name: String,
                        description: String,
                        protected val _strength: Int,
                        protected val _cardEffect: Option[CardEffect] = None)
                        extends Card(name, description) with IUnitCard {

  protected var _currentStrength: Int = _strength


  def strength: Int = _strength

  def currentStrength: Int = _currentStrength

  def currentStrength_=(newStrength: Int): Unit = {
    _currentStrength = newStrength.max(0)
  }


  def cardEffect: Option[CardEffect] = _cardEffect

}