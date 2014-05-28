package utils

import scala.concurrent.duration._

object Dividing extends App {

  var startTime = Duration(System.nanoTime(), NANOSECONDS)
  (20 to 10000000).map(x => x / 2)
  var endTime = Duration(System.nanoTime(), NANOSECONDS)

  val time = 1 second

  println((endTime - startTime).toMillis)

  startTime = Duration(System.nanoTime(), NANOSECONDS)
  (20 to 10000000).map(x => x >> 1)
  endTime = Duration(System.nanoTime(), NANOSECONDS)

  println((endTime - startTime).toMillis)





}
