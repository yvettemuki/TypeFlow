package com.notyy.visualfp.example1

object UserInputInterpreter {
  sealed trait InterpreterResult
  case class UnknownCommand(input: String) extends InterpreterResult
  case object QuitCommand extends InterpreterResult
  case class CreateModelCommand(modelName: String) extends InterpreterResult

  sealed trait ModifyModelCommand extends InterpreterResult
  case class AddElementCommand(elementType: String, elementName: String, modelName: String) extends InterpreterResult
  case class ConnectElementCommand(from: String, to: String, modelName: String) extends InterpreterResult

  private val CreateModelPattern = """(.*) (.*)""".r
  private val AddElementPattern = """Add (.*) (.*) in (.*)""".r
  private val ConnectElementPattern = """Connect from (.*) to (.*) in (.*)""".r

  def execute(input: String): InterpreterResult = {
    input match {
      case ":q" => QuitCommand
      case (CreateModelPattern("CreateModel", modelName)) => CreateModelCommand(modelName)
      case (AddElementPattern(elementType, elementName, modelName)) => AddElementCommand(elementType, elementName, modelName)
      case (ConnectElementPattern(from, to, modelName)) => ConnectElementCommand(from, to, modelName)
      case _ => UnknownCommand(input)
    }
  }
}