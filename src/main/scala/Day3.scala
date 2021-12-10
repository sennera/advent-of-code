/**
 * Day 3: Binary Diagnostic
 */
object Day3 {

  // needed for sample
//  val SIZE = 5
  // needed for real
  val SIZE = 12

  def find1(in: Array[String]): Int = {
    val gammaBinaryStr = (0 until SIZE).map(i => findGreatest(in, i))
      .mkString("")
    val epsilonBinaryStr = (0 until SIZE).map(i => findLeast(in, i))
      .mkString("")
    val gDecimalValue = Integer.parseInt(gammaBinaryStr, 2)
    val eDecimalValue = Integer.parseInt(epsilonBinaryStr, 2)
    eDecimalValue * gDecimalValue
  }

  def findGreatest(in: Array[String], positionInString: Int): String = {
    val zeros = in.map(i => i.charAt(positionInString))
      .count(i => i == '0')
    val ones = in.map(i => i.charAt(positionInString))
      .count(i => i == '1')
    if (zeros > ones) "0" else "1"
  }

  def findLeast(in: Array[String], positionInString: Int): String = {
    val zeros = in.map(i => i.charAt(positionInString))
      .count(i => i == '0')
    val ones = in.map(i => i.charAt(positionInString))
      .count(i => i == '1')
    if (zeros > ones) "1" else "0"
  }

  def find2(in: Array[String]): Int = {
    val a = findStringWithGreatest(in)
    val b = findStringWithLeast(in)

    val aDecimalValue = Integer.parseInt(a, 2)
    val bDecimalValue = Integer.parseInt(b, 2)

    aDecimalValue * bDecimalValue
  }

  private def findStringWithGreatest(in: Array[String]): String = {
    var answer = ""
    var remainingStrings: Array[String] = in
    (0 until SIZE)
      .takeWhile(_ => answer.equals(""))
      .foreach(a => {
        val greatestBinaryStr = findGreatest(remainingStrings, a)
        remainingStrings = Seq.from(remainingStrings).filter(_.charAt(a).equals(greatestBinaryStr.charAt(0))).toArray
        if (remainingStrings.length == 1) answer = remainingStrings(0)
      })
    answer
  }

  private def findStringWithLeast(in: Array[String]): String = {
    var answer = ""
    var remainingStrings: Array[String] = in
    (0 until SIZE)
      .takeWhile(_ => answer.equals(""))
      .foreach(a => {
        val leastBinaryStr = findLeast(remainingStrings, a)
        remainingStrings = Seq.from(remainingStrings).filter(_.charAt(a).equals(leastBinaryStr.charAt(0))).toArray
        if (remainingStrings.length == 1) answer = remainingStrings(0)
      })
    answer
  }

}
