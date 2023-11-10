package cl.uchile.dcc
package gwent.cardTest

import gwent.cards._

import gwent.cardEffects.CardEffect
import munit.FunSuite

import scala.collection.mutable.ArrayBuffer

class MeleeCardTest extends FunSuite {

  test("name, description and strength of MeleeCard are initialized properly") {
    val card1 = new MeleeCard("Geralt", "desc", 5)
    assertEquals(card1.name, "Geralt")
    assertEquals(card1.description, "desc")
    assertEquals(card1.strength, 5)
  }

  test("currentStrength of MeleeCard is initialized correctly") {
    val card = new MeleeCard("Ciri", "desc", 7)
    assertEquals(card.currentStrength, 7)
  }

  test("currentStrength setter of MeleeCard works correctly") {
    val card = new MeleeCard("Eskel", "desc", 5)
    card.currentStrength = 8
    assertEquals(card.currentStrength, 8)
  }

  test("currentStrength of MeleeCard does not go below zero") {
    val card = new MeleeCard("Eskel", "desc", 5)
    card.currentStrength = -10
    assertEquals(card.currentStrength, 0)
  }


  test("Equals returns true for two RangedCards with the same name, strength and description") {
    val card1 = new MeleeCard("Geralt", "desc", 5)
    val card2 = new MeleeCard("Geralt", "desc", 5)
    assert(card1.equals(card2))
    assert(card2.equals(card1))
  }

  test("Equals returns false when comparing two MeleeCards with different names, strength or description") {
    val card1 = new MeleeCard("Geralt", "desc", 1)
    val card2 = new MeleeCard("Geralt", "desc", 5)
    val card3 = new MeleeCard("Vesemir", "desc", 5)
    val card4 = new MeleeCard("Vesemir", "desc2", 5)
    assert(!card1.equals(card2))
    assert(!card2.equals(card3))
    assert(!card1.equals(card3))
    assert(!card3.equals(card4))
  }

  test("Equals returns false when comparing a MeleeCard with another type of Card") {
    val card1 = new MeleeCard("Geralt", "desc", 5)
    val badClimateEffect: (ICard, ArrayBuffer[_ <: IUnitCard]) => Unit = (originCard, affectedCards) => {
      affectedCards.foreach(card => card.currentStrength = 1)
    }
    val niebla: CardEffect = new CardEffect("Efecto Niebla impenetrable",
      "Establece el valor de fuerza de todas las cartas de combate a distancia a 1.",
      badClimateEffect)
    val card2 = new WeatherCard("Niebla impenetrable", "desc", niebla, (false, true, false))
    assert(!card1.equals(card2))
  }

  test("HashCode returns the same value for two RangedCards with the same name, strength and description") {
    val card1 = new MeleeCard("Geralt", "desc", 5)
    val card2 = new MeleeCard("Geralt", "desc", 5)
    assertEquals(card1.hashCode(), card2.hashCode())
  }
}
