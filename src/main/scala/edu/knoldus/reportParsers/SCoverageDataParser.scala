package edu.knoldus.reportParsers

import akka.actor.{Actor, Props}
import edu.knoldus.model.{ClassWiseCoverage, PackageWiseCoverage}
import edu.knoldus.reportParsers.ParserCalling.Parse
import edu.knoldus.util.Constant._
import edu.knoldus.util.LoggerUtil._

import scala.xml.Elem

class SCoverageDataParser extends Actor {

  override def receive: PartialFunction[Any, Unit] = {
    case Parse(sCoverageXml: Elem, reportType: String) =>
      if (reportType.equals(sCoverage_Report)) {
        if (reportType.equals(sCoverage_Report)) {
          logger.info("File passed for SCoverage Data parser")
          val sCoverageData = (sCoverageXml \\ "package").map { packageNode =>
            val packageName = (packageNode \ "@name").text
            val packageCoverage = (packageNode \ STATEMENT_RATE).toString().toDouble
            val classWithCoverage = (packageNode \\ "class").map { classNode =>
              val className = (classNode \ "@name").text
              val classCoverage = (classNode \ STATEMENT_RATE).toString().toDouble
              ClassWiseCoverage(className, classCoverage)
            }
            PackageWiseCoverage(packageName, packageCoverage, classWithCoverage)
          }
          logger.info(s"SCoverage Data: $sCoverageData")
        }
        else {
          logger.info("File failed for SCoverage Data parser")
          sender ! None
        }
      }
      else {
        logger.info("File failed for SCoverage Data parser")
      }
  }
}

object SCoverageDataParser {
  def props: Props = Props(new SCoverageDataParser)
}
