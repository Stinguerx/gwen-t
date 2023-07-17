package cl.uchile.dcc
package gwent.cardEffects

import gwent.cards.{ICard, IUnitCard}

import scala.collection.mutable.ArrayBuffer

/** Class that implements the ICardEffect trait
 *  @param name The name of the effect
 *  @param description The description of the effect
 *  @param effect The effect function that will applied to the cards
 *  @param affectSelf Whether or not the card that holds the effect should be affected
 * */
class CardEffect(protected val _name: String,
                 protected val _description: String,
                 val effect: (ICard, ArrayBuffer[_ <: IUnitCard]) => Unit,
                 ) extends ICardEffect {

  def name: String = _name

  def description: String = _description

  def applyEffect(originCard: ICard, targetCards: ArrayBuffer[_ <: IUnitCard]): Unit = {
    effect(originCard, targetCards)
  }
}
