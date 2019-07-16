package edu.knoldus.reportParsers

import akka.actor.{Actor, Props}
import edu.knoldus.reportParsers.ParserCalling.Parse
import edu.knoldus.util.Constant.{SCOVERAGE_TAG, STATEMENT_RATE, sCoverage_Report}
import edu.knoldus.util.LoggerUtil._
import scala.xml.Elem

class SCoverageParser extends Actor {
  override def receive: PartialFunction[Any, Unit] = {

    case Parse(sCoverageXml: Elem, reportType: String) =>
      if (reportType.equals(sCoverage_Report)) {
        logger.info("File passed for SCoverage parser")
        val sCoverageReport = sCoverageXml \\ SCOVERAGE_TAG \ STATEMENT_RATE
        logger.info(s"Statement Rate of SCoverage Report :  $sCoverageReport")
      } else {
        logger.info("File failed for SCoverage parser")
      }
  }
}

object SCoverageParser {
  def props: Props = Props(new SCoverageParser)
}
