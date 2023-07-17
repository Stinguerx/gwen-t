package cl.uchile.dcc
package gwent.cardEffects

import gwent.cards._

import scala.collection.mutable.ArrayBuffer

/** Trait exposing the common attributes and behaviour of an card effect in Gwent. */
trait ICardEffect {

  protected val _name: String

  protected val _description: String

  val effect: (ICard, ArrayBuffer[_ <: IUnitCard]) => Unit

  def applyEffect(originCard: ICard, targetCards: ArrayBuffer[_ <: IUnitCard]): Unit

}
