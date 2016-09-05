package pl.wroc.uni.ii.mjanuszkiewicz
package deep

import aggregation.{ Aggregate, AggregateBuilder }

case class ColumnAggregateDescription[ColumnType](
    sourceColumnDescription: SourceColumnDescription[ColumnType],
    aggregateBuilder: AggregateBuilder[ColumnType],
    outputName: String) {
  def fresh: ColumnAggregate[ColumnType] = new ColumnAggregate(sourceColumnDescription, aggregateBuilder.fresh, outputName)
}

class ColumnAggregate[-ColumnType](
    sourceColumnDescription: SourceColumnDescription[ColumnType],
    aggregate: Aggregate[ColumnType],
    outputName: String) {
  def add(entry: Entry) = sourceColumnDescription.parseEntry(entry).foreach { aggregate.add(_) }
  def output: Seq[(String, String)] = aggregate.getOutput
}
