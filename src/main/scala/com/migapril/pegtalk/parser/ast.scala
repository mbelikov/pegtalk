package com.migapril.pegtalk.parser

sealed trait ParsedValue
case class ParsedSimpleValue(value: String) extends ParsedValue
case class ParsedObjectValue(className: String, values: Seq[ParsedValue]) extends ParsedValue
