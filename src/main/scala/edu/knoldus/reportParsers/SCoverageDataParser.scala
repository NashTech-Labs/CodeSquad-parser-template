package edu.knoldus.reportParsers

import akka.actor.{Actor, Props}
import edu.knoldus.model.{ClassWiseCoverage, PackageWiseCoverage}
import edu.knoldus.reportParsers.ParserCalling.Parse
import edu.knoldus.util.Constant._
import scala.xml.Elem
import edu.knoldus.util.LoggerUtil._

class SCoverageDataParser extends Actor {

  override def receive: PartialFunction[Any, Unit] = {
    case Parse(sCoverageXml: Elem,reportType: String) =>
      if (reportType.equals(sCoverage_Report)) {
        logger.info("File passed for SCoverage Data parser")
        val sCoverageData = (sCoverageXml \\ PACKAGE).map { packageNode =>
          val packageName = (packageNode \ NAME_TAG).text
          val packageCoverage = (packageNode \\ STATEMENT_RATE).toList.headOption.map(_.toString.toDouble)
          val classesInPackage = packageNode \\ CLASS

          val classWithCoverage = classesInPackage.map { clazz =>
            val className = (clazz \ NAME_TAG).text
            val classCoverage = (classesInPackage \\ STATEMENT_RATE).toList.headOption.map(_.toString.toDouble)
            classCoverage.map(coverage => ClassWiseCoverage(className, coverage))
          }.toList.flatten

          logger.info(s"classWithCoverage  : $classWithCoverage")
          packageCoverage.map(coverage => PackageWiseCoverage(packageName, coverage, classWithCoverage))
        }.toList.flatten
        logger.info(s"SCoverage Data: $sCoverageData")
      }
      else {
        logger.info("File failed for SCoverage Data parser")
      }
  }
}

object SCoverageDataParser {
  def props: Props = Props(new SCoverageDataParser)
}
