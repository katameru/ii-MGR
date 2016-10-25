package pl.wroc.uni.ii.mjanuszkiewicz

import deep._
import deep.compilation._
import shallow._

object SampleStatsConfiguration extends StatsConfiguration {
  select(
    "vehicle.speed" as nothing summarise count into "speed.events",
    "vehicle.speed" as float summarise mean into "speed")

  select(
    "vehicle.speed" as nothing summarise count into "speed.events",
    "vehicle.speed" as float summarise mean into "speed").
    groupBy("vehicle.type" as string)
}
