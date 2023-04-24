package cl.uchile.dcc
package gwent.cardTests

import munit.FunSuite
import gwent.cards._

class SiegeCardTest extends FunSuite {

  test("name and strength of SiegeCard is initialized properly") {
    val card1 = new SiegeCard("Trebuchet", 5)
    assertEquals(card1.name, "Trebuchet")
    assertEquals(card1.strength, 5)
  }

  test("currentStrength of SiegeCard is initialized correctly") {
    val card = new SiegeCard("Trebuchet", 7)
    assertEquals(card.currentStrength, 7)
  }

  test("currentStrength setter of SiegeCard works correctly") {
    val card = new SiegeCard("Trebuchet", 5)
    card.currentStrength = 8
    assertEquals(card.currentStrength, 8)
  }

  test("currentStrength of SiegeCard does not go below zero") {
    val card = new SiegeCard("Trebuchet", 5)
    card.currentStrength = -10
    assertEquals(card.currentStrength, 0)
  }

  test("Equals returns true for two SiegeCard with the same name and strength") {
    val card1 = new SiegeCard("Trebuchet", 5)
    val card2 = new SiegeCard("Trebuchet", 5)
    assert(card1.equals(card2))
    assert(card2.equals(card1))
  }

  test("Equals returns false when comparing two SiegeCard with different names or strength") {
    val card1 = new SiegeCard("Trebuchet", 1)
    val card2 = new SiegeCard("Trebuchet", 5)
    val card3 = new SiegeCard("Ballista", 5)
    assert(!card1.equals(card2))
    assert(!card2.equals(card3))
    assert(!card1.equals(card3))
  }

  test("Equals returns false when comparing a SiegeCard with another type of Card") {
    val card1 = new SiegeCard("Archer", 5)
    val card2 = new WeatherCard("Frost")

    assert(!card1.equals(card2))
  }

  test("HashCode returns the same value for two SiegeCard with the same name and strength") {
    val card1 = new SiegeCard("Frost", 2)
    val card2 = new SiegeCard("Frost", 2)
    assertEquals(card1.hashCode(), card2.hashCode())
  }
}