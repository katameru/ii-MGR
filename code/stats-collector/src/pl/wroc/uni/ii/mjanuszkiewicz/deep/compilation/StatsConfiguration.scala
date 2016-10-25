package pl.wroc.uni.ii.mjanuszkiewicz.deep.compilation

import scala.collection.mutable.ArrayBuffer
import pl.wroc.uni.ii.mjanuszkiewicz.deep.ColumnAggregateDescription
import pl.wroc.uni.ii.mjanuszkiewicz.deep.GroupedDescription
import pl.wroc.uni.ii.mjanuszkiewicz.deep.SourceColumnDescription

trait StatsConfiguration {
  class StubbedGroupedDescriptions(descriptions: Seq[ColumnAggregateDescription[_]]) {
    private val groupingBuffer: ArrayBuffer[SourceColumnDescription[String]] = ArrayBuffer()
    private[StatsConfiguration] def finishStub() = GroupedDescription(groupingBuffer.toSeq, descriptions)

    def groupBy(groupings: SourceColumnDescription[String]*): this.type = {
      groupingBuffer ++= groupings
      this
    }
  }

  private val groupedDescriptions: ArrayBuffer[StubbedGroupedDescriptions] = ArrayBuffer()
  protected def select(descriptions: ColumnAggregateDescription[_]*): StubbedGroupedDescriptions = {
    val stubbedDescription = new StubbedGroupedDescriptions(descriptions)
    groupedDescriptions += stubbedDescription
    stubbedDescription
  }

  def configure: Seq[GroupedDescription] = groupedDescriptions.map(_.finishStub())
}
