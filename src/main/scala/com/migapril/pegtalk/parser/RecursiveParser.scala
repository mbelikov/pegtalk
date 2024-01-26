package com.migapril.pegtalk.parser

import org.parboiled2.{ Parser, ParserInput, Rule0, StringBuilding }

class RecursiveParser(val input: ParserInput) extends Parser with StringBuilding {
  def EndlessRecursive: Rule0 = rule(EndlessRecursive ~ 'a')
  def RecursiveButOk: Rule0 = rule('a' ~ RecursiveButOk)
}

object RecursiveMain extends App {
  // it will blow the stack:
//  val result1 = new RecursiveParser("aaaa").EndlessRecursive.run()

  // it will work
  val result2 = new RecursiveParser("aaaa").RecursiveButOk.run()
}
