package cl.uchile.dcc
package gwent

import munit.FunSuite
import gwent.cards._
import gwent.cardEffects.CardEffect
import gwent.board.{Board, BoardSection}

import scala.collection.mutable.ArrayBuffer

class effectsTest extends FunSuite {

  var player1: Player = _
  var player2: Player = _
  var section1: BoardSection = _
  var section2: BoardSection = _
  val refuerzoMoral: (ICard, UnitCard) => Unit = (card: ICard, affectedCard: UnitCard) => {
    affectedCard.currentStrength = affectedCard.currentStrength + 1
  }
  val vinculoEstrecho: (ICard, UnitCard) => Unit = (card: ICard, affectedCard: UnitCard) => {
    if (card.name == affectedCard.name) {
      affectedCard.currentStrength = affectedCard.currentStrength * 2
    }
  }
  val efecto1: Option[CardEffect] = Some(new CardEffect("Refuerzo Moral", "Desc1", refuerzoMoral, false))
  var efecto2: Option[CardEffect] = Some(new CardEffect("Vinculo Estrecho", "Desc2", vinculoEstrecho, true))

  var testDeck: ArrayBuffer[ICard] = ArrayBuffer(
    new MeleeCard("Card 1", "desc", 1, efecto1),
    new MeleeCard("Card 2", "desc", 2, efecto2),
    new RangedCard("Card 3", "desc", 3, efecto1),
    new RangedCard("Card 4", "desc", 4, efecto2),
    new SiegeCard("Card 5", "desc", 5, efecto1),
    new SiegeCard("Card 6", "desc", 6, efecto2),
    new WeatherCard("Card 7", "desc"),
    new MeleeCard("Card 8", "desc", 1),
    new MeleeCard("Card 9", "desc", 2),
    new RangedCard("Card 10", "desc", 3),
    new RangedCard("Card 11", "desc", 4),
    new SiegeCard("Card 12", "desc", 5),
    new SiegeCard("Card 13", "desc", 6),
    new WeatherCard("Card 14", "desc"),
    new MeleeCard("Card 15", "desc", 1),
    new MeleeCard("Card 16", "desc", 2),
    new RangedCard("Card 17", "desc", 3),
    new RangedCard("Card 18", "desc", 4),
    new SiegeCard("Card 19", "desc", 5),
    new SiegeCard("Card 20", "desc", 6),
    new WeatherCard("Card 21", "desc"),
    new MeleeCard("Card 22", "desc", 1),
    new MeleeCard("Card 23", "desc", 2),
    new RangedCard("Card 24", "desc", 3),
    new RangedCard("Card 25", "desc", 4)
  )

  override def beforeEach(context: BeforeEach): Unit = {
    player1 = new Player("player1", testDeck)
    player2 = new Player("player2", testDeck)
    val board: Board = new Board(player1, player2)
    player1.drawCards(15)
    player2.drawCards(15)
    section1 = board.SectionA
    section2 = board.SectionB
  }

  test("A card with an effect gets correctly initialized") {
    val newCard: MeleeCard = new MeleeCard("Card 1", "desc", 1, efecto1)
    assertEquals(newCard.cardEffect.get, efecto1.get)
  }

  test("The effect RefuerzoMoral gets correctly applied to the cards") {
    println("testing")
    player1.playCard(18)
    val before: Int = section1.MeleeZone(0).currentStrength
    assertEquals(before, 1)
    player1.playCard(24)
    val after: Int = section1.MeleeZone(0).currentStrength
    assertEquals(after, before + 1)
  }
}
