package com.github.pedrovgs.dotto

import scala.io.Source

trait Resources {

  def readTextResource(path: String): String = {
    val textStream = getClass.getResourceAsStream(path)
    Source.fromInputStream(textStream).getLines().mkString
  }

  def readLowerCaseTextResource(path: String): String = {
    readTextResource(path).toLowerCase()
  }
}
