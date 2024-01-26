package com.migapril.pegtalk.parser

import org.parboiled2._

class CaseClassParser(val input: ParserInput) extends Parser with StringBuilding {
  def ClassName: Rule1[String] = rule(
    capture(CharPredicate.Alpha ~ zeroOrMore(CharPredicate.AlphaNum))
  )

  def SimpleNumValue: Rule1[String] = rule(
    capture(oneOrMore(CharPredicate.Digit))
  )

  def SimpleStrValue: Rule1[String] = rule(
    capture(oneOrMore(CharPredicate.All -- ",()"))
  )

  def SimpleValue: Rule1[ParsedSimpleValue] = rule(
    (SimpleNumValue | SimpleStrValue) ~> (s => ParsedSimpleValue(s))
  )

  def PrintedValue: Rule1[ParsedValue] = rule(
    ObjectValue | SimpleValue
  )

  def PrintedValues: Rule1[Seq[ParsedValue]] = rule(
    '(' ~ zeroOrMore(PrintedValue).separatedBy(',') ~ ')' ~> ((values: Seq[ParsedValue]) => values)
  )

  def ObjectValue: Rule1[ParsedObjectValue] = rule(
    (ClassName ~ PrintedValues) ~> ((cn: String, values: Seq[ParsedValue]) =>
      ParsedObjectValue(cn, values)
    )
  )

  def InputLine = rule(ObjectValue ~ EOI)
}
