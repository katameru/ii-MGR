package pl.wroc.uni.ii.mjanuszkiewicz
package deep

case class GroupedDescription(
    grouping: Seq[SourceColumnDescription[_]],
    descriptionsUnderGrouping: Seq[ColumnAggregateDescription[_]]) {
  def hasGrouping(entry: Entry) = parsedGrouping(entry).isDefined

  def parsedGrouping(entry: Entry): Option[Seq[String]] = {
    val parsed = grouping.flatMap { _.parseEntry(entry).map(p => p.toString) }
    if (parsed.size == grouping.size) {
      Some(parsed)
    } else {
      None
    }
  }
}
