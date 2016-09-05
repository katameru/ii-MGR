package pl.wroc.uni.ii.mjanuszkiewicz

class Entry(underlying: Map[String, String]) {
  def get = underlying.get _
}

object Entry {
  implicit def fromMap(e: Map[String, String]): Entry = new Entry(e)
}
