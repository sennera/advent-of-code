/**
 * Crab submarines fuel consumption
 * Part 1 Time: 15 min
 * Part 2 Time: 4 min
 * TIL: abs and sum are nice :) using ranges more and more (but also always forget the meaning of to vs until!)
 */
object Day7 {

  /** ****** Part 1 ********* */
  /**
   */
  def find1(in: Array[String]): Int = {
    val ins = in(0).split(",").map(Integer.parseInt)
    val min = ins.min
    val max = ins.max

    var lowestFuel = Int.MaxValue
    var indexOfLowestFuel = Int.MaxValue

    (min to max).foreach(i => {
      val fuelForThisIndex = ins.map(j => (j - i).abs).sum
      if (lowestFuel > fuelForThisIndex) {
        lowestFuel = fuelForThisIndex
        indexOfLowestFuel = i
      }
    })

    lowestFuel
  }

  /** ****** Part 2 ********* */
  /**
   */
  def find2(in: Array[String]): Int = {
    val ins = in(0).split(",").map(Integer.parseInt)
    val min = ins.min
    val max = ins.max

    var lowestFuel = Int.MaxValue
    var indexOfLowestFuel = Int.MaxValue

    (min to max).foreach(i => {
      val fuelForThisIndex = ins
        .map(j => (j - i).abs)
        .map(k => (0 to k).sum)
        .sum
      if (lowestFuel > fuelForThisIndex) {
        lowestFuel = fuelForThisIndex
        indexOfLowestFuel = i
      }
    }
    )

    lowestFuel
  }

  def main(args: Array[String]): Unit = {
    val testFileLines = FileReader.readFileLines("test7.txt")
    println("Test Answer: " + find1(testFileLines))

    // 15 min
    // 361, 1001 too low
    val fileLines = FileReader.readFileLines("input7.txt")
    val time = System.currentTimeMillis()
    println("Final Answer: " + find1(fileLines))
    val time2 = System.currentTimeMillis() - time
    println("Time Answer 1: " + time2)

    println("Test Answer 2: " + find2(testFileLines))

    // 4 min
    val time3 = System.currentTimeMillis()
    println("Final Answer 2: " + find2(fileLines))
    val time4 = System.currentTimeMillis() - time3
    println("Time Answer 2: " + time4)
  }

}
