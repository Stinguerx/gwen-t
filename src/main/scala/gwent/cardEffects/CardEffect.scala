package cl.uchile.dcc
package gwent.cardEffects

import gwent.cards.{ICard, UnitCard}


class CardEffect(protected val _name: String,
                 protected val _description: String,
                 val effect: (ICard, UnitCard) => Unit,
                 val selfEffect: Boolean) extends ICardEffect {

  def name: String = _name

  def description: String = _description

  def applyEffect(card: ICard, affectedCard: UnitCard): Unit = {
    effect(card, affectedCard)
  }
}
