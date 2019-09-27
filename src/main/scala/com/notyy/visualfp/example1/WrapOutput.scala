package com.notyy.visualfp.example1

import com.notyy.visualfp.example1.SaveNewModel.{ModelCreationSuccess, ModelSaveFailed}
import com.notyy.visualfp.example1.UpdateModel.{ModelUpdateFailed, ModelUpdateSuccess}
import com.notyy.visualfp.example1.UserInputIntepreter.{QuitCommand, UnknownCommand}

object WrapOutput {
  def execute(output: Object): String = {
    output match {
      case QuitCommand => "quit"
      case UnknownCommand(str) => s"unknown command '$str'"
      case ModelCreationSuccess(modelName) => s"model $modelName created successfully"
      case ModelSaveFailed(modelName,msg) => s"model $modelName save failed, error is: $msg"
      case ModelUpdateSuccess(modelName) => s"model $modelName updated successfully"
      case ModelUpdateFailed(modelName,msg) => s"model $modelName update failed, error is: $msg"
    }
  }
}