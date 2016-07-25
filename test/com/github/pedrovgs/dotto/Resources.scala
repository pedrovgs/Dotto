package com.github.pedrovgs.dotto

trait Resources {

  def readTextResource(path: String): String = {
    val textStream = getClass.getResourceAsStream("/anyText.txt")
    scala.io.Source.fromInputStream(textStream).getLines().mkString
  }

  def readLowerCaseTextResource(path: String): String =  {
    readTextResource(path).toLowerCase()
  }
}
