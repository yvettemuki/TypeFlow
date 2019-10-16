package com.github.notyy.typeflow.editor

import org.scalatest.{FunSpec, Matchers}

class Json2ModelTest extends FunSpec with Matchers {
  describe("Json2Model"){
    it("can transform from json string to Model"){
      val json = """{"name":"typeflow_editor","definitions":[{"jsonClass":"InputEndpoint","name":"UserInputEndpoint","outputType":{"name":"UserInput"}},{"jsonClass":"Function","name":"UserInputInterpreter","inputType":{"name":"UserInput"},"outputs":[{"outputType":{"name":"UnknownCommand"},"index":1},{"outputType":{"name":"QuitCommand"},"index":2}]},{"jsonClass":"Function","name":"WrapOutput","inputType":{"name":"java.lang.Object"},"outputs":[{"outputType":{"name":"WrappedOutput"},"index":1}]},{"jsonClass":"OutputEndpoint","name":"CommandLineOutputEndpoint","inputType":{"name":"WrappedOutput"},"outputType":{"name":"Unit"},"errorOutput":[]}],"flows":[{"name":"minimalFlow","instances":[{"id":"UserInputEndpoint","definition":{"jsonClass":"InputEndpoint","name":"UserInputEndpoint","outputType":{"name":"UserInput"}}},{"id":"UserInputInterpreter","definition":{"jsonClass":"Function","name":"UserInputInterpreter","inputType":{"name":"UserInput"},"outputs":[{"outputType":{"name":"UnknownCommand"},"index":1},{"outputType":{"name":"QuitCommand"},"index":2}]}},{"id":"WrapOutput","definition":{"jsonClass":"Function","name":"WrapOutput","inputType":{"name":"java.lang.Object"},"outputs":[{"outputType":{"name":"WrappedOutput"},"index":1}]}},{"id":"CommandLineOutputEndpoint","definition":{"jsonClass":"OutputEndpoint","name":"CommandLineOutputEndpoint","inputType":{"name":"WrappedOutput"},"outputType":{"name":"Unit"},"errorOutput":[]}}],"connections":[{"fromInstanceId":"UserInputEndpoint","outputIndex":1,"toInstanceId":"UserInputInterpreter"},{"fromInstanceId":"UserInputInterpreter","outputIndex":1,"toInstanceId":"WrapOutput"},{"fromInstanceId":"UserInputInterpreter","outputIndex":2,"toInstanceId":"WrapOutput"},{"fromInstanceId":"WrapOutput","outputIndex":1,"toInstanceId":"CommandLineOutputEndpoint"}]}],"activeFlow":{"name":"minimalFlow","instances":[{"id":"UserInputEndpoint","definition":{"jsonClass":"InputEndpoint","name":"UserInputEndpoint","outputType":{"name":"UserInput"}}},{"id":"UserInputInterpreter","definition":{"jsonClass":"Function","name":"UserInputInterpreter","inputType":{"name":"UserInput"},"outputs":[{"outputType":{"name":"UnknownCommand"},"index":1},{"outputType":{"name":"QuitCommand"},"index":2}]}},{"id":"WrapOutput","definition":{"jsonClass":"Function","name":"WrapOutput","inputType":{"name":"java.lang.Object"},"outputs":[{"outputType":{"name":"WrappedOutput"},"index":1}]}},{"id":"CommandLineOutputEndpoint","definition":{"jsonClass":"OutputEndpoint","name":"CommandLineOutputEndpoint","inputType":{"name":"WrappedOutput"},"outputType":{"name":"Unit"},"errorOutput":[]}}],"connections":[{"fromInstanceId":"UserInputEndpoint","outputIndex":1,"toInstanceId":"UserInputInterpreter"},{"fromInstanceId":"UserInputInterpreter","outputIndex":1,"toInstanceId":"WrapOutput"},{"fromInstanceId":"UserInputInterpreter","outputIndex":2,"toInstanceId":"WrapOutput"},{"fromInstanceId":"WrapOutput","outputIndex":1,"toInstanceId":"CommandLineOutputEndpoint"}]}}"""
      val model = Json2Model.execute(json)
      model.name shouldBe "typeflow_editor"
      model.definitions.size shouldBe 4
      model.activeFlow.get.name shouldBe "minimalFlow"
      model.activeFlow.get.connections.size shouldBe 4
    }
  }
}
