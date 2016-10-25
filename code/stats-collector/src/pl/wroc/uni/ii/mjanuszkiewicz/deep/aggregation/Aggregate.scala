package pl.wroc.uni.ii.mjanuszkiewicz
package deep.aggregation

trait Aggregate[-Elem] {
  def add(e: Elem): this.type
  def getOutput: Seq[(String, String)]

  def persistState: String
  def unpersistState(s: String): this.type
}

class StateAggregate[-Elem, State: Persistable](
    zero: State,
    stateTransit: (Elem, State) => State,
    stateOutput: State => Seq[(String, String)]) extends Aggregate[Elem] {

  var state = zero
  def add(e: Elem) = {
    state = stateTransit(e, state)
    this
  }
  def getOutput: Seq[(String, String)] = stateOutput(state)
  def persistState: String = implicitly[Persistable[State]].persist(state)
  def unpersistState(s: String): this.type = {
    state = implicitly[Persistable[State]].unpersist(s).get
    this
  }
}

class Count[-T] extends StateAggregate(
  0,
  (e: T, c: Int) => c + 1,
  (count: Int) => Seq("count" -> count.toString))

class PredicateCount[-T](p: T => Boolean) extends StateAggregate(
  0,
  (e: T, c: Int) => if (p(e)) c + 1 else c,
  (count: Int) => Seq("count" -> count.toString))

class Mean(implicit persister: Persistable[(Float, Int)]) extends Aggregate[Float] {
  type State = (Float, Int)
  var runningSum = 0f
  var count = 0

  def add(e: Float) = {
    runningSum += e
    count += 1
    this
  }

  def persistState = persister.persist((runningSum, count))
  def unpersistState(s: String) = {
    val state = persister.unpersist(s).get
    runningSum = state._1
    count = state._2
    this
  }

  def getOutput = Seq("mean" -> (runningSum / count).toString)
}
