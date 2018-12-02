package chat

import chisel3._
import scala.language.higherKinds

class AbstractIO[C[_], A <: Data, B <: Data] ( inType:C[A], outType:C[B]) extends Module {

  val io = IO (new Bundle {
    val a  = Input (inType)
    val b  = Input (inType)
    val c  = Output (outType)
  })
 
  // stuff
}


