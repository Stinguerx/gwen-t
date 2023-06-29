package cl.uchile.dcc
package gwent.board

import gwent.cards.{MeleeCard, RangedCard, SiegeCard, WeatherCard}

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

  /** Method in charge of placing Melee type cards in their zone. */
  def placeCard(placedCard: MeleeCard): Unit = {
    if (placedCard.cardEffect.nonEmpty) {
      if (placedCard.cardEffect.get.selfEffect) {
        _MeleeZone += placedCard
        applyMeleeEffect(placedCard)
      } else {
        applyMeleeEffect(placedCard)
        _MeleeZone += placedCard
      }
    } else {
      _MeleeZone += placedCard
    }
  }

  /** Method in charge of placing Ranged type cards in their zone. */
  def placeCard(placedCard: RangedCard): Unit = {
    if (placedCard.cardEffect.nonEmpty) {
      if (placedCard.cardEffect.get.selfEffect) {
        _RangedZone += placedCard
        applyRangedEffect(placedCard)
      } else {
        applyRangedEffect(placedCard)
        _RangedZone += placedCard
      }
    } else {
      _RangedZone += placedCard
    }
  }

  /** Method in charge of placing Siege type cards in their zone. */
  def placeCard(placedCard: SiegeCard): Unit = {
    if (placedCard.cardEffect.nonEmpty) {
      if (placedCard.cardEffect.get.selfEffect) {
        _SiegeZone += placedCard
        applySiegeEffect(placedCard)
      } else {
        applySiegeEffect(placedCard)
        _SiegeZone += placedCard
      }
    } else {
      _SiegeZone += placedCard
    }
  }

  private def applyMeleeEffect(placedCard: MeleeCard): Unit = {
    for (elem <- _MeleeZone) {
      placedCard.cardEffect.get.applyEffect(placedCard, elem)
    }
  }

  private def applyRangedEffect(placedCard: RangedCard): Unit = {
    for (elem <- _RangedZone) {
      placedCard.cardEffect.get.applyEffect(placedCard, elem)
    }
  }

  private def applySiegeEffect(placedCard: SiegeCard): Unit = {
    for (elem <- _SiegeZone) {
      placedCard.cardEffect.get.applyEffect(placedCard, elem)
    }
  }

  def applyGlobalEffect(placedCard: WeatherCard): Unit = {
    for (elem <- _MeleeZone) {
      placedCard.cardEffect.get.applyEffect(placedCard, elem)
    }

    for (elem <- _RangedZone) {
      placedCard.cardEffect.get.applyEffect(placedCard, elem)
    }

    for (elem <- _SiegeZone) {
      placedCard.cardEffect.get.applyEffect(placedCard, elem)
    }
  }

   /** Empties every zone in the section. */
  def clear(): Unit = {
    _MeleeZone.clear()
    _RangedZone.clear()
    _SiegeZone.clear()
  }
}
