import scala.collection.mutable

/**
 * bracket matching and funky points
 * Part 1 Time: 20 min
 * Part 2 Time: 50 min
 * TIL: STACKS! Use Long to not get Int overflow to negative numbers
 */
object Day10 {

  val validClose = Map(
    '<' -> '>',
    '[' -> ']',
    '(' -> ')',
    '{' -> '}'
  )

  val matchingOpen = Map(
    '>' -> '<',
    ']' -> '[',
    ')' -> '(',
    '}' -> '{'
  )

  def find1(in: Array[String]): Int = {
    val points = Map(
      ')' -> 3,
      ']' -> 57,
      '}' -> 1197,
      '>' -> 25137
    )
    val stack = new mutable.Stack[Char]()

    in.map(line => {
      val chars = line.toList
      val maybeOffendingChar = chars.find {
        case a if validClose.contains(a) => stack.push(a); false
        case b if b == validClose(stack.top) => stack.pop; false
        case _ => true
      }
      maybeOffendingChar
    })
      .filter(_.isDefined)
      .map(_.get)
      .toList
      .map(points(_))
      .sum
  }

  def find2(in: Array[String]): Long = {
    val corruptLines = in.filter(line => {
      val stack = new mutable.Stack[Char]()
      val chars = line.toList
      val maybeOffendingChar = chars.find {
        case a if validClose.contains(a) => stack.push(a); false
        case b if b == validClose(stack.top) => stack.pop; false
        case _ => true
      }
      maybeOffendingChar.isDefined
    })

    val sorted = in.filter(!corruptLines.contains(_))
      .map(line => {
        val stack = new mutable.Stack[Char]()
        val chars = line.toList
        chars.foreach {
          case a if validClose.contains(a) => stack.push(a)
          case b if b == validClose(stack.top) => stack.pop
          //        case _ => true
        }
        stack
      })
      .map(finishLine)
      .map(calculateScore)
      .sorted
    println(sorted.toList)
    sorted.drop(sorted.length / 2).head
  }

  def finishLine(remainingStack: mutable.Stack[Char]): Seq[Char] = {
    remainingStack.map(validClose(_)).toList
  }

  def calculateScore(remainingChars: List[Char]): Long = {
    val points = Map(
      ')' -> 1,
      ']' -> 2,
      '}' -> 3,
      '>' -> 4
    )

    var answer = 0L
    remainingChars.foreach(c => answer = answer * 5 + points(c))
    answer
  }

  def main(args: Array[String]): Unit = {
    val testFileLines = FileReader.readFileLines("test10.txt")
    val fileLines = FileReader.readFileLines("input10.txt")

    println("Test Answer 1: " + find1(testFileLines))
    println("Final Answer 1: " + find1(fileLines))

    println("Test Answer 2: " + find2(testFileLines))
    // 380617097 too low
    // List(-1607106751, -1368290239, -1307465725, -1283767362, -1201892609, -1175478414, -1053172682, -316108759, 667466, 9729816, 12261217, 65777651, 73748569, 75562872, 92417197, 116530356, 178733372, 207996793, 242780543, 284030604, 327639913, 328770584, 331068374, 380617097, 383593097, 405214907, 541449140, 657212487, 682771236, 718708367, 721303044, 843332948, 854651574, 858179706, 956046206, 970190894, 1152088313, 1171154127, 1513044944, 1557642258, 1632575136, 1901474248, 1911251021, 2047442083, 2129861400, 2132182164, 2132294704)
    // guessed next answer, 383593097, still too low
    println("Final Answer 2: " + find2(fileLines))
  }

}
