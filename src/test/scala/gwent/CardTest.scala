package cl.uchile.dcc
package gwent

import munit.FunSuite

class CardTest extends FunSuite {
  test("genericCard should have correct name") {
    val card = new genericCard("Archer", "Unit", 5, None)
    assertEquals(card.getName, "Archer")
  }

  test("genericCard should have correct classification") {
    val card = new genericCard("Archer", "Unit", 5, None)
    assertEquals(card.getClassification, "Unit")
  }

  test("genericCard should have correct original strength") {
    val card = new genericCard("Archer", "Unit", 5, None)
    assertEquals(card.getOriginalStrength, 5)
  }

  test("genericCard should have correct current strength") {
    val card = new genericCard("Archer", "Unit", 5, None)
    assertEquals(card.getCurrentStrength, 5)
  }

  test("genericCard strength should be set correctly") {
    val card = new genericCard("Archer", "Unit", 5, None)
    card.setStrength(3)
    assertEquals(card.getCurrentStrength, 3)
  }

  test("genericCard should have correct effect") {
    val card = new genericCard("Frost", "Weather", 0, Some("Decrease the strength of all Melee units by 2"))
    assertEquals(card.getEffect, Some("Decrease the strength of all Melee units by 2"))
  }

  test("genericCard should have no effect if not specified") {
    val card = new genericCard("Archer", "Unit", 5)
    assertEquals(card.getEffect, None)
  }
}
