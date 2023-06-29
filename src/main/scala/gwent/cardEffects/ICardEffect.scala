package cl.uchile.dcc
package gwent.cardEffects

import gwent.cards._


trait ICardEffect {

  protected val _name: String

  protected val _description: String

  val effect: (ICard, UnitCard) => Unit

  def applyEffect(card: ICard, affectedCard: UnitCard): Unit

}
