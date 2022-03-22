import scala.collection.mutable

/**
 * summary
 * Part 1 Time: 30 min
 * Part 2 Time: 3 hr
 * TIL:
 */
object Day21 {

  //(rollCount, rollSum, player1position, player1score, player2position, player2score) to winner player number
  val statesSeenToWinner: mutable.HashMap[Array[Int], Int] = new mutable.HashMap()
//  val statesSeenToWinner: mutable.HashMap[SubState, Int] = new mutable.HashMap()

  def find1(player1Start: Int, player2Start: Int): Int = {
    var p1 = player1Start
    var p2 = player2Start
    var s1 = 0
    var s2 = 0

    var index = 1
    while (s1 < 1000 && s2 < 1000) {
      val roll = index % 100 + (index + 1) % 100 + (index + 2) % 100
      if (index % 2 == 0) {
        val x = (p2 + roll) % 10
        p2 = if (x == 0) 10 else x
        s2 += p2
      } else {
        val x = (p1 + roll) % 10
        p1 = if (x == 0) 10 else x
        s1 += p1
      }

      index += 3
    }
    val loser = if (s1 < s2) s1 else s2
    loser * (index - 1)
  }

  def find2(player1Start: Int, player2Start: Int): Int = {
    val s = State(
      rollCount = 0,
      rollSum = 0,
      thisPlayer = Player(playerNum = 1, pos = player1Start),
      otherPlayer = Player(playerNum = 2, pos = player2Start)
    )
    val u1 = universe(1, s)
    val u2 = universe(2, s)
    val u3 = universe(3, s)
    val endingState = collectWins(u1, u2, u3)
    endingState.otherPlayer.wins.max(endingState.thisPlayer.wins)
  }


  // Returns the winner if there was one
  def universe(rollOutcome: Int, state: State): State = {
      println(state)
      val newRollCount = (state.rollCount + 1) % 3
      val scoringRoll = newRollCount == 0
      if (scoringRoll) {
        val threeRolls = rollOutcome + state.rollSum
        val x = (state.thisPlayer.pos + threeRolls) % 10
        val newPosition = if (x == 0) 10 else x
        val newScore = state.thisPlayer.score + newPosition

        if (newScore >= 21) {
          state.statesSeenBeforeWin.foreach(ss => statesSeenToWinner.put(ss, state.thisPlayer.playerNum))
          statesSeenToWinner.put(state.subState, state.thisPlayer.playerNum)

          state.copy(thisPlayer = state.thisPlayer.copy(pos = newPosition, score = newScore, wins = state.thisPlayer.wins + 1))
        } else {
          //kick off first roll for other player
          val s = State(
            rollCount = 0,
            rollSum = 0,
            thisPlayer = state.otherPlayer,
            otherPlayer = state.thisPlayer,
            statesSeenBeforeWin = state.statesSeenBeforeWin + state.subState
          )
          if (statesSeenToWinner.contains(s.subState)) {
            println("Found a dupe! Win!")
            val winnerPlayer = statesSeenToWinner(s.subState)
            if (s.thisPlayer.playerNum == winnerPlayer) {
              state.copy(thisPlayer = state.thisPlayer.copy(wins = state.thisPlayer.wins + 1))
            } else {
              state.copy(otherPlayer = state.otherPlayer.copy(wins = state.otherPlayer.wins + 1))
            }
          }
          val u1 = universe(1, s)
          val u2 = universe(2, s)
          val u3 = universe(3, s)
          collectWins(u1, u2, u3)
        }
      } else {
        // kick off next roll for same player's turn
        val s = state.copy(rollCount = newRollCount, rollSum = state.rollSum + rollOutcome, statesSeenBeforeWin = state.statesSeenBeforeWin + state.subState)
        val u1 = universe(1, s)
        val u2 = universe(2, s)
        val u3 = universe(3, s)
        collectWins(u1, u2, u3)
    }
  }

