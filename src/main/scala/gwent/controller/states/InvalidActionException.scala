package cl.uchile.dcc
package gwent.controller.states

case class InvalidActionException(message: String) extends Exception(message)
