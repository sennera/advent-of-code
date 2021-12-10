import scala.collection.mutable

/**
 * summary
 * Part 1 Time: 45 min
 * Part 2 Time: - min
 * TIL: capitalize vals used in case statements,
 * more practice with multi-dim arrays --
 * clicked that they're just 2 arrays, called one after the other!
 */
object Day9 {

  var Rows: Int = 0
  var Cols: Int = 0

  /** ****** Part 1 ********* */
  /**
   */
  def find1(in: Array[String]): Int = {
    Rows = in.length
    Cols = in(0).length

    var row = 0
    val table = Array.ofDim[Int](Rows, Cols)
    in.foreach(a => {
      var col = 0
      a.toList.foreach(b => {
        table(row)(col) = Integer.parseInt(b.toString)
        col += 1
      })
      row +=1
    })

    val lowestNums = new mutable.ListBuffer[Int]()
    (0 until Rows).foreach(r => {
      (0 until Cols).foreach(c => {
        if (lowerThanUp(r, c, table) && lowerThanDown(r, c, table) && lowerThanLeft(r, c, table) && lowerThanRight(r, c, table)) {
          lowestNums.addOne(table(r)(c))
        }
      })
    })
    lowestNums.map(_+1).sum
  }

  def lowerThanUp(row: Int, col: Int, table: Array[Array[Int]]): Boolean = {
    (row, col) match {
      case (0, _) => true
      case (i, j) => table(i)(j) < table(i - 1)(j)
    }
  }

  def lowerThanDown(row: Int, col: Int, table: Array[Array[Int]]): Boolean = {
    val MaxRow = Rows - 1
    (row, col) match {
      case (MaxRow, _) => true
      case (i, j) => table(i)(j) < table(i + 1)(j)
    }
  }

  def lowerThanLeft(row: Int, col: Int, table: Array[Array[Int]]): Boolean = {
    (row, col) match {
      case (_, 0) => true
      case (i, j) => table(i)(j) < table(i)(j - 1)
    }
  }

  def lowerThanRight(row: Int, col: Int, table: Array[Array[Int]]): Boolean = {
    val MaxCol = Cols - 1
    (row, col) match {
      case (_, MaxCol ) => true
      case (i, j) => table(i)(j) < table(i)(j + 1)
    }
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
    val testFileLines = FileReader.readFileLines("test9.txt")
    val fileLines = FileReader.readFileLines("input9.txt")

    println("Test Answer 1: " + find1(testFileLines))
    println("Final Answer 1: " + find1(fileLines))

    println("Test Answer 2: " + find2(testFileLines))
    println("Final Answer 2: " + find2(fileLines))
  }

}
