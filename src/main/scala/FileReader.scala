import java.nio.file.{Files, Paths}

object FileReader {

  def readFile(fileName: String): String = {
    val path = Paths.get(getClass.getClassLoader.getResource(fileName).toURI)
    new String(Files.readAllBytes(path))
  }

  def readFileLines(fileName: String): Array[String] = {
    val fileStr = readFile(fileName)
    fileStr.split("\n")
  }

}
