/**
 * summary
 * Part 1 Time:  min
 * Part 2 Time:  min
 * TIL:
 */
object Day16 {

  /** ****** Part 1 ********* */
  /**
   */
  def find1(in: Array[String]): Int = {
    val binary = hexToBin(in(0))
//    val version = getPacketVersion(binary)
    val typeId = getTypeId(binary)
    val theRest = getTheRest(binary)

//    val decimalVersion = Integer.parseInt(version, 2)
    val decimalTypeId = Integer.parseInt(typeId, 2)


    if (decimalTypeId == 4) {
      val bits = getBits(theRest)
      val resultOfBits = Integer.parseInt(bits, 2)
    }

    val answer = 0
    answer
  }

//  import java.math.BigInteger
//  def hexToBin(s: String): String = new BigInteger(s, 16).toString(2)

  private def getBits(theRest: String): String = {
    var fourBits = theRest.substring(1, 5)
    if (theRest.substring(0, 1).equals("1")) {
      fourBits += getBits(theRest.substring(5))
    }
    fourBits
  }

  private def hexToBin(hexIn: String) = {
    var hex = hexIn
    hex = hex.replaceAll("0", "0000")
    hex = hex.replaceAll("1", "0001")
    hex = hex.replaceAll("2", "0010")
    hex = hex.replaceAll("3", "0011")
    hex = hex.replaceAll("4", "0100")
    hex = hex.replaceAll("5", "0101")
    hex = hex.replaceAll("6", "0110")
    hex = hex.replaceAll("7", "0111")
    hex = hex.replaceAll("8", "1000")
    hex = hex.replaceAll("9", "1001")
    hex = hex.replaceAll("A", "1010")
    hex = hex.replaceAll("B", "1011")
    hex = hex.replaceAll("C", "1100")
    hex = hex.replaceAll("D", "1101")
    hex = hex.replaceAll("E", "1110")
    hex = hex.replaceAll("F", "1111")
    hex
  }

  def getPacketVersion(string: String) = {
    string.substring(0, 3)
  }

  def getTypeId(string: String) = {
    string.substring(3, 6)
  }

  def getTheRest(string: String) = {
    string.substring(6)
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
