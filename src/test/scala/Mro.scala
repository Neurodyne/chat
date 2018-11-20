// Method resolution order ( MRO ) test 
// Boris V.Kuznetsov
// Nov 17 2018

package chat

import  chisel3._

import  chisel3.iotesters
import  chisel3.iotesters.{PeekPokeTester}
import  org.scalatest.{Matchers, FlatSpec}

abstract trait Common extends Bundle {
  val c0 = Bool()
  val c1 = Bool()

  // Redefine this in derived classes
  type Bus 
}

trait Foo extends Common {
  val aa = UInt
}

trait Bar extends Common {
  val bb = UInt
}

class A extends Bundle with Foo {
  type Bus = UInt 
}

class B extends Bundle with Bar /* with Foo with Bar with ... */ {
  type Bus = UInt
}

// A BlackBox that has the following I/Os
// c0, c1, aa
class MyBBoxA extends BlackBox {
 val io = IO(Input(new A))
}

// A BlackBox that has the following I/Os
// c0, c1, bb
class MyBBoxB extends BlackBox {
 val io = IO(Output(new B))
}

class Mro extends Module {
 val io = IO(new Bundle {})
 val mod1 = Module(new MyBBoxA)
 val mod2 = Module(new MyBBoxB)
 mod1.io <> mod2.io // <-- now compiles
 // Non-DRY code, want to avoid
 //mod1.io.c0 <> mod2.io.c0
 //mod1.io.c1 <> mod2.io.c1
}

class MroTest (c: Mro) extends PeekPokeTester (c) {}

class MroSpec extends FlatSpec with Matchers {
  behavior of "Mro Test"  

  it should "Pass basic assertion" in {
    //chisel3.iotesters.Driver(() => new Mro) { c=> 
    chisel3.iotesters.Driver.execute( Array("--backend-name", "verilator"), () => new Mro) { c=> 
      new MroTest(c)
    } should be (true)
  }
}


