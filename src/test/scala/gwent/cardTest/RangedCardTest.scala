package cl.uchile.dcc
package gwent.cardTest

import gwent.cards._
import munit.FunSuite

class RangedCardTest extends FunSuite {

  test("name, description and strength of RangedCard are initialized properly") {
    val card1 = new RangedCard("CrossbowMan", "desc", 5)
    assertEquals(card1.name, "CrossbowMan")
    assertEquals(card1.description, "desc")
    assertEquals(card1.strength, 5)
  }

  test("currentStrength of RangedCard is initialized correctly") {
    val card = new RangedCard("CrossbowMan", "desc", 7)
    assertEquals(card.currentStrength, 7)
  }

  test("currentStrength setter of RangedCard works correctly") {
    val card = new RangedCard("CrossbowMan", "desc", 5)
    card.currentStrength = 8
    assertEquals(card.currentStrength, 8)
  }

  test("currentStrength of RangedCard does not go below zero") {
    val card = new RangedCard("CrossbowMan", "desc", 5)
    card.currentStrength = -10
    assertEquals(card.currentStrength, 0)
  }

  test("Equals returns true for two RangedCards with the same name, strength and description") {
    val card1 = new RangedCard("Archer", "desc", 5)
    val card2 = new RangedCard("Archer", "desc", 5)
    assert(card1.equals(card2))
    assert(card2.equals(card1))
  }

  test("Equals returns false when comparing two RangedCards with different names, strength or description") {
    val card1 = new RangedCard("Archer", "desc", 1)
    val card2 = new RangedCard("Archer", "desc", 5)
    val card3 = new RangedCard("CrossbowMan", "desc", 5)
    val card4 = new RangedCard("CrossbowMan", "desc2", 5)
    assert(!card1.equals(card2))
    assert(!card2.equals(card3))
    assert(!card1.equals(card3))
    assert(!card3.equals(card4))
  }

  test("Equals returns false when comparing a RangedCard with another type of Card") {
    val card1 = new RangedCard("Archer", "desc", 5)
    val card2 = new WeatherCard("Frost", "desc")
    assert(!card1.equals(card2))
  }

  test("HashCode returns the same value for two RangedCards with the same name, strength and description") {
    val card1 = new RangedCard("Frost", "desc", 2)
    val card2 = new RangedCard("Frost", "desc", 2)
    assertEquals(card1.hashCode(), card2.hashCode())
  }
}
