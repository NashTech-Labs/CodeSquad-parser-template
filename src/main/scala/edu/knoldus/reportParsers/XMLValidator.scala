package edu.knoldus.reportParsers

import java.io.{ByteArrayInputStream, File, InputStream}
import java.nio.charset.StandardCharsets
import javax.xml.XMLConstants
import javax.xml.transform.sax.SAXSource
import javax.xml.validation.SchemaFactory
import org.xml.sax.InputSource
import scala.util.control.NonFatal

class XMLValidator {

  def validateXML(xmlInputStream: InputStream, xsdPath: String): Boolean = {
    try {
      val sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
      val schema = sf.newSchema(new File(xsdPath))
      val validator = schema.newValidator()
      val source = new SAXSource(new InputSource(xmlInputStream))
      validator.validate(source)
      true
    } catch {
      case NonFatal(_) => false
    }
  }

  def getXMLInputStream(xml: String): ByteArrayInputStream = {
    new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8.name()))
  }
}