  def collectWins(s1: State, s2: State, s3: State): State = {
    val thisNum = s1.thisPlayer.playerNum
    val otherNum = s1.otherPlayer.playerNum
    var thisWins = s1.thisPlayer.wins
    var otherWins = s1.otherPlayer.wins

    if (thisNum == s2.thisPlayer.playerNum) {
      thisWins += s2.thisPlayer.wins
      otherWins += s2.otherPlayer.wins
    } else {
      thisWins += s2.otherPlayer.wins
      otherWins += s2.thisPlayer.wins
    }
    if (thisNum == s3.thisPlayer.playerNum) {
      thisWins += s3.thisPlayer.wins
      otherWins += s3.otherPlayer.wins
    } else {
      thisWins += s3.otherPlayer.wins
      otherWins += s3.thisPlayer.wins
    }
    s1.copy(
      thisPlayer = s1.thisPlayer.copy(wins = thisWins),
      otherPlayer = s1.otherPlayer.copy(wins = otherWins)
    )

  }

  case class State(
    rollCount: Int,
    rollSum: Int,
    thisPlayer: Player,
    otherPlayer: Player,
    statesSeenBeforeWin: Set[Array[Int]] = Set.empty
//    statesSeenBeforeWin: Seq[SubState] = Seq.empty
  ) {
    val subStateClass: SubState = {
      val player1position = if (thisPlayer.playerNum == 1) thisPlayer.pos else otherPlayer.pos
      val player1score = if (thisPlayer.playerNum == 1) thisPlayer.score else otherPlayer.score
      val player2position = if (thisPlayer.playerNum == 2) thisPlayer.pos else otherPlayer.pos
      val player2score = if (thisPlayer.playerNum == 2) thisPlayer.score else otherPlayer.score
      SubState(rollCount, rollSum, player1position, player1score, player2position, player2score)
    }
    val subState: Array[Int] = {
      val player1position = if (thisPlayer.playerNum == 1) thisPlayer.pos else otherPlayer.pos
      val player1score = if (thisPlayer.playerNum == 1) thisPlayer.score else otherPlayer.score
      val player2position = if (thisPlayer.playerNum == 2) thisPlayer.pos else otherPlayer.pos
      val player2score = if (thisPlayer.playerNum == 2) thisPlayer.score else otherPlayer.score
      Array(rollCount, rollSum, player1position, player1score, player2position, player2score)
    }
  }

  case class SubState(rollCount: Int, rollSum: Int, player1position: Int, player1score: Int, player2position: Int, player2score: Int)
//
//
//  // Returns the winner if there was one
//  def universe(rollOutcome: Int, state: State): State = {
//    val newRollCount = (state.rollCount + 1) % 3
//    val scoringRoll = newRollCount == 0
//    if (scoringRoll) {
//      val threeRolls = rollOutcome + state.rollSum
//      val x = (state.thisPos + threeRolls) % 10
//      val newPosition = if (x == 0) 10 else x
//      val newScore = state.thisScore + newPosition
//
//      if (newScore >= 21) {
//        state.copy(winner = Some(state.thisPlayer))
//      } else {
//        //switch players
//        universe(State(
//          rollCount = newRollCount,
//          rollSum = 0,
//          thisPlayer = state.otherPlayer,
//          otherPlayer = state.thisPlayer,
//          thisPos = state.otherPos,
//          otherPos = newPosition,
//          thisScore = state.otherScore,
//          otherScore = newScore,
//          winner = None
//        ))
//      }
//    } else {
//      // same player's turn
//      universe(state.copy(rollCount = newRollCount, rollSum = state.rollSum + rollOutcome))
//    }
//  }
//
//  case class State(
//    rollCount: Int,
//    rollSum: Int,
//    thisPlayer: Int,
//    otherPlayer: Int,
//    thisPos: Int,
//    otherPos: Int,
//    thisScore: Int,
//    otherScore: Int,
//    winner: Option[Int]
//  )

  case class Player(
                    playerNum: Int,
                    pos: Int,
                    score: Int = 0,
                    wins: Int = 0
                  )

  def main(args: Array[String]): Unit = {
    println("Test Answer 1: " + find1(4, 8))
    println("Final Answer 1: " + find1(5, 10))
    println("Aaron's Final Answer 1: " + find1(10, 6))

    println("Test Answer 2: " + find2(4, 8))
//    println("Final Answer 2: " + find2(5, 10))
  }

}
