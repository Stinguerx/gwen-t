package cl.uchile.dcc
package gwent

import munit.FunSuite
import gwent.cards._
import gwent.cardEffects.CardEffect
import gwent.board.{Board, BoardSection}

import cl.uchile.dcc.gwent.player.Player

import scala.collection.mutable.ArrayBuffer

class effectsTest extends FunSuite {

  var player1: Player = _
  var player2: Player = _
  var section1: BoardSection = _
  var section2: BoardSection = _

  val badClimateEffect: (ICard, ArrayBuffer[_ <: IUnitCard]) => Unit = (originCard, affectedCards) => {
    affectedCards.foreach(card => card.currentStrength = 1)
  }

  val goodClimateEffect: (ICard, ArrayBuffer[_ <: IUnitCard]) => Unit = (originCard, affectedCards) => {
    affectedCards.foreach(card => card.currentStrength = card.strength)
  }

  val niebla: CardEffect = new CardEffect("Efecto Niebla impenetrable",
    "Establece el valor de fuerza de todas las cartas de combate a distancia a 1.",
    badClimateEffect)

  val lluvia: CardEffect = new CardEffect("Efecto lluvia torrencial",
    "Establece el valor de todas las cartas de asedio a 1.",
    badClimateEffect)

  val escarcha: CardEffect = new CardEffect("Efecto escarcha mordiente",
    "Establece el valor de fuerza de todas las cartas de combate cuerpo a cuerpo en 1.",
    badClimateEffect)

  val despejado: CardEffect = new CardEffect("Efecto clima despejado",
    "Establece el valor de fuerza de todas las cartas de combate cuerpo a cuerpo en 1.",
    goodClimateEffect)

  val refuerzoEffect: (ICard, ArrayBuffer[_ <: IUnitCard]) => Unit = (originCard, affectedCards) => {
    affectedCards.foreach(card => if (originCard != card) {
      card.currentStrength += 1
    })
  }

  val vinculoEffect: (ICard, ArrayBuffer[_ <: IUnitCard]) => Unit = (originCard, affectedCards) => {
    var check: Int = 0
    affectedCards.foreach(card => if (originCard.name == card.name) check += 1)

    if (check > 1) {
      affectedCards.foreach(card => if (originCard.name == card.name) card.currentStrength *= 2)
    }
  }

  val refuerzo: CardEffect = new CardEffect("Refuerzo moral",
    "Cuando la carta entra en el campo, añade +1 a la fuerza de todas las cartas en su fila, excepto a si misma.",
    refuerzoEffect)

  val vinculo: CardEffect = new CardEffect("Vinculo estrecho",
    ": Si ya existe una carta con el mismo nombre en la fila, duplica la fuerza de esa(s) carta(s), incluyendose a sí misma",
    vinculoEffect)

  var testDeck: ArrayBuffer[ICard] = _
  var testDeck2: ArrayBuffer[ICard] = _

  override def beforeEach(context: BeforeEach): Unit = {
    testDeck = ArrayBuffer(
      new MeleeCard("refuerzo test", "desc1", 1, Some(refuerzo)),
      new MeleeCard("Card 2", "desc2", 2),
      new RangedCard("Card 3", "desc3", 3),
      new RangedCard("Card 4", "desc4", 4),
      new SiegeCard("Card 5", "desc5", 5),
      new SiegeCard("Card 6", "desc6", 6),
      new WeatherCard("Escarcha mordiente", "desc7", escarcha, (true, false, false)),
      new MeleeCard("vinculo test", "desc8", 1),
      new MeleeCard("vinculo test", "desc9", 2, Some(vinculo)),
      new RangedCard("Card 10", "desc10", 3),
      new RangedCard("Card 11", "desc11", 4),
      new SiegeCard("Card 12", "desc12", 5),
      new SiegeCard("Card 13", "desc13", 6),
      new WeatherCard("Niebla impenetrable", "desc14", niebla, (false, true, false)),
      new MeleeCard("Card 15", "desc15", 1),
      new MeleeCard("Card 16", "desc16", 2),
      new RangedCard("Card 17", "desc17", 3),
      new RangedCard("Card 18", "desc18", 4),
      new SiegeCard("Card 19", "desc19", 5),
      new SiegeCard("Card 20", "desc20", 6),
      new WeatherCard("Clima despejado", "desc21", despejado, (true, true, true)),
      new MeleeCard("Card 22", "desc22", 1),
      new MeleeCard("Card 23", "desc23", 2),
      new RangedCard("Card 24", "desc24", 3),
      new RangedCard("Card 25", "desc25", 4)
    )
    testDeck2 = ArrayBuffer(
      new MeleeCard("Card 1", "desc1", 1),
      new MeleeCard("Card 2", "desc2", 2),
      new RangedCard("Card 3", "desc3", 3),
      new RangedCard("Card 4", "desc4", 4),
      new SiegeCard("Card 5", "desc5", 5),
      new SiegeCard("Card 6", "desc6", 6),
      new WeatherCard("Escarcha mordiente", "desc7", escarcha, (true, false, false)),
      new MeleeCard("Card 8", "desc8", 1),
      new MeleeCard("Card 9", "desc9", 2),
      new RangedCard("Card 10", "desc10", 3),
      new RangedCard("Card 11", "desc11", 4),
      new SiegeCard("Card 12", "desc12", 5),
      new SiegeCard("Card 13", "desc13", 6),
      new WeatherCard("Niebla impenetrable", "desc14", niebla, (false, true, false)),
      new MeleeCard("Card 15", "desc15", 1),
      new MeleeCard("Card 16", "desc16", 2),
      new RangedCard("Card 17", "desc17", 3),
      new RangedCard("Card 18", "desc18", 4),
      new SiegeCard("Card 19", "desc19", 5),
      new SiegeCard("Card 20", "desc20", 6),
      new WeatherCard("Clima despejado", "desc21", despejado, (true, true, true)),
      new MeleeCard("Card 22", "desc22", 1),
      new MeleeCard("Card 23", "desc23", 2),
      new RangedCard("Card 24", "desc24", 3),
      new RangedCard("Card 25", "desc25", 4)
    )
    player1 = new Player("player1", testDeck)
    player2 = new Player("player2", testDeck2)
    val board: Board = new Board(player1, player2)
    player1.drawCards(15)
    player2.drawCards(15)
    section1 = board.SectionA
    section2 = board.SectionB
  }

  test("A card with an effect gets correctly initialized") {
    val newCard: MeleeCard = new MeleeCard("Card 1", "desc", 1, Some(vinculo))
    assertEquals(newCard.cardEffect.get, vinculo)
  }

  test("The effect Refuerzo Moral gets correctly applied to the cards") {
    player1.playCard(2)
    val before: Int = section1.MeleeZone(0).currentStrength
    assertEquals(before, 2)
    player1.playCard(1)
    val after: Int = section1.MeleeZone(0).currentStrength
    assertEquals(after, before + 1)
  }

  test("The effect Vinculo estrecho works correctly") {
    val before1: Int = player1.hand(7).asInstanceOf[MeleeCard].currentStrength
    val before2: Int = player1.hand(8).asInstanceOf[MeleeCard].currentStrength
    player1.playCard(8)
    player1.playCard(8)
    val after1: Int = section1.MeleeZone(0).currentStrength
    val after2: Int = section1.MeleeZone(1).currentStrength
    assertEquals(after1, before1 * 2)
    assertEquals(after2, before2 * 2)
  }
}
