package com.migapril.pegtalk.parser

import org.parboiled2.ParseError

final case class Address(
  street: String,
  building: Int,
  city: String,
  zip: Int,
)

final case class Employee(
  name: String,
  age: Int,
  address: Address,
)

/** Demo of parsing stringified case classes:
  *
  *    `ClassName(Val,Val,...)`
  *
  * where a Val can be:
  *   - simple value Int or String (should not contain '(', ')', ',')
  *   - a case object
  */
object Demo extends App {
  // Create an Address instance
  val address = Address("Main St", 123, "IL", 62704)
  // Create an Employee instance
  val employee = Employee("John Doe", 30, address)

  val addressString = address.toString
  // > ParsedObjectValue(Address,Vector(ParsedSimpleValue(Main St), ParsedSimpleValue(123), ParsedSimpleValue(IL), ParsedSimpleValue(62704)))

  val employeeString = employee.toString
  // > ParsedObjectValue(Employee,Vector(ParsedSimpleValue(John Doe), ParsedSimpleValue(30), ParsedObjectValue(Address,Vector(ParsedSimpleValue(Main St), ParsedSimpleValue(123), ParsedSimpleValue(IL), ParsedSimpleValue(62704)))))

  def parse(s: String) = {
    // parser for a specific input
    val parser = new CaseClassParser(s)

    // run the parser
    val result = parser.InputLine.run()

    def prettyError(t: Throwable) = t match {
      case pe: ParseError => parser.formatError(pe)
      case _ => t.toString
    }

    result.fold(prettyError, identity)
  }

  val parseAddressResult = parse(addressString)
  val parseEmployeeResult = parse(employeeString)

  val errorResult = parse("goo goo, daah daah")

  println(parseAddressResult)
  println(parseEmployeeResult)
  println(errorResult)
}
