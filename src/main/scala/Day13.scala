/**
 * summary
 * Part 1 Time: 40 min
 * Part 2 Time:  min
 * TIL:
 */
object Day13 {

  /** ****** Part 1 ********* */
  /**
   */
  def find1(in1: Array[String], in2: Array[String]): Int = {
    val coordinates = in1.map(_.split(",").map(Integer.parseInt)).map(arr => arr(0) -> arr(1))
    val fold1Str = in2(0).substring(13)
    val fold1 = Integer.parseInt(fold1Str)

    val markedGrid = markCoordinates(coordinates)
    val markedGridWithFold = foldX(markedGrid, fold1)

    markedGridWithFold.map(xArr => xArr.count(a => a)).sum
  }

  def markCoordinates(coordinates: Array[(Int, Int)]): Array[Array[Boolean]] = {
    val maxX = coordinates.map(_._1).max
    val maxY = coordinates.map(_._2).max

    val a = Array.ofDim[Boolean](maxX + 1, maxY + 1)

    coordinates.foreach(t => a(t._1)(t._2) = true)
    a
  }

  def foldX(markedGrid: Array[Array[Boolean]], xLine: Int): Array[Array[Boolean]] = {
    // wipe out the fold line
    markedGrid(0).indices.foreach(i => markedGrid(xLine)(i) = false)

    markedGrid(0).indices.foreach(Y => {
      (xLine until markedGrid.length - 1).foreach {
        case x if markedGrid(x)(Y) =>
          //mark right coord as false
          markedGrid(x)(Y) = false

          //mark left coord as true
          val distanceFromXLine = x - xLine
          val newX = xLine - distanceFromXLine
          if (newX >= 0) {
            markedGrid(newX)(Y) = true
          }
        case _ => // do nothing
      }
    })

    markedGrid
  }

  def foldY(markedGrid: Array[Array[Boolean]], yLine: Int): Array[Array[Boolean]] = {
    // wipe out the fold line
    markedGrid.indices.foreach(i => markedGrid(i)(yLine) = false)

    markedGrid.indices.foreach(X => {
      (yLine until markedGrid(0).length - 1).foreach {
        case y if markedGrid(X)(y) =>
          //mark bottom coord as false
          markedGrid(X)(y) = false

          //mark top coord as true
          val distanceFromYLine = y - yLine
          val newY = yLine - distanceFromYLine
          if (newY >= 0) {
            markedGrid(X)(newY) = true
          }
        case _ => // do nothing
      }
    })

    markedGrid
  }

  def fold(markedGrid: Array[Array[Boolean]], foldInstruction: (String, Int)): Array[Array[Boolean]] = {
    foldInstruction match {
      case ("x", num) => foldX(markedGrid, num)
      case ("y", num) => foldY(markedGrid, num)
    }

    markedGrid
  }

  def removeEmptyRowsCols(markedGrid: Array[Array[Boolean]]): Array[Array[Boolean]] = {
    val a = Array.ofDim[Boolean](40, 6) // max y and x folds from file

    (0 until 40).foreach(x => {
      (0 until 6).foreach(y => {
        a(x)(y) = markedGrid(x)(y)
      })
    })
    a
  }

  /** ****** Part 2 ********* */
  /**
   */
  def find2(in1: Array[String], in2: Array[String]): Int = {
    val coordinates = in1.map(_.split(",").map(Integer.parseInt)).map(arr => arr(0) -> arr(1))
    val axisToNumbers: Array[(String, Int)] = in2.map(a => a.replaceAll("fold along ", ""))
      .map(_.split("="))
      .map(a => (a(0), Integer.parseInt(a(1))))

    val markedGrid = markCoordinates(coordinates)
    axisToNumbers.foreach(fold(markedGrid, _))
    val cleanedGrid = removeEmptyRowsCols(markedGrid)

    println({printTable(cleanedGrid)})
    cleanedGrid.map(xArr => xArr.count(a => a)).sum
  }

  def printTable(ins: Array[Array[Boolean]]) = ins.map(a => a.map {
    case true => "#"
    case false => "."
  }.mkString("", " ", "")).mkString("\n", "\n", "")

  def main(args: Array[String]): Unit = {
//    val testFileLines = FileReader.readFileLines("test13.txt")
    val fileLines = FileReader.readFileLines("input13.txt")
    val fileLines2 = FileReader.readFileLines("input13.1.txt")

//    println("Test Answer 1: " + find1(testFileLines))
    println("Final Answer 1: " + find1(fileLines, fileLines2))

//    println("Test Answer 2: " + find2(testFileLines))
    println("Final Answer 2: " + find2(fileLines, fileLines2))
  }

}
