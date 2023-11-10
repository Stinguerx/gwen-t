package cl.uchile.dcc
package gwent.player

case class InvalidCardException(message: String) extends Exception(message)
