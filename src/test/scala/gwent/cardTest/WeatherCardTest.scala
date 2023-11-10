package cl.uchile.dcc
package gwent.cardTest

import munit.FunSuite
import gwent.cards._

import gwent.cardEffects.CardEffect

import scala.collection.mutable.ArrayBuffer

class WeatherCardTest extends FunSuite {
  val badClimateEffect: (ICard, ArrayBuffer[_ <: IUnitCard]) => Unit = (originCard, affectedCards) => {
    affectedCards.foreach(card => card.currentStrength = 1)
  }
  val niebla: CardEffect = new CardEffect("Efecto Niebla impenetrable",
    "Establece el valor de fuerza de todas las cartas de combate a distancia a 1.",
    badClimateEffect)


  test("name and description of WeatherCard are initialized properly") {
    val card1 = new WeatherCard("Frost", "desc", niebla, (false, true, false))
    assertEquals(card1.name, "Frost")
    assertEquals(card1.description, "desc")
  }

  test("Equals returns true for two WeatherCards with the same attributes") {
    val card1 = new WeatherCard("Frost", "desc", niebla, (false, true, false))
    val card2 = new WeatherCard("Frost", "desc", niebla, (false, true, false))
    assert(card1.equals(card2))
    assert(card2.equals(card1))
  }

  test("Equals returns false when comparing two WeatherCards with different attributes") {
    val card1 = new WeatherCard("Frost", "desc", niebla, (false, true, false))
    val card2 = new WeatherCard("Rain", "desc", niebla, (false, true, false))
    val card3 = new WeatherCard("Frost", "desc2", niebla, (false, true, false))
    assert(!card1.equals(card2))
    assert(!card1.equals(card3))
  }

  test("Equals returns false when comparing a WeatherCard with another type of Card") {
    val card1 = new WeatherCard("Frost", "desc", niebla, (false, true, false))
    val card2 = new RangedCard("Archer", "desc", 5)
    assert(!card1.equals(card2))
  }

  test("HashCode returns the same value for two WeatherCards with the same attributes") {
    val card1 = new WeatherCard("Frost", "desc", niebla, (false, true, false))
    val card2 = new WeatherCard("Frost", "desc", niebla, (false, true, false))
    assertEquals(card1.hashCode(), card2.hashCode())
  }
}

