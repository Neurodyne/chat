package chat

import  chisel3._
import  chisel3.util.{Counter}

import  chisel3.iotesters.{PeekPokeTester}
import  org.scalatest.{Matchers, FlatSpec}

trait Event[T] {
  def out:T
}

class Property (ev:Bool, a:Bool, b:Bool) {
  def |-> = a && b
      
}

class RisingEdge () extends Module {
  val io = IO (new Bundle {
    val a  = Input (Bool())
    val b  = Input (Bool())
    
    val out = Output(Bool())  
  })
  
  val ev  = clock.asUInt
  val risingEdge  = ev & ~RegNext(ev)

  printf ("ev = %d risingEdge = %d \n", ev, risingEdge)

  when (risingEdge === 1.U) {
    printf ("Event high\n")
  }

  val res  = new Property ( risingEdge === 1.U, io.a, io.b)
 
  io.out := 0.U
  
}



class RisingEdgeTester (c:RisingEdge) extends PeekPokeTester(c) {
  val uut  = c.io
  
  reset()

  (0 until 5) foreach { tic =>
    poke(uut.a, 0) 
    poke(uut.b, 0) 
    step(1)
    
  }
}

class EventSpec extends FlatSpec with Matchers {
  behavior of "Event"

  it should "" in {
    chisel3.iotesters.Driver(() => new RisingEdge) { c=>
      new RisingEdgeTester(c)
    }  should be (true)
  }
}

  
