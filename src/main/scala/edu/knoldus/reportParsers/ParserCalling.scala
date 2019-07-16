package edu.knoldus.reportParsers

import scala.xml.Elem

object ParserCalling {

  final case class Parse(content: Elem, reportType: String)

  final case class StringParser(content: Vector[String], reportType: String)

}
