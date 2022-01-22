/**
 * Velocities trying to get in a target area
 * Part 1 Time: 60 min
 * Part 2 Time: 10 min
 * TIL: Scala has while loops who knew. Betta than takeWhile.
 * takeWhile only applies to the stream I think, so it seems to not do anything if you ignore the input to it. Any old boolean doesn't work.
 */
object Day17 {

  val minX = 230
  val maxX = 283
  val minY = -107
  val maxY = -57

  /** ****** Part 1 ********* */
  /** x=230..283, y=-107..-57
   */
  def find1(): Int = {
    // check xs from 0 to 283
    // check ys from -107 to 300
    // for each check if it's in the target
    // if so, see the max y
    // choose max y
    (0 to maxX).map(x => {
      (minY to 300)
        .map(y => {
          println(s"$x $y")
          maxYIfInTarget(x, y)
        })
        .max
    }).max
  }

  def maxYIfInTarget(x: Int, y: Int): Int = {
    var xPos = 0
    var yPos = 0

    var inTarget = false
    var maxYThisTime = Int.MinValue

    var xDiff = x
    var yDiff = y

    while (xPos <= maxX && yPos >= minY) {
      xPos += xDiff
      yPos += yDiff
      if (yPos > maxYThisTime) {
        maxYThisTime = yPos
      }
      if (xPos <= maxX && xPos >= minX && yPos <= maxY && yPos >= minY) {
        inTarget = true
      }
      if (xDiff != 0) {
        xDiff -= 1
      }
      yDiff -= 1
    }

    if (inTarget) {
      maxYThisTime
    } else {
      Int.MinValue
    }
  }

  /** ****** Part 2 ********* */
  /**
   */
  def find2(): Int = {
    (0 to maxX).map(x => {
      (minY to 300)
        .map(y => {
          val f = maxYIfInTarget(x, y)
          if (f > Int.MinValue) println(s"$x $y")
          f
        }).count(_ != Int.MinValue)
    }).sum
  }

  def main(args: Array[String]): Unit = {
//    val testFileLines = FileReader.readFileLines("test6.txt")
//    val fileLines = FileReader.readFileLines("input6.txt")

//    println("Final Answer 1: " + find1())

    println("Final Answer 2: " + find2())
  }

}
