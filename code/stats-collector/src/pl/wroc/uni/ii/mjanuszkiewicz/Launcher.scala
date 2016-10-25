package pl.wroc.uni.ii.mjanuszkiewicz

import pl.wroc.uni.ii.mjanuszkiewicz.deep.compilation.ConfigurationCompiler

object Launcher {
  def main(args: Array[String]) = {

    val stats = SampleStatsConfiguration
    val compiler = new ConfigurationCompiler(stats)

    val processor = compiler.compiled

    val entries = Seq(
      Map("vehicle.speed" -> "50.0", "vehicle.type" -> "car"),
      Map("vehicle.speed" -> "51.0", "vehicle.type" -> "car"),
      Map("vehicle.speed" -> "52.0", "vehicle.type" -> "car"),
      Map("vehicle.speed" -> "53.0", "vehicle.type" -> "truck"))

    entries.foreach(processor.processEntry(_))

    processor.outputResult.map {
      case (grouping, output, result) =>
        val groupingString = if (grouping.isEmpty) "all" else grouping.mkString(".")
        val resultString = result.head.productIterator.mkString(": ")
        s"$groupingString $output $resultString"
    }.sorted.foreach(println)
  }
}
