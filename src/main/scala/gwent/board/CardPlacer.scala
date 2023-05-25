package cl.uchile.dcc
package gwent.board


import gwent.cards._
import gwent.Player


/**
 * A CardVisitor implementation that places the cards on the board based on the assigned section of the player and
 * the type of card being played.
 * */
class CardPlacer extends CardVisitor {
  def visit(player: Player, card: MeleeCard): Unit = {
    val section: BoardSection = player.assignedSection.get
    section.placeCard(card)
  }

  def visit(player: Player, card: RangedCard): Unit = {
    val section: BoardSection = player.assignedSection.get
    section.placeCard(card)
  }

  def visit(player: Player, card: SiegeCard): Unit = {
    val section: BoardSection = player.assignedSection.get
    section.placeCard(card)
  }

  def visit(player: Player, card: WeatherCard): Unit = {
    val board = player.board.get
    board.weather = card
  }

}
