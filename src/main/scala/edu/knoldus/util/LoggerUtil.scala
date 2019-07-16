package edu.knoldus.util

import org.apache.log4j.Logger

trait LoggerUtil {

  val logger: Logger = Logger.getLogger(getClass.getName)

}

object LoggerUtil extends LoggerUtil
