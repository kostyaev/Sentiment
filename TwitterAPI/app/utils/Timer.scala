package utils

import core.SentimentTools
import scala.concurrent.duration._

object Timer extends App with SentimentTools {

  def runTask(msg: String) = {
    val startTime = Duration(System.nanoTime(), NANOSECONDS)
    getGrade(msg)
    val endTime = Duration(System.nanoTime(), NANOSECONDS)
    (endTime - startTime).toMillis
  }
  runTask("test sentence before checking running time")
  runTask("The acclaimed anime film Harlock: Space Pirate, from Shinju Aramaki, will exclusively be available")

  val tweet1 = """If you propose to speak always ask yourself, is it true, is it necessary, is it kind"""
  val tweet2 =
    """I'd love to come! Where is it Zach Raise your hand if you're headed to a Bar Mitzvah in New Jersey. CoryBooker are u coming?"""
  val tweet3 = """Not to be absolutely certain is, I think, one of the essential things in rationality."""
  val tweet4 = """Be who you are and say what you feel, because those who mind don't matter and those who matter don't mind. - Elbert Hubbard"""
  val tweet5 = """India's oldest car factory shelves elite sedan"""
  val tweet6 = """The first gameplay trailer for Batman: Arkham Knight is here. What do you think of it?"""
  val tweet7 = """What happens when a group of rock stars and designers re-imagine a classic lamp"""
  val tweet8 = """Did you ever notice that the Washington Monument has an accurate moving shadow on Google Maps?"""
  val tweet9 = """21 of the most adorable baby turtles"""



  println("Timer: " + runTask(tweet1) + " milliseconds")
  println("Timer: " + runTask(tweet2) + " milliseconds")
  println("Timer: " + runTask(tweet3) + " milliseconds")
  println("Timer: " + runTask(tweet4) + " milliseconds")
  println("Timer: " + runTask(tweet5) + " milliseconds")
  println("Timer: " + runTask(tweet6) + " milliseconds")
  println("Timer: " + runTask(tweet7) + " milliseconds")
  println("Timer: " + runTask(tweet8) + " milliseconds")
  println("Timer: " + runTask(tweet9) + " milliseconds")




}
