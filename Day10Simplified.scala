import scala.collection.mutable

/**
 * bracket matching -- to be used for an interview question
 */
object Day10Simplified {

  val openToValidClose = Map(
    '[' -> ']',
    '(' -> ')'
  )

  val matchingOpen = Map(
    ']' -> '[',
    ')' -> '('
  )

  /**
   * The input is a String containing round and square brackets. Determine if the input has a mismatched closing bracket somewhere in it.
   *
   * Matching:
   * ()
   * []
   * ([])
   * ([][[()]])
   *
   * Not matching:
   * (]
   * [()()())
   */
  def hasMismatchBracket(in: String): Boolean = {
    val stack = new mutable.Stack[Char]()

    val maybeOffendingChar = in.toList.find {
      case a if openToValidClose.contains(a) => stack.push(a); false
      case b if b == openToValidClose(stack.top) => stack.pop; false
      case _ => true
    }
    maybeOffendingChar.isDefined
  }

  /**
   * Now determine if it's an incomplete String, so it's just missing some close brackets at the end
   *
   * Incomplete examples:
   * (
   * [[
   * ([](
   */
  def isIncomplete(in: String): Boolean = {
    if (hasMismatchBracket(in)) {
      return false
    }

    val stack = new mutable.Stack[Char]()

    val chars = in.toList
    chars.foreach {
      case a if openToValidClose.contains(a) => stack.push(a)
      case b if b == openToValidClose(stack.top) => stack.pop
    }
    stack.nonEmpty
  }

  /**
   * If it's an incomplete String, return the rest of the String that would make it complete. Otherwise throw IllegalArgumentException.
   *
   * Examples:
   * Input: `(`      Output: `)`
   * Input: `[[`     Output: `]]`
   * Input: `([](`   Output: `))`
   */
  def getCompletedLine(in: String): String = {
    if (hasMismatchBracket(in) || !isIncomplete(in)) {
      throw new IllegalArgumentException()
    }

    val stack = new mutable.Stack[Char]()

    val chars = in.toList
    chars.foreach {
      case a if openToValidClose.contains(a) => stack.push(a)
      case b if b == openToValidClose(stack.top) => stack.pop
    }
    finishLine(stack).mkString("")
  }

  def finishLine(remainingStack: mutable.Stack[Char]): List[Char] = {
    remainingStack.map(openToValidClose(_)).toList
  }

  def main(args: Array[String]): Unit = {
    assert(!hasMismatchBracket("()"))
    assert(!hasMismatchBracket("[]"))
    assert(!hasMismatchBracket("([])"))
    assert(!hasMismatchBracket("([][[()]])"))

    assert(hasMismatchBracket("(]"))
    assert(hasMismatchBracket("[()()())"))

    assert(!isIncomplete("()"))
    assert(!isIncomplete("[]"))
    assert(!isIncomplete("([])"))
    assert(!isIncomplete("([][[()]])"))
    assert(!isIncomplete("(]"))
    assert(!isIncomplete("[()()())"))

    assert(isIncomplete("("))
    assert(isIncomplete("[()()()"))

    try {
      val result = getCompletedLine("()")
      throw new RuntimeException("Did not find expected exception. Result: " + result)
    } catch {
      case _: IllegalArgumentException => // success
      case e => throw new RuntimeException("Did not find expected exception. Exception: " + e)
    }
    try {
      val result = getCompletedLine("[)")
      throw new RuntimeException("Did not find expected exception. Result: " + result)
    } catch {
      case _: IllegalArgumentException => // success
      case e => throw new RuntimeException("Did not find expected exception. Exception: " + e)
    }

    assert(getCompletedLine("(").equals(")"))
    assert(getCompletedLine("[[").equals("]]"))
    assert(getCompletedLine("([](").equals("))"))

    println("All tests passed!")
  }

//  def main(args: Array[String]): Unit = {
//    println("hasMismatchBracket: Expect FALSE")
//    println("Test input: ()            Actual answer: " + hasMismatchBracket("()"))
//    println("Test input: []            Actual answer: " + hasMismatchBracket("[]"))
//    println("Test input: ([])          Actual answer: " + hasMismatchBracket("([])"))
//    println("Test input: ([][[()]])    Actual answer: " + hasMismatchBracket("([][[()]])"))
//    println()
//
//    println("hasMismatchBracket: Expect TRUE")
//    println("Test input: (]            Actual answer: " + hasMismatchBracket("(]"))
//    println("Test input: [()()())      Actual answer: " + hasMismatchBracket("[()()())"))
//    println()
//
//    println("isIncomplete: Expect FALSE")
//    println("Test input: ()            Actual answer: " + isIncomplete("()"))
//    println("Test input: []            Actual answer: " + isIncomplete("[]"))
//    println("Test input: ([])          Actual answer: " + isIncomplete("([])"))
//    println("Test input: ([][[()]])    Actual answer: " + isIncomplete("([][[()]])"))
//    println("Test input: (]            Actual answer: " + isIncomplete("(]"))
//    println("Test input: [()()())      Actual answer: " + isIncomplete("[()()())"))
//    println()
//
//    println("isIncomplete: Expect TRUE")
//    println("Test input: (            Actual answer: " + isIncomplete("("))
//    println("Test input: [()()()      Actual answer: " + isIncomplete("[()()()"))
//    println()
//
//    println("getCompletedLine: Expect exception")
//    try {
//      val result = getCompletedLine("()")
//      println("Test input: ()            Did not find expected exception. Result: " + result)
//    } catch {
//      case _: IllegalArgumentException => println("Test input: ()           Found exception")
//    }
//    try {
//      val result = getCompletedLine("[)")
//      println("Test input: [)           Did not find expected exception. Result: " + result)
//    } catch {
//      case _: IllegalArgumentException => println("Test input: [)           Found exception")
//    }
//    println()
//
//    println("getCompletedLine:")
//    println("Test input: (     Expected/Actual: ) " + getCompletedLine("("))
//    println("Test input: [[    Expected/Actual: ]] " + getCompletedLine("[["))
//    println("Test input: ([](  Expected/Actual: )) " + getCompletedLine("([]("))
//  }

}
