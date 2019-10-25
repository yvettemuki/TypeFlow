package com.github.notyy.typeflow.editor

import scala.io.Source

object TestReadUML extends App {
  val source = Source.fromFile("fixtures/typeflow_editor.puml")
  val rs = source.mkString
  source.close()
  val plantUML = PlantUML("multi_param", rs)
  PlantUML2Model.execute(plantUML)
}
