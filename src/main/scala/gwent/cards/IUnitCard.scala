package cl.uchile.dcc
package gwent.cards

import gwent.cardEffects.CardEffect


/** Trait showing the basic properties and methods of a unit card in gwent*/
trait IUnitCard extends Card{

  /** The default strength of the card
   *  Immutable property */
  protected val _strength: Int

  /** The current strength of the card */
  protected var _currentStrength: Int

  /** The effect (if any) of the card */
  protected val _cardEffect: Option[CardEffect]

  /** @return The default strength of the card. */
  def strength: Int

  /** @return The current strength of the card. */
  def currentStrength: Int

  /** Sets the current strength of the card.
   *
   * @param newStrength The new strength of the card.
   * */
  def currentStrength_=(newStrength: Int): Unit

  /** @return The effect of the card, if any. */
  def cardEffect: Option[CardEffect]
}
