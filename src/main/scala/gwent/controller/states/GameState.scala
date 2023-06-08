package cl.uchile.dcc
package gwent.controller.states

import gwent.controller.GameController

class GameState protected (val context: GameController) {
  def startMatch(): Unit = {
    InvalidActionException("Start Match")
  }
  def playCard(card: Int): Unit = {
    InvalidActionException("Play Card")
  }

  def passTurn(): Unit = {
    InvalidActionException("Pass Turn")
  }

  def startRound(): Unit = {
    InvalidActionException("Start Round")
  }

  def calculateScores(): Unit = {
    InvalidActionException("Calculate Scores")
  }

}
