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

  "Day1 Part  #1" should "return answer" in {
    val fileLines = readFileLines("input1.csv")
    println("Number of increasing values: " + Day1.findIncreasing(fileLines))
  }

  // tried 1856
  "Day1 Part #2" should "return answer" in {
    val fileLines = readFileLines("input1.csv")
    println("Number of increasing sums: " + Day1.find3SumIncreasing(fileLines))
  }

  "Day1 Part #2 sample" should "return answer" in {
    val fileLines = readFileLines("sampleInput1.2.csv")
    println("Number of increasing sums: " + Day1.find3SumIncreasing(fileLines))
  }

  "Day2 Part #1" should "return answer" in {
    val fileLines = readFileLines("input2.1.txt")
    println("Answer: " + Day2.findEnd3DLocation(fileLines))
  }

  "Day2 Part #2" should "return answer" in {
    val fileLines = readFileLines("input2.1.txt")
    println("Answer: " + Day2.findEnd3DLocationWithAim(fileLines))
  }

  "Day3 Part #1" should "return answer" in {
    val fileLines = readFileLines("input3.1.txt")
    println("Answer: " + Day3.find1(fileLines))
  }

  // 738423 too low
  "Day3 Part #2" should "return answer" in {
    val fileLines = readFileLines("input3.1.txt")
    println("Answer: " + Day3.find2(fileLines))
  }

  // 220 instead of 230
  "Day3 Part #2 sample" should "return answer" in {
    val fileLines = readFileLines("sampleInput3.1.txt")
    println("Answer: " + Day3.find2(fileLines))
  }
}
