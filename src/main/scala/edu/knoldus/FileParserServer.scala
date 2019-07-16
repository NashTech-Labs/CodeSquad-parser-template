package edu.knoldus

import java.io.File
import akka.actor.{ActorSystem, Props}
import edu.knoldus.model.ProcessFile

object FileParserServer extends App {

  val system = ActorSystem("Custom-CodeSquad-Report-parser")
  val fileProcessingActorRef = system.actorOf(Props[FileProcessingActor], "fileProcessingActor")
  val file = new File("./src/main/resources/xmlFiles/scoverage.xml")
  fileProcessingActorRef ! ProcessFile(file)
}
