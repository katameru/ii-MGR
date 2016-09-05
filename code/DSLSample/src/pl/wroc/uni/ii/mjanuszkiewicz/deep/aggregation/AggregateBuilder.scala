package pl.wroc.uni.ii.mjanuszkiewicz
package deep
package aggregation

trait AggregateBuilder[-Elem] {
  type Aggr <: Aggregate[Elem]
  def fresh: Aggr
}

class BasicBuilder[-Elem, AggType <: Aggregate[Elem]](constructor: => AggType) extends AggregateBuilder[Elem] {
  type Aggr = AggType
  def fresh = constructor
}

class CounterBuilder[Elem]
  extends BasicBuilder[Elem, Count[Elem]](new Count[Elem])

class PredicateCounterBuilder[Elem](p: Elem => Boolean)
  extends BasicBuilder[Elem, PredicateCount[Elem]](new PredicateCount[Elem](p))

class MeanBuilder
  extends BasicBuilder[Float, Mean](new Mean)

