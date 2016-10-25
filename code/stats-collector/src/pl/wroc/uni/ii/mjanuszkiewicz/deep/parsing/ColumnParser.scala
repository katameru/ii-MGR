package pl.wroc.uni.ii.mjanuszkiewicz
package deep.parsing

import scala.util.Try

trait ColumnParser[+A] {
  def name: String
  def parse(column: String): Option[A]
  def default: Option[A] = None
}

class SimpleParser[+A](val name: String, parser: String => Option[A]) extends ColumnParser[A] {
  def parse(column: String) = parser(column)
}

class WithDefault[+A](baseParser: ColumnParser[A], defaultValue: A) extends ColumnParser[A] {
  val name = s"${baseParser.name} or $default"
  def parse(column: String) = baseParser.parse(column)
  override def default = Some(defaultValue)
}

object IntParser extends SimpleParser[Int]("int", col => Try(col.toInt).toOption)

object FloatParser extends SimpleParser[Float]("float", col => Try(col.toFloat).toOption)

object StringParser extends SimpleParser[String]("string", col => Some(col))

object EmptyParser extends SimpleParser[Unit]("empty", col => Some(()))
