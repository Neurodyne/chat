
package chat

import  chisel3.iotesters
import  chisel3.iotesters.{PeekPokeTester}
import  org.scalatest.{Matchers, FlatSpec}

class HelloTest (c: Hello) extends PeekPokeTester (c) {}

class HelloSpec extends FlatSpec with Matchers {
  behavior of "Hello World"  

  it should "Pass basic assertion" in {
    chisel3.iotesters.Driver(() => new Hello) { c=> 
      new HelloTest(c)
    } should be (true)
  }
}


