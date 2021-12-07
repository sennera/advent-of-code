import scala.collection.mutable
import scala.collection.mutable.ListBuffer

object Day6 {

  /** ****** Part 1 ********* */
  /** Although you know nothing about this specific species of lanternfish, you make some guesses about their attributes.
   * Surely, each lanternfish creates a new lanternfish once every 7 days.
   *
   * However, this process isn't necessarily synchronized between every lanternfish -
   * one lanternfish might have 2 days left until it creates another lanternfish, while another
   * might have 4. So, you can model each fish as a single number that represents the number of days until it creates a new lanternfish.
   *
   * Furthermore, you reason, a new lanternfish would surely need slightly longer
   * before it's capable of producing more lanternfish: two more days for its first cycle.
   *
   * 353079 for 80 days
   */
  def find1(in: Array[String], days: Int): Int = {
    val ins = in(0).split(",").map(Integer.parseInt)

    val fishes = new ListBuffer[Int]
    fishes.addAll(ins.toList)
    (0 until days).foreach(day => {
//      println("Day " + day)
      spawn(fishes)
//      println("Size " + fishes.size)
//      println("Time " + System.currentTimeMillis())
    })

    fishes.size
  }

  def spawn(fishes: ListBuffer[Int]): ListBuffer[Int] = {
    fishes.flatMapInPlace {
      case 0 => ListBuffer(6, 8)
      case i => ListBuffer(i - 1)
    }
  }

  /** ****** Part 2 ********* */
  /**
   * Final Answer 2: 1605400130036
   */
  def find2(in: Array[String], days: Int): Long = {
    val ins = in(0).split(",").map(Integer.parseInt)

    var ages = new mutable.HashMap[Long, Long]
    (0L to 8L).foreach(i => ages += (i -> 0L))
    ins.foreach(i => ages += (i.asInstanceOf[Number].longValue -> (ages(i) + 1L)))

    (0 until days).foreach(day => {
//      println("Day " + day)
      ages = spawnWithHashmap(ages)
//      println("Ages " + ages)
//      println("Size " + ages.values.sum)
//      println("Time " + System.currentTimeMillis())
    })

    ages.values.sum
  }

  def spawnWithHashmap(ages: mutable.HashMap[Long, Long]): mutable.HashMap[Long, Long] = {
    var newAges = new mutable.HashMap[Long, Long]
    ages.foreach {
      case (0, count) => newAges += (6L -> newAges.get(6).map(_ + count).getOrElse(count), 8L -> count)
      case (age, count) => newAges += (age - 1 -> newAges.get(age - 1).map(_ + count).getOrElse(count))
    }
    newAges
  }

  def spawnWithFlatmap(fishes: ListBuffer[Int]): ListBuffer[Int] = {
    fishes.flatMap {
      case 0 => ListBuffer(6, 8)
      case i => ListBuffer(i - 1)
    }
  }

  def spawnWithAppend(fishes: ListBuffer[Int]): ListBuffer[Int] = {
    val updatedFishes = new ListBuffer[Int]
    fishes.foreach {
      case 0 =>
        updatedFishes.addOne(6)
        updatedFishes.addOne(8)
      case i => updatedFishes.addOne(i - 1)
    }
    updatedFishes
  }

  /** ****** RUN IT! ********* */
  def main(args: Array[String]): Unit = {
    val testFileLines = FileReader.readFileLines("test6.txt")
    println("Test Answer 1: " + find1(testFileLines, 80))

    val fileLines = FileReader.readFileLines("input6.txt")
    println("Final Answer: " + find1(fileLines, 80))

    val testFileLines2 = FileReader.readFileLines("test6.txt")
    println("Test Answer 2: " + find2(testFileLines2, 80))

    // so far, 141 days in ~7 min
    val fileLines2 = FileReader.readFileLines("input6.txt")
    println("Final Answer 2: " + find2(fileLines2, 256))

//    Test Answer 1: 5934
//    Final Answer: 353079
//    Test Answer 2: 5934
//    Final Answer 2: 1605400130036
  }

