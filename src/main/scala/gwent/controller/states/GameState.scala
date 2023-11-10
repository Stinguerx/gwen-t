package cl.uchile.dcc
package gwent.controller.states

import gwent.controller.GameController

class GameState protected (val context: GameController) {

  /** Method for starting the game. */
  def startMatch(): Unit = {
    throw InvalidActionException("Start Match")
  }

  /** Method called when the player decides to play a card.
   * @param card The index of the card played.
   * */
  def playCard(card: Int): Unit = {
    throw InvalidActionException("Play Card")
  }

  /** Method called when the player decides to pass the turn. */
  def passTurn(): Unit = {
    throw InvalidActionException("Pass Turn")
  }

  /** Method for starting a new round in the game. */
  def startRound(): Unit = {
    throw InvalidActionException("Start Round")
  }

  /** Method used at the end of each round to calculate who won. */
  def calculateScores(): Unit = {
    throw InvalidActionException("Calculate Scores")
  }

  /** Method used when one or both players lost their gems, thus ending the game. */
  def endMatch(): Unit = {
    throw InvalidActionException("End Match")
  }
}
