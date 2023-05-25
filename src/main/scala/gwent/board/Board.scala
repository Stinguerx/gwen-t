package cl.uchile.dcc
package gwent.board


import gwent.cards._
import gwent.Player

/**
 * Class that implements the IBoard trait.
 * The board in in charge of keeping track of the overall state of a game in Gwent. When instantiated it creates two
 * board sections and assigns each player to one of them. It also creates an instance of the CardPlacer class to handle
 * the placement of cards in each section.
 *
 * @param player1 the first player associated with this particular instance of the game.
 * @param player2 the second player associated with this particular instance of the game.
 * */
class Board(player1: Player, player2: Player) extends IBoard {
  private val cardPlacer = new CardPlacer
  private var _weather: Option[WeatherCard] = None
  private val _SectionA = new BoardSection
  private val _SectionB = new BoardSection

  player1.assignedSection = _SectionA
  player1.board = this
  player2.assignedSection = _SectionB
  player2.board = this

  /** @return the first section of the board */
  def SectionA: BoardSection = _SectionA

  /** @return the second section of the board */
  def SectionB: BoardSection = _SectionB

  /** @return the current active weather card in the board, if there is one. */
  def weather: Option[WeatherCard] = _weather

  /**
   * Sets the active weather card of the board to a new card.
   *
   * @param card the new weather card
   * */
  def weather_=(card: WeatherCard): Unit = {
    _weather = Some(card)
  }

  def placeCard(player: Player, card: Card): Unit = {
    card.accept(cardPlacer, player)
  }

}
