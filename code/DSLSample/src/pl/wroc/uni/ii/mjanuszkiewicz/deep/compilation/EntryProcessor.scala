package pl.wroc.uni.ii.mjanuszkiewicz
package deep.compilation

import scala.collection.mutable.Map
import scala.collection.breakOut
import pl.wroc.uni.ii.mjanuszkiewicz.Entry
import pl.wroc.uni.ii.mjanuszkiewicz.deep.ColumnAggregate
import pl.wroc.uni.ii.mjanuszkiewicz.deep.GroupedDescription

class EntryProcessor(groupedDescriptions: Seq[GroupedDescription]) {
  private val store: Map[(Seq[String], String), ColumnAggregate[_]] = Map.empty

  def processEntry(entry: Entry): Unit = {
    groupedDescriptions.
      filter(_.hasGrouping(entry)).
      foreach { groupedByDescriptions =>
        val parsedGroupByColumns = groupedByDescriptions.parsedGrouping(entry).get
        groupedByDescriptions.descriptionsUnderGrouping.foreach {
          columnAggregateDescription =>
            val storeKey = (parsedGroupByColumns, columnAggregateDescription.outputName)
            if (!store.contains(storeKey)) {
              store += (storeKey -> columnAggregateDescription.fresh)
            }
            store(storeKey).add(entry)
        }
      }
  }

  def outputResult: Seq[(Seq[String], String, Seq[(String, String)])] = {
    store.map {
      case ((group, outputName), aggregate) =>
        (group, outputName, aggregate.output)
    }(breakOut)
  }
}
