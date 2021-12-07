/**
 * summary
 * Part 1 Time:  min
 * Part 2 Time:  min
 * TIL:
 */
object DayBlank {

  /** ****** Part 1 ********* */
  /**
   */
  def find1(in: Array[String]): Int = {
    val ins = in(0).split(",").map(Integer.parseInt)
    val answer = 0
    answer
  }

  /** ****** Part 2 ********* */
  /**
   */
  def find2(in: Array[String]): Int = {
    val ins = in(0).split(",").map(Integer.parseInt)
    val answer = 0
    answer
  }

  def main(args: Array[String]): Unit = {
    val testFileLines = FileReader.readFileLines("test6.txt")
    val fileLines = FileReader.readFileLines("input6.txt")

    println("Test Answer 1: " + find1(testFileLines))
    println("Final Answer 1: " + find1(fileLines))

    println("Test Answer 2: " + find2(testFileLines))
    println("Final Answer 2: " + find2(fileLines))
  }

}
