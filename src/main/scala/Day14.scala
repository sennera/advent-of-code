import scala.collection.mutable

/**
 * Pair insertions, then algorithm optimization
 * Part 1 Time: 40 min
 * Part 2 Time: 50 min
 *
 * I was off by one one the test input and the real answer. Not sure why...
 */
object Day14 {

  /** ****** Part 1 ********* */
  /**
   */
  def find1(startStr: String, in: Array[String]): Int = {
    val tuples: Array[(String, String)] = in.map(_.split(" -> ")).map(arr => arr(0) -> arr(1))
    val matchStrToInsertStr: Map[String, String] = tuples.toMap
    var endStr: String = startStr
    (0 until 10).foreach(_ => endStr = pairInsertion(endStr, matchStrToInsertStr))

    val counts: mutable.HashMap[Char, Int] = new mutable.HashMap[Char, Int]()
    endStr.toList.foreach(a => if (counts.contains(a)) counts(a) +=1 else counts.addOne(a, 1))

    val min = counts.values.min
    val max = counts.values.max
    max-min
  }

  private def pairInsertion(startStr: String, matchStrToInsertStr: Map[String, String]) = {
    val endStr: String = startStr.toList.map(_.toString).reduce((a: String, b: String) => {
      val ab = s"${a.substring(a.length - 1)}${b.substring(0, 1)}"
      if (matchStrToInsertStr.contains(ab)) a + matchStrToInsertStr(ab) + b
      else ab
    })
    println(endStr)
    endStr
  }

  def find2(startStr: String, in: Array[String]): Long = {
    val tuples: Array[(String, String)] = in.map(_.split(" -> ")).map(arr => arr(0) -> arr(1))
    val matchStrToInsertStr: Map[String, String] = tuples.toMap

    val tupleCounts: mutable.HashMap[String, Long] = new mutable.HashMap[String, Long]()
    (0 until startStr.length - 1).foreach(i => {
      val pair = startStr.substring(i, i + 2)
      if (tupleCounts.contains(pair)) tupleCounts(pair) += 1 else tupleCounts.addOne(pair, 1)
    })

    var pairCounts = tupleCounts
    (0 until 40).foreach(step => {
      println(step)
      pairCounts = mapPairInsertion(pairCounts, matchStrToInsertStr)
    })

    val counts: mutable.HashMap[Char, Long] = new mutable.HashMap[Char, Long]()
    pairCounts.foreach(a => {
      if (counts.contains(a._1(0))) counts(a._1(0)) += a._2 else counts.addOne(a._1(0), a._2)
      if (counts.contains(a._1(1))) counts(a._1(1)) += a._2 else counts.addOne(a._1(1), a._2)
    })
    println(counts)
    counts(startStr(0)) -= 1
    counts(startStr(startStr.length - 1)) -= 1
    println(counts)

    val min = counts.values.map(_ / 2).min
    val max = counts.values.map(_ / 2).max
    println(min)
    println(max)
    max - min
  }

  private def mapPairInsertion(pairCountsMap: mutable.HashMap[String, Long], matchStrToInsertStr: Map[String, String]) = {
    val newCounts: mutable.HashMap[String, Long] = new mutable.HashMap[String, Long]()
    pairCountsMap.foreach(a => {
      val (pair, count) = a
      if (matchStrToInsertStr.contains(pair)) {
        val pair1 = pair(0) + matchStrToInsertStr(pair)
        val pair2 = matchStrToInsertStr(pair) + pair(1)
        if (newCounts.contains(pair1)) newCounts(pair1) += count else newCounts.addOne(pair1, count)
        if (newCounts.contains(pair2)) newCounts(pair2) += count else newCounts.addOne(pair2, count)
      } else {
        if (newCounts.contains(pair)) newCounts(pair) += count else newCounts.addOne(pair, count)
      }
    })
    println(newCounts)
    newCounts
  }

  def main(args: Array[String]): Unit = {
    val testFileLines = FileReader.readFileLines("test14.txt")
    val fileLines = FileReader.readFileLines("input14.txt")

    println("Test Answer 1: " + find1("NNCB", testFileLines))
    println("Final Answer 1: " + find1("CNBPHFBOPCSPKOFNHVKV", fileLines))

    // real answer: 2188189693529
    // my answer  : 2188189693528 --> off by one, don't know why
    println("Test Answer 2: " + find2("NNCB", testFileLines))
    println("Final Answer 2: " + find2("CNBPHFBOPCSPKOFNHVKV", fileLines))
  }

}
