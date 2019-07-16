package edu.knoldus.model

import java.io.File

final case class ProcessFile(file: File)

final case class ClassWiseCoverage(className: String, coverage: Double)

final case class PackageWiseCoverage(packageName: String, coverage: Double, classCoverage: Seq[ClassWiseCoverage])
