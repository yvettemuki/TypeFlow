package com.github.notyy.typeflow.example1

object UserOutputEndpoint {
  def execute(output: String):String = {
    println(output)
    output
  }
}