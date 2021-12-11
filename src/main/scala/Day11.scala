import scala.collection.mutable

/**
 * Octopus flashes
 * Part 1 Time: 1 hr 20 min
 * Part 2 Time: 5 min
 * TIL: first recursion solution!
 */
object Day11 {

  /** ****** Part 1 ********* */
  /**
   */
  def find1(in: Array[String]): Int = {
    val ins: Array[Array[Int]] = in.map(_.toArray.map(a => Integer.parseInt(a.toString)))
    (1 to 100).map(i => {
      increaseAll(ins)
      println(s"$i: after increase ${printTable(ins)}")  // seems good
      val i1 = countFlashes(ins)
      println(s"$i: countFlashes ${i1}")
      i1
    }).sum
  }

  def printTable(ins: Array[Array[Int]]) = ins.map(_.mkString("", "", "")).mkString("\n", "\n", "")

  private def increaseAll(ins: Array[Array[Int]]): Unit = {
    (0 until 10).foreach(r => {
      (0 until 10).foreach(c => {
        ins(r)(c) += 1
      })
    })
  }

  private def countFlashes(ins: Array[Array[Int]]): Int = {
    val locationsOfNines = new mutable.ListBuffer[(Int, Int)]
    (0 until 10).foreach(r => {
      (0 until 10).foreach(c => {
        if (ins(r)(c) > 9) {
          locationsOfNines.addOne(r -> c)
        }
      })
    })

//    println(s"nines $locationsOfNines")
    locationsOfNines.foreach(tuple => increaseAllAround(tuple._1, tuple._2, ins))
//    println(s"table before zeroing ${printTable(ins)}")
    val flashes = countFlashesInTable(ins)
    setFlashesToZero(ins)
    flashes
  }

  def increaseAllAround(row: Int, col: Int, table: Array[Array[Int]]): Array[Array[Int]] = {
    val minRow = if (row == 0) 0 else row - 1
    val maxRow = if (row == 9) 9 else row + 1
    val minCol = if (col == 0) 0 else col - 1
    val maxCol = if (col == 9) 9 else col + 1

//    println(s"row $row col $col minRow $minRow maxRow $maxRow minCol $minCol maxCol $maxCol")
    (minRow to maxRow).map(r => {
      (minCol to maxCol).map(c => {
        if (r == row && c == col) {
          // do nothing
        } else if (table(r)(c) == 10) {
          // do nothing
        } else {
          table(r)(c) += 1
          if (table(r)(c) == 10) {
//            println(s"r $r c $c")
            increaseAllAround(r, c, table)
          }
        }
      })
    })

    table
  }

  private def countFlashesInTable(ins: Array[Array[Int]]): Int = {
    (0 until 10).map(r => {
      (0 until 10).count(c => ins(r)(c) > 9)
    }).sum
  }

  private def setFlashesToZero(ins: Array[Array[Int]]): Unit = {
    (0 until 10).foreach(r => {
      (0 until 10).foreach(c => {
        if (ins(r)(c) > 9) {
          ins(r)(c) = 0
        }
      })
    })
  }

  def find2(in: Array[String]): Int = {
    val ins: Array[Array[Int]] = in.map(_.toArray.map(a => Integer.parseInt(a.toString)))
    var firstFlash = 0
    (1 to 1000).foreach(step => {
      increaseAll(ins)
      val flashCount = countFlashes(ins)
      if (flashCount == 100 && firstFlash == 0) {
        firstFlash = step
      }
    })
    firstFlash
  }

  def main(args: Array[String]): Unit = {
    val testFileLines = FileReader.readFileLines("test11.txt")
    val fileLines = FileReader.readFileLines("input11.txt")

    println("Test Answer 1: " + find1(testFileLines))
    // 1815 too high
    // 1601 is right
    println("Final Answer 1: " + find1(fileLines))

    println("Test Answer 2: " + find2(testFileLines))
    println("Final Answer 2: " + find2(fileLines))
  }

}
