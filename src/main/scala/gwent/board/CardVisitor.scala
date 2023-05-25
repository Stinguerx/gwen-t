package cl.uchile.dcc
package gwent.board

import gwent.cards._
import gwent.board.BoardSection

import gwent.Player

/** Trait that represents a visitor for different types of cards.*/
trait CardVisitor {

  /**
   * Visits a MeleeCard and places it in the appropriate place in the board.
   *
   * @param player the player who plays the card
   * @param card   the MeleeCard being visited
   */
  def visit(player: Player, card: MeleeCard): Unit

  /**
   * Visits a RangedCard and places it in the appropriate place in the board.
   *
   * @param player the player who plays the card
   * @param card   the RangedCard being visited
   */
  def visit(player: Player, card: RangedCard): Unit


  /**
   * Visits a SiegeCard and places it in the appropriate place in the board.
   *
   * @param player the player who plays the card
   * @param card   the SiegeCard being visited
   */
  def visit(player: Player, card: SiegeCard): Unit

  /**
   * Visits a WeatherCard and places it in the appropriate place in the board.
   *
   * @param player the player who plays the card
   * @param card   the WeatherCard being visited
   */
  def visit(player: Player, card: WeatherCard): Unit
}
