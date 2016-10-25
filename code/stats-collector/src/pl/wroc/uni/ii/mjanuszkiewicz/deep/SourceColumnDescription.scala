package pl.wroc.uni.ii.mjanuszkiewicz
package deep

import parsing.ColumnParser

case class SourceColumnDescription[+Column](columnName: String, columnParser: ColumnParser[Column]) {
  def parseEntry(entry: Entry): Option[Column] = {
    entry.get(columnName) flatMap columnParser.parse orElse columnParser.default
  }
}
