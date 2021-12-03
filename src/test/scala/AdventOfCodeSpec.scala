import java.nio.file.{Files, Paths}

class AdventOfCodeSpec extends UnitSpec {

  def readFile(fileName: String): String = {
    val path = Paths.get(getClass.getClassLoader.getResource(fileName).toURI)
    new String(Files.readAllBytes(path))
  }

  def readFileLines(fileName: String): Array[String] = {
    val fileStr = readFile(fileName)
    fileStr.split("\n")
  }

  "Day1 Part 1 #1" should "return answer" in {
    val fileLines = readFileLines("input1.csv")
    println("Number of increasing values: " + Day1.findIncreasing(fileLines))
  }

  // tried 1856
  "Day1 Part 1 #2" should "return answer" in {
    val fileLines = readFileLines("input1.csv")
    println("Number of increasing sums: " + Day1.find3SumIncreasing(fileLines))
  }

  "Day1 Part 1 #2 sample" should "return answer" in {
    val fileLines = readFileLines("sampleInput1.2.csv")
    println("Number of increasing sums: " + Day1.find3SumIncreasing(fileLines))
  }

  "Day1 Part 2 #1" should "return answer" in {
    val fileLines = readFileLines("input2.1.txt")
    println("Answer: " + Day2.findEnd3DLocation(fileLines))
  }

  "Day1 Part 2 #2" should "return answer" in {
    val fileLines = readFileLines("input2.1.txt")
    println("Answer: " + Day2.findEnd3DLocationWithAim(fileLines))
  }
}
