package cl.uchile.dcc
package gwent.cardTest

import munit.FunSuite
import gwent.cards._

class WeatherCardTest extends FunSuite {

  test("name and description of WeatherCard are initialized properly") {
    val card1 = new WeatherCard("Frost", "desc")
    assertEquals(card1.name, "Frost")
    assertEquals(card1.description, "desc")
  }

  test("Equals returns true for two WeatherCards with the same name and description") {
    val card1 = new WeatherCard("Frost", "desc")
    val card2 = new WeatherCard("Frost", "desc")
    assert(card1.equals(card2))
    assert(card2.equals(card1))
  }

  test("Equals returns false when comparing two WeatherCards with a different names or description") {
    val card1 = new WeatherCard("Frost", "desc")
    val card2 = new WeatherCard("Rain", "desc")
    val card3 = new WeatherCard("Frost", "desc2")
    assert(!card1.equals(card2))
    assert(!card1.equals(card3))
  }

  test("Equals returns false when comparing a WeatherCard with another type of Card") {
    val card1 = new WeatherCard("Frost", "desc")
    val card2 = new RangedCard("Archer", "desc", 5)
    assert(!card1.equals(card2))
  }

  test("HashCode returns the same value for two WeatherCards with the same name and description") {
    val card1 = new WeatherCard("Frost", "desc")
    val card2 = new WeatherCard("Frost", "desc")
    assertEquals(card1.hashCode(), card2.hashCode())
  }
}
