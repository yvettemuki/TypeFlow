package com.github.notyy.typeflow.editor

import java.io.File

import org.scalatest.{FunSpec, Matchers}

import scala.io.Source
import scala.util.Success

class SaveToFileTest extends FunSpec with Matchers {
  describe("SaveToFile") {
    it("can save to given path, replace current content if file already exists") {
      val path = "localoutput/testSaveFile.txt"
      val file = new File(path)
      if (file.exists()) file.delete() else ()
      val content = "test content"
      SaveToFile.execute(Path(path), content) shouldBe Success(())
      Source.fromFile(path).mkString shouldBe content
    }
  }
}
