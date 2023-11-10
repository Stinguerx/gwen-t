package cl.uchile.dcc
package gwent.board

import gwent.cards.{MeleeCard, RangedCard, SiegeCard, WeatherCard}

import gwent.cardEffects.CardEffect

import scala.collection.mutable.ArrayBuffer


/**
 * Class that defines a section of the board in the Gwent game. Each player of the game has assigned a board section,
 * and each section has three zones, one for Melee type cards, one for Ranged type cards and one for Siege type cards.
 * Each section is in charge of storing and placing cards in the appropriate zone and apply its effects if needed.
 * */
class BoardSection {
  private var _MeleeZone: ArrayBuffer[MeleeCard] = ArrayBuffer.empty[MeleeCard]
  private var _RangedZone: ArrayBuffer[RangedCard] = ArrayBuffer.empty[RangedCard]
  private var _SiegeZone: ArrayBuffer[SiegeCard] = ArrayBuffer.empty[SiegeCard]

  /** @return The array of cards in the melee zone of the board */
  def MeleeZone: ArrayBuffer[MeleeCard] = _MeleeZone

  /** @return The array of cards in the ranged zone of the board */
  def RangedZone: ArrayBuffer[RangedCard] = _RangedZone

  /** @return The array of cards in the siege zone of the board */
  def SiegeZone: ArrayBuffer[SiegeCard] = _SiegeZone

  /** Method in charge of placing Melee type cards in their zone and applying their effects. */
  def addMeleeCard(card: MeleeCard): Unit = {
    _MeleeZone += card
    if (card.cardEffect.nonEmpty) {
      card.cardEffect.get.applyEffect(card, _MeleeZone)
    }
  }

  /** Method in charge of placing Ranged type cards in their zone applying their effects. */
  def addRangedCard(card: RangedCard): Unit = {
    _RangedZone += card
    if (card.cardEffect.nonEmpty) {
      card.cardEffect.get.applyEffect(card, _RangedZone)
    }
  }

  /** Method in charge of placing Siege type cards in their zone applying their effects. */
  def addSiegeCard(card: SiegeCard): Unit = {
    _SiegeZone += card
    if (card.cardEffect.nonEmpty) {
      card.cardEffect.get.applyEffect(card, _SiegeZone)
    }
  }

  /** Method called when a weather card is placed and its effect must be propagated to all cards. */
  def applyWeatherEffect(placedCard: WeatherCard): Unit = {
    val effect: CardEffect = placedCard.cardEffect
    if (placedCard.affectedCards._1) effect.applyEffect(placedCard, _MeleeZone)
    if (placedCard.affectedCards._2) effect.applyEffect(placedCard, _RangedZone)
    if (placedCard.affectedCards._3) effect.applyEffect(placedCard, _SiegeZone)
  }

  def calculateStrength(): Int = {
    val result: Int = _MeleeZone.map(_.currentStrength).sum +
                      _RangedZone.map(_.currentStrength).sum +
                      _SiegeZone.map(_.currentStrength).sum

    result
  }

   /** Empties every zone in the section. */
  def clear(): Unit = {
    _MeleeZone.clear()
    _RangedZone.clear()
    _SiegeZone.clear()
  }
}
