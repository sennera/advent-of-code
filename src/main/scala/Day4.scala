import scala.collection.IterableOnce.iterableOnceExtensionMethods
import scala.collection.mutable.ListBuffer

object Day4 {

  /** ****** Part 1 ********* */
  /**
   */
  def find1(inCalls: Array[String], inBoards: Array[String]): Int = {
    val calls: Array[Int] = inCalls(0).split(",").map(Integer.parseInt)

    val boards: IndexedSeq[Array[Array[Option[Int]]]] = (0 until inBoards.length / 6 + 1)
      .map(i => i * 6)
      .map(i => buildBoard(inBoards, i))

    var answerCall = 0
    var winner: Array[Array[Option[Int]]] = null
    for (call <- calls) {
      if (answerCall == 0) {
        val winnerOpt: Option[Array[Array[Option[Int]]]] = boards.map(markBoard(call, _))
          .find(checkForWinner)
        if (winnerOpt.isDefined) {
          answerCall = call
          winner = winnerOpt.get
        }
      }
    }
    println(" AAA " + sumBoard(winner) + " " +  answerCall)
    sumBoard(winner) * answerCall
  }

  def buildBoard(inBoards: Array[String], startIndex: Int): Array[Array[Option[Int]]] = {
    val a = Array.ofDim[Option[Int]](5, 5)
    (startIndex to startIndex + 4).foreach(i => {
//      println(inBoards(i))
//      println(i)
      val rowNum = i - startIndex
      val fiveInts = inBoards(i).trim.split("\\s+").map(_.trim).map(Integer.parseInt)
      (0 until 5).foreach(j => {
        a(rowNum)(j) = Some(fiveInts(j))
      })
    })
    a
  }

  def markBoard(callNumber: Int, board: Array[Array[Option[Int]]]): Array[Array[Option[Int]]] = {
    (0 until 5).foreach(i => {
      (0 until 5).foreach(j => {
        if (board(i)(j).contains(callNumber)) board(i)(j) = None
      })
    })
    board
  }

  def checkForWinner(board: Array[Array[Option[Int]]]) = {
    val marksInRow: Seq[Int] = (0 until 5).map(i => {
      (0 until 5).map(board(i)(_)).count(_.isEmpty)
    })

    val marksInColumn: Seq[Int] = (0 until 5).map(i => {
      (0 until 5).map(board(_)(i)).count(_.isEmpty)
    })

    if (marksInRow.contains(5)) true
    else if (marksInColumn.contains(5)) true
    else false
  }

  def sumBoard(board: Array[Array[Option[Int]]]): Int = {
    (0 until 5).map(i => (0 until 5).map(board(i)(_)).filter(_.isDefined).map(_.get).sum).sum
  }

  /** ****** Part 2 ********* */
  /**
   */
  def find2(inCalls: Array[String], inBoards: Array[String]): Int = {
    val calls: Array[Int] = inCalls(0).split(",").map(Integer.parseInt)

    val boards: List[Array[Array[Option[Int]]]] = (0 until inBoards.length / 6 + 1)
      .map(i => i * 6)
      .map(i => buildBoard(inBoards, i))
      .toList

    var remainingBoards = new ListBuffer[Array[Array[Option[Int]]]]
    remainingBoards.addAll(boards)

    var answerCall = 0
    var winner: Array[Array[Option[Int]]] = null
    for (call <- calls) {
      if (answerCall == 0) {
        boards.map(markBoard(call, _))
          .filter(checkForWinner)
          .foreach(w => {
            remainingBoards -= w
            if (remainingBoards.isEmpty && answerCall == 0) {
              answerCall = call
              winner = w
            }
          }
          )
      }
    }
    println("AAA " + sumBoard(winner) + " " + answerCall)
    sumBoard(winner) * answerCall
  }

}
