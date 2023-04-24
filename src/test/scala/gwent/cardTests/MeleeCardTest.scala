package cl.uchile.dcc
package gwent.cardTests

import gwent.cards._

import munit.FunSuite

class MeleeCardTest extends FunSuite {

  test("name and strength of MeleeCard is initialized properly") {
    val card1 = new MeleeCard("Geralt", 5)
    assertEquals(card1.name, "Geralt")
    assertEquals(card1.strength, 5)
  }

  test("currentStrength of MeleeCard is initialized correctly") {
    val card = new MeleeCard("Ciri", 7)
    assertEquals(card.currentStrength, 7)
  }

  test("currentStrength setter of MeleeCard works correctly") {
    val card = new MeleeCard("Eskel", 5)
    card.currentStrength = 8
    assertEquals(card.currentStrength, 8)
  }

  test("currentStrength of MeleeCard does not go below zero") {
    val card = new MeleeCard("Eskel", 5)
    card.currentStrength = -10
    assertEquals(card.currentStrength, 0)
  }


  test("Equals returns true for two RangedCards with the same name and strength") {
    val card1 = new MeleeCard("Geralt", 5)
    val card2 = new MeleeCard("Geralt", 5)
    assert(card1.equals(card2))
    assert(card2.equals(card1))
  }

  test("Equals returns false when comparing two MeleeCards with different names or strength") {
    val card1 = new MeleeCard("Geralt", 1)
    val card2 = new MeleeCard("Geralt", 5)
    val card3 = new MeleeCard("Vesemir", 5)
    assert(!card1.equals(card2))
    assert(!card2.equals(card3))
    assert(!card1.equals(card3))
  }

  test("Equals returns false when comparing a MeleeCard with another type of Card") {
    val card1 = new MeleeCard("Geralt", 5)
    val card2 = new WeatherCard("Frost")
    assert(!card1.equals(card2))
  }

  test("HashCode returns the same value for two RangedCards with the same name and strength") {
    val card1 = new MeleeCard("Geralt", 5)
    val card2 = new MeleeCard("Geralt", 5)
    assertEquals(card1.hashCode(), card2.hashCode())
  }
}
