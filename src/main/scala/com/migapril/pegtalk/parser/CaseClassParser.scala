package com.migapril.pegtalk.parser

import org.parboiled2._

class CaseClassParser(val input: ParserInput) extends Parser with StringBuilding {
  def SomeRule = rule(ANY)

  def InputLine = rule(SomeRule ~ EOI)
}
