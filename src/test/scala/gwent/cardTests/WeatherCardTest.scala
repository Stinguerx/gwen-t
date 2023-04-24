package cl.uchile.dcc
package gwent.cardTests

import munit.FunSuite
import gwent.cards._

class WeatherCardTest extends FunSuite {

  test("name of WeatherCard is initialized properly") {
    val card1 = new WeatherCard("Frost")
    assertEquals(card1.name, "Frost")
  }

  test("Equals returns true for two WeatherCards with the same name") {
    val card1 = new WeatherCard("Frost")
    val card2 = new WeatherCard("Frost")
    assert(card1.equals(card2))
    assert(card2.equals(card1))
  }

  test("Equals returns false when comparing a WeatherCard with another WeatherCard with a different name") {
    val card1 = new WeatherCard("Frost")
    val card2 = new WeatherCard("Rain")
    assert(!card1.equals(card2))
  }

  test("Equals returns false when comparing a WeatherCard with another type of Card") {
    val card1 = new WeatherCard("Frost")
    val card2 = new RangedCard("Archer", 5)
    assert(!card1.equals(card2))
  }

  test("HashCode returns the same value for two WeatherCards with the same name") {
    val card1 = new WeatherCard("Frost")
    val card2 = new WeatherCard("Frost")
    assertEquals(card1.hashCode(), card2.hashCode())
  }
}

