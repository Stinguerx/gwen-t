package cl.uchile.dcc
package gwent.view

import scala.io.StdIn
import scala.util.Random

class ConsoleInterface extends UserInterface {
  override def promptPlayer(maximum: Int): Int = {
    // Placeholder for real user input
    Random.nextInt(maximum + 1)
  }
}