  /**
   * /Library/Java/JavaVirtualMachines/adoptopenjdk-8.jdk/Contents/Home/bin/java -javaagent:/Applications/IntelliJ IDEA CE.app/Contents/lib/idea_rt.jar=64534:/Applications/IntelliJ IDEA CE.app/Contents/bin -Dfile.encoding=UTF-8 -classpath /Library/Java/JavaVirtualMachines/adoptopenjdk-8.jdk/Contents/Home/jre/lib/charsets.jar:/Library/Java/JavaVirtualMachines/adoptopenjdk-8.jdk/Contents/Home/jre/lib/ext/cldrdata.jar:/Library/Java/JavaVirtualMachines/adoptopenjdk-8.jdk/Contents/Home/jre/lib/ext/dnsns.jar:/Library/Java/JavaVirtualMachines/adoptopenjdk-8.jdk/Contents/Home/jre/lib/ext/jaccess.jar:/Library/Java/JavaVirtualMachines/adoptopenjdk-8.jdk/Contents/Home/jre/lib/ext/localedata.jar:/Library/Java/JavaVirtualMachines/adoptopenjdk-8.jdk/Contents/Home/jre/lib/ext/nashorn.jar:/Library/Java/JavaVirtualMachines/adoptopenjdk-8.jdk/Contents/Home/jre/lib/ext/sunec.jar:/Library/Java/JavaVirtualMachines/adoptopenjdk-8.jdk/Contents/Home/jre/lib/ext/sunjce_provider.jar:/Library/Java/JavaVirtualMachines/adoptopenjdk-8.jdk/Contents/Home/jre/lib/ext/sunpkcs11.jar:/Library/Java/JavaVirtualMachines/adoptopenjdk-8.jdk/Contents/Home/jre/lib/ext/zipfs.jar:/Library/Java/JavaVirtualMachines/adoptopenjdk-8.jdk/Contents/Home/jre/lib/jce.jar:/Library/Java/JavaVirtualMachines/adoptopenjdk-8.jdk/Contents/Home/jre/lib/jsse.jar:/Library/Java/JavaVirtualMachines/adoptopenjdk-8.jdk/Contents/Home/jre/lib/management-agent.jar:/Library/Java/JavaVirtualMachines/adoptopenjdk-8.jdk/Contents/Home/jre/lib/resources.jar:/Library/Java/JavaVirtualMachines/adoptopenjdk-8.jdk/Contents/Home/jre/lib/rt.jar:/Library/Java/JavaVirtualMachines/adoptopenjdk-8.jdk/Contents/Home/lib/dt.jar:/Library/Java/JavaVirtualMachines/adoptopenjdk-8.jdk/Contents/Home/lib/jconsole.jar:/Library/Java/JavaVirtualMachines/adoptopenjdk-8.jdk/Contents/Home/lib/sa-jdi.jar:/Library/Java/JavaVirtualMachines/adoptopenjdk-8.jdk/Contents/Home/lib/tools.jar:/Users/Shared/Development/NikeBuild/code/advent-of-code/target/scala-2.13/classes:/Users/Shared/Development/NikeBuild/.ivy2/cache/org.scala-lang.modules/scala-xml_2.13/bundles/scala-xml_2.13-1.2.0.jar:/Users/Shared/Development/NikeBuild/.ivy2/cache/org.scala-lang/scala-library/jars/scala-library-2.13.0.jar:/Users/Shared/Development/NikeBuild/.ivy2/cache/org.scala-lang/scala-reflect/jars/scala-reflect-2.13.0.jar:/Users/Shared/Development/NikeBuild/.ivy2/cache/org.scalactic/scalactic_2.13/bundles/scalactic_2.13-3.2.0.jar:/Users/Shared/Development/NikeBuild/.ivy2/cache/org.scalatest/scalatest-compatible/bundles/scalatest-compatible-3.2.0.jar:/Users/Shared/Development/NikeBuild/.ivy2/cache/org.scalatest/scalatest-core_2.13/bundles/scalatest-core_2.13-3.2.0.jar:/Users/Shared/Development/NikeBuild/.ivy2/cache/org.scalatest/scalatest-diagrams_2.13/bundles/scalatest-diagrams_2.13-3.2.0.jar:/Users/Shared/Development/NikeBuild/.ivy2/cache/org.scalatest/scalatest-featurespec_2.13/bundles/scalatest-featurespec_2.13-3.2.0.jar:/Users/Shared/Development/NikeBuild/.ivy2/cache/org.scalatest/scalatest-flatspec_2.13/bundles/scalatest-flatspec_2.13-3.2.0.jar:/Users/Shared/Development/NikeBuild/.ivy2/cache/org.scalatest/scalatest-freespec_2.13/bundles/scalatest-freespec_2.13-3.2.0.jar:/Users/Shared/Development/NikeBuild/.ivy2/cache/org.scalatest/scalatest-funspec_2.13/bundles/scalatest-funspec_2.13-3.2.0.jar:/Users/Shared/Development/NikeBuild/.ivy2/cache/org.scalatest/scalatest-funsuite_2.13/bundles/scalatest-funsuite_2.13-3.2.0.jar:/Users/Shared/Development/NikeBuild/.ivy2/cache/org.scalatest/scalatest-matchers-core_2.13/bundles/scalatest-matchers-core_2.13-3.2.0.jar:/Users/Shared/Development/NikeBuild/.ivy2/cache/org.scalatest/scalatest-mustmatchers_2.13/bundles/scalatest-mustmatchers_2.13-3.2.0.jar:/Users/Shared/Development/NikeBuild/.ivy2/cache/org.scalatest/scalatest-propspec_2.13/bundles/scalatest-propspec_2.13-3.2.0.jar:/Users/Shared/Development/NikeBuild/.ivy2/cache/org.scalatest/scalatest-refspec_2.13/bundles/scalatest-refspec_2.13-3.2.0.jar:/Users/Shared/Development/NikeBuild/.ivy2/cache/org.scalatest/scalatest-shouldmatchers_2.13/bundles/scalatest-shouldmatchers_2.13-3.2.0.jar:/Users/Shared/Development/NikeBuild/.ivy2/cache/org.scalatest/scalatest-wordspec_2.13/bundles/scalatest-wordspec_2.13-3.2.0.jar:/Users/Shared/Development/NikeBuild/.ivy2/cache/org.scalatest/scalatest_2.13/bundles/scalatest_2.13-3.2.0.jar Day6
   * Day 0
   * Size 5
   * Time 1638810043166
   * Day 1
   * Size 6
   * Time 1638810043167
   * Day 2
   * Size 7
   * Time 1638810043167
   * Day 3
   * Size 9
   * Time 1638810043167
   * Day 4
   * Size 10
   * Time 1638810043167
   * Day 5
   * Size 10
   * Time 1638810043167
   * Day 6
   * Size 10
   * Time 1638810043168
   * Day 7
   * Size 10
   * Time 1638810043168
   * Day 8
   * Size 11
   * Time 1638810043168
   * Day 9
   * Size 12
   * Time 1638810043169
   * Day 10
   * Size 15
   * Time 1638810043169
   * Day 11
   * Size 17
   * Time 1638810043170
   * Day 12
   * Size 19
   * Time 1638810043170
   * Day 13
   * Size 20
   * Time 1638810043170
   * Day 14
   * Size 20
   * Time 1638810043170
   * Day 15
   * Size 21
   * Time 1638810043171
   * Day 16
   * Size 22
   * Time 1638810043171
   * Day 17
   * Size 26
   * Time 1638810043172
   * Day 18
   * Size 29
   * Time 1638810043172
   * Day 19
   * Size 34
   * Time 1638810043173
   * Day 20
   * Size 37
   * Time 1638810043173
   * Day 21
   * Size 39
   * Time 1638810043174
   * Day 22
   * Size 41
   * Time 1638810043175
   * Day 23
   * Size 42
   * Time 1638810043175
   * Day 24
   * Size 47
   * Time 1638810043176
   * Day 25
   * Size 51
   * Time 1638810043176
   * Day 26
   * Size 60
   * Time 1638810043179
   * Day 27
   * Size 66
   * Time 1638810043179
   * Day 28
   * Size 73
   * Time 1638810043180
   * Day 29
   * Size 78
   * Time 1638810043181
   * Day 30
   * Size 81
   * Time 1638810043181
   * Day 31
   * Size 88
   * Time 1638810043182
   * Day 32
   * Size 93
   * Time 1638810043182
   * Day 33
   * Size 107
   * Time 1638810043183
   * Day 34
   * Size 117
   * Time 1638810043183
   * Day 35
   * Size 133
   * Time 1638810043183
   * Day 36
   * Size 144
   * Time 1638810043183
   * Day 37
   * Size 154
   * Time 1638810043184
   * Day 38
   * Size 166
   * Time 1638810043184
   * Day 39
   * Size 174
   * Time 1638810043184
   * Day 40
   * Size 195
   * Time 1638810043184
   * Day 41
   * Size 210
   * Time 1638810043185
   * Day 42
   * Size 240
   * Time 1638810043185
   * Day 43
   * Size 261
   * Time 1638810043185
   * Day 44
   * Size 287
   * Time 1638810043186
   * Day 45
   * Size 310
   * Time 1638810043186
   * Day 46
   * Size 328
   * Time 1638810043187
   * Day 47
   * Size 361
   * Time 1638810043187
   * Day 48
   * Size 384
   * Time 1638810043187
   * Day 49
   * Size 435
   * Time 1638810043188
   * Day 50
   * Size 471
   * Time 1638810043188
   * Day 51
   * Size 527
   * Time 1638810043188
   * Day 52
   * Size 571
   * Time 1638810043189
   * Day 53
   * Size 615
   * Time 1638810043189
   * Day 54
   * Size 671
   * Time 1638810043190
   * Day 55
   * Size 712
   * Time 1638810043190
   * Day 56
   * Size 796
   * Time 1638810043190
   * Day 57
   * Size 855
   * Time 1638810043191
   * Day 58
   * Size 962
   * Time 1638810043192
   * Day 59
   * Size 1042
   * Time 1638810043192
   * Day 60
   * Size 1142
   * Time 1638810043193
   * Day 61
   * Size 1242
   * Time 1638810043194
   * Day 62
   * Size 1327
   * Time 1638810043195
   * Day 63
   * Size 1467
   * Time 1638810043196
   * Day 64
   * Size 1567
   * Time 1638810043196
   * Day 65
   * Size 1758
   * Time 1638810043197
   * Day 66
   * Size 1897
   * Time 1638810043199
   * Day 67
   * Size 2104
   * Time 1638810043199
   * Day 68
   * Size 2284
   * Time 1638810043200
   * Day 69
   * Size 2469
   * Time 1638810043201
   * Day 70
   * Size 2709
   * Time 1638810043202
   * Day 71
   * Size 2894
   * Time 1638810043204
   * Day 72
   * Size 3225
   * Time 1638810043206
   * Day 73
   * Size 3464
   * Time 1638810043207
   * Day 74
   * Size 3862
   * Time 1638810043208
   * Day 75
   * Size 4181
   * Time 1638810043209
   * Day 76
   * Size 4573
   * Time 1638810043210
   * Day 77
   * Size 4993
   * Time 1638810043212
   * Day 78
   * Size 5363
   * Time 1638810043214
   * Day 79
   * Size 5934
   * Time 1638810043216
   * Test Answer 1: 5934
   * Day 0
   * Size 300
   * Time 1638810043218
   * Day 1
   * Size 388
   * Time 1638810043219
   * Day 2
   * Size 447
   * Time 1638810043219
   * Day 3
   * Size 493
   * Time 1638810043219
   * Day 4
   * Size 550
   * Time 1638810043219
   * Day 5
   * Size 600
   * Time 1638810043219
   * Day 6
   * Size 600
   * Time 1638810043220
   * Day 7
   * Size 600
   * Time 1638810043220
   * Day 8
   * Size 688
   * Time 1638810043220
   * Day 9
   * Size 747
   * Time 1638810043221
   * Day 10
   * Size 881
   * Time 1638810043221
   * Day 11
   * Size 997
   * Time 1638810043221
   * Day 12
   * Size 1093
   * Time 1638810043221
   * Day 13
   * Size 1150
   * Time 1638810043222
   * Day 14
   * Size 1200
   * Time 1638810043222
   * Day 15
   * Size 1288
   * Time 1638810043222
   * Day 16
   * Size 1347
   * Time 1638810043223
   * Day 17
   * Size 1569
   * Time 1638810043223
   * Day 18
   * Size 1744
   * Time 1638810043224
   * Day 19
   * Size 1974
   * Time 1638810043224
   * Day 20
   * Size 2147
   * Time 1638810043225
   * Day 21
   * Size 2293
   * Time 1638810043225
   * Day 22
   * Size 2438
   * Time 1638810043226
   * Day 23
   * Size 2547
   * Time 1638810043227
   * Day 24
   * Size 2857
   * Time 1638810043227
   * Day 25
   * Size 3091
   * Time 1638810043228
   * Day 26
   * Size 3543
   * Time 1638810043229
   * Day 27
   * Size 3891
   * Time 1638810043229
   * Day 28
   * Size 4267
   * Time 1638810043230
   * Day 29
   * Size 4585
   * Time 1638810043231
   * Day 30
   * Size 4840
   * Time 1638810043232
   * Day 31
   * Size 5295
   * Time 1638810043233
   * Day 32
   * Size 5638
   * Time 1638810043234
   * Day 33
   * Size 6400
   * Time 1638810043235
   * Day 34
   * Size 6982
   * Time 1638810043236
   * Day 35
   * Size 7810
   * Time 1638810043237
   * Day 36
   * Size 8476
   * Time 1638810043238
   * Day 37
   * Size 9107
   * Time 1638810043240
   * Day 38
   * Size 9880
   * Time 1638810043241
   * Day 39
   * Size 10478
   * Time 1638810043243
   * Day 40
   * Size 11695
   * Time 1638810043244
   * Day 41
   * Size 12620
   * Time 1638810043246
   * Day 42
   * Size 14210
   * Time 1638810043247
   * Day 43
   * Size 15458
   * Time 1638810043249
   * Day 44
   * Size 16917
   * Time 1638810043251
   * Day 45
   * Size 18356
   * Time 1638810043253
   * Day 46
   * Size 19585
   * Time 1638810043259
   * Day 47
   * Size 21575
   * Time 1638810043260
   * Day 48
   * Size 23098
   * Time 1638810043261
   * Day 49
   * Size 25905
   * Time 1638810043263
   * Day 50
   * Size 28078
   * Time 1638810043264
   * Day 51
   * Size 31127
   * Time 1638810043266
   * Day 52
   * Size 33814
   * Time 1638810043267
   * Day 53
   * Size 36502
   * Time 1638810043270
   * Day 54
   * Size 39931
   * Time 1638810043272
   * Day 55
   * Size 42683
   * Time 1638810043274
   * Day 56
   * Size 47480
   * Time 1638810043276
   * Day 57
   * Size 51176
   * Time 1638810043279
   * Day 58
   * Size 57032
   * Time 1638810043289
   * Day 59
   * Size 61892
   * Time 1638810043291
   * Day 60
   * Size 67629
   * Time 1638810043294
   * Day 61
   * Size 73745
   * Time 1638810043297
   * Day 62
   * Size 79185
   * Time 1638810043301
   * Day 63
   * Size 87411
   * Time 1638810043306
   * Day 64
   * Size 93859
   * Time 1638810043316
   * Day 65
   * Size 104512
   * Time 1638810043320
   * Day 66
   * Size 113068
   * Time 1638810043325
   * Day 67
   * Size 124661
   * Time 1638810043330
   * Day 68
   * Size 135637
   * Time 1638810043345
   * Day 69
   * Size 146814
   * Time 1638810043351
   * Day 70
   * Size 161156
   * Time 1638810043360
   * Day 71
   * Size 173044
   * Time 1638810043372
   * Day 72
   * Size 191923
   * Time 1638810043397
   * Day 73
   * Size 206927
   * Time 1638810043462
   * Day 74
   * Size 229173
   * Time 1638810043481
   * Day 75
   * Size 248705
   * Time 1638810043511
   * Day 76
   * Size 271475
   * Time 1638810043540
   * Day 77
   * Size 296793
   * Time 1638810043598
   * Day 78
   * Size 319858
   * Time 1638810043640
   * Day 79
   * Size 353079
   * Time 1638810043685
   * Final Answer: 353079
   * Day 0
   * Size 300
   * Time 1638810043687
   * Day 1
   * Size 388
   * Time 1638810043687
   * Day 2
   * Size 447
   * Time 1638810043687
   * Day 3
   * Size 493
   * Time 1638810043687
   * Day 4
   * Size 550
   * Time 1638810043687
   * Day 5
   * Size 600
   * Time 1638810043687
   * Day 6
   * Size 600
   * Time 1638810043687
   * Day 7
   * Size 600
   * Time 1638810043687
   * Day 8
   * Size 688
   * Time 1638810043687
   * Day 9
   * Size 747
   * Time 1638810043687
   * Day 10
   * Size 881
   * Time 1638810043688
   * Day 11
   * Size 997
   * Time 1638810043688
   * Day 12
   * Size 1093
   * Time 1638810043688
   * Day 13
   * Size 1150
   * Time 1638810043688
   * Day 14
   * Size 1200
   * Time 1638810043688
   * Day 15
   * Size 1288
   * Time 1638810043688
   * Day 16
   * Size 1347
   * Time 1638810043688
   * Day 17
   * Size 1569
   * Time 1638810043689
   * Day 18
   * Size 1744
   * Time 1638810043689
   * Day 19
   * Size 1974
   * Time 1638810043690
   * Day 20
   * Size 2147
   * Time 1638810043690
   * Day 21
   * Size 2293
   * Time 1638810043690
   * Day 22
   * Size 2438
   * Time 1638810043691
   * Day 23
   * Size 2547
   * Time 1638810043691
   * Day 24
   * Size 2857
   * Time 1638810043692
   * Day 25
   * Size 3091
   * Time 1638810043692
   * Day 26
   * Size 3543
   * Time 1638810043693
   * Day 27
   * Size 3891
   * Time 1638810043694
   * Day 28
   * Size 4267
   * Time 1638810043695
   * Day 29
   * Size 4585
   * Time 1638810043696
   * Day 30
   * Size 4840
   * Time 1638810043698
   * Day 31
   * Size 5295
   * Time 1638810043698
   * Day 32
   * Size 5638
   * Time 1638810043699
   * Day 33
   * Size 6400
   * Time 1638810043700
   * Day 34
   * Size 6982
   * Time 1638810043701
   * Day 35
   * Size 7810
   * Time 1638810043702
   * Day 36
   * Size 8476
   * Time 1638810043703
   * Day 37
   * Size 9107
   * Time 1638810043705
   * Day 38
   * Size 9880
   * Time 1638810043706
   * Day 39
   * Size 10478
   * Time 1638810043708
   * Day 40
   * Size 11695
   * Time 1638810043716
   * Day 41
   * Size 12620
   * Time 1638810043721
   * Day 42
   * Size 14210
   * Time 1638810043727
   * Day 43
   * Size 15458
   * Time 1638810043731
   * Day 44
   * Size 16917
   * Time 1638810043735
   * Day 45
   * Size 18356
   * Time 1638810043740
   * Day 46
   * Size 19585
   * Time 1638810043747
   * Day 47
   * Size 21575
   * Time 1638810043750
   * Day 48
   * Size 23098
   * Time 1638810043755
   * Day 49
   * Size 25905
   * Time 1638810043762
   * Day 50
   * Size 28078
   * Time 1638810043766
   * Day 51
   * Size 31127
   * Time 1638810043772
   * Day 52
   * Size 33814
   * Time 1638810043777
   * Day 53
   * Size 36502
   * Time 1638810043781
   * Day 54
   * Size 39931
   * Time 1638810043786
   * Day 55
   * Size 42683
   * Time 1638810043792
   * Day 56
   * Size 47480
   * Time 1638810043798
   * Day 57
   * Size 51176
   * Time 1638810043803
   * Day 58
   * Size 57032
   * Time 1638810043811
   * Day 59
   * Size 61892
   * Time 1638810043819
   * Day 60
   * Size 67629
   * Time 1638810043828
   * Day 61
   * Size 73745
   * Time 1638810043842
   * Day 62
   * Size 79185
   * Time 1638810043850
   * Day 63
   * Size 87411
   * Time 1638810043863
   * Day 64
   * Size 93859
   * Time 1638810043872
   * Day 65
   * Size 104512
   * Time 1638810043887
   * Day 66
   * Size 113068
   * Time 1638810043901
   * Day 67
   * Size 124661
   * Time 1638810043909
   * Day 68
   * Size 135637
   * Time 1638810043918
   * Day 69
   * Size 146814
   * Time 1638810043928
   * Day 70
   * Size 161156
   * Time 1638810043937
   * Day 71
   * Size 173044
   * Time 1638810043949
   * Day 72
   * Size 191923
   * Time 1638810043958
   * Day 73
   * Size 206927
   * Time 1638810043967
   * Day 74
   * Size 229173
   * Time 1638810043991
   * Day 75
   * Size 248705
   * Time 1638810044003
   * Day 76
   * Size 271475
   * Time 1638810044019
   * Day 77
   * Size 296793
   * Time 1638810044036
   * Day 78
   * Size 319858
   * Time 1638810044077
   * Day 79
   * Size 353079
   * Time 1638810044112
   * Day 80
   * Size 379971
   * Time 1638810044150
   * Day 81
   * Size 421096
   * Time 1638810044203
   * Day 82
   * Size 455632
   * Time 1638810044261
   * Day 83
   * Size 500648
   * Time 1638810044349
   * Day 84
   * Size 545498
   * Time 1638810044418
   * Day 85
   * Size 591333
   * Time 1638810044444
   * Day 86
   * Size 649872
   * Time 1638810044475
   * Day 87
   * Size 699829
   * Time 1638810044523
   * Day 88
   * Size 774175
   * Time 1638810044593
   * Day 89
   * Size 835603
   * Time 1638810044691
   * Day 90
   * Size 921744
   * Time 1638810044764
   * Day 91
   * Size 1001130
   * Time 1638810044838
   * Day 92
   * Size 1091981
   * Time 1638810044895
   * Day 93
   * Size 1195370
   * Time 1638810045049
   * Day 94
   * Size 1291162
   * Time 1638810045309
   * Day 95
   * Size 1424047
   * Time 1638810045368
   * Day 96
   * Size 1535432
   * Time 1638810045449
   * Day 97
   * Size 1695919
   * Time 1638810045532
   * Day 98
   * Size 1836733
   * Time 1638810045723
   * Day 99
   * Size 2013725
   * Time 1638810045818
   * Day 100
   * Size 2196500
   * Time 1638810045920
   * Day 101
   * Size 2383143
   * Time 1638810046194
   * Day 102
   * Size 2619417
   * Time 1638810046775
   * Day 103
   * Size 2826594
   * Time 1638810046887
   * Day 104
   * Size 3119966
   * Time 1638810047026
   * Day 105
   * Size 3372165
   * Time 1638810048013
   * Day 106
   * Size 3709644
   * Time 1638810048203
   * Day 107
   * Size 4033233
   * Time 1638810048752
   * Day 108
   * Size 4396868
   * Time 1638810048956
   * Day 109
   * Size 4815917
   * Time 1638810049951
   * Day 110
   * Size 5209737
   * Time 1638810050335
   * Day 111
   * Size 5739383
   * Time 1638810050810
   * Day 112
   * Size 6198759
   * Time 1638810051996
   * Day 113
   * Size 6829610
   * Time 1638810052495
   * Day 114
   * Size 7405398
   * Time 1638810054569
   * Day 115
   * Size 8106512
   * Time 1638810055238
   * Day 116
   * Size 8849150
   * Time 1638810057786
   * Day 117
   * Size 9606605
   * Time 1638810058524
   * Day 118
   * Size 10555300
   * Time 1638810061363
   * Day 119
   * Size 11408496
   * Time 1638810062721
   * Day 120
   * Size 12568993
   * Time 1638810066313
   * Day 121
   * Size 13604157
   * Time 1638810067900
   * Day 122
   * Size 14936122
   * Time 1638810071428
   * Day 123
   * Size 16254548
   * Time 1638810073791
   * Day 124
   * Size 17713117
   * Time 1638810077371
   * Day 125
   * Size 19404450
   * Time 1638810082504
   * Day 126
   * Size 21015101
   * Time 1638810085907
   * Day 127
   * Size 23124293
   * Time 1638810091910
   * Day 128
   * Size 25012653
   * Time 1638810096843
   * Day 129
   * Size 27505115
   * Time 1638810102823
   * Day 130
   * Size 29858705
   * Time 1638810111855
   * Day 131
   *
   * 9.032 sec
   *
   * -----------------
   *
   * Flatmap
   *
   * Day 129
   * Size 27505115
   * Time 1638810375461
   * Day 130
   * Size 29858705
   * Time 1638810381576
   *
   * 6.115 sec
   *
   * ---------------
   *
   * appending
   *
   * Day 129
   * Size 27505115
   * Time 1638810601186
   * Day 130
   * Size 29858705
   * Time 1638810607940
   *
   * 6.754 sec
   */

}
