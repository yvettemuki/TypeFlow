package com.github.notyy.typeflow.editor

import org.scalatest.{FunSpec, Matchers}
import com.github.notyy.typeflow.domain.{Input, InputEndpoint, InputType, PureFunction, OutputEndpoint}

class PlantUML2ModelTest extends FunSpec with Matchers {
  describe("PlantUML2Model") {
    it("can transform from simple plantuml string to type flow model") {
      val puml = ReadFile.execute(Path("./fixtures/diff/newModel.puml")).get
      val model = PlantUML2Model.execute(PlantUML("newModel", puml))
      model.name shouldBe "newModel"
      model.definitions.size shouldBe 4
      model.definitions.map(_.name) should contain("NumInput")
      model.definitions.map(_.name) should contain("Add2")
      model.definitions.map(_.name) should contain("Multi3")
      model.definitions.map(_.name) should contain("AddAndPrint")
      val numInput: InputEndpoint = model.definitions.find(_.name == "NumInput").get.asInstanceOf[InputEndpoint]
      numInput.outputType.name shouldBe "NI::Integer"
      val add2: PureFunction = model.definitions.find(_.name == "Add2").get.asInstanceOf[PureFunction]
      add2.inputs.size shouldBe 1
      add2.inputs.head shouldBe Input(InputType("Integer"), 1)
      val addAndPrint: OutputEndpoint = model.definitions.find(_.name == "AddAndPrint").get.asInstanceOf[OutputEndpoint]
      addAndPrint.inputs.size shouldBe 2
      addAndPrint.inputs.head shouldBe Input(InputType("Integer"), 1)
      addAndPrint.inputs.last shouldBe Input(InputType("Integer"), 2)
    }
    it("can transform from complex plantuml string to type flow model") {
      val puml = ReadFile.execute(Path("./fixtures/diff/multi_param.puml")).get
      val model = PlantUML2Model.execute(PlantUML("multi_param", puml))
      model.name shouldBe "multi_param"
      model.definitions.size shouldBe 5
      model.definitions.map(_.name) should contain("NumInput")
      model.definitions.map(_.name) should contain("Add2")
      model.definitions.map(_.name) should contain("Multi3")
      model.definitions.map(_.name) should contain("AddAndPrint")
      model.definitions.map(_.name) should contain("PrintEP")

      val numInput: InputEndpoint = model.definitions.find(_.name == "NumInput").get.asInstanceOf[InputEndpoint]
      numInput.outputType.name shouldBe "NI::Integer"
      val add2: PureFunction = model.definitions.find(_.name == "Add2").get.asInstanceOf[PureFunction]
      add2.inputs.size shouldBe 1
      add2.inputs.head shouldBe Input(InputType("Integer"), 1)
      val addAndPrint: OutputEndpoint = model.definitions.find(_.name == "AddAndPrint").get.asInstanceOf[OutputEndpoint]
      addAndPrint.inputs.size shouldBe 2
      addAndPrint.inputs.head shouldBe Input(InputType("Integer"), 1)
      addAndPrint.inputs.last shouldBe Input(InputType("Integer"), 2)

      val flow = model.activeFlow.get
      flow.name shouldBe model.name
      flow.instances.size shouldBe 6
      flow.instances.exists(_.id == "1::Add2") shouldBe true
      flow.instances.exists(_.definition.name == "Add2") shouldBe true
      flow.connections.size shouldBe 6
      flow.connections.count(conn => conn.fromInstanceId == "NumInput") shouldBe 3
      flow.connections.count(conn => conn.toInstanceId == "AddAndPrint") shouldBe 2
      flow.connections.count(conn => conn.toInstanceId == "PrintEP") shouldBe 1
    }
    it("can transform from more complex plantuml string to type flow model") {
      val puml = ReadFile.execute(Path("./fixtures/typeflow_editor.puml")).get
      val model = PlantUML2Model.execute(PlantUML("typeflow_editor", puml))
      model.name shouldBe "typeflow_editor"
      model.definitions.size shouldBe 13
      model.definitions.map(_.name) should contain("UserInputEndpoint")
      model.definitions.map(_.name) should contain("UserInputInterpreter")
      model.definitions.map(_.name) should contain("WrapOutput")
      model.definitions.map(_.name) should contain("CommandLineOutputEndpoint")
      model.definitions.map(_.name) should contain("getModelPath1")
      model.definitions.map(_.name) should contain("command2ModelName1")
      model.definitions.map(_.name) should contain("getModelPath2")
      model.definitions.map(_.name) should contain("readFile1")
      model.definitions.map(_.name) should contain("json2Model1")
      model.definitions.map(_.name) should contain("command2ModelName2")
      model.definitions.map(_.name) should contain("getModelPath3")
      model.definitions.map(_.name) should contain("readFile2")
      model.definitions.map(_.name) should contain("json2Model2")

      val userInput: InputEndpoint = model.definitions.find(_.name == "UserInputEndpoint").get.asInstanceOf[InputEndpoint]
      userInput.outputType.name shouldBe "UIE::UserInput"
      val getModelPath2: PureFunction = model.definitions.find(_.name == "getModelPath2").get.asInstanceOf[PureFunction]
      getModelPath2.inputs.size shouldBe 1
      getModelPath2.inputs.head shouldBe Input(InputType("String"), 1)
      val readFile2: OutputEndpoint = model.definitions.find(_.name == "readFile2").get.asInstanceOf[OutputEndpoint]
      readFile2.inputs.size shouldBe 1
      readFile2.inputs.head shouldBe Input(InputType("Path"), 1)

      val flow = model.activeFlow.get
      flow.name shouldBe model.name
      flow.instances.size shouldBe
      flow.instances.exists(_.id == "UserInputEndpoint") shouldBe true
      flow.instances.exists(_.definition.name == "UserInputEndpoint") shouldBe true
      flow.connections.size shouldBe 12
      flow.connections.count(conn => conn.fromInstanceId == "UserInputInterpreter") shouldBe 8
      flow.connections.count(conn => conn.toInstanceId == "WrapOutput") shouldBe 4

    }
  }
}
