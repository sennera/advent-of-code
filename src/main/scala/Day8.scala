import scala.collection.mutable

/**
 * digits
 * Part 1 Time: 30 min
 * Part 2 Time: 1 hr 40 min
 * TIL:
 */
object Day8 {

  /** ****** Part 1 ********* */
  /** 0:      1:      2:      3:      4:
   * aaaa    ....    aaaa    aaaa    ....
   * b    c  .    c  .    c  .    c  b    c
   * b    c  .    c  .    c  .    c  b    c
   * ....    ....    dddd    dddd    dddd
   * e    f  .    f  e    .  .    f  .    f
   * e    f  .    f  e    .  .    f  .    f
   * gggg    ....    gggg    gggg    ....
   *
   * 5:      6:      7:      8:      9:
   * aaaa    aaaa    aaaa    aaaa    aaaa
   * b    .  b    .  .    c  b    c  b    c
   * b    .  b    .  .    c  b    c  b    c
   * dddd    dddd    ....    dddd    dddd
   * .    f  e    f  .    f  e    f  .    f
   * .    f  e    f  .    f  e    f  .    f
   * gggg    gggg    ....    gggg    gggg
   *
   * 1 has 2 digits = c, f
   *
   * 7 has 3 digits = a, c, f
   *
   * 4 has 4 digits = b, d, c, f
   *
   * 2 has 5 digits = (a), c, d, e, g // only one with e
   * 3 has 5 digits = (a), c, d, f, g
   * 5 has 5 digits = (a), b, d, f, g // only one with b, only one without c
   * d is in all
   *
   * 0 has 6 digits = (a), b, c, e, f, g //c e
   * 6 has 6 digits = (a), b, d, e, f, g //d e
   * 9 has 6 digits = (a), b, c, d, f, g //c d
   * a, b, f, g in all
   *
   * 8 has 7 digits = a, b, c, d, e, f, g ==> not helpful
   *
   *
   * a is in all
   */
  def find1(in: Array[String]): Int = {
    val sizesWeCareAbout = List(2, 4, 3, 7)
    in.map(a => {
      val lastFour = a.substring(a.indexOf("|"))
      lastFour.split(" ").count(b => sizesWeCareAbout.contains(b.length))
    }).sum
  }

  def find2(in: Array[String]): Int = {
    // lines in file ( list of size 2, the 10/4 digits ( list of digits, size 10 and 4))
    in.map(a => {
      val tenDigitsAndFourDigits = a.split(" \\| ")
      val stringses = tenDigitsAndFourDigits.map(_.split(" "))
      val tenDigits = stringses(0)
      val fourDigits = stringses(1)

      val stringsByLength = tenDigits.sortBy(_.length)

      val stringToNum = new mutable.HashMap[String, Int]()
      val numToString = new mutable.HashMap[Int, String]()
      storeStringToNum(stringToNum, numToString, stringsByLength(0), 1)
      storeStringToNum(stringToNum, numToString, stringsByLength(1), 7)
      storeStringToNum(stringToNum, numToString, stringsByLength(2), 4)
      storeStringToNum(stringToNum, numToString, stringsByLength(9), 8)

      val lengthOfFives = stringsByLength.filter(_.length == 5)
      val lengthOfSixes = stringsByLength.filter(_.length == 6)

      val anA: Char = numToString(7).toList.find(c => !numToString(1).toList.contains(c)).get

      val three = lengthOfFives.find(digit => digit.toList.filter(_ != anA).count(d => !numToString(1).toList.contains(d)) == 2).get
      val two = lengthOfFives.filter(_ != three).find(digit => digit.toList.filter(_ != anA).count(d => !numToString(4).toList.contains(d)) == 2).get
      val five = lengthOfFives.filter(_ != three).find(_ != two).get

      val lettersInCommonOnSixes = lengthOfSixes(0).toSet.intersect(lengthOfSixes(1).toSet).intersect(lengthOfSixes(2).toSet)
      val nine = lengthOfSixes.find(digit => digit.toList.filter(!lettersInCommonOnSixes.contains(_)).count(d => !numToString(4).toList.contains(d)) == 0).get

      val sixOrZero = lengthOfSixes.filter(_ != nine)
      val anE = sixOrZero(0).toSet.intersect(sixOrZero(1).toSet).filter(!lettersInCommonOnSixes.contains(_)).head
      val zero = sixOrZero.find(digit => digit.toList.filter(_ != anE).filter(!lettersInCommonOnSixes.contains(_)).count(d => !numToString(7).toList.contains(d)) == 0).get
      val six = sixOrZero.find(_ != zero).get

      storeStringToNum(stringToNum, numToString, two, 2)
      storeStringToNum(stringToNum, numToString, three, 3)
      storeStringToNum(stringToNum, numToString, five, 5)
      storeStringToNum(stringToNum, numToString, six, 6)
      storeStringToNum(stringToNum, numToString, nine, 9)
      storeStringToNum(stringToNum, numToString, zero, 0)

      val stringNumber = fourDigits.map(a => stringToNum(a.toList.sorted.mkString)).mkString
      Integer.parseInt(stringNumber)
    }).sum
  }

  //          stringses


  private def storeStringToNum(stringToNum: mutable.HashMap[String, Int], numToString: mutable.HashMap[Int, String], digit: String, number: Int) = {
    stringToNum.addOne(digit.toList.sorted.mkString, number)
    numToString.addOne(number, digit)
  }

  def main(args: Array[String]): Unit = {
    val testFileLines = FileReader.readFileLines("test8.txt")
    val fileLines = FileReader.readFileLines("input8.txt")

    println("Test Answer 1: " + find1(testFileLines))
    println("Final Answer 1: " + find1(fileLines))

    println("Test Answer 2: " + find2(testFileLines))
    // 1030421 too low
    println("Final Answer 2: " + find2(fileLines))
  }

}
