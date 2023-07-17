package cl.uchile.dcc
package gwent.view

/** Trait for the user interface in the game Gwent. */
trait UserInterface {

  /** Method for asking the player which card it wants to play, if any.
   *  0 is interpreted as passing the turn, otherwise the number entered */
  def promptPlayer(max: Int): Int

}
