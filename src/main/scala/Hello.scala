package chat

import  chisel3._

class Hello () extends Module {

  val io = IO (new Bundle {
    val a  = Input (Bool())
    val b  = Output (UInt(4.W))
  })
  
  assert (1 == 2, "Hello failed")    
  io.b := 0.U
}


