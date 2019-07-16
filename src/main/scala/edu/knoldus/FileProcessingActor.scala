package edu.knoldus

import java.io.File
import akka.actor.{Actor, ActorRef}
import edu.knoldus.model.ProcessFile
import edu.knoldus.reportParsers.ParserCalling.Parse
import edu.knoldus.reportParsers.{SCoverageDataParser, SCoverageParser, XMLValidator}
import edu.knoldus.util.Constant.{EMPTY, sCoverageXSD, sCoverage_Report}
import edu.knoldus.util.LoggerUtil._
import scala.util.{Failure, Success, Try}
import scala.xml.SAXParseException
import scala.xml.XML.loadFile

class FileProcessingActor extends Actor {

  val sCoverageDataParser: ActorRef = context.actorOf(SCoverageDataParser.props)
  val sCoverageParser: ActorRef = context.actorOf(SCoverageParser.props)

  private def getStream(xmlValidator: XMLValidator, xmlReport: String) = xmlValidator.getXMLInputStream(xmlReport)

  def getReportType(file: File): String = {
    Try{
      val xmlValidator = new XMLValidator
      val xmlContent = loadFile(file)
      val xmlReport = xmlContent.toString()
      if (xmlValidator.validateXML(getStream(xmlValidator, xmlReport), sCoverageXSD)) sCoverage_Report
      else EMPTY
    } match {
      case Success(reportType) => reportType
      case Failure(exception) => logger.error(s"Error occur in getReportType reason :$exception")
        throw new RuntimeException(exception)
    }

  }

  override def receive: Receive = {
    case ProcessFile(file) =>
      try {
        val reportType = getReportType(file)
        val xmlContent = loadFile(file)
        val parsers = List(sCoverageParser, sCoverageDataParser)
        parsers.foreach(fileProcessingActor => fileProcessingActor ! Parse(xmlContent, reportType))

      } catch {
        case ex: SAXParseException => logger.error(s" Exception occur due to :$ex")
      }
  }
}
