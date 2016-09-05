package pl.wroc.uni.ii.mjanuszkiewicz.deep.aggregation

import scala.util.Try

trait Persistable[A] {
  def persist(state: A): String
  def unpersist(persistedState: String): Option[A]
}

object Persistable {
  implicit val persistableInt: Persistable[Int] = new Persistable[Int] {
    def persist(i: Int) = i.toString
    def unpersist(s: String) = Try(s.toInt).toOption
  }

  implicit val persistableFloat: Persistable[Float] = new Persistable[Float] {
    def persist(i: Float) = i.toString
    def unpersist(s: String) = Try(s.toFloat).toOption
  }

  def persistableTuple2[T1: Persistable, T2: Persistable](sep: String = ";"): Persistable[(T1, T2)] = new Persistable[(T1, T2)] {
    def persist(t: (T1, T2)): String = implicitly[Persistable[T1]].persist(t._1) + sep + implicitly[Persistable[T2]].persist(t._2)
    def unpersist(s: String): Option[(T1, T2)] = s.split(sep) match {
      case Array(t1, t2) => Try((implicitly[Persistable[T1]].unpersist(t1).get, implicitly[Persistable[T2]].unpersist(t2).get)).toOption
      case _             => None
    }
  }

  implicit val persistableMean: Persistable[(Float, Int)] = persistableTuple2()
}