package cl.uchile.dcc
package gwent

/** Trait that defines the attributes of a card in Gwent and behaviours of a card in Gwent*/
trait Card {
  /** @return the name of the card. */
  def getName: String

  /** @return the classification of the card. */
  def getClassification: String

  /** @return the effect (if any) of the card. */
  def getEffect: Option[String]
  
  /** @return the default strength of the card. */
  def getOriginalStrength: Int

  /** @return the current of the card. */
  def getCurrentStrength: Int

  /** Sets the current strength of the card to the value passed.
   *
   * @param str the new current strength value of the card
   */
  def setStrength(str: Int): Unit
}

/** Class that implements the uCard trait. The currentStrength attribute gets initialized to the value of
 *  the strength parameter, then it gets modified taking in consideration the effect of other Unit or Weather
 *  cards in the table.
 *
 * @param name the name of the card.
 * @param classification the classification of the card, be it melee, range, siege or weather.
 * @param strength the default strength of the card.
 * @param effect the effect (if any) that the card has over the game when played.
 * */
class genericCard(name: String, classification: String, strength: Int, effect: Option[String]) extends Card {
  private var currentStrength = strength

  def getName: String = name

  def getClassification: String = classification

  def getOriginalStrength: Int = strength

  def getCurrentStrength: Int = currentStrength

  def setStrength(str: Int): Unit = {
    currentStrength = str
  }

  def getEffect: Option[String] = effect

}

