package pl.wroc.uni.ii.mjanuszkiewicz

import pl.wroc.uni.ii.mjanuszkiewicz.deep._
import pl.wroc.uni.ii.mjanuszkiewicz.deep.parsing._
import pl.wroc.uni.ii.mjanuszkiewicz.deep.aggregation.PredicateCounterBuilder
import pl.wroc.uni.ii.mjanuszkiewicz.deep.aggregation.MeanBuilder
import pl.wroc.uni.ii.mjanuszkiewicz.deep.aggregation.CounterBuilder
import pl.wroc.uni.ii.mjanuszkiewicz.deep.aggregation.AggregateBuilder

package object shallow {
  //Parsers
  def int = IntParser
  def float = FloatParser
  def string = StringParser
  def nothing = EmptyParser

  //Parser default
  implicit class DefaultParser[A](parser: ColumnParser[A]) {
    def or(defaultValue: A): ColumnParser[A] = { new WithDefault(parser, defaultValue) }
  }

  //Aggregators
  def count[A] = new CounterBuilder[A]
  def count[A](p: A => Boolean) = new PredicateCounterBuilder[A](p)
  def mean = new MeanBuilder

  //Describe source column
  implicit class ColumnNameWrapper(columnName: String) {
    def as[A](columnParser: ColumnParser[A]): SourceColumnDescription[A] = SourceColumnDescription(columnName, columnParser)
  }

  //Describe aggregation
  implicit class SourceDescriptionWrapper[A](description: SourceColumnDescription[A]) {
    class IncompleteDescription(aggregateBuilder: AggregateBuilder[A]) {
      def into(outputName: String) = ColumnAggregateDescription(description, aggregateBuilder, outputName)
    }

    def summarise(aggregateBuilder: AggregateBuilder[A]) = new IncompleteDescription(aggregateBuilder)
  }
}